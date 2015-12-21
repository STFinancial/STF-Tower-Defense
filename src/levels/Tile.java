package levels;

import towers.Tower;
import towers.TowerManager;

/*
 * Basic component of what makes up a map
 */
public class Tile {
	Map map; //the map to which the tile belongs
	
	int x, y;
	TileType type;
	Tower tower;
	
	boolean groundTraversable;
	boolean airTraversable;
	private boolean groundBuildable;
	private boolean airBuildable;

	Tile(int y, int x, TileType type) {
		this.y = y;
		this.x = x;
		this.type = type;
		tower = null;
		groundTraversable = type.groundTraversable;
		airTraversable = type.airTraversable;
		groundBuildable = type.groundBuildable;
		airBuildable = type.airBuildable;
	}
	
	void addTower(Tower t) {
		tower = t;
		groundTraversable &= !TowerManager.getInstance().isOnGround(t); 
		airTraversable &= !TowerManager.getInstance().isInAir(t);
		/* We won't allow multiple towers on the same tile */
		groundBuildable = false;
		airBuildable = false;
	}
	
	void removeTower() {
		groundTraversable = type.groundTraversable;
		airTraversable = type.airTraversable;
		groundBuildable = type.groundBuildable;
		airBuildable = type.airBuildable;
	}
	
	boolean isBuildable(boolean ground) {
		if (ground) {
			return groundBuildable;
		} else {
			return airBuildable;
		}
	}
	
	public int getX(){ return x;}
	public int getY(){ return y;}
	public TileType getType() { return type; }
	
}
