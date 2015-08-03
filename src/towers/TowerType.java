package towers;

import utilities.Constants;
import creeps.DamageType;
import projectiles.*;
//EARTH, FIRE, WATER, WIND, LIGHT, DARK, PHYSICAL;
public enum TowerType {
	//TODO I want each (upgraded) tower to have it's unique projectile effect too
	EARTH (new BaseAttributeList(){{
		name                = "Earth";
		mainDamageType      = DamageType.EARTH;
		baseWidth			= 2;
		baseHeight			= 2;
		baseDamageArray		= new float[]{/*E*/10, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/60};
		baseSlowDuration	= 0;
		baseCost			= 200;
		baseAttackCoolDown	= 15f;
		baseDamageSplash	= 0.2f;
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
		baseDamageArray		= new float[]{/*E*/0, /*F*/35, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDuration	= 0;
		baseCost			= 200;
		baseAttackCoolDown	= 12f;
		baseDamageSplash	= 0.25f;
		baseEffectSplash	= 0.25f;
		baseSplashRadius	= 1.5f;
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
		baseDamageArray		= new float[]{/*E*/0, /*F*/0, /*WA*/15, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDuration	= 10;
		baseCost			= 200;
		baseAttackCoolDown	= 10f;
		baseDamageSplash	= 0f;
		baseEffectSplash	= 0.25f;
		baseSplashRadius	= 1f;
		baseRange			= 7.5f;
		baseSlow			= 0.3f;
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
		baseDamageArray		= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/10, /*L*/0, /*D*/0, /*P*/10};
		baseSlowDuration	= 10;
		baseCost			= 200;
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
	//TODO should the base stats of the towers be identical to their downgrade?
	EARTH_EARTH (new BaseAttributeList(){{
		//does an AOE earthquake? cannot hit flying
		name				= "Earthquake";
		downgradeType 		= EARTH;
		mainDamageType      = DamageType.EARTH;
		baseWidth			= 2;
		baseHeight			= 2;
		baseDamageArray		= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/100};
		baseSlowDuration	= 0;
		baseAttackCoolDown	= 15f;
		baseDamageSplash	= 0f;
		baseEffectSplash	= 0f;
		baseSplashRadius	= 0f;
		baseRange			= 8.5f;
		baseSlow			= 0f;
		hitsAir				= false;
		hitsGround			= true;
		upgrades			= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Fault Lines";
						 text 		= "Increase the base RANGE";
						 isBase		= true;
						 baseCost   = 400;}
						 public void upgrade(Tower t) { t.range += 2; }
					},
					new Upgrade() {
						{name		= "Tectonics";
						 text 		= "Increase the base pulse rate";
						 isBase		= true;
						 baseCost   = 500;}
						 public void upgrade(Tower t) { t.attackCoolDown -= 3; }
					},
					new Upgrade() {
						{name		= "Channel the Earth";
						 text 		= "Converts each point of PHYSICAL damage to 1.5 points of EARTH damage";
						 isBase		= false;
						 baseCost   = 2000;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.EARTH.ordinal()] += t.damageArray[Constants.NUM_DAMAGE_TYPES - 1] * 1.5f; t.damageArray[Constants.NUM_DAMAGE_TYPES - 1] = 0; }
					},
					new Upgrade() {
						{name		= "Living Earth";
						 text 		= "All earth tiles deal EARTH damage in a circle around them";
						 isBase		= false;
						 baseCost   = 5000;}
						 public void upgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "Hardened Earth";
						 text 		= "Double the PHYSICAL Damage";
						 isBase 	= false;
						 baseCost 	= 600;}
						 public void upgrade(Tower t) { t.damageArray[Constants.NUM_DAMAGE_TYPES - 1] *= 2; }
					},
					new Upgrade() {
						{name		= "Fault Lines";
						 text 		= "Increase the base RANGE";
						 isBase		= true;
						 baseCost   = 400;}
						 public void upgrade(Tower t) { t.range += 2; }
					},
					new Upgrade() {
						{name		= "Fragmentation";
						 text 		= "Deal additional PHYSICAL damage as a bleed";
						 isBase		= true;
						 baseCost   = 2500;}
						 public void upgrade(Tower t) { t.damageArray[Constants.NUM_DAMAGE_TYPES - 1] += 50; }
					},
					new Upgrade() {
						{name		= "Grievous Wound";
						 text 		= "Creep affected by this Tower's bleed have reduced TOUGHNESS";
						 isBase		= false;
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
		mainDamageType      = DamageType.EARTH;
		baseWidth			= 2;
		baseHeight			= 2;
		baseDamageArray		= new float[]{/*E*/10, /*F*/0, /*WA*/10, /*WI*/0, /*L*/0, /*D*/0, /*P*/70};
		baseSlowDuration	= 0;
		baseAttackCoolDown	= 13f;
		baseDamageSplash	= 0f;
		baseEffectSplash	= 0f;
		baseSplashRadius	= 0f;
		baseRange			= 8.5f;
		baseSlow			= 0f;
		hitsAir				= false;
		hitsGround			= true;
		upgrades			= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "";
						 text 		= "Increases the base radius of the stalagmites";
						 isBase		= true;
						 baseCost   = 400;}
						 public void upgrade(Tower t) { ((TowerEarthWater) t).areaRadius += 1; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Increases the radius even more and the range of the tower";
						 isBase		= true;
						 baseCost   = 500;}
						 public void upgrade(Tower t) { ((TowerEarthWater) t).areaRadius += 1; t.range += 1; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Splash Radius is added to the range of this tower, and now applies splash damage to all affected";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) { ((TowerEarthWater) t).areaRadius += t.splashRadius; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Siphon bonus coefficient increased to 0.75 for splash coefficients";
						 isBase		= false;
						 baseCost   = 3000;}
						 public void upgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "Power of the Earth";
						 text 		= "Increases the base EARTH damage";
						 isBase 	= true;
						 baseCost 	= 600;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.EARTH.ordinal()] += 40; }
					},
					new Upgrade() {
						{name		= "Hardening";
						 text 		= "Increases the base PHYSICAL damage";
						 isBase		= true;
						 baseCost   = 400;}
						 public void upgrade(Tower t) { t.damageArray[Constants.NUM_DAMAGE_TYPES - 1] += 40; }
					},
					new Upgrade() {
						{name		= "Elemental Balance";
						 text 		= "Adds 40% of PHYSICAL damage as WATER damage";
						 isBase		= false;
						 baseCost   = 1500;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.EARTH.ordinal()] += t.damageArray[Constants.NUM_DAMAGE_TYPES - 1] * .4f; }
					},
					new Upgrade() {
						{name		= "Burial";
						 text 		= "Doubles the damage of this tower";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) { for (int i=0;i<Constants.NUM_DAMAGE_TYPES-1;i++) { t.damageArray[i]*=2; } }
					},
				}
		};
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
		name     			= "Cold Snap";
		mainDamageType      = DamageType.WATER;
		baseWidth  			= 2;
		baseHeight   		= 2;
		baseDamageArray  	= new float[]{/*E*/0, /*F*/0, /*WA*/15, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDuration 	= 10;
		baseAttackCoolDown 	= 10f;
		baseDamageSplash 	= 0f;
		baseEffectSplash 	= 0f;
		baseSplashRadius 	= 1f;
		baseRange   		= 7.5f;
		baseSlow   			= 0.3f;
		hitsAir    			= false;
		hitsGround   		= true;
		upgrades   = new Upgrade[][]{
				{
					 new Upgrade() {
						 {name  	 = "First Frost";
				          text   	 = "Increases base RANGE";
				          isBase  	 = true;
				          baseCost   = 400;}
						  public void upgrade(Tower t) { t.range += 2; }
						 
				     },
				     new Upgrade() {
				    	 {name 	 	 = "Chilling Breath";
				    	  text  	 = "Increases base SLOW Duration";
				    	  isBase  	 = true;
				    	  baseCost   = 500;}
				    	  public void upgrade(Tower t) { t.slowDurationArray[DamageType.WATER.ordinal()] += 5; }
				     },
				     new Upgrade() {
				    	 {name 		 = "Chilled to the Bone";
				    	  text  	 = "Doubles WATER Slow potency";
				    	  isBase 	 = false;
				    	  baseCost   = 2000;}
				    	  public void upgrade(Tower t) { t.slowArray[DamageType.WATER.ordinal()] *= 2; }
				     },
				     new Upgrade() {
				    	 {name 		 = "Sheer Cold";
				    	  text 		 = "Roots Enemies in place";
				    	  isBase 	 = false;
				    	  baseCost   = 5000;}
				    	  public void upgrade(Tower t) {  }
				     },
				},
				{
				     new Upgrade() {
				      	 {name 		 = "Churning Waters";
				      	  text   	 = "Increases base pulse rate";
				      	  isBase	 = true;
				      	  baseCost   = 600;}
				      	  public void upgrade(Tower t) { t.attackCoolDown -= 3; }
				     },
				     new Upgrade() {
				    	 {name 		 = "Black Ice";
				    	  text   	 = "Increases base WATER damage";
				    	  isBase  	 = true;
				    	  baseCost   = 700;}
				    	  public void upgrade(Tower t) { t.damageArray[DamageType.WATER.ordinal()] += 50; }
				     },
				     new Upgrade() {
				    	 {name  	 = "Poisoned Well";
				    	  text  	 = "Slowed Enemies take additional damage over time and double WATER damage";
				    	  isBase  	 = false;
				    	  baseCost   = 4500;}
				    	  public void upgrade(Tower t) {  }
				     },
				     new Upgrade() {
				    	 {name  	 = "Cleanse";
				    	  text   	 = "Consumes all damage over time effects on enemies hit";
				    	  isBase  	 = false;
				    	  baseCost   = 5000;}
				    	  public void upgrade(Tower t) {  }
				     },
				}
		};
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
		mainDamageType      = DamageType.WIND;
		baseWidth			= 2;
		baseHeight			= 2;
		baseDamageArray		= new float[]{/*E*/0, /*F*/10, /*WA*/0, /*WI*/25, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDuration	= 0;
		baseCost			= 200;
		baseAttackCoolDown	= 7f;
		baseDamageSplash	= 0.10f;
		baseEffectSplash	= 0.10f;
		baseSplashRadius	= 0f;
		baseRange			= 8.5f;
		baseSlow			= 0f;
		hitsAir				= true;
		hitsGround			= true;
		upgrades			= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Forking";
						 text 		= "Increase the maximum chaining by 3";
						 isBase		= false;
						 baseCost   = 400;}
						 public void upgrade(Tower t) { ((TowerWindFire) t).maxChains += 3; }
					},
					new Upgrade() {
						{name		= "Conductivity";
						 text 		= "Increase all ELEMENTAL DAMAGE done by this tower";
						 isBase		= false;
						 baseCost   = 1000;}
						 public void upgrade(Tower t) { for (int i=0;i<Constants.NUM_DAMAGE_TYPES-1;i++) { t.damageArray[i]*=1.25; } }
					},
					new Upgrade() {
						{name		= "No Mercy";
						 text 		= "The tower may chain to a target it has already hit";
						 isBase		= false;
						 baseCost   = 2000;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Superconducting";
						 text 		= "Removes the DAMAGE and EFFECT penalty for chaining";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) { ((TowerWindFire) t).chainPenalty = 1; }
					},
				},
				{
					new Upgrade() {
						{name		= "Cloud Cover";
						 text 		= "Increases the base RANGE";
						 isBase 	= true;
						 baseCost 	= 600;}
						 public void upgrade(Tower t) { t.range += 2; }
					},
					new Upgrade() {
						{name		= "Brewing Storm";
						 text 		= "Increase the base FIRE and WIND damage";
						 isBase		= true;
						 baseCost   = 1500;}
						 public void upgrade(Tower t) { t.damageArray[1] += 30; t.damageArray[3] += 30; }
					},
					new Upgrade() {
						{name		= "Short Circuiting";
						 text 		= "Drains a creep's SHIELD at twice the rate";
						 isBase		= false;
						 baseCost   = 2500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Electrocution";
						 text 		= "Shocks targets for a short duration";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) {  }
					},
				}
		};
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
