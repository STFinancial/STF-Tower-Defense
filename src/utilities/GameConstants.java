package utilities;

public final class GameConstants {
	// World Constants
	public static final int NUM_WORLDS = 4;
	public static final int[] LEVELS_IN_WORLD = { 7, 7, 7, 7 };
	public static final int LARGEST_WORLD = 7; //this seems kind of clunky
	
	public static final int NUM_BASIC_TYPES = 4;
	public static final int NUM_DAMAGE_TYPES = 7;
	public static final int SIPHON_BASE_COST = 300; //the gold cost of siphoning
	public static final float SIPHON_CHAIN_COST_MULTIPLIER = 1.5f; //chaining is exponential, this ensures the cost is too
	
	public static final float DAMAGE_RESIST_DENOMINATOR_VALUE = 100f;
	public static final float SLOW_RESIST_DENOMINATOR_VALUE = 100f;
	
	public static final float BASE_TOWER_REFUND_RATE = 0.75f;
	public static final float BASE_TOTAL_UPGRADE_REFUND_RATE = 0.75f;
	public static final float BASE_TRACK_UPGRADE_REFUND_RATE = 0.65f;
	public static final boolean DEFAULT_UNSIPHON_REFUND_OPTION = false;
	
	
	public enum UpgradePathType { UPPER_PATH, LOWER_PATH; }
	public static final int NUM_UPGRADE_PATHS = 2;
	public static final int UPGRADE_PATH_LENGTH = 4;
	public static final int NUM_TOWER_TALENTS = 3;
	
	public static final int BASE_INTEREST_RATE = 3; //TODO: What was this for? Unspent gold?
	
	public static final float SQRT_2_OVER_2 = 0.7071f;
	public static final float SQRT_2 = 1.414f;
	public static final int NEGATIVE_INF = Integer.MIN_VALUE;
	public static final int POSITIVE_INF = Integer.MAX_VALUE;
	
	
	public static final int TOWER_TALENT_TREE_SIZE = 3;
	
	public static final float BASE_SPEED_PROJECTILE_BASIC = 0.20f;
	public static final float BASE_SPEED_PROJECTILE_AOE = 0;
	public static final float BASE_SPEED_PROJECTILE_AREA = 0;
	public static final float BASE_SPEED_PROJECTILE_BEAM = 0;
	public static final float BASE_SPEED_PROJECTILE_CHAIN = 0;
	public static final float BASE_SPEED_PROJECTILE_EFFECT_PATCH = 0.20f;
	public static final float BASE_SPEED_PROJECTILE_PASS_THROUGH_AREA = 0.20f;
	public static final float BASE_SPEED_PROJECTILE_PASS_THROUGH_TARGET = 0.20f;
	public static final float BASE_SPEED_PROJECTILE_RANDOM = 0.20f;
	
	
	public static final String BASE_FILE_PATH = "./data/";
	public static final String PLAYER_DATA_FILE_EXT = "player.dat";
	public static final String MACRO_TALENT_DATA_FILE_EXT = "macro_talent.dat";
	public static final String ADVENTURE_PROGRESS_FILE_EXT = "adventure_progress.dat";
}
