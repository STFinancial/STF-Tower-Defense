package levels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import creeps.Creep;
import creeps.CreepManager;
import creeps.CreepType;
import creeps.Wave;
import game.Game;
import game.GameEvent;
import game.GameEventType;
import creeps.DamageType;
import players.Player;
import projectiles.Projectile;
import projectiles.ProjectileManager;
import towers.*;
import utilities.Circle;
import utilities.CreepWaveGenerator;

/*
 * Executes main game logic loop
 */
public class Level {
	private Map map;
	private Player player;

	//TODO: This shit drives me nuts, fix it.
	private float gold = 10000;
	private int health = 100;

	//Managers
	private ProjectileManager projManager;
	private TowerManager towerManager;
	private CreepManager creepManager;
	
	//Currently loaded/active units
	private ArrayList<EffectPatch> effectPatches = new ArrayList<EffectPatch>();

	private Path groundPath, flyingPath, proposedGroundPath;
	//This will change when we create and destroy new terrain
	//TODO: I think having some of these as hash sets is sub-optimal.
	private HashSet<Circle> earthTiles = new HashSet<Circle>();
	private HashSet<Creep> groundCreepAdjacentToEarth = new HashSet<Creep>();
	private HashSet<Creep> allCreepAdjacentToEarth = new HashSet<Creep>();

	private ArrayList<GameEvent> events = new ArrayList<GameEvent>();
	
	Level(Player player, Map map) {
		this.player = player;
		this.map = map;
		this.projManager = ProjectileManager.getInstance();
		this.towerManager = TowerManager.getInstance();
		this.creepManager = CreepManager.getInstance();
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				if (map.getTileType(y, x) == TileType.EARTH) { 
					earthTiles.add(new Circle(x + 0.5f, y + 0.5f, 1.5f));
				}
			}
		}
		updatePath();
	}
	
	void reduceHealth(float amount) {
		health -= amount;
	}
	
	float getHealth() { return health; }

	//Can be called from App
	void startRound() {
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

		
		if (towerManager.hasEarthEarth()) {
			//TODO: Since we loop through all the creeps here we could assign everything in one loop if we do it well enough.
			updateGroundCreepAdjacentToEarth();
		}
		projManager.update();

		

		towerManager.update();
		
		Iterator<EffectPatch> it = effectPatches.iterator();
		while (it.hasNext()) {
			EffectPatch e = it.next();
			if (e.update() == -1) {
				it.remove();
			}
		}
	}

	private void detonateProjectile(Projectile p) {
		p.detonate();
		newEvent(GameEventType.PROJECTILE_EXPIRED, p);
	}

	public void addGold(float amount) { gold += amount; }
	public float getGold() { return gold; }
	public void removeGold(float amount) { gold -= amount; }
	
	
	
	public boolean canBuyTower(TowerType type) {
		return gold >= type.getCost();
	}

	//GUI should call this method to build towers
	public Tower buyTower(TowerType type, int y, int x) {
		Tile tile = map.getTile(y, x);
		gold -= type.getCost(); //TODO: In this method in tower should be affected by global talents
		Tower t = towerManager.constructTower(tile, type);
		updatePath();
		towerManager.updateTowerChain(t);
		newEvent(GameEventType.TOWER_CREATED, t);
		return t;
	}
	
	

	public Tower unsiphonTower(Tower destination, boolean refund) {
		
	}

	//Can be called from App
	public void upgradeTower(Tower t, int path) {
		towerManager.upgrade(t, path);
	}

//	public boolean canSellTower(Tower t) {
//		return t.siphoningTo == null;
//	}

	public void updatePath() {
		VertexGraph vg = new VertexGraph();
		vg = PathFinder.mapToVertexGraph(vg, map);
		groundPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, true);
		flyingPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, false);
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

	public Map getMap() { return map; } //TODO: Not sure if I want to offer access to this... I would rather have delegation methods
	
	public HashSet<Creep> getCreepAdjacentToEarth(boolean isFlying) {
		if (isFlying) {
			return allCreepAdjacentToEarth;
		} else {
			return groundCreepAdjacentToEarth;
		}
	}
	
	private void updateGroundCreepAdjacentToEarth() {
		groundCreepAdjacentToEarth.clear();
		allCreepAdjacentToEarth.clear();
		for (Circle c: earthTiles) {
			groundCreepAdjacentToEarth.addAll(ProjectileManager.getInstance().getCreepInRange(c, false));
			allCreepAdjacentToEarth.addAll(ProjectileManager.getInstance().getCreepInRange(c, true));
		}
	}

	public boolean canBuild(TowerType type, int y, int x) {
		if (!type.isBaseType() || type.getCost() > gold) {
			return false;
		}
		int width = type.getWidth();
		int height = type.getHeight();
		if (x < 0 || y < 0 || x + width >= map.getWidth() || y + height >= map.getHeight()) {
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

	public GameEvent getEvent() {
		if (events.size() > 0) {
			return events.remove(0);
		}
		return null;
	}

	public void addEffectPatch(EffectPatch effectPatch) {
		effectPatches.add(effectPatch);
	}

	public int getVertexBelow(Vertex currentVertex) {
		int y = currentVertex.y;
		for (int i = 0; i < groundPath.getLength(); i++) {
			if (groundPath.getVertex(i).y == y) {
				return i;
			}
		}
		return 0;
	}

	public Path getPath(boolean isFlying) {
		return (isFlying ? flyingPath : groundPath);
	}

	public void createProjectileDetonationEvents(LinkedList<Projectile> detonatedProj) {
		for (Projectile p: detonatedProj) {
			newEvent(GameEventType.PROJECTILE_DETONATED, p);
		}
	}
}
