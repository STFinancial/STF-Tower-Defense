package towers;

public enum TowerType {
	WIND, EARTH, FIRE, WATER,
	WIND_WIND, WIND_EARTH, WIND_FIRE, WIND_WATER,
	EARTH_WIND, EARTH_EARTH, EARTH_FIRE, EARTH_WATER,
	FIRE_WIND, FIRE_EARTH, FIRE_FIRE, FIRE_WATER,
	WATER_WIND, WATER_EARTH, WATER_FIRE, WATER_WATER;
	
	public static TowerType getUpgrade(TowerType tetherFrom, TowerType tetherTo) {
		return TowerType.values()[((tetherFrom.ordinal() + 1) * 4) + tetherTo.ordinal()];
	}
}
