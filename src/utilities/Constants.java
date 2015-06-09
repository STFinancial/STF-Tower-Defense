package utilities;

public final class Constants {
	public static final int NUM_WORLDS = 4;
	public static final int[] LEVELS_IN_WORLD = { 10, 10, 10, 10 };
	public static final int LARGEST_WORLD = 10; //this seems kind of clunky
	
	public static final int NUM_BASIC_TYPES = 4;
	public static final int NUM_DAMAGE_TYPES = 7;
	//TODO: Should these two modifiers be equal?
	//Sum of infinite series of .5 is 1, so we can never more than double a stat
	public static final float SIPHON_BONUS_MODIFIER = 0.50f; //stats stolen from siphon
	//TODO: Not actually using the the siphon penalty yet
	public static final float SIPHON_PENALTY_MODIFIER = 0.25f; //stats lost from siphon
	public static final int SIPHON_BASE_COST = 300; //the "mana" cost of siphoning
	public static final float SIPHON_CHAIN_COST_MULTIPLIER = 1.5f; //chaining is exponential, this ensures the cost is too
	
	public final int BASE_INTEREST_RATE = 3;
	
	public final static float SQRT_2_OVER_2 = 0.7071f;
	public final static float SQRT_2 = 1.414f;
}
