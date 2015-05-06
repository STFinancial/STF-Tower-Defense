package maps;
/*
 * functional classification of map tiles;
 */
public enum TileType {
	
	//ground traversable, air traversable, buildable
	LAND (true, true, true), 
	GRAVEL (true, true, false), 
	VOID (false, true, false),
	BLOCK (false, false, false),
	EDGE (false, false, false), 
	START (true, true, false), 
	FINISH (true, true, false);
	
	//Can shorten these names if they are too wordy
	public boolean groundTraversable;
	public boolean airTraversable;
	public boolean buildable;
	
	private TileType (boolean GT, boolean AT, boolean B) {
		groundTraversable = GT;
		airTraversable = AT;
		buildable = B;
	}
}
