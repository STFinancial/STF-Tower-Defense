package maps;

import utilities.GameConstants;

public enum DirectionType {
	
	UP_LEFT(-GameConstants.SQRT_2_OVER_2, -GameConstants.SQRT_2_OVER_2), UP(0,-1), UP_RIGHT(GameConstants.SQRT_2_OVER_2, -GameConstants.SQRT_2_OVER_2), 
	RIGHT(1,0), LEFT(-1,0),
	DOWN_RIGHT(GameConstants.SQRT_2_OVER_2, GameConstants.SQRT_2_OVER_2), DOWN(0,1), DOWN_LEFT(-GameConstants.SQRT_2_OVER_2, GameConstants.SQRT_2_OVER_2),
	NONE(0,0);
	
	public float x, y;
	
	DirectionType(float x, float y){
		this.x = x;
		this.y = y;
	}
}
