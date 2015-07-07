package levels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import maps.Map;
import maps.Vertex;
import maps.VertexGraph;
import creeps.Creep;
import creeps.CreepType;
import creeps.Wave;
import creeps.DamageType;
import maps.Tile;
import players.Player;
import projectiles.Projectile;
import projectiles.TargetingType;
import towers.*;
import utilities.Circle;
import utilities.CreepWaveGenerator;
import utilities.MapGenerator;
import utilities.PathFinder;
import utilities.TowerHelper;

/*
 * Executes main game logic loop
 */
public class Level {

	public final Map map;
	private final Player player;
	private final ArrayList<Wave> creepWaves;

	public int round = 0; //Each round represents a specific creepwave (Or waves for multiple entrance)
	public int tick = 0; //Specific game logic step, smallest possible difference in game states time wise

	public int gold = 500;
	public int health = 100;
	int nextSpawnTick = -1;
	public Wave currentWave;
	public boolean roundInProgress = false;

	//Currently loaded/active units
	public ArrayList<Tower> towers = new ArrayList<Tower>();
	public TowerHelper towerHelper = new TowerHelper();
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public ArrayList<Creep> creeps = new ArrayList<Creep>();

	public Path groundPath, airPath, proposedGroundPath;

	public ArrayList<GameEvent> events = new ArrayList<GameEvent>();
	//Temp Variables for readability
	Creep c;
	int i;
	private boolean creepLeft;

	public Level() {
		this(new Player(), (new MapGenerator()).generateMap(), (new CreepWaveGenerator()).generateCreepWaves());
	}

	public Level(Player player, Map map, ArrayList<Wave> creepWaves) {
		this.player = player;
		this.map = map;
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

		tick++;
	}

	private void detonateProjectile(Projectile p) {
		//Check for aoe and complicated shit
		if (p.targetingType == TargetingType.CREEP) {
			//Targeted a specific minion
			p.applyEffects(p.targetCreep);
			p.applySplashEffects(getCreepInRange(p, p.splashRadius));
			p.parent.attackCoolDown += p.targetCreep.disruptorAmount;
		} else if (p.targetingType == TargetingType.AREA) {
			//Targeted an area
			//TODO: currently applies normal and splash effects, is this wanted?
			HashSet<Creep> creepsInRange = getCreepInRange(p.parent.placeToTarget);
			for (Creep c: creepsInRange) {
				p.applyEffects(c);
			}
			p.applySplashEffects(creepsInRange);
		}
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
		health -= c.healthCost;
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
		gold -= type.getCost();
		return t;
	}

	private Tower buildTower(TowerType type, int y, int x) {
		Tower t;
		Tile tile = map.getTile(y, x);
		t = new Tower(this, tile, type);
		towers.add(t);
		for (int i = 0; i < t.width; i++) {
			for (int j = 0; j < t.height; j++) {
				map.getTile(t.y + j, t.x + i).addTower(t);
			}
		}
		updatePath();
		t.adjustTowerValues();
		newEvent(GameEventType.TOWER_CREATED, t);
		return t; //TODO too lazy to implement event system so i can grab this relevant information, so returning for now
	}

	//TODO need an event so that the GUI knows it can change the tower graphic
	//But I think it can just take the type before and after and change it.
	public Tower unsiphonTower(Tower t) {
		Tower sf = t.siphoningFrom;
		sf.siphoningFrom.siphoningTo = null;
		sf.siphoningFrom = null;
		sf.adjustTowerValues();
		t.type = t.baseAttributeList.downgradeType;
		//TODO preserve upgrade levels and do damage calculations
		boolean[][][] upgradeTracks = t.upgradeTracks;
		razeTower(t);
		t = buildTower(t.type, t.y, t.x);
		t.upgradeTracks = upgradeTracks;
		
		return t;
	}

	public void siphonTower(Tower siphonTo, Tower siphonFrom) {
		TowerType newType = null;
		if (siphonFrom.type.isBaseType()) {
			newType = TowerType.getUpgrade(siphonTo.type, siphonFrom.type);
		} else {
			newType = TowerType.getUpgrade(siphonTo.type, siphonFrom.type.getAttributeList().downgradeType);
		}
		//TODO preserve upgrade levels and do damage calculations
		boolean[][][] upgradeTracks = siphonTo.upgradeTracks;
		razeTower(siphonTo);
		Tower t = buildTower(newType, siphonTo.y, siphonTo.x);
		t.upgradeTracks = upgradeTracks;
		t.siphoningFrom = siphonFrom;
		t.siphoningTo = null;
		siphonFrom.siphoningTo = t;
		t.adjustTowerValues();
	}

	//Can be called from App
	public void upgradeTower(Tower t, int index) {
		t.upgrade(index);
	}

	public boolean canSellTower(Tower t) {
		return t.siphoningTo == null;
	}
	
	//GUI should call this method
	public void sellTower(Tower t) {
		if (t.siphoningFrom == null) {
			gold += t.cost * .75f;
		} else {
			gold += t.cost * .5f;
		}
		razeTower(t);
	}

	private void razeTower(Tower t) {
		towers.remove(t);
		for (int i = 0; i < t.width; i++) {
			for (int j = 0; j < t.height; j++) {
				map.getTile(t.y + j, t.x + i).removeTower();
			}
		}
		updatePath();

		newEvent(GameEventType.TOWER_DESTROYED, t);
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

	public Creep findTargetCreep(Tower tower) {
		Creep toTarget = null;
		ArrayList<Creep> inRange = new ArrayList<Creep>();
		for (Creep c : creeps) {
			if (c.hitBox.intersects(tower.targetArea)) {
				inRange.add(c);
			}
		}
		//System.out.println("Saw : " + inRange.size());
		switch (tower.targetingType) {
//		case AREA:
//			break;
		case FIRST:
			int max = -1;
			for (Creep c : inRange) {
				if (c.pathIndex > max) {
					toTarget = c;
					max = c.pathIndex;
				}
			}
			break;
		case HIGHEST_HEALTH:
			break;
		case LAST:
			break;
		default:
			break;

		}
		return toTarget;
	}

	public HashSet<Creep> getCreepInRange(Projectile p, float range) {
		HashSet<Creep> inRange = new HashSet<Creep>();
		Circle splash = new Circle(p.x, p.y, range);
		for (Creep c : creeps) {
			if (c.hitBox.intersects(splash)) {
				inRange.add(c);
			}
		}
		return inRange;
	}
	
	public HashSet<Creep> getCreepInRange(Circle area) {
		HashSet<Creep> inRange = new HashSet<Creep>();
		for (Creep c: creeps) {
			if (c.hitBox.intersects(area)) {
				inRange.add(c);
			}
		}
		return inRange;
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

}
