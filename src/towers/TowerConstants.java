package towers;

public final class TowerConstants {
	public final static String WATER_BASE_NAME = "Water";
	public final static int WATER_BASE_WIDTH = 2;
	public final static int WATER_BASE_HEIGHT = 2;
	public final static int WATER_BASE_PHYSICAL_DAMAGE = 15;
	public final static int WATER_BASE_MAGIC_DAMAGE = 15;
	public final static int WATER_BASE_FIRE_RATE = 10; //# of game ticks?
	public final static float WATER_BASE_DAMAGE_SPLASH_COEFFICIENT = 0f;
	public final static float WATER_BASE_EFFECT_SPLASH_COEFFICIENT = .05f;
	public final static float WATER_BASE_SPLASH_RADIUS = 0f; //# of tiles
	public final static boolean WATER_BASE_HITS_AIR = false;
	public final static boolean WATER_BASE_HITS_GROUND = true;
	public final static float WATER_BASE_RANGE = 1.2f; //this needs to be defined
	public final static float WATER_BASE_SLOW = .25f; //percentage
	public final static int WATER_BASE_SLOW_DURATION = 10;
	
	public final static String EARTH_BASE_NAME = "Earth";
	public final static int EARTH_BASE_WIDTH = 2;
	public final static int EARTH_BASE_HEIGHT = 2;
	public final static int EARTH_BASE_PHYSICAL_DAMAGE = 55; //this seems high
	public final static int EARTH_BASE_MAGIC_DAMAGE = 0;
	public final static int EARTH_BASE_FIRE_RATE = 15; //# of game ticks?
	public final static float EARTH_BASE_DAMAGE_SPLASH_COEFFICIENT = .05f;
	public final static float EARTH_BASE_EFFECT_SPLASH_COEFFICIENT = .05f;
	public final static float EARTH_BASE_SPLASH_RADIUS = 1f; //# of tiles
	public final static boolean EARTH_BASE_HITS_AIR = false;
	public final static boolean EARTH_BASE_HITS_GROUND = true;
	public final static float EARTH_BASE_RANGE = 1.7f; //this needs to be defined
	public final static float EARTH_BASE_SLOW = 0f;
	public final static int EARTH_BASE_SLOW_DURATION = 10;
	
	public final static String FIRE_BASE_NAME = "Firebolt";
	public final static int FIRE_BASE_WIDTH = 2;
	public final static int FIRE_BASE_HEIGHT = 2;
	public final static int FIRE_BASE_PHYSICAL_DAMAGE = 15;
	public final static int FIRE_BASE_MAGIC_DAMAGE = 25;
	public final static int FIRE_BASE_FIRE_RATE = 12; //# of game ticks?
	public final static float FIRE_BASE_DAMAGE_SPLASH_COEFFICIENT = .25f; //these seem high
	public final static float FIRE_BASE_EFFECT_SPLASH_COEFFICIENT = .20f;
	public final static float FIRE_BASE_SPLASH_RADIUS = 0f; //# of tiles
	public final static boolean FIRE_BASE_HITS_AIR = true; //?
	public final static boolean FIRE_BASE_HITS_GROUND = true;
	public final static float FIRE_BASE_RANGE = 2.1f; //this needs to be defined
	public final static float FIRE_BASE_SLOW = 0f;
	public final static int FIRE_BASE_SLOW_DURATION = 10;
	
	public final static String WIND_BASE_NAME = "Wind";
	public final static int WIND_BASE_WIDTH = 2;
	public final static int WIND_BASE_HEIGHT = 2;
	public final static int WIND_BASE_PHYSICAL_DAMAGE = 10;
	public final static int WIND_BASE_MAGIC_DAMAGE = 10;
	public final static int WIND_BASE_FIRE_RATE = 5; //# of game ticks?
	public final static float WIND_BASE_DAMAGE_SPLASH_COEFFICIENT = 0f;
	public final static float WIND_BASE_EFFECT_SPLASH_COEFFICIENT = .10f;
	public final static float WIND_BASE_SPLASH_RADIUS = 0f; //# of tiles
	public final static boolean WIND_BASE_HITS_AIR = true;
	public final static boolean WIND_BASE_HITS_GROUND = true;
	public final static float WIND_BASE_RANGE = 3.5f; //this needs to be defined
	public final static float WIND_BASE_SLOW = 0f;
	public final static int WIND_BASE_SLOW_DURATION = 10;
}
