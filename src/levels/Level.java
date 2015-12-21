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
import utilities.GameConstants;
import utilities.GameConstants.UpgradePathType;

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
		/* The barriers to siphoning is that we have enough gold, and that we don't create a block in the path */
		if (!towerManager.getType(to).isBaseType()) {
			return false;
		}
		//TODO: What about discounts?
		if (gold < GameConstants.SIPHON_BASE_COST * (float) Math.pow(GameConstants.SIPHON_CHAIN_COST_MULTIPLIER, towerManager.getNewSiphonChainLength(from, to))) {
			return false;
		}
		
		
		/* Since it's guaranteed a base type to a non-upgraded type, we don't need to worry about upgrades at all */
		TowerType newType = TowerType.getUpgrade(towerManager.getType(from), towerManager.getType(to));
		Tile topLeft = towerManager.getTopLeftTile(to);
		
		/* If it intersects the currentPath, we need to propose a new path */
		if (intersectsPath(topLeft.x, topLeft.y, newType.getWidth(), newType.getHeight(), false) || intersectsPath(topLeft.x, topLeft.y, newType.getWidth(), newType.getHeight(), true)) {
			proposePath(topLeft.x, topLeft.y, newType.getWidth(), newType.getHeight());
			/* If we're on the ground and the ground path is null, or if we're in the air and the air path is null, we can't siphon */
			if (newType.isOnGround() && proposedGroundPath == null || newType.isInAir() && proposedFlyingPath == null) {
				return false;
			} else {
				return true;
			}
		} else {
			/* If it doesn't intersect the existing path then we can siphon */
			return true;
		}
	}
	
	/**
	 * This function checks whether we are capable of building a {@link Tower} of the specified
	 * {@link TowerType type} at the provided location, which would be the top-left {@link Tile} of
	 * where the Tower would be.
	 * @param type - This is the TowerType of the Tower we want to build. 
	 * In general, Towers are all the same size, so this does not affect whether a path is possible,
	 * but it does change the gold value, and so we check to see if we have enough gold to build this type.
	 * Furthermore, types that are not base will return false when passed to this function, as those are not
	 * technically "built".
	 * @param x - The x value of the Tile that will be the upper left tile of this Tower.
	 * @param y - The y value of the Tile that will be the upper left tile of this Tower.
	 * @return True if building a Tower of this type with the upper left tile being in the specified location will not
	 * result in no possible air path or no possible ground path from the start to the finish, the type is a base type, and
	 * we have enough gold to build the Tower. Will return false if no such path exists, if the type is not a base type (as these
	 * cannot be built directly), or if we don't have enough gold to purchase the Tower.
	 */
	boolean canBuild(TowerType type, int x, int y) {
		/* If it isn't a base type, we don't have enough gold, 
		 * or the desired position isn't in the map, return false */
		//TODO: Don't need to do all this if it's not intersecting any of the paths to begin with.
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
	
	/**
	 * This function checks whether we can {@link Upgrade} the specified {@link Tower}
	 * along the specified {@link UpgradePathType upgradePath}.
	 * @param t - The Tower we are attempting to Upgrade.
	 * @param upgradePath - The Upgrade path along which we want to Upgrade. This is currently
	 * UPPER_PATH and LOWER_PATH.
	 * @return True if the Tower can be upgraded and we have enough gold, false if not. Future Upgrades
	 * may change the size of the Tower or whether it takes up air or ground space. This function will need
	 * to change accordingly.
	 */
	boolean canUpgrade(Tower t, UpgradePathType upgradePath) {
		//TODO: Need a way to "mock" apply upgrades to check if they break something.
		if (!towerManager.getType(t).isBaseType()) {
			/* Need to check size of Tower post Upgrade to see if it can fit (not sure we want this to change) */
			if (gold < towerManager.getUpgradeCost(t, upgradePath)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}


	public void updatePath() {
		VertexGraph vg = new VertexGraph();
		vg = PathFinder.mapToVertexGraph(vg, map);
		groundPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, true);
		flyingPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, false);
	}

	private void proposePath(int x, int y, int width, int height) {
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
	public Path getProposedPath(boolean isFlying) {
		return (isFlying ? proposedFlyingPath : proposedGroundPath);
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
	
	/**
	 * This function checks to see if an object of the size and position provided
	 * will intersect with any of the paths that the creep must take.
	 * @param x - The x position of the top left tile of the proposed object.
	 * @param y - The y position of the top left tile of the proposed object.
	 * @param width - The width of the proposed object, in number of tiles.
	 * @param height - The height of the proposed object, in number of tiles.
	 * @param isFlying - A variable that specifies if we are checking against the air path or the flying path.
	 * @return True if the specified path intersects (shares a tile) with the proposed build site of the object, false otherwise.
	 */
	private boolean intersectsPath(int x, int y, int width, int height, boolean isFlying) {
		//TODO: Improve the speed of this by caching vertex graph and checking against that.
		//This allows for a quick grab of the vertices that this space contains and then we can see if its in the path
		
		/* Obtain all of the tiles contained that this proposed object will cover */
		int length = width * height;
		Tile[] tiles = new Tile[length];
		for (int j = 0; j < y; j++) {
			for (int i = 0; i < x; i++) {
				tiles[j * height + i] = map.getTile(y + j, x + i);
			}
		}
		
		Iterator<Vertex> pathIterator;
		Vertex currentPathVertex;
		if (isFlying) {
			/* If we're checking against the flying path, we need to iterate through that */
			pathIterator = flyingPath.getIterator();
			while (pathIterator.hasNext()) {
				currentPathVertex = pathIterator.next();
				/* Check if the vertex contains the tiles in the list of tiles where the proposed object is */
				for (Tile proposedObjectTile: tiles) {
					if (currentPathVertex.contains(proposedObjectTile)) {
						return true;
					}
				}
			}
		} else {
			/* If we're checking against the ground path, we need to iterate through that */
			pathIterator = groundPath.getIterator();
			while (pathIterator.hasNext()) {
				currentPathVertex = pathIterator.next();
				/* Check if the vertex contains the tiles in the list of tiles where the proposed object is */
				for (Tile proposedObjectTile: tiles) {
					if (currentPathVertex.contains(proposedObjectTile)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
