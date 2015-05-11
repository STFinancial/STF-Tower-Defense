package utilities;

public final class Constants {
	public static final int NUM_WORLDS = 4;
	public static final int[] LEVELS_IN_WORLD = { 10, 10, 10, 10 };
	public static final int LARGEST_WORLD = 10; //this seems kind of clunky
	
	public final int WATER_BASE_PHYSICAL_DAMAGE = 15;
	public final int WATER_BASE_MAGIC_DAMAGE = 15;
	public final int WATER_BASE_FIRE_RATE = 10; //# of game ticks?
	public final int WATER_BASE_DAMAGE_SPLASH_COEFFICIENT = 0; //% splashed
	public final int WATER_BASE_EFFECT_SPLASH_COEFFICIENT = 5; //% of slow/others that is splashed
	public final float WATER_BASE_SPLASH_RADIUS = 1f; //# of tiles
	public final boolean WATER_BASE_HITS_AIR = false;
	public final boolean WATER_BASE_HITS_GROUND = true;
	public final float WATER_BASE_RANGE = 120; //this needs to be defined
	public final int WATER_BASE_SLOW = 25; //percentage
	
	public final int EARTH_BASE_PHYSICAL_DAMAGE = 55; //this seems high
	public final int EARTH_BASE_MAGIC_DAMAGE = 0;
	public final int EARTH_BASE_FIRE_RATE = 15; //# of game ticks?
	public final int EARTH_BASE_DAMAGE_SPLASH_COEFFICIENT = 5; //% splashed
	public final int EARTH_BASE_EFFECT_SPLASH_COEFFICIENT = 5; //% of slow/others that is splashed
	public final float EARTH_BASE_SPLASH_RADIUS = 1f; //# of tiles
	public final boolean EARTH_BASE_HITS_AIR = false;
	public final boolean EARTH_BASE_HITS_GROUND = true;
	public final float EARTH_BASE_RANGE = 1.7f; //this needs to be defined
	public final int EARTH_BASE_SLOW = 0; //percentage
	
	public final int FIRE_BASE_PHYSICAL_DAMAGE = 15;
	public final int FIRE_BASE_MAGIC_DAMAGE = 25;
	public final int FIRE_BASE_FIRE_RATE = 12; //# of game ticks?
	public final int FIRE_BASE_DAMAGE_SPLASH_COEFFICIENT = 25; //% splashed
	public final int FIRE_BASE_EFFECT_SPLASH_COEFFICIENT = 20; //% of slow/others that is splashed
	public final float FIRE_BASE_SPLASH_RADIUS = 1f; //# of tiles
	public final boolean FIRE_BASE_HITS_AIR = true; //?
	public final boolean FIRE_BASE_HITS_GROUND = true;
	public final float FIRE_BASE_RANGE = 2.1f; //this needs to be defined
	public final int FIRE_BASE_SLOW = 0; //percentage
	
	public final int AIR_BASE_PHYSICAL_DAMAGE = 10;
	public final int AIR_BASE_MAGIC_DAMAGE = 10;
	public final int AIR_BASE_FIRE_RATE = 5; //# of game ticks?
	public final int AIR_BASE_DAMAGE_SPLASH_COEFFICIENT = 0; //% splashed
	public final int AIR_BASE_EFFECT_SPLASH_COEFFICIENT = 10; //% of slow/others that is splashed
	public final float AIR_BASE_SPLASH_RADIUS = 1f; //# of tiles
	public final boolean AIR_BASE_HITS_AIR = true;
	public final boolean AIR_BASE_HITS_GROUND = true;
	public final float AIR_BASE_RANGE = 3.5f; //this needs to be defined
	public final int AIR_BASE_SLOW = 0; //percentage
	
	
	public final int BASE_INTEREST_RATE = 3;
	
	public final static float SQRT_2_OVER_2 = 0.7071f;
	public final static float SQRT_2 = 1.414f;
}
