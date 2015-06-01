package towers;

import creeps.DamageType;

public enum TowerType {
	WIND (DamageType.WIND, null), 
	EARTH (DamageType.EARTH, null), 
	FIRE (DamageType.FIRE, null), 
	WATER (DamageType.WATER, null),
	WIND_WIND (DamageType.WIND, TowerType.WIND), 
	WIND_EARTH (DamageType.WIND, TowerType.WIND), 
	WIND_FIRE (DamageType.WIND, TowerType.WIND), 
	WIND_WATER (DamageType.WIND, TowerType.WIND),
	EARTH_WIND (DamageType.EARTH, TowerType.EARTH), 
	EARTH_EARTH (DamageType.EARTH, TowerType.EARTH), 
	EARTH_FIRE (DamageType.EARTH, TowerType.EARTH),  
	EARTH_WATER (DamageType.EARTH, TowerType.EARTH),
	FIRE_WIND (DamageType.FIRE, TowerType.FIRE), 
	FIRE_EARTH (DamageType.FIRE, TowerType.FIRE), 
	FIRE_FIRE (DamageType.FIRE, TowerType.FIRE), 
	FIRE_WATER (DamageType.FIRE, TowerType.FIRE),
	WATER_WIND (DamageType.WATER, TowerType.WATER), 
	WATER_EARTH (DamageType.WATER, TowerType.WATER), 
	WATER_FIRE (DamageType.WATER, TowerType.WATER), 
	WATER_WATER (DamageType.WATER, TowerType.WATER);
	
	private DamageType mainDamageType;
	private TowerType downgradeType;
	
	private TowerType (DamageType mainDamageType, TowerType downgradeType) {
		this.mainDamageType = mainDamageType;
		this.downgradeType = downgradeType;
	}
	
	//TODO it's kind of bad practice to rely on the ordering of the enum
	//TODO this way is a lot slower and more tedious though
	public static TowerType getUpgrade(TowerType tetherFrom, TowerType tetherTo) {
		if (tetherFrom == WIND) {
			if (tetherTo == WIND) {
				return WIND_WIND;
			} else if (tetherTo == EARTH) {
				return WIND_EARTH;
			} else if (tetherTo == FIRE) {
				return WIND_FIRE;
			} else if (tetherTo == WATER) {
				return WIND_WATER;
			}
		} else if (tetherFrom == EARTH) {
			if (tetherTo == WIND) {
				return EARTH_WIND;
			} else if (tetherTo == EARTH) {
				return EARTH_EARTH;
			} else if (tetherTo == FIRE) {
				return EARTH_FIRE;
			} else if (tetherTo == WATER) {
				return EARTH_WATER;
			}
		} else if (tetherFrom == FIRE) {
			if (tetherTo == WIND) {
				return FIRE_WIND;
			} else if (tetherTo == EARTH) {
				return FIRE_EARTH;
			} else if (tetherTo == FIRE) {
				return FIRE_FIRE;
			} else if (tetherTo == WATER) {
				return FIRE_WATER;
			}
		} else if (tetherFrom == WATER) {
			if (tetherTo == WIND) {
				return WATER_WIND;
			} else if (tetherTo == EARTH) {
				return WATER_EARTH;
			} else if (tetherTo == FIRE) {
				return WATER_FIRE;
			} else if (tetherTo == WATER) {
				return WATER_WATER;
			}
		}
		return null;
	}
	
	public TowerType getDowngrade() {
		return downgradeType;
	}
	
	public DamageType getDamageType() {
		return mainDamageType;
	}
}
