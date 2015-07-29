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
		basePhysicalDamage  = 70;
		baseElementalDamage = 0;
		baseSlowDuration	= 0;
		baseCost			= 200;
		baseFireRate		= 15f;
		baseAttackCoolDown	= 15f;
		baseDamageSplash	= 0.1f;
		baseEffectSplash	= 0f;
		baseSplashRadius	= 2f;
		baseRange			= 6.5f;
		baseSlow			= 0f;
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
		basePhysicalDamage  = 25;
		baseElementalDamage = 25;
		baseSlowDuration	= 0;
		baseCost			= 200;
		baseFireRate		= 12f;
		baseAttackCoolDown	= 12f;
		baseDamageSplash	= 0.25f;
		baseEffectSplash	= 0.25f;
		baseSplashRadius	= 0f;
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
		baseRange			= 8.5f;
		baseSlow			= 0f;
		hitsAir				= true;
		hitsGround			= true;
		upgrades			= null;
		additionalEffect    = null;
	}}),
	//TODO this is sort of the blueprint for how the upgrades are defined
	//TODO these functions are called by the upgrade method in the tower class
	EARTH_EARTH (new BaseAttributeList(){{
		//does an AOE earthquake? cannot hit flying
		name				= "Gaia";
		downgradeType 		= EARTH;
		mainDamageType      = DamageType.EARTH;
		baseWidth			= 2;
		baseHeight			= 2;
		basePhysicalDamage  = 100;
		baseElementalDamage = 0;
		baseSlowDuration	= 10;
		baseFireRate		= 15f;
		baseAttackCoolDown	= 15f;
		baseDamageSplash	= 0f;
		baseEffectSplash	= 0f;
		baseSplashRadius	= 0f;
		baseRange			= 8.5f;
		baseSlow			= 0f;
		hitsAir				= false;
		hitsGround			= true;
		additionalEffect    = null;
		upgrades			= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Fault Lines";
						 text 		= "Increase the base RANGE";
						 baseCost   = 400;}
						 public void upgrade(Tower t) { t.baseAttributeList.baseRange += 2; }
					},
					new Upgrade() {
						{name		= "Tectonics";
						 text 		= "Increase the base pulse rate";
						 baseCost   = 500;}
						 public void upgrade(Tower t) { t.baseAttributeList.baseFireRate -= 3; t.baseAttributeList.baseAttackCoolDown -= 3; }
					},
					new Upgrade() {
						{name		= "Channel the Earth";
						 text 		= "Converts all PHYSICAL damage to EARTH damage";
						 baseCost   = 2000;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.EARTH.ordinal()] += t.damageArray[Constants.NUM_DAMAGE_TYPES - 1]; t.damageArray[Constants.NUM_DAMAGE_TYPES - 1] = 0; }
					},
					new Upgrade() {
						{name		= "Living Earth";
						 text 		= "All earth tiles deal EARTH damage in a circle around them";
						 baseCost   = 5000;}
						 public void upgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "Hardened Earth";
						 text 		= "Double the PHYSICAL Damage";
						 baseCost 	= 600;}
						 public void upgrade(Tower t) { t.damageArray[Constants.NUM_DAMAGE_TYPES - 1] *= 2; }
					},
					new Upgrade() {
						{name		= "Fault Lines";
						 text 		= "Increase the base RANGE";
						 baseCost   = 400;}
						 public void upgrade(Tower t) { t.baseAttributeList.baseRange += 2; }
					},
					new Upgrade() {
						{name		= "Fragmentation";
						 text 		= "Deal additional PHYSICAL damage as a bleed";
						 baseCost   = 2500;}
						 public void upgrade(Tower t) { t.baseAttributeList.basePhysicalDamage += 50; }
					},
					new Upgrade() {
						{name		= "Grievous Wound";
						 text 		= "Creep affected by this Tower's bleed have reduced TOUGHNESS";
						 baseCost   = 5000;}
						 public void upgrade(Tower t) {  }
					},
				}
		};
	}}), 
	EARTH_FIRE (new BaseAttributeList(){{
		//does tons of damage and armor penetration at some point?
		name 				= "Meteor";
		downgradeType 		= EARTH;
	}}),
	EARTH_WATER (new BaseAttributeList(){{
		//this will do rupture spikes on a selected area
		name 				= "Stalagmite";
		downgradeType 		= EARTH;
	}}),
	EARTH_WIND (new BaseAttributeList(){{
		//has enormous splash radius
		//basically "explodes" outward
		downgradeType 		= EARTH;
	}}), 
	FIRE_EARTH (new BaseAttributeList(){{
		//this tower shreds through enemy armor for a period of time
		downgradeType 		= FIRE;
	}}), 
	FIRE_FIRE (new BaseAttributeList(){{
		//this tower ignites enemies with all types of elemental damage
		name				= "Greater Fire";
		downgradeType 		= FIRE;
	}}),  
	FIRE_WATER (new BaseAttributeList(){{
		//attacks ignore all types of defenses
		name				= "Plasma";
		downgradeType 		= FIRE;
	}}), 
	FIRE_WIND (new BaseAttributeList(){{
		//this tower fires a laser thing that passes through enemies and goes a fixed distance
		downgradeType 		= FIRE;
	}}), 
	WATER_EARTH (new BaseAttributeList(){{
		//does large AoE slow
		downgradeType 		= WATER;
	}}), 
	WATER_FIRE (new BaseAttributeList(){{
		//this tower applies an MR shred for a period of time
		name				= "Purging"; //(?)
		downgradeType 		= WATER;
	}}),  
	WATER_WATER (new BaseAttributeList(){{
		//this tower does an aoe slow/freeze
		downgradeType 		= WATER;
	}}), 
	WATER_WIND (new BaseAttributeList(){{
		//this tower has a high splash effect coefficient?
		name				= "Hail";
		downgradeType 		= WATER;
	}}), 
	WIND_EARTH (new BaseAttributeList(){{
		//this tower disorients enemies and makes them walk randomly
		name 				= "Sandstorm";
		downgradeType 		= WIND;
	}}), 
	WIND_FIRE (new BaseAttributeList(){{
		//this tower chains damage and removes shield (?)
		name 				= "Lightning";
		downgradeType 		= WIND;
	}}),  
	WIND_WATER (new BaseAttributeList(){{
		//slows enemies and reduces toughness
		name				= "Blizzard";
		downgradeType 		= WIND;
	}}), 
	WIND_WIND (new BaseAttributeList(){{
		//this tower does a pushback (is this too hard?)
		name 				= "Gale";
		downgradeType 		= WIND;
	}});
	
	private BaseAttributeList baseAttributeList;
	
	private TowerType (BaseAttributeList baseAttributeList) {
		baseAttributeList.downgradeType = this;
		baseAttributeList.type = this;
		this.baseAttributeList = baseAttributeList;
	}
	
	//TODO this way is a lot slower and more tedious though
	public static TowerType getUpgrade(TowerType source, TowerType destination) {
		if (source == WIND) {
			if (destination == WIND) {
				return WIND_WIND;
			} else if (destination == EARTH) {
				return WIND_EARTH;
			} else if (destination == FIRE) {
				return WIND_FIRE;
			} else if (destination == WATER) {
				return WIND_WATER;
			}
		} else if (source == EARTH) {
			if (destination == WIND) {
				return EARTH_WIND;
			} else if (destination == EARTH) {
				return EARTH_EARTH;
			} else if (destination == FIRE) {
				return EARTH_FIRE;
			} else if (destination == WATER) {
				return EARTH_WATER;
			}
		} else if (source == FIRE) {
			if (destination == WIND) {
				return FIRE_WIND;
			} else if (destination == EARTH) {
				return FIRE_EARTH;
			} else if (destination == FIRE) {
				return FIRE_FIRE;
			} else if (destination == WATER) {
				return FIRE_WATER;
			}
		} else if (source == WATER) {
			if (destination == WIND) {
				return WATER_WIND;
			} else if (destination == EARTH) {
				return WATER_EARTH;
			} else if (destination == FIRE) {
				return WATER_FIRE;
			} else if (destination == WATER) {
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
