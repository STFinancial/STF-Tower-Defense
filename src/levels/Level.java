package levels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import maps.Map;
import maps.TileType;
import maps.Vertex;
import maps.VertexGraph;
import creeps.Creep;
import creeps.CreepType;
import creeps.Wave;
import creeps.DamageType;
import maps.Tile;
import players.Player;
import projectiles.Projectile;
import towers.*;
import utilities.Circle;
import utilities.CreepWaveGenerator;
import utilities.MapGenerator;
import utilities.PathFinder;

/*
 * Executes main game logic loop
 */
public class Level {
	private final Map map;
	private final Player player;
	private final ArrayList<Wave> creepWaves;

	private int round = 0; //Each round represents a specific creepwave (Or waves for multiple entrance)
	private int tick = 0; //Specific game logic step, smallest possible difference in game states time wise
	private int currentTowerID = 0;
	
	private int gold = 500;
	private int health = 100;
	private int nextSpawnTick = -1;
	private Wave currentWave;
	private boolean roundInProgress = false;

	//Currently loaded/active units
	private ArrayList<Tower> towers = new ArrayList<Tower>();
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private ArrayList<EffectPatch> effectPatches = new ArrayList<EffectPatch>();
	private ArrayList<Creep> creeps = new ArrayList<Creep>();

	private Path groundPath, airPath, proposedGroundPath;
	//This will change when we create and destroy new terrain
	private HashSet<Circle> earthTiles = new HashSet<Circle>();
	private HashSet<Creep> creepAdjacentToEarth = new HashSet<Creep>();
	private boolean hasEarthEarth = false;

	private ArrayList<GameEvent> events = new ArrayList<GameEvent>();
	//Temp Variables for readability.. wat
	Creep c;
	int i;
	private boolean creepLeft;

	public Level() {
		this(new Player(), (new MapGenerator()).generateMap(), (new CreepWaveGenerator()).generateCreepWaves());
	}

	public Level(Player player, Map map, ArrayList<Wave> creepWaves) {
		this.player = player;
		this.map = map;
		for (int y = 0; y < map.height; y++) {
			for (int x = 0; x < map.width; x++) {
				if (map.getTileType(y, x) == TileType.EARTH) { 
					earthTiles.add(new Circle(x + 0.5f, y + 0.5f, 1.5f));
				}
			}
		}
		this.creepWaves = creepWaves;
		updatePath();
	}

	//Can be called from App
	public void startRound() {
		if (!roundInProgress) {
			roundInProgress = true;
			creepLeft = true;
			currentWave = creepWaves.get(round);
			round++;
			System.out.println("Starting round " + round);
			tick = 0;
			nextSpawnTick = currentWave.getDelayForNextCreep();
		}
	}

	public void gameTick() {
		//Check for new spawns from creep wave;
		if (tick == nextSpawnTick) {
			spawnCreeps(currentWave.getNextCreeps());
			nextSpawnTick = tick + currentWave.getDelayForNextCreep();
			if (nextSpawnTick < tick) {
				creepLeft = false;
			}
		}

		for (i = 0; i < creeps.size(); i++) {
			c = creeps.get(i);
			c.update();
			if (c.currentVertex.equals(groundPath.getFinish())) {
				escapeCreep(c);
				creeps.remove(i);
				i--;
				if (creeps.size() == 0 && creepLeft == false) {
					roundInProgress = false;
				}
			}
		}
		if (hasEarthEarth) {
			//TODO: Since we loop through all the creeps here we could assign everything in one loop if we do it well enough.
			updateGroundCreepAdjacentToEarth();
		}
		
		for (i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			p.update();
			if (p.isDone()) {
				detonateProjectile(p);
				projectiles.remove(i);
				i--;
			}
		}

		for (i = 0; i < creeps.size(); i++) {
			c = creeps.get(i);
			if (c.isDead()) {
				killCreep(c);
				creeps.remove(c);
				i--;
				if (creeps.size() == 0 && creepLeft == false) {
					roundInProgress = false;
				}
			}
		}

		for (Tower t : towers) {
			t.update();
		}
		
		Iterator<EffectPatch> i = effectPatches.iterator();
		while (i.hasNext()) {
			EffectPatch e = i.next();
			if (e.update() == -1) {
				i.remove();
			}
		}

		tick++;
	}

	private void detonateProjectile(Projectile p) {
		p.detonate();
		newEvent(GameEventType.PROJECTILE_EXPIRED, p);
	}

	private void spawnCreeps(HashSet<Creep> creepsToSpawn) {
		for (Creep c : creepsToSpawn) {

			if (c.is(CreepType.FLYING)) {
				c.setPath(airPath);
			} else {
				c.setPath(groundPath);
			}
			creeps.add(c);
			newEvent(GameEventType.CREEP_SPAWNED, c);
		}
	}

	private void escapeCreep(Creep c) {
		//TODO
		newEvent(GameEventType.CREEP_ESCAPED, c);
		health -= c.getHealthCost();
		//Check if we lose?

	}

	private void killCreep(Creep c) {
		// TODO Auto-generated method stub
		ArrayList<Creep> deathRattleChildren;
		deathRattleChildren = c.death();
		if (deathRattleChildren != null) {
			for (Creep drc : deathRattleChildren) {
				drc.setLocation(c);
				creeps.add(drc);
			}
		}

		newEvent(GameEventType.CREEP_KILLED, c);

		gold += c.goldValue;

	}
	
	public boolean canBuyTower(TowerType type) {
		return gold >= type.getCost();
	}

	//GUI should call this method to build towers
	public Tower buyTower(TowerType type, int y, int x) {
		Tower t = buildTower(type, y, x);
		//TODO this needs to be affected by global talents
		gold -= type.getCost();
		return t;
	}

	private Tower buildTower(TowerType type, int y, int x) {
		Tower t;
		Tile tile = map.getTile(y, x);
		t = TowerFactory.generateTower(this, tile, type, currentTowerID++);
		constructTower(t);
		updatePath();
		t.updateTowerChain();
		newEvent(GameEventType.TOWER_CREATED, t);
		return t; //TODO too lazy to implement event system so i can grab this relevant information, so returning for now
	}
	
	private void constructTower(Tower t) {
		if (t.type == TowerType.EARTH_EARTH) {
			hasEarthEarth = true;
		}
		towers.add(t);
		for (int i = 0; i < t.width; i++) {
			for (int j = 0; j < t.height; j++) {
				map.getTile(t.y + j, t.x + i).addTower(t);
			}
		}
	}

	public Tower unsiphonTower(Tower source, Tower destination) {
		source.siphoningTo.remove(destination);
		Tower newDest = TowerFactory.generateTower(this, destination.topLeft, destination.baseAttributeList.downgradeType, destination.towerID);
		newDest.upgradeTracks = destination.upgradeTracks;
		newDest.siphoningTo = destination.siphoningTo;
		destroyTower(destination);
		newEvent(GameEventType.TOWER_DESTROYED, destination);
		constructTower(newDest);
		newDest.updateTowerChain();
		source.updateTowerChain();
		newEvent(GameEventType.TOWER_CREATED, newDest);
		return newDest;
	}

	public Tower siphonTower(Tower source, Tower destination) {
		source.siphoningTo.add(destination);
		Tower newDest = TowerFactory.generateTower(this, destination.topLeft, TowerType.getUpgrade(source.type, destination.type), destination.towerID);
		newDest.upgradeTracks = destination.upgradeTracks;
		newDest.siphoningFrom = source;
		newDest.siphoningTo = destination.siphoningTo;
		destroyTower(destination);
		newEvent(GameEventType.TOWER_DESTROYED, destination);
		constructTower(newDest);
		newDest.updateTowerChain();
		source.updateTowerChain();
		newEvent(GameEventType.TOWER_CREATED, newDest);
		return newDest;
	}

	//Can be called from App
	public void upgradeTower(Tower t, int track) {
		t.upgrade(track);
	}

//	public boolean canSellTower(Tower t) {
//		return t.siphoningTo == null;
//	}
	
	//GUI should call this method
	//TODO this method needs to be more sophistocated if we've bought upgrades
	public void sellTower(Tower t) {
		if (t.siphoningFrom == null) {
			gold += t.cost * .75f;
		} else {
			gold += t.cost * .5f;
		}
		razeTower(t);
	}

	private void razeTower(Tower t) {
		destroyTower(t);
		updatePath();
		newEvent(GameEventType.TOWER_DESTROYED, t);
	}
	
	private void destroyTower(Tower t) {
		towers.remove(t);
		hasEarthEarth = false;
		for (Tower tow: towers) {
			if (tow.type == TowerType.EARTH_EARTH) {
				hasEarthEarth = true;
			}
		}
		for (int i = 0; i < t.width; i++) {
			for (int j = 0; j < t.height; j++) {
				map.getTile(t.y + j, t.x + i).removeTower();
			}
		}
	}

	public void updatePath() {
		VertexGraph vg = new VertexGraph();
		vg = PathFinder.mapToVertexGraph(vg, map);
		groundPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, true);
		airPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, false);
	}

	public void proposePath(int x, int y, int width, int height) {
		boolean old[][] = new boolean[height][width];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				old[j][i] = map.getTile(y + j, x + i).groundTraversable;
				map.getTile(y + j, x + i).groundTraversable = false;
			}
		}

		VertexGraph vg = new VertexGraph();
		vg = PathFinder.mapToVertexGraph(vg, map);
		proposedGroundPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, true);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map.getTile(y + j, x + i).groundTraversable = old[j][i];
			}
		}
	}

	public ArrayList<Creep> getCreeps() { return creeps; }
	public Map getMap() { return map; } //TODO: Not sure if I want to offer access to this... I would rather have delegation methods
	
	HashSet<Creep> getGroundCreepAdjacentToEarth() {
		return creepAdjacentToEarth;
	}
	
	private void updateGroundCreepAdjacentToEarth() {
		creepAdjacentToEarth.clear();
		for (Circle c: earthTiles) {
			creepAdjacentToEarth.addAll(ProjectileGuider.getInstance().getCreepInRange(c, false));
		}
	}

	public void addProjectile(Projectile p) {
		projectiles.add(p);
		newEvent(GameEventType.PROJECTILE_FIRED, p);
	}

	public boolean canBuild(TowerType type, int y, int x) {
		if (type.getCost() > gold) {
			return false;
		}
		int width = type.getWidth();
		int height = type.getHeight();
		if (x < 0 || y < 0 || x + width >= map.width || y + height >= map.height) {
			proposedGroundPath = null;
			return false;
		}
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				if (!map.getTile(j, i).buildable) {
					proposedGroundPath = null;
					return false;
				}
			}
		}
		proposePath(x, y, width, height);
		return proposedGroundPath != null;
	}

	private void newEvent(GameEventType t, Object o) {
		events.add(new GameEvent(t, o, round, tick));
	}

	public GameEvent getEvent() {
		if (events.size() > 0) {
			return events.remove(0);
		}
		return null;
	}

	public void addEffectPatch(EffectPatch effectPatch) {
		effectPatches.add(effectPatch);
	}

}
