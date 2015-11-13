package levels;

import utilities.GameConstants;

public enum DirectionType {
	UP_LEFT(-GameConstants.SQRT_2_OVER_2, -GameConstants.SQRT_2_OVER_2), 
	UP(0,-1), 
	UP_RIGHT(GameConstants.SQRT_2_OVER_2, -GameConstants.SQRT_2_OVER_2), 
	RIGHT(1,0), 
	LEFT(-1,0),
	DOWN_RIGHT(GameConstants.SQRT_2_OVER_2, GameConstants.SQRT_2_OVER_2), 
	DOWN(0,1), 
	DOWN_LEFT(-GameConstants.SQRT_2_OVER_2, GameConstants.SQRT_2_OVER_2),
	NONE(0,0);
	
	public float x, y;
	private DirectionType opposite;
	
	static {
		UP_LEFT.opposite = DOWN_RIGHT;
		UP.opposite = DOWN;
		UP_RIGHT.opposite = DOWN_LEFT;
		RIGHT.opposite = LEFT;
		LEFT.opposite = RIGHT;
		DOWN_RIGHT.opposite = UP_LEFT;
		DOWN.opposite = UP;
		DOWN_LEFT.opposite = UP_RIGHT;
		NONE.opposite = NONE;
	}
	
	private DirectionType(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public DirectionType getOpposite() {
		return opposite;
	}
}
