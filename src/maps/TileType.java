package maps;
/*
 * functional classification of map tiles;
 */
public enum TileType {
	LAND, GRAVEL, VOID, EDGE, START, FINISH;
	
	public boolean isBuildable(){
		return this == LAND;
	}
	
	public boolean isGroundTraversable(){
		switch(this){
		case EDGE:
		case VOID:
			return false;
		case GRAVEL:
		case LAND:
		case START:
		case FINISH:
		default:
			return true;
		}
	}
	
	public boolean isAirTraversable(){
		switch(this){
		case EDGE:
			return false;
		case VOID:
		case GRAVEL:
		case LAND:
		case START:
		case FINISH:
		default:
			return true;
		}
	}
}
