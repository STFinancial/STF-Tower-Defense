package levels;
/*
 * functional classification of map tiles;
 */
public enum TileType {
	/* Ground Traversable, Air Traversable, Ground Buildable, Air Buildable */
	
	/** This is the standard tile with nothing obstructing the air or ground **/
	LAND (true, true, true, true),
	
	/** This is what you might think a tile that is "blocked by a rock" would be like.
	 * Also used for the EarthEarth Tower final upgrade functionality **/
	EARTH (false, true, false, true),
	
	/** This is basically land except you can't build on it on the ground **/
	GRAVEL (true, true, false, true), 
	/** This is basically land except you can't build on it in the air **/
	CLOUD (true, true, true, false),
	/** This is basically land except you can't build on it for both **/
	SPIRE (true, true, false, false),
	
	/** This is basically like a "hole" in the ground **/
	GROUND_HOLE (false, true, false, true),
	/** This is basically like a "hole" in the air **/
	AIR_HOLE (true, false, true, false),
	
	/** This tile is a non edge tile that cannot be interacted with (except changed perhaps) **/
	BLOCK (false, false, false, false),
	/** This tile is for the edges of the map. It cannot be interacted with. **/
	EDGE (false, false, false, false),
	
	// TODO: Might want to separate start and finish types
	
	/** This is a tile that spawns minions **/
	START (true, true, false, false), 
	/** This is a tile where creep disappear from the map **/
	FINISH (true, true, false, false);
	
	public boolean groundTraversable;
	public boolean airTraversable;
	public boolean groundBuildable;
	public boolean airBuildable;
	
	private TileType (boolean GT, boolean AT, boolean GB, boolean AB) {
		groundTraversable = GT;
		airTraversable = AT;
		groundBuildable = GB;
		airBuildable = AB;
	}
}
