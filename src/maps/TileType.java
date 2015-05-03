package maps;
/*
 * functional classification of map tiles;
 */
public enum TileType {
	
	//ground traversable, air traversable, buildable
	LAND (true, true, true), 
	GRAVEL (true, true, false), 
	VOID (false, true, false), 
	EDGE (false, false, false), 
	START (true, true, false), 
	FINISH (true, true, false);
	
	//Can shorten these names if they are too wordy
	boolean groundTraversable;
	boolean airTraversable;
	boolean buildable;
	
	private TileType (boolean GT, boolean AT, boolean B) {
		groundTraversable = GT;
		airTraversable = AT;
		buildable = B;
	}
	
	
	
//	public boolean isBuildable(){
//		return this == LAND;
//	}
//	
//	public boolean isGroundTraversable(){
//		switch (this) {
//		case EDGE:
//		case VOID:
//			return false;
//		case GRAVEL:
//		case LAND:
//		case START:
//		case FINISH:
//		default:
//			return true;
//		}
//	}
//	
//	public boolean isAirTraversable(){
//		switch (this) {
//		case EDGE:
//			return false;
//		case VOID:
//		case GRAVEL:
//		case LAND:
//		case START:
//		case FINISH:
//		default:
//			return true;
//		}
//	}
}
