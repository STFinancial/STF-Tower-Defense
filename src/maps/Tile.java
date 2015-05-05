package maps;
/*
 * Basic component of what makes up a map
 */
public class Tile {
	public Map map; //the map to which the tile belongs
	
	int x, y;
	public TileType type; //going to make this public for now
	

	public Tile(int y, int x, TileType type){
		this.y = y;
		this.x = x;
		this.type = type;
	}
}
