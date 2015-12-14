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
	private Path proposedFlyingPath;
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
	
	

	void addGold(float amount) { gold += amount; }
	float getGold() { return gold; }
	float getHealth() { return health; }
	void reduceHealth(float amount) { health -= amount; }
	void removeGold(float amount) { gold -= amount; }
	
	
	boolean canSiphon(Tower from, Tower to) {
		//TODO:
	}
	
	boolean canBuild(TowerType type, int x, int y) {
		/* If it isn't a base type, we don't have enough gold, 
		 * or the desired position isn't in the map, return false */
		if (!type.isBaseType() || type.getCost() > gold || 
				map.isOutside(x, y) || x + type.getWidth() >= map.getWidth() ||
				y + type.getHeight() >= map.getHeight())  {
			proposedGroundPath = null;
			proposedFlyingPath = null;
			return false;
		}
		
		int width = type.getWidth();
		int height = type.getHeight();
		boolean onGround = type.isOnGround();
		boolean inAir = type.isInAir();
		
		Tile currentTile;
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				currentTile = map.getTile(j, i);
				if ((onGround && !currentTile.isBuildable(true)) ||
						(inAir && !currentTile.isBuildable(false))) {
					proposedGroundPath = null;
					proposedFlyingPath = null;
					return false;
				}
			}
		}
		
		proposePath(x, y, width, height);
		if (onGround) {
			if (inAir) {
				return proposedGroundPath != null && proposedFlyingPath != null;
			} else {
				return proposedGroundPath != null;
			}
		} else {
			if (inAir) {
				return proposedFlyingPath != null;
			} else {
				return true;
			}
		}
		
	}
	
	
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
		boolean oldGround[][] = new boolean[height][width];
		boolean oldFlying[][] = new boolean[height][width];

		Tile currentTile;	
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				currentTile = map.getTile(y + j, x + i);
				oldGround[j][i] = currentTile.groundTraversable;
				oldFlying[j][i] = currentTile.airTraversable;
				currentTile.groundTraversable = false;
				currentTile.airTraversable = false;
			}
		}

		VertexGraph vg = new VertexGraph();
		vg = PathFinder.mapToVertexGraph(vg, map);
		proposedGroundPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, true);
		proposedFlyingPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, false);
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				currentTile = map.getTile(y + j, x + i);
				currentTile.groundTraversable = oldGround[j][i];
				currentTile.airTraversable = oldFlying[j][i];
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
