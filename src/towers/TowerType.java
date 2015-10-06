package towers;

import utilities.GameConstants;
import creeps.DamageType;

//TODO: Have to consider the possibility that toughness is too strong. It mitigates each type of damage.
public enum TowerType {
	//TODO: I want to properly comment each of the enums so they are working for the javadoc and you can get information about each tower.
	//TODO: Instead of using "isBase" just modify the base values directly. This way we can mix. Need to clone the attribute lists for each tower then, but no biggie.
	//TODO: Come up with consistent wording and formatting for descriptions of upgrades
	//TODO: Going to create different siphon coefficients for different stats that vary by tower
	//TODO: Make an update in Tower that applies global effects (e.g. On one level we want all towers to have the additional projectile effect that they heal enemies on hit)
	//TODO: Upgrades that modify the damage against certain types of creep?
	
	//TODO: Review and set all slow duration and effect arrays. Make sure the effectiveness is set to fractions, not larger than 1.
	//TODO: This can be done when we set all of the siphon coefficients.
	EARTH (new BaseAttributeList(){{
		name                  	= "Earth";
		baseWidth			  	= 2;
		baseHeight			  	= 2;
		baseDamageArray		  	= new float[]{/*E*/10, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/60};
		baseSlowDurationArray 	= new int[]{/*E*/12, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/12};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseCost			  	= 750;
		baseAttackCooldown	  	= 15f;
		baseDamageSplash		= 0.2f;
		baseEffectSplash		= 0f;
		baseSplashRadius		= 2f;
		baseRange				= 6.5f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= null;
	}}),
	FIRE (new BaseAttributeList(){{
		name               	 	= "Fire";
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/35, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/10, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseCost				= 900;
		baseAttackCooldown		= 12f;
		baseDamageSplash		= 0.25f;
		baseEffectSplash		= 0.25f;
		baseSplashRadius		= 1.5f;
		baseRange				= 7.5f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= null;
	}}),
	WATER (new BaseAttributeList(){{
		name               	 	= "Water";
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/15, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/10, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0.15f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseCost				= 850;
		baseAttackCooldown		= 10f;
		baseDamageSplash		= 0f;
		baseEffectSplash		= 0.25f;
		baseSplashRadius		= 1f;
		baseRange				= 7.5f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= null;
	}}),
	WIND (new BaseAttributeList(){{
		name               	 	= "Wind";
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/17, /*L*/0, /*D*/0, /*P*/17};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/4, /*L*/0, /*D*/0, /*P*/4};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0.05f, /*L*/0, /*D*/0, /*P*/0};
		baseCost				= 1000;
		baseAttackCooldown		= 8.4f;
		baseDamageSplash		= 0f;
		baseEffectSplash		= 0.10f;
		baseSplashRadius		= 2.3f;
		baseRange				= 8.6f;
		hitsAir					= true;
		hitsGround				= true;
		upgrades				= null;
	}}),
	//TODO should the base stats of the towers be identical to their downgrade?
	/**
	 * Towers of this type deal damage and slows to all creep around it. Currently does not apply splash effects or on-hit effects.
	 * Deals primarily physical damage, barring an upgrade that converts physical damage to earth damage.
	 * Track 1:
	 * 	Upgrade 1:
	 *  Upgrade 2:
	 *  Upgrade 3:
	 *  Upgrade 4:
	 * Track 2:
	 * 	Upgrade 1:
	 *  Upgrade 2:
	 *  Upgrade 3:
	 *  Upgrade 4:
	 */
	EARTH_EARTH (new BaseAttributeList(){{
		//does an AOE earthquake? cannot hit flying
		name					= "Earthquake";
		downgradeType 			= EARTH;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/100};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/8, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/8};
		baseAttackCooldown		= 15f;
		baseDamageSplash		= 0.23f;
		baseEffectSplash		= 0f;
		baseSplashRadius		= 3f;
		baseRange				= 8.1f;
		damageSiphon			= 0.54f; //TODO: Should this be an array of siphon coefficients?
		slowDurationSiphon		= 0.28f;
		slowSiphon				= 0.28f;
		attackCooldownSiphon	= 4.2f;
		damageSplashSiphon		= 0.45f;
		effectSplashSiphon		= 0.32f;
		radiusSplashSiphon		= 0.51f;
		rangeSiphon				= 0.07f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Fault Lines";
						 text 		= "Increase the base RANGE";
						 baseCost   = 500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 2; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Tectonics";
						 text 		= "Increase the base pulse rate";
						 baseCost   = 500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 3; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Channel the Earth";
						 text 		= "Converts each point of PHYSICAL damage to 1.8 points of EARTH damage";
						 baseCost   = 2000;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.EARTH.ordinal()] += t.damageArray[DamageType.PHYSICAL.ordinal()] * 1.8f; t.damageArray[DamageType.PHYSICAL.ordinal()] = 0; } //TODO: Do we want to set the physical damage to 0?
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Living Earth";
						 text 		= "All earth tiles deal EARTH damage in a circle around them";
						 baseCost   = 5000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerEarthEarth) t).detonationModifier = 0.10f; }
					},
				},
				{
					new Upgrade() {
						{name		= "Hardened Earth";
						 text 		= "Double the PHYSICAL damage";
						 baseCost 	= 1300;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] *= 2; }
					},
					new Upgrade() {
						{name		= "Magnitude";
						 text 		= "Increase the base RANGE and PHYSICAL damage";
						 baseCost   = 400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 1; t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 34; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Fragmentation";
						 text 		= "Deal additional PHYSICAL damage as a bleed";
						 baseCost   = 2500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 50; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerEarthEarth) t).bleedModifier = 0.50f; ((TowerEarthEarth) t).bleedDuration = 18; }
					},
					new Upgrade() {
						{name		= "Grievous Wound";
						 text 		= "Creep affected by this Tower's bleed have reduced PHYSICAL damage resistance";
						 baseCost   = 5000;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerEarthEarth) t).shredModifier = 0.04f; ((TowerEarthEarth) t).armorShredDuration = 18; ((TowerEarthEarth) t).maxShredStacks = 5; }
					},
				}
		};
	}}), 
	EARTH_FIRE (new BaseAttributeList(){{
		//does tons of damage and armor penetration at some point?
		name 					= "Ironworks";
		downgradeType 			= EARTH;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/10, /*F*/10, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/50};
		baseSlowDurationArray 	= new int[]{/*E*/12, /*F*/7, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/10};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 13.1f;
		baseDamageSplash		= 0.25f;
		baseEffectSplash		= 0.25f;
		baseSplashRadius		= 2.2f;
		baseRange				= 8.6f;
		damageSiphon			= 0.60f;
		slowDurationSiphon		= 0.18f;
		slowSiphon				= 0.21f;
		attackCooldownSiphon	= 4.8f;
		damageSplashSiphon		= 0.33f;
		effectSplashSiphon		= 0.27f;
		radiusSplashSiphon		= 0.59f;
		rangeSiphon				= 0.06f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Rapid Fire";
						 text 		= "Reduces base ATTACK COOLDOWN";
						 baseCost   = 400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 1.9; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Aerodynamics";
						 text 		= "Increases the range";
						 baseCost   = 500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 1.1; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.range += 3.1; }
					},
					new Upgrade() {
						{name		= "Gather the Scraps";
						 text 		= "Tower applies a wealth debuff to minions";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerEarthFire) t).wealthDuration = 30; ((TowerEarthFire) t).wealthModifier = 1.15f; }
					},
					new Upgrade() {
						{name		= "Motherload";
						 text 		= "Increases the duration and effectiveness of wealth";
						 baseCost   = 5000;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerEarthFire) t).wealthDuration += 20; ((TowerEarthFire) t).wealthModifier += 0.30; }
					},
				},
				{
					new Upgrade() {
						{name		= "Arms Race";
						 text 		= "Increases the base PHYSICAL damage";
						 baseCost 	= 600;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 50; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Armory";
						 text 		= "Increases the PHYSICAL damage";
						 baseCost   = 500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 13; }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] *= 1.5; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "FMJ";
						 text 		= "Attacks and splash ignores armor";
						 baseCost   = 1500;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Piercing";
						 text 		= "Attacks apply a stacking armor shred";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerEarthFire) t).shredModifier = 0.015f; ((TowerEarthFire) t).shredDuration = 15; ((TowerEarthFire) t).maxShredStacks = 10; }
					},
				}
		};
	}}),
	EARTH_WATER (new BaseAttributeList(){{
		//this will do rupture spikes on a selected area
		name 					= "Stalagmite";
		downgradeType 			= EARTH;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/30, /*F*/0, /*WA*/30, /*WI*/0, /*L*/0, /*D*/0, /*P*/70};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 13f;
		baseDamageSplash		= 0.15f;
		baseEffectSplash		= 0.15f;
		baseSplashRadius		= 1.3f;
		baseRange				= 8.6f;
		damageSiphon			= 0.78f;
		slowDurationSiphon		= 0.12f;
		slowSiphon				= 0.12f;
		attackCooldownSiphon	= 4.0f;
		damageSplashSiphon		= 0.33f;
		effectSplashSiphon		= 0.27f;
		radiusSplashSiphon		= 0.59f;
		rangeSiphon				= 0.06f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Eon";
						 text 		= "Increases the base radius of the stalagmites";
						 baseCost   = 400;}
						 public void baseUpgrade(Tower t) { ((TowerEarthWater) t).areaRadius += 1; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Cave Building";
						 text 		= "Increases the radius even more and the range of the tower";
						 baseCost   = 1300;}
						 public void baseUpgrade(Tower t) { ((TowerEarthWater) t).areaRadius += 1.2; t.baseAttributeList.baseRange += 0.4f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.range += 2.4f; }
					},
					new Upgrade() {
						{name		= "Echo";
						 text 		= "Splash Radius is added to the range and hit radius of this tower, and now applies splash damage to all affected";
						 baseCost   = 6200;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerEarthWater) t).areaRadius += t.splashRadius / 4; t.range += t.splashRadius; ((TowerEarthWater) t).doesSplash = true; }
					},
					new Upgrade() {
						{name		= "Shattering";
						 text 		= "Increases splash siphon coefficients, and now applies on hits to all affected";
						 baseCost   = 5300;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.radiusSplashSiphon += 0.15f; t.baseAttributeList.effectSplashSiphon += 0.15f; t.baseAttributeList.damageSplashSiphon += 0.15f; }
						 public void midSiphonUpgrade(Tower t) {  }
						 public void postSiphonUpgrade(Tower t) { ((TowerEarthWater) t).doesOnHit = true; }
					},
				},
				{
					new Upgrade() {
						{name		= "Power of the Earth";
						 text 		= "Increases the base EARTH damage";
						 baseCost 	= 600;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.EARTH.ordinal()] += 40; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Hardening";
						 text 		= "Increases the base PHYSICAL damage";
						 baseCost   = 400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 40; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Elemental Balance";
						 text 		= "Adds 40% of PHYSICAL damage as WATER damage";
						 baseCost   = 2900;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.WATER.ordinal()] += t.damageArray[DamageType.PHYSICAL.ordinal()] * .4f; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Burial";
						 text 		= "Doubles the damage of this tower";
						 baseCost   = 9001;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { for (int i=0;i<GameConstants.NUM_DAMAGE_TYPES-1;i++) { t.damageArray[i]*=2; } }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				}
		};
	}}),
	EARTH_WIND (new BaseAttributeList(){{
		//has enormous splash radius
		//basically "explodes" outward
		//does not target flying but splash hits flying
		name					= "Fragmentation";
		downgradeType 			= EARTH;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/10, /*F*/0, /*WA*/0, /*WI*/20, /*L*/0, /*D*/0, /*P*/60};
		baseSlowDurationArray 	= new int[]{/*E*/9, /*F*/0, /*WA*/0, /*WI*/4, /*L*/0, /*D*/0, /*P*/8};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 14f;
		baseDamageSplash		= 0.15f;
		baseEffectSplash		= 0.15f;
		baseSplashRadius		= 5f;
		baseRange				= 9.0f;
		damageSiphon			= 0.47f;
		slowDurationSiphon		= 0.10f;
		slowSiphon				= 0.10f;
		attackCooldownSiphon	= 5.2f;
		damageSplashSiphon		= 0.46f;
		effectSplashSiphon		= 0.37f;
		radiusSplashSiphon		= 0.39f;
		rangeSiphon				= 0.07f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Explosive Force";
						 text 		= "Increases the splash damage and effectiveness";
						 baseCost   = 1350;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageSplash += .1; t.baseAttributeList.baseEffectSplash += .1; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.damageSplash += .1; t.effectSplash += .1; }
					},
					new Upgrade() {
						{name		= "Explosive Force";
						 text 		= "Increases the splash damage and effectiveness";
						 baseCost   = 1350;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageSplash += .1; t.baseAttributeList.baseEffectSplash += .1; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.damageSplash += .1; t.effectSplash += .1; }
					},
					new Upgrade() {
						{name		= "Blast Zone";
						 text 		= "Greatly increases the splash radius";
						 baseCost   = 2600;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { t.splashRadius *= 1.1f; }
						 public void postSiphonUpgrade(Tower t) { t.splashRadius += 3; }
					},
					new Upgrade() {
						{name		= "Ground Zero";
						 text 		= "Greatly increases the base splash radius and splash hits flying creeps";
						 baseCost   = 5000;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadius += 5; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.splashHitsAir = true; }
					},
				},
				{
					new Upgrade() {
						{name		= "Howl";
						 text 		= "Reduces base and non-base ATTACK COOLDOWN";
						 baseCost 	= 1000;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 0.7f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.attackCooldown -= 1.6f; }
					},
					new Upgrade() {
						{name		= "Brewing Storm";
						 text 		= "Increase base WIND damage and base RANGE";
						 baseCost   = 1600;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WIND.ordinal()] += 44; t.baseAttributeList.baseRange += 2f;}
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Whipping Winds";
						 text 		= "Reduces ATTACK COOLDOWN and adds 50% of PHYSICAL damage as WIND damage";
						 baseCost   = 2150;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { t.attackCooldown -= 3; t.damageArray[DamageType.WIND.ordinal()] += 0.5 * t.damageArray[DamageType.PHYSICAL.ordinal()]; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Sand Blast";
						 text 		= "Converts ATTACK COOLDOWN to PHYSICAL damage and now fires a blast of sand";
						 baseCost   = 8000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) {  t.damageArray[DamageType.PHYSICAL.ordinal()] += (13f / t.attackCooldown) * 40; }
						 public void postSiphonUpgrade(Tower t) { }
					},
				}
		};
	}}), 
	FIRE_EARTH (new BaseAttributeList(){{ //TODO: Needs work, doesn't synergize with self
		name					= "Magma";
		downgradeType 			= FIRE;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/20, /*F*/35, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/35};
		baseSlowDurationArray 	= new int[]{/*E*/11, /*F*/7, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0.05f, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0.05f};
		baseAttackCooldown		= 14f;
		baseDamageSplash		= 0.05f;
		baseEffectSplash		= 0.05f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		damageSiphon			= 0.52f;
		slowDurationSiphon		= 0.16f;
		slowSiphon				= 0.18f;
		attackCooldownSiphon	= 4.7f;
		damageSplashSiphon		= 0.39f;
		effectSplashSiphon		= 0.33f;
		radiusSplashSiphon		= 0.33f;
		rangeSiphon				= 0.08f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Volcano";
						 text 		= "The tower now does AoE damage";
						 baseCost   = 2500;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Eruption Force";
						 text 		= "Increase BASE range of the tower";
						 baseCost   = 1300;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 2.5f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Pyroclastic Flow";
						 text 		= "Increases BASE damage and reduces BASE attack cooldown";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 2.2; t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()] += 33; t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 33; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Supervolcano";
						 text 		= "Dramatically increases range and fire damage. Poisons all enemies.";
						 baseCost   = 11000;}
						 public void baseUpgrade(Tower t) {   }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=20; }
						 public void postSiphonUpgrade(Tower t) { t.range += 10f; t.damageArray[DamageType.FIRE.ordinal()] += 145; ((TowerFireEarth) t).poisonModifier=0.10f; ((TowerFireEarth) t).poisonDuration=18; ((TowerFireEarth) t).maxPoisonStacks = 1; }
					},
				},
				{
					new Upgrade() {
						{name		= "Melt Armor";
						 text 		= "Attacks reduce armor by a flat amount";
						 baseCost 	= 1400;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerFireEarth) t).armorShredModifier = 1; ((TowerFireEarth) t).armorShredDuration = 20; ((TowerFireEarth) t).maxArmorShredStacks = 10; }
					},
					new Upgrade() {
						{name		= "Sear Flesh";
						 text 		= "Attacks reduce toughness by a flat amount";
						 baseCost   = 2400;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerFireEarth) t).toughnessShredModifier = 1; ((TowerFireEarth) t).toughnessShredDuration = 17; ((TowerFireEarth) t).maxToughnessShredStacks = 9; }
					},
					new Upgrade() {
						{name		= "Swelling";
						 text 		= "Attacks against afflicted enemies deal additional FIRE damage";
						 baseCost   = 3000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerFireEarth) t).DOHModifier = 0.05f; ((TowerFireEarth) t).DOHLifetime = 25; ((TowerFireEarth) t).maxDOHStacks = 1; ((TowerFireEarth) t).doesOnHit = true; }
					},
					new Upgrade() {
						{name		= "Blood Boil";
						 text 		= "Deals additional FIRE damage as a percent of max health";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerFireEarth) t).percentMaxHealthModifier = 0.02f; }
					},
				}
		};
	}}), 
	FIRE_FIRE (new BaseAttributeList(){{
		//this tower ignites enemies with all types of elemental damage
		name					= "Greater Fire";
		downgradeType 			= FIRE;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/70, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/25};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/14, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0.13f, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 12f;
		baseDamageSplash		= 0.25f;
		baseEffectSplash		= 0.25f;
		baseSplashRadius		= 2f;
		baseRange				= 8.5f;
		damageSiphon			= 0.80f;
		slowDurationSiphon		= 0.16f;
		slowSiphon				= 0.18f;
		attackCooldownSiphon	= 4.8f;
		damageSplashSiphon		= 0.44f;
		effectSplashSiphon		= 0.38f;
		radiusSplashSiphon		= 0.37f;
		rangeSiphon				= 0.09f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Stoke the Flames";
						 text 		= "Increases the base FIRE damage";
						 baseCost   = 500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()] += 50; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Heating Up"; //TODO ugh
						 text 		= "Increases base FIRE damage then doubles FIRE damage";
						 baseCost   = 1700;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()] += 22; }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()] *= 2; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Intensity";
						 text 		= "Increases then triples FIRE damage";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) {  t.damageArray[DamageType.FIRE.ordinal()] += 40; t.damageArray[DamageType.FIRE.ordinal()] *= 3; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Elemental Fire";
						 text 		= "Applies a bleed of each element type equal to a percentage of your FIRE damage";
						 baseCost   = 8700;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerFireFire) t).bleedModifier = 1; ((TowerFireFire) t).bleedDuration = 15; ((TowerFireFire) t).bleedTiming = 3; ((TowerFireFire) t).maxBleedStacks = 3; }
					},
				},
				{
					new Upgrade() {
						{name		= "Spreading Flames";
						 text 		= "Increase base splash range and effectiveness";
						 baseCost 	= 1300;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadius += 1; t.baseAttributeList.baseDamageSplash += .10; t.baseAttributeList.baseEffectSplash += .10;}
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Uncontained";
						 text 		= "Increases base range, splash damage coefficient, and damage siphon coefficient";
						 baseCost   = 1900;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 1.2; t.baseAttributeList.baseDamageSplash += .15; t.baseAttributeList.damageSiphon += 0.05; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Scorched Earth";
						 text 		= "Scorches the earth around the target, damaging all enemies that pass through it";
						 baseCost   = 5500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerFireFire) t).effectPatchDuration = 12; ((TowerFireFire) t).effectPatchTiming = 3; ((TowerFireFire) t).effectPatchDamageModifier = 0.15f; }
					},
					new Upgrade() {
						{name		= "Burning Flesh";
						 text 		= "Scorched Earth reduces all elemental resistances";
						 baseCost   = 7000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { ((TowerFireFire) t).effectPatchShredDuration = 14; ((TowerFireFire) t).effectPatchShredModifier = 0.0003f; ((TowerFireFire) t).maxShredStacks = 4;  }
					},
				}
		};
	}}),  
	FIRE_WATER (new BaseAttributeList(){{
		//attacks ignore all types of defenses
		name					= "Plasma";
		downgradeType 			= FIRE;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/70, /*WA*/50, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 12.3f;
		baseDamageSplash		= 0.25f;
		baseEffectSplash		= 0.25f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Fission";
						 text 		= "Increases base damage of all types and siphon bonus";
						 isBase		= true;
						 baseCost   = 2700;}
						 public void upgrade(Tower t) { t.siphonBonus += .25f; for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){DamageType d = DamageType.values()[i]; if (d!=DamageType.LIGHT || d!=DamageType.DARK) { t.damageArray[i]+=20; }}}
					},
					new Upgrade() {
						{name		= "Amplification";
						 text 		= "Multiplies all types of damage";
						 isBase		= false;
						 baseCost   = 2200;}
						 public void upgrade(Tower t) { for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){DamageType d = DamageType.values()[i]; if (d!=DamageType.LIGHT || d!=DamageType.DARK) { t.damageArray[i]*=1.5f; }} }
					},
					new Upgrade() {
						{name		= "Superheated";
						 text 		= "Attacks ignore all types of damage resistance";
						 isBase		= false;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Projectile hits all creeps it passes through until reaching its target";
						 isBase		= false;
						 baseCost   = 6000;}
						 public void upgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "Chain Reaction";
						 text 		= "Increases base splash radius";
						 isBase 	= true;
						 baseCost 	= 1400;}
						 public void upgrade(Tower t) { t.splashRadius += 3; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Increases splash radius and effectiveness";
						 isBase		= false;
						 baseCost   = 3400;}
						 public void upgrade(Tower t) { t.splashRadius += 5; t.damageSplash += .1f; t.effectSplash += .1f; }
					},
					new Upgrade() {
						{name		= "Ground Zero";
						 text 		= "Leaves behind a zone that, when passed through, deals damage based on max health";
						 isBase		= false;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Gamma Radiation";
						 text 		= "Increases damage. Enemies in Ground Zero have their health and shield regeneration permanently set to zero";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.WATER.ordinal()] += 75; t.damageArray[DamageType.FIRE.ordinal()] += 75; }
					},
				}
		};
	}}), 
	//TODO: I'm definitely sensing a power creep here. Again we need to normalize some stats I think.
	FIRE_WIND (new BaseAttributeList(){{
		//this tower fires a laser thing that passes through enemies and goes a fixed distance
		name					= "Firestorm";
		downgradeType 			= FIRE;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/40, /*WA*/0, /*WI*/5, /*L*/0, /*D*/0, /*P*/20};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0.10f, /*WA*/0, /*WI*/0.10f, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 10f;
		baseDamageSplash		= 0f;
		baseEffectSplash		= 0f;
		baseSplashRadius		= 0f;
		baseRange				= 8.8f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Global Warming";
						 text 		= "Increases base RANGE";
						 isBase		= true;
						 baseCost   = 1250;}
						 public void upgrade(Tower t) { t.range += 2; }
					},
					new Upgrade() {
						{name		= "Dry Air";
						 text 		= "Greatly increases RANGE";
						 isBase		= false;
						 baseCost   = 2500;}
						 public void upgrade(Tower t) { t.range += 7; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Increases base FIRE and WIND damage and widens the laser";
						 isBase		= true;
						 baseCost   = 3500;}
						//TODO: This is a good candidate for modifying the siphon coefficients
						 public void upgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=67f; t.damageArray[DamageType.WIND.ordinal()]+= 34; }
					},
					new Upgrade() {
						{name		= "Heat Column";
						 text 		= "Increases laser width and range, hits flying, and applies splash effects to affected creep";
						 isBase		= false;
						 baseCost   = 6000;}
						 public void upgrade(Tower t) { t.hitsAir = true; t.range += 4.1; }
					},
				},
				{
					new Upgrade() {
						{name		= "";
						 text 		= "Multiplies FIRE damage";
						 isBase 	= false;
						 baseCost 	= 2000;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()] *= 1.5f; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Multiply all damage types";
						 isBase		= false;
						 baseCost   = 4300;}
						 public void upgrade(Tower t) { for(int i=0; i<GameConstants.NUM_DAMAGE_TYPES; i++) { t.damageArray[i] *= 1.2; } }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Greatly increases siphon coefficients";
						 isBase		= true;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) { /*//TODO */}
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Increases siphon coefficients of all towers nearby"; //TODO: Could I increase nearby towers' siphon coefficients?
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) {  }
					},
				}
		};
	}}), 
	WATER_EARTH (new BaseAttributeList(){{
		//does large AoE slow
		name					= "Mud";
		downgradeType 			= WATER;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/10, /*F*/0, /*WA*/10, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 12.3f;
		baseDamageSplash		= 0f;
		baseEffectSplash		= 0f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Flash Flood";
						 text 		= "Increases the base range";
						 isBase		= true;
						 baseCost   = 900;}
						 public void upgrade(Tower t) { t.range += 2.7f; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Reduces attack cooldown";
						 isBase		= false;
						 baseCost   = 1300;}
						 public void upgrade(Tower t) { t.attackCooldown -= 5; }
					},
					new Upgrade() {
						{name		= "Churning River";
						 text 		= "Reduces enemy armor";
						 isBase		= false;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Heavy Rains";
						 text 		= "Hits air targets, doubles base slow duration, and...";
						 isBase		= true;
						 baseCost   = 2000;}
						 public void upgrade(Tower t) { t.hitsAir = true; for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){t.slowDurationArray[i]*=2;} }
					},
				},
				{
					new Upgrade() {
						{name		= "Thickening Mud";
						 text 		= "Increases base slow";
						 isBase 	= true;
						 baseCost 	= 1900;}
						 public void upgrade(Tower t) { t.slowArray[DamageType.WATER.ordinal()] += .2f; t.slowArray[DamageType.EARTH.ordinal()] += .2f; }
					},
					new Upgrade() {
						{name		= "Cloud Cover";
						 text 		= "Doubles base EARTH and WATER slow duration";
						 isBase		= true;
						 baseCost   = 1500;}
						 public void upgrade(Tower t) { t.slowDurationArray[DamageType.WATER.ordinal()]*=2; t.slowDurationArray[DamageType.EARTH.ordinal()]*=2; }
					},
					new Upgrade() {
						{name		= "Shape the Earth";
						 text 		= "Allows for modification of tile types"; //TODO: Implement this
						 isBase		= true;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Petrification";
						 text 		= "Permanently reduces maximum creep speed";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) {  }
					},
				}
		};
	}}), 
	WATER_FIRE (new BaseAttributeList(){{
		//this tower applies an MR shred for a period of time
		name					= "Purging"; //(?)
		downgradeType 			= WATER;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/50, /*WA*/40, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/10, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0.10f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 13.1f;
		baseDamageSplash		= 0.04f;
		baseEffectSplash		= 0.04f;
		baseSplashRadius		= 1f;
		baseRange				= 8.2f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Watery Grave";
						 text 		= "Increases base WATER damage, slow, and slow duration";
						 isBase		= true;
						 baseCost   = 2700;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.WATER.ordinal()]+=42; t.slowArray[DamageType.WATER.ordinal()]+=0.11f; t.slowDurationArray[DamageType.WATER.ordinal()]+=7; }
					},
					new Upgrade() {
						{name		= "Elemental Fury";
						 text 		= "Reduces base attack cooldown";
						 isBase		= true;
						 baseCost   = 1600;}
						 public void upgrade(Tower t) { t.attackCooldown -= 3.1f; }
					},
					new Upgrade() {
						{name		= "Clarity";
						 text 		= "Reduces disruptor effects for a time"; //TODO: Is this too strong?
						 isBase		= false;
						 baseCost   = 7500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Cleansing";
						 text 		= "Increases the disruptor reduction and disables creep deathrattles for a time"; //TODO: Is this too strong?
						 isBase		= false;
						 baseCost   = 8900;}
						 public void upgrade(Tower t) { }
					},
				},
				{
					new Upgrade() {
						{name		= "Boiling";
						 text 		= "Increases base FIRE damage and reduces base attack cooldown";
						 isBase		= true;
						 baseCost   = 2700;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=57; t.attackCooldown-=1.4f; }
					},
					new Upgrade() {
						{name		= "Scalding";
						 text 		= "Increases base FIRE damage and splash radius";
						 isBase		= true;
						 baseCost   = 3400;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=61; t.splashRadius+=1.3f; } //TODO: Would like this to be mixed base and not
					},
					new Upgrade() {
						{name		= "Third Degree";
						 text 		= "Increases base FIRE and WATER damage and base damage splash coefficient";
						 isBase		= true;
						 baseCost   = 6400;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=158; t.damageArray[DamageType.WATER.ordinal()]+=34; t.damageSplash+=0.075f; } //TODO: again I want to mix base and not base. Not base damage but base splash coefficient
					},
					new Upgrade() {
						{name		= "Superheated";
						 text 		= "Ignores creep FIRE and WATER resistances";
						 isBase		= false;
						 baseCost   = 5320;}
						 public void upgrade(Tower t) {  }
					},
				}
		};
	}}),  
	WATER_WATER (new BaseAttributeList(){{
		//this tower does an aoe slow/freeze
		downgradeType 			= WATER;
		name     				= "Cold Snap";
		baseWidth  				= 2;
		baseHeight   			= 2;
		baseDamageArray  		= new float[]{/*E*/0, /*F*/0, /*WA*/15, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/20, /*WI*/0, /*L*/0, /*D*/0, /*P*/10};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0.40f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0.10f};
		baseAttackCooldown 		= 16f;
		baseDamageSplash 		= 0f;
		baseEffectSplash 		= 0f;
		baseSplashRadius 		= 1f;
		baseRange   			= 7.5f;
		hitsAir    				= false;
		hitsGround   			= true;
		upgrades   				= new Upgrade[][]{
				{
					 new Upgrade() {
						 {name  	 = "First Frost";
				          text   	 = "Increases base RANGE";
				          isBase  	 = true;
				          baseCost   = 900;}
						  public void upgrade(Tower t) { t.range += 2; }
				     },
				     new Upgrade() {
				    	 {name 	 	 = "Chilling Breath";
				    	  text  	 = "Increases base SLOW Duration";
				    	  isBase  	 = true;
				    	  baseCost   = 1400;}
				    	  public void upgrade(Tower t) { t.slowDurationArray[DamageType.WATER.ordinal()] += 17; }
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
				      	  public void upgrade(Tower t) { t.attackCooldown -= 3; }
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
		//Fires a missile that does one of three random effects. Gold, damage, slow, and additional effects
		name					= "Hail";
		downgradeType 			= WATER;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/20, /*WI*/20, /*L*/0, /*D*/0, /*P*/20};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/20, /*WI*/5, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0.20f, /*WI*/0.05f, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 11.1f;
		baseDamageSplash		= 0.25f;
		baseEffectSplash		= 0.25f;
		baseSplashRadius		= 4f;
		baseRange				= 7.4f;
		hitsAir					= true;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 2700;}
						 public void upgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 2200;}
						 public void upgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 6000;}
						 public void upgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase 	= false;
						 baseCost 	= 1400;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 3400;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) {  }
					},
				}
		};
	}}), 
	WIND_EARTH (new BaseAttributeList(){{
		//this tower disorients enemies and makes them walk randomly
		name 					= "Sandstorm";
		downgradeType 			= WIND;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/10, /*F*/0, /*WA*/0, /*WI*/15, /*L*/0, /*D*/0, /*P*/30};
		baseSlowDurationArray 	= new int[]{/*E*/10, /*F*/0, /*WA*/0, /*WI*/10, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 12.3f;
		baseDamageSplash		= 0f;
		baseEffectSplash		= 0f;
		baseSplashRadius		= 0f;
		baseRange				= 8.2f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Convection Currents";
						 text 		= "Reduces base ATTACK COOLDOWN";
						 isBase		= true;
						 baseCost   = 1200;}
						 public void upgrade(Tower t) { t.attackCooldown -= 1.8f; }
					},
					new Upgrade() {
						{name		= "Desertification";
						 text 		= "Increases base RANGE";
						 isBase		= true;
						 baseCost   = 800;}
						 public void upgrade(Tower t) { t.range += 2.3f; }
					},
					new Upgrade() {
						{name		= "Whipping Sands";
						 text 		= "Attacks now knockup affected targets";
						 isBase		= false;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Cyclone";
						 text 		= "Tower now fires in a path and hits all enemies along the way";
						 isBase		= false;
						 baseCost   = 6000;}
						 public void upgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "";
						 text 		= "Increases base RANGE";
						 isBase 	= true;
						 baseCost 	= 1400;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Erosion";
						 text 		= "Increases base EARTH damage";
						 isBase		= false;
						 baseCost   = 2000;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.EARTH.ordinal()]+=56f; }
					},
					new Upgrade() {
						{name		= "Sand Blindness";
						 text 		= "Attacks now disorient targets";
						 isBase		= false;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Blasting Force";
						 text 		= "Multiplies EARTH and WIND damage and creeps move faster during disorient";
						 isBase		= false;
						 baseCost   = 6200;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.WIND.ordinal()]*=1.5f; t.damageArray[DamageType.EARTH.ordinal()]*=1.35f; }
					}, //TODO: Not enough towers have upgrades that modify instead of adding base damage
				}
		};
	}}), 
	WIND_FIRE (new BaseAttributeList(){{
		//this tower chains damage and removes shield (?)
		name 					= "Lightning";
		downgradeType 			= WIND;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/10, /*WA*/0, /*WI*/25, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseCost				= 200;
		baseAttackCooldown		= 7f;
		baseDamageSplash		= 0.10f;
		baseEffectSplash		= 0.10f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		hitsAir					= true;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Forking";
						 text 		= "Increase the maximum chaining by 3";
						 isBase		= false;
						 baseCost   = 400;}
						 public void upgrade(Tower t) { ((TowerWindFire) t).maxChains += 3; } //TODO: Shouldn't be accessing this way
					},
					new Upgrade() {
						{name		= "Conductivity";
						 text 		= "Increase all ELEMENTAL DAMAGE done by this tower";
						 isBase		= false;
						 baseCost   = 1400;}
						 public void upgrade(Tower t) { for (int i=0;i<GameConstants.NUM_DAMAGE_TYPES-1;i++) { t.damageArray[i]*=1.25; } }
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
		name					= "Blizzard";
		downgradeType 			= WIND;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 10.7f;
		baseDamageSplash		= 0f;
		baseEffectSplash		= 0.10f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		hitsAir					= true;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 2700;}
						 public void upgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 2200;}
						 public void upgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 6000;}
						 public void upgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase 	= false;
						 baseCost 	= 1400;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 3400;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) {  }
					},
				}
		};
	}}), 
	WIND_WIND (new BaseAttributeList(){{
		//this tower does a pushback (is this too hard?)
		name 					= "Gale";
		downgradeType 			= WIND;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/5, /*L*/0, /*D*/0, /*P*/50};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 6.9f;
		baseDamageSplash		= 0f;
		baseEffectSplash		= 0f;
		baseSplashRadius		= 2.2f;
		baseRange				= 11f;
		hitsAir					= true;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					//TODO: Can change this to siphon coefficients modification once that is implemented
					new Upgrade() {
						{name		= "Pushback";
						 text 		= "Increases base PHYSICAL damage";
						 isBase		= false;
						 baseCost   = 2700;}
						 public void upgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Focused Attacks";
						 text 		= "Sets the attack cooldown of this tower to 1 and removes all splash effects";
						 isBase		= false;
						 baseCost   = 3000;}
						 public void upgrade(Tower t) { t.attackCooldown = 1; t.splashRadius = 0; }
					},
					new Upgrade() {
						{name		= "Trade Winds";
						 text 		= "Enemies hit by this tower apply a debuff that, when hit, grants towers reduced attack cooldown";
						 isBase		= false;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Windfall";
						 text 		= "Applies a stacking debuff, when hit, grants gold";
						 isBase		= false;
						 baseCost   = 6000;}
						 public void upgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "Gale Force I";
						 text 		= "Increases base WIND damage";
						 isBase 	= true;
						 baseCost 	= 1400;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.WIND.ordinal()]+=28; }
					},
					new Upgrade() {
						{name		= "Gale Force II";
						 text 		= "Increases base WIND damage";
						 isBase		= true;
						 baseCost   = 1900;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.WIND.ordinal()]+=38; }
					},
					new Upgrade() {
						{name		= "Turbulence";
						 text 		= "Attacks now ground flying creep. Creep that are grounded take extra PHYSICAL damage";
						 isBase		= false;
						 baseCost   = 3500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Wind Resistance";
						 text 		= "Deals additional PHYSICAL and WIND damage based on speed of creep";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) {  }
					},
				}
		};
	}});
	
	private BaseAttributeList baseAttributeList;
	
	private TowerType (BaseAttributeList baseAttributeList) {
		baseAttributeList.downgradeType = this;
		baseAttributeList.type = this;
		this.baseAttributeList = baseAttributeList;
	}
	
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

	//TODO: Are these needed or can the attributes just be package private
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
