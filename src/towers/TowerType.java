package towers;

import utilities.Constants;
import creeps.DamageType;
import projectiles.*;

public enum TowerType {
	//TODO I want each (upgraded) tower to have it's unique projectile effect too
	EARTH (new BaseAttributeList(){{
		name                = "Earth";
		mainDamageType      = DamageType.EARTH;
		baseWidth			= 2;
		baseHeight			= 2;
		basePhysicalDamage  = 55;
		baseElementalDamage = 0;
		baseSlowDuration	= 10;
		baseCost			= 200;
		baseFireRate		= 15f;
		baseAttackCoolDown	= 15f;
		baseDamageSplash	= 0.05f;
		baseEffectSplash	= 0.05f;
		baseSplashRadius	= 1f;
		baseRange			= 7.5f;
		baseSlow			= 0.10f;
		hitsAir				= false;
		hitsGround			= true;
		upgrades			= null;
		additionalEffect    = null;
	}}),
	FIRE (new BaseAttributeList(){{
		name                = "Fire";
		mainDamageType      = DamageType.FIRE;
		baseWidth			= 2;
		baseHeight			= 2;
		basePhysicalDamage  = 15;
		baseElementalDamage = 25;
		baseSlowDuration	= 0;
		baseCost			= 200;
		baseFireRate		= 12f;
		baseAttackCoolDown	= 12f;
		baseDamageSplash	= 0.25f;
		baseEffectSplash	= 0.25f;
		baseSplashRadius	= 1f;
		baseRange			= 7.5f;
		baseSlow			= 0;
		hitsAir				= true;
		hitsGround			= true;
		upgrades			= null;
		additionalEffect    = null;
	}}),
	WATER (new BaseAttributeList(){{
		name                = "Water";
		mainDamageType      = DamageType.WATER;
		baseWidth			= 2;
		baseHeight			= 2;
		basePhysicalDamage  = 15;
		baseElementalDamage = 15;
		baseSlowDuration	= 10;
		baseCost			= 200;
		baseFireRate		= 10f;
		baseAttackCoolDown	= 10f;
		baseDamageSplash	= 0f;
		baseEffectSplash	= 0.25f;
		baseSplashRadius	= 1f;
		baseRange			= 7.5f;
		baseSlow			= 0.35f;
		hitsAir				= false;
		hitsGround			= true;
		upgrades			= null;
		additionalEffect    = null;
	}}),
	WIND (new BaseAttributeList(){{
		name                = "Wind";
		mainDamageType      = DamageType.WIND;
		baseWidth			= 2;
		baseHeight			= 2;
		basePhysicalDamage  = 10;
		baseElementalDamage = 10;
		baseSlowDuration	= 10;
		baseCost			= 200;
		baseFireRate		= 5f;
		baseAttackCoolDown	= 5f;
		baseDamageSplash	= 0f;
		baseEffectSplash	= 0.10f;
		baseSplashRadius	= 0f;
		baseRange			= 7.5f;
		baseSlow			= 0f;
		hitsAir				= true;
		hitsGround			= true;
		upgrades			= null;
		additionalEffect    = null;
	}}),
	//TODO this is sort of the blueprint for how the upgrades are defined
	//TODO these functions are called by the upgrade method in the tower class
	EARTH_EARTH (new BaseAttributeList(){{
		downgradeType 		= EARTH;
		upgrades			= new Upgrade[][]{
				{
					new Upgrade() {
						{text 		= "Double the Splash Effectiveness";
						 baseCost   = 500;}
						public void upgrade(Tower t) {
							t.damageSplash *= 2;
							t.effectSplash *= 2;
						}
					}
				},
				{
					new Upgrade() {
						{text 		= "Double the Physical Damage";
						baseCost 	= 600;}
						public void upgrade(Tower t) {
							t.damageArray[Constants.NUM_DAMAGE_TYPES - 1] *= 2;
						}
					}
				}
		};
	}}), 
	EARTH_FIRE (new BaseAttributeList(){{
		name = "Meteor";
		downgradeType 		= EARTH;
	}}),
	EARTH_WATER (new BaseAttributeList(){{
		//this will do rupture spikes on a selected area
		name = "Stalagmite";
		targetingType		= TargetingType.AREA;
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
		name = "Sandstorm";
		downgradeType 		= WIND;
	}}), 
	WIND_FIRE (new BaseAttributeList(){{
		name = "Lightning";
		downgradeType 		= WIND;
	}}),  
	WIND_WATER (new BaseAttributeList(){{
		name = "Blizzard";
		downgradeType 		= WIND;
	}}), 
	WIND_WIND (new BaseAttributeList(){{
		name = "Gale";
		downgradeType 		= WIND;
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
