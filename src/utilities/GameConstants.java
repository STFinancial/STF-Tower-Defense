package utilities;

public final class GameConstants {
	public static final int NUM_WORLDS = 4;
	public static final int[] LEVELS_IN_WORLD = { 10, 10, 10, 10 };
	public static final int LARGEST_WORLD = 10; //this seems kind of clunky
	
	public static final int NUM_BASIC_TYPES = 4;
	public static final int NUM_DAMAGE_TYPES = 7;
	public static final int SIPHON_BASE_COST = 300; //the "mana" cost of siphoning
	public static final float SIPHON_CHAIN_COST_MULTIPLIER = 1.5f; //chaining is exponential, this ensures the cost is too
	
	public static final float DAMAGE_RESIST_DENOMINATOR_VALUE = 100f;
	public static final float SLOW_RESIST_DENOMINATOR_VALUE = 100f;
	
	public static final int NUM_UPGRADE_PATHS = 2;
	public static final int UPGRADE_PATH_LENGTH = 4;
	
	public static final int BASE_INTEREST_RATE = 3;
	
	public static final float SQRT_2_OVER_2 = 0.7071f;
	public static final float SQRT_2 = 1.414f;
	public static final int NEGATIVE_INF = Integer.MIN_VALUE;
	public static final int POSITIVE_INF = Integer.MAX_VALUE;
}
