package towers;

import utilities.Constants;
import creeps.DamageType;

public enum TowerType {
	//TODO I want each (upgraded) tower to have it's unique projectile effect too
	//TODO need to adjust stats so that each tower can merit being the head of a chain (attack speed/range is too strong)
	EARTH (new BaseAttributeList(){{
		name                = "Earth";
		mainDamageType      = DamageType.EARTH;
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
		targetsCreep		= true;
		upgrades			= null;
	}}),
	FIRE (new BaseAttributeList(){{
		name                = "Fire";
		mainDamageType      = DamageType.FIRE;
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
		targetsCreep		= true;
		upgrades			= null;
	}}),
	WATER (new BaseAttributeList(){{
		name                = "Water";
		mainDamageType      = DamageType.WATER;
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
		targetsCreep		= true;
		upgrades			= null;
	}}),
	WIND (new BaseAttributeList(){{
		name                = "Wind";
		mainDamageType      = DamageType.WIND;
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
		targetsCreep		= true;
		upgrades			= null;
	}}),
	//TODO this is sort of the blueprint for how the upgrades are defined
	//TODO these functions are called by the upgrade method in the tower class
	EARTH_EARTH (new BaseAttributeList(){{
		downgradeType 		= EARTH;
		targetsCreep		= true;
		upgrades			= new Upgrade[][]{
				{
					new Upgrade() {
						{text = "Double the Splash Effectiveness";}
						public void upgrade(Tower t) {
							t.damageSplash *= 2;
							t.effectSplash *= 2;
						}
					}
				},
				{
					new Upgrade() {
						{text = "Double the Physical Damage";}
						public void upgrade(Tower t) {
							t.damageArray[Constants.NUM_DAMAGE_TYPES - 1] *= 2;
						}
					}
				}
		};
	}}), 
	EARTH_FIRE (new BaseAttributeList(){{
		downgradeType 		= EARTH;
		targetsCreep		= true;
	}}),
	EARTH_WATER (new BaseAttributeList(){{
		downgradeType 		= EARTH;
		targetsCreep		= true;
	}}),
	EARTH_WIND (new BaseAttributeList(){{
		downgradeType 		= EARTH;
		targetsCreep		= true;
	}}), 
	FIRE_EARTH (new BaseAttributeList(){{
		downgradeType 		= FIRE;
		targetsCreep		= true;
	}}), 
	FIRE_FIRE (new BaseAttributeList(){{
		downgradeType 		= FIRE;
		targetsCreep		= true;
	}}),  
	FIRE_WATER (new BaseAttributeList(){{
		downgradeType 		= FIRE;
		targetsCreep		= true;
	}}), 
	FIRE_WIND (new BaseAttributeList(){{
		downgradeType 		= FIRE;
		targetsCreep		= true;
	}}), 
	WATER_EARTH (new BaseAttributeList(){{
		downgradeType 		= WATER;
		targetsCreep		= true;
	}}), 
	WATER_FIRE (new BaseAttributeList(){{
		downgradeType 		= WATER;
		targetsCreep		= true;
	}}),  
	WATER_WATER (new BaseAttributeList(){{
		downgradeType 		= WATER;
		targetsCreep		= true;
	}}), 
	WATER_WIND (new BaseAttributeList(){{
		downgradeType 		= WATER;
		targetsCreep		= true;
	}}), 
	WIND_EARTH (new BaseAttributeList(){{
		downgradeType 		= WIND;
		targetsCreep		= true;
	}}), 
	WIND_FIRE (new BaseAttributeList(){{
		downgradeType 		= WIND;
		targetsCreep		= true;
	}}),  
	WIND_WATER (new BaseAttributeList(){{
		downgradeType 		= WIND;
		targetsCreep		= true;
	}}), 
	WIND_WIND (new BaseAttributeList(){{
		downgradeType 		= WIND;
		targetsCreep		= true;
	}});
	
	private BaseAttributeList baseAttributeList;
	
	private TowerType (BaseAttributeList baseAttributeList) {
		baseAttributeList.downgradeType = this;
		baseAttributeList.type = this;
		this.baseAttributeList = baseAttributeList;
	}
	
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
	
	public boolean isBaseType() {
		return this == EARTH || this == FIRE || this == WATER || this == WIND;
	}
}
