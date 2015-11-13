package levels;
/*
 * functional classification of map tiles;
 */
public enum TileType {
	
	//ground traversable, air traversable, buildable
	LAND (true, true, true),
	EARTH (false, false, false), //This is used for the earth tower functionality
	GRAVEL (true, true, false), 
	VOID (false, true, false),
	BLOCK (false, false, false), //What functional purpose does this add compared to EDGE
	EDGE (false, false, false), 
	START (true, true, false), 
	FINISH (true, true, false);
	
	public boolean groundTraversable;
	public boolean airTraversable;
	public boolean buildable;
	
	private TileType (boolean GT, boolean AT, boolean B) {
		groundTraversable = GT;
		airTraversable = AT;
		buildable = B;
	}
}
