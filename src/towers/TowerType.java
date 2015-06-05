package towers;

import creeps.DamageType;

public enum TowerType {
	
	EARTH (new BaseAttributeList(){{
		type                = EARTH;
		name                = "Earth";
		mainDamageType      = DamageType.EARTH;
		downgradeType		= EARTH;
		baseWidth			= 2;
		baseHeight			= 2;
		basePhysicalDamage  = 55;
		baseElementalDamage = 0;
		baseFireRate		= 15;
		baseAttackCoolDown	= 15;
		baseSlowDuration	= 10;
		baseCost			= 200;
		baseDamageSplash	= 0.05f;
		baseEffectSplash	= 0.05f;
		baseSplashRadius	= 1f;
		baseRange			= 7.5f;
		baseSlow			= 0.10f;
		hitsAir				= false;
		hitsGround			= true;
	}}),
	FIRE (new BaseAttributeList(){{
		type                = FIRE;
		name                = "Fire";
		mainDamageType      = DamageType.FIRE;
		downgradeType		= FIRE;
		baseWidth			= 2;
		baseHeight			= 2;
		basePhysicalDamage  = 15;
		baseElementalDamage = 25;
		baseFireRate		= 12;
		baseAttackCoolDown	= 12;
		baseSlowDuration	= 0;
		baseCost			= 200;
		baseDamageSplash	= 0.25f;
		baseEffectSplash	= 0.25f;
		baseSplashRadius	= 1f;
		baseRange			= 7.5f;
		baseSlow			= 0;
		hitsAir				= true;
		hitsGround			= true;
	}}),
	WATER (new BaseAttributeList(){{
		type                = WATER;
		name                = "Water";
		mainDamageType      = DamageType.WATER;
		downgradeType		= WATER;
		baseWidth			= 2;
		baseHeight			= 2;
		basePhysicalDamage  = 15;
		baseElementalDamage = 15;
		baseFireRate		= 10;
		baseAttackCoolDown	= 10;
		baseSlowDuration	= 10;
		baseCost			= 200;
		baseDamageSplash	= 0f;
		baseEffectSplash	= 0.25f;
		baseSplashRadius	= 1f;
		baseRange			= 7.5f;
		baseSlow			= 0.35f;
		hitsAir				= false;
		hitsGround			= true;
	}}),
	WIND (new BaseAttributeList(){{
		type                = WIND;
		name                = "Wind";
		mainDamageType      = DamageType.WIND;
		downgradeType		= WIND;
		baseWidth			= 2;
		baseHeight			= 2;
		basePhysicalDamage  = 10;
		baseElementalDamage = 10;
		baseFireRate		= 5;
		baseAttackCoolDown	= 5;
		baseSlowDuration	= 10;
		baseCost			= 200;
		baseDamageSplash	= 0f;
		baseEffectSplash	= 0.10f;
		baseSplashRadius	= 0f;
		baseRange			= 7.5f;
		baseSlow			= 0f;
		hitsAir				= true;
		hitsGround			= true;
	}}),
	EARTH_EARTH (new BaseAttributeList(){{
		downgradeType 		= EARTH;
	}}), 
	EARTH_FIRE (new BaseAttributeList(){{
		downgradeType 		= EARTH;
	}}),
	EARTH_WATER (new BaseAttributeList(){{
		downgradeType 		= EARTH;
	}}),
	EARTH_WIND (new BaseAttributeList(){{
		downgradeType 		= EARTH;
	}}), 
	FIRE_EARTH (new BaseAttributeList(){{
		downgradeType 		= FIRE;
	}}), 
	FIRE_FIRE (new BaseAttributeList(){{
		downgradeType 		= FIRE;
	}}),  
	FIRE_WATER (new BaseAttributeList(){{
		downgradeType 		= FIRE;
	}}), 
	FIRE_WIND (new BaseAttributeList(){{
		downgradeType 		= FIRE;
	}}), 
	WATER_EARTH (new BaseAttributeList(){{
		downgradeType 		= WATER;
	}}), 
	WATER_FIRE (new BaseAttributeList(){{
		downgradeType 		= WATER;
	}}),  
	WATER_WATER (new BaseAttributeList(){{
		downgradeType 		= WATER;
	}}), 
	WATER_WIND (new BaseAttributeList(){{
		downgradeType 		= WATER;
	}}), 
	WIND_EARTH (new BaseAttributeList(){{
		downgradeType 		= WIND;
	}}), 
	WIND_FIRE (new BaseAttributeList(){{
		downgradeType 		= WIND;
	}}),  
	WIND_WATER (new BaseAttributeList(){{
		downgradeType 		= WIND;
	}}), 
	WIND_WIND (new BaseAttributeList(){{
		downgradeType 		= WIND;
	}});
	
	private BaseAttributeList baseAttributeList;
	
	private TowerType (BaseAttributeList baseAttributeList) {
		this.baseAttributeList = baseAttributeList;
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

	public BaseAttributeList getAttributeList() {
		return baseAttributeList;
	}
	
	public int getCost() {
		return baseAttributeList.baseCost;
	}

	public int getWidth() {
		return baseAttributeList.baseWidth;
	}

	public int getHeight() {
		return baseAttributeList.baseHeight;
	}

	public float getRange() {
		return baseAttributeList.baseRange;
	}
}
