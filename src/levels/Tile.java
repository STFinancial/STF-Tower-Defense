package levels;

import towers.Tower;

/*
 * Basic component of what makes up a map
 */
public class Tile {
	Map map; //the map to which the tile belongs
	
	int x, y;
	TileType type;
	Tower tower;
	boolean groundTraversable, airTraversable, buildable;

	Tile(int y, int x, TileType type) {
		this.y = y;
		this.x = x;
		this.type = type;
		tower = null;
		groundTraversable = type.groundTraversable;
		airTraversable = type.airTraversable;
		buildable = type.buildable;
	}
	
	void addTower(Tower t) {
		tower = t;
		buildable = false;
		groundTraversable = false;
	}
	
	void removeTower() {
		buildable = true;
		groundTraversable = true;
	}
	
}
