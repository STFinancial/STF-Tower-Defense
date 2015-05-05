package maps;
/*
 * Essentially the level data of a game, contains information about the Size, Tiles, Money, and Creeps (Possibly more as we expand)
 */
public class Map {
	public MapType type;
	
	public Tile[][] map;
	public int width;
	public int height;
	
	public Map(int width, int height){
		map = new Tile[height][width];
		this.width = width;
		this.height = height;
	}
	
	public boolean canBuild(int y, int x) {
		return map[y][x].type.buildable;
	}
	
	public TileType getTileType(int y, int x) {
		return map[y][x].type;
	}
	
	public Tile getTile(int y, int x) {
		return map[y][x];
	}
	
	public void setTile(int y, int x, TileType type){
		map[y][x] = new Tile(y, x, type);
	}
}
