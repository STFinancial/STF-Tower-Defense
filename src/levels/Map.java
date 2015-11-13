package levels;
/*
 * Essentially the level data of a game, contains information about the Size, Tiles, Money, and Creeps (Possibly more as we expand)
 */
public class Map {
	MapType type;
	
	Tile[][] map;
	private int width;
	private int height;
	
	public Map(int width, int height){
		map = new Tile[height][width];
		this.width = width;
		this.height = height;
	}
	
	public boolean canBuild(int y, int x) {
		return map[y][x].type.buildable;
	}
	
	public int getHeight() {
		return height;
	}
	
	public TileType getTileType(int y, int x) {
		return map[y][x].type;
	}
	
	public Tile getTile(int y, int x) {
		return map[y][x];
	}
	
	public int getWidth() { 
		return width;
	}
	
	public void setTile(int y, int x, TileType type){
		map[y][x] = new Tile(y, x, type);
	}
	
	public boolean isOutside(float x, float y) {
		return x < 0 || x > width || y < 0 || y > height; 
	}
}
