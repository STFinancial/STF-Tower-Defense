package levels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import creeps.Creep;
import creeps.CreepManager;
import players.Player;
import projectiles.ProjectileManager;
import towers.*;
import utilities.Circle;

/*
 * Executes main game logic loop
 */
public class Level {
	private Map map;
	private Player player;

	//TODO: This shit drives me nuts, fix it.
	private float gold = 1000000;
	private int health = 100;

	//Managers
	private ProjectileManager projManager;
	private TowerManager towerManager;
	private CreepManager creepManager;
	
	//Currently loaded/active units
	private ArrayList<EffectPatch> effectPatches = new ArrayList<EffectPatch>();

	private Path groundPath;
	private Path flyingPath;
	private Path proposedGroundPath;
	//This will change when we create and destroy new terrain
	//TODO: I think having some of these as hash sets is sub-optimal.
	private HashSet<Circle> earthTiles = new HashSet<Circle>();
	private HashSet<Creep> groundCreepAdjacentToEarth = new HashSet<Creep>();
	private HashSet<Creep> allCreepAdjacentToEarth = new HashSet<Creep>();
	
	
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
	
	
	
	

	void startRound(int roundNum) {
		effectPatches.clear();
		groundCreepAdjacentToEarth.clear();
		allCreepAdjacentToEarth.clear();
	}
	
	

	public void addGold(float amount) { gold += amount; }
	public float getGold() { return gold; }
	float getHealth() { return health; }
	void reduceHealth(float amount) { health -= amount; }
	public void removeGold(float amount) { gold -= amount; }
	
	
	public boolean canBuild(TowerType type, int x, int y) {
		if (!type.isBaseType() || type.getCost() > gold) {
			return false;
		}
		int width = type.getWidth();
		int height = type.getHeight();
		if (map.isOutside(x, y) || x + width >= map.getWidth() || y + height >= map.getHeight()) {
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
	
	
	//TODO: Need to return the "can build tower" method in here

	public boolean canBuyTower(TowerType type) {
		return gold >= type.getCost();
	}


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
	
	void updateEffectPatches() {
		Iterator<EffectPatch> it = effectPatches.iterator();
		while (it.hasNext()) {
			EffectPatch e = it.next();
			e.update();
			if (e.isDone()) {
				it.remove();
			}
		}
	}
	
	void updateCreepAdjacentToEarth() {
		if (towerManager.hasEarthEarth()) {
			groundCreepAdjacentToEarth.clear();
			allCreepAdjacentToEarth.clear();
			for (Circle c: earthTiles) {
				groundCreepAdjacentToEarth.addAll(creepManager.getCreepInRange(c, false));
				allCreepAdjacentToEarth.addAll(creepManager.getCreepInRange(c, true));
			}
		}
	}
}
