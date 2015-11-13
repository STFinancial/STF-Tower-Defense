package levels;

import towers.Tower;

/*
 * Basic component of what makes up a map
 */
public class Tile {
	public Map map; //the map to which the tile belongs
	
	public int x, y;
	public TileType type; //going to make this public for now
	public Tower tower;
	public boolean groundTraversable, airTraversable, buildable;

	public Tile(int y, int x, TileType type){
		this.y = y;
		this.x = x;
		this.type = type;
		tower = null;
		groundTraversable = type.groundTraversable;
		airTraversable = type.airTraversable;
		buildable = type.buildable;
	}
	
	public void addTower(Tower t){
		tower = t;
		buildable = false;
		groundTraversable = false;
	}

	public void removeTower(){
		buildable = true;
		groundTraversable = true;
	}
	
}
