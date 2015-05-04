package maps;

import utilities.Constants;

public enum DirectionType {
	
	UP_LEFT(-Constants.SQRT_2_OVER_2, -Constants.SQRT_2_OVER_2), UP(0,-1), UP_RIGHT(Constants.SQRT_2_OVER_2, -Constants.SQRT_2_OVER_2), 
	RIGHT(1,0), LEFT(-1,0),
	DOWN_RIGHT(Constants.SQRT_2_OVER_2, Constants.SQRT_2_OVER_2), DOWN(0,1), DOWN_LEFT(-Constants.SQRT_2_OVER_2, Constants.SQRT_2_OVER_2);
	
	public float x, y;
	
	DirectionType(float x, float y){
		this.x = x;
		this.y = y;
	}
}
