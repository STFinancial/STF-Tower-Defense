package towers;

import utilities.GameConstants;
import creeps.DamageType;

//TODO: If slows take too many resources, then we can do something where slows under a certain value do not do anything.
//TODO: Working on this, need to go back through and modify slow coefficients then
//TODO: Have to consider the possibility that toughness is too strong. It mitigates each type of damage.

/**
 * Each {@link Tower} has a TowerType which determines the base stats and attributes of the Tower,
 * including its cost, damage, slow, range, splash, size, etc.
 * <br><br>
 * TowerTypes are separated into two tiers: base types and non-base types. Base types are Towers of the four elements:
 * {@link TowerType#EARTH EARTH}, {@link TowerType#FIRE FIRE}, {@link TowerType#WATER WATER}, and {@link TowerType#WIND WIND}. 
 * <br><br>
 * Non-base TowerTypes consist of a combination of these four elements, one element being
 * primary, and the other being secondary, resulting in a total of 16 non-base types.
 * Non-base types can be formed by linking two Towers together through "siphoning," whereby
 * one Tower siphons power from the other. The result is that the Tower that is doing the siphoning
 * will be come a Tower whose primary type is the type of that Tower, and whose secondary type is
 * the type of the Tower being siphoned from (e.g. An EARTH Tower siphons from a FIRE Tower, the result
 * is that the EARTH Tower becomes {@link TowerType#EARTH_FIRE EARTH_FIRE}).
 * @author Timothy
 * @see creeps.DamageType DamageType
 */
public enum TowerType {
	//TODO: I want to properly comment each of the enums so they are working for the javadoc and you can get information about each tower.
	//TODO: Come up with consistent wording and formatting for descriptions of upgrades
	//TODO: Make an update in Tower that applies global effects (e.g. On one level we want all towers to have the additional projectile effect that they heal enemies on hit)
	//TODO: Upgrades that modify the damage against certain types of creep?
	//TODO: Want to move physical to first in the array, but that requires changing all of the array initializers
	
	/**
	 * Basic Tower of the EARTH type.
	 * Earth towers deal a large amount of physical damage
	 * and have a large splash radius and a strong splash damage coefficient.
	 * However, they have a short
	 * range and a very low attack speed.
	 */
	EARTH (new BaseAttributeList(){{
		name                  	= "Earth";
		downgradeType			= null;
		baseWidth			  	= 2;
		baseHeight			  	= 2;
		baseDamageArray		  	= new float[]{/*E*/10, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/60};
		baseSlowDurationArray 	= new int[]{/*E*/12, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/12};
		baseSlowArray			= new float[]{/*E*/0.10f, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0.05f};
		baseCost			  	= 750;
		baseAttackCooldown	  	= 15f;
		baseSplashDamage		= 0.2f;
		baseSplashEffect		= 0f;
		baseSplashRadius		= 2f;
		baseRange				= 6.5f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades				= null;
	}}),
	/**
	 * Basic Tower of the FIRE type.
	 * Fire towers deal a large amount of non-physical FIRE damage,
	 * attack fairly slowly, and have a medium range.
	 * Has very strong splash coefficients.
	 */
	FIRE (new BaseAttributeList(){{
		name               	 	= "Fire";
		downgradeType			= null;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/35, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/10, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0.10f, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseCost				= 900;
		baseAttackCooldown		= 12f;
		baseSplashDamage		= 0.25f;
		baseSplashEffect		= 0.25f;
		baseSplashRadius		= 1.5f;
		baseRange				= 7.5f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades				= null;
	}}),
	/**
	 * Basic Tower of the WATER type. 
	 * Water towers deal a medium to low amount of damage, 
	 * attack fairly quickly, and have a medium range, 
	 * but have a strong slow and a strong effect splash coefficient.
	 */
	WATER (new BaseAttributeList(){{
		name               	 	= "Water";
		downgradeType			= null;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/15, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/10, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0.18f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseCost				= 850;
		baseAttackCooldown		= 10f;
		baseSplashDamage		= 0f;
		baseSplashEffect		= 0.25f;
		baseSplashRadius		= 1f;
		baseRange				= 7.5f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades				= null;
	}}),
	/**
	 * Basic Tower of the WIND type.
	 * Wind towers deal low damage
	 * but have a very high fire rate and a
	 * large range. Air towers can also hit
	 * flying targets.
	 */
	WIND (new BaseAttributeList(){{
		name               	 	= "Wind";
		downgradeType			= null;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/17, /*L*/0, /*D*/0, /*P*/17};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/4, /*L*/0, /*D*/0, /*P*/4};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0.05f, /*L*/0, /*D*/0, /*P*/0};
		baseCost				= 1000;
		baseAttackCooldown		= 8.4f;
		baseSplashDamage		= 0f;
		baseSplashEffect		= 0.10f;
		baseSplashRadius		= 2.3f;
		baseRange				= 8.6f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= true;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades				= null;
	}}),
	//TODO should the base stats of the towers be identical to their downgrade?
	/**
	 * Towers of this type deal damage and slows to all creep around it. Currently does not apply splash effects or on-hit effects.
	 * Deals primarily physical damage, barring an upgrade that converts physical damage to earth damage.
	 * <p>
	 * <ol>
	 * Track 1: Focuses on increasing the AoE capacity of the tower.
	 * <ol>
	 * <li> Increases base RANGE
	 * <li> Reduces base ATTACK COOLDOWN
	 * <li> Converts each point of PHYSICAL DAMAGE to 1.8 points of EARTH DAMAGE
	 * <li> All EARTH tiles deal EARTH DAMAGE in a circle around them
	 * </ol>
	 * Track 2: Focuses on increasing the physical damage of the tower.
	 * <ol>
	 * <li> Doubles PHYSICAL DAMAGE
	 * <li> Increases base RANGE and base PHYSICAL DAMAGE
	 * <li> Increases base PHYSICAL DAMAGE and deals a portion of PHYSICAL DAMAGE as a Bleed
	 * <li> Creep affected by this Tower's Bleed have reduced PHYSICAL DAMAGE resistance
	 * </ol>
	 * </ol>
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
		baseSplashDamage		= 0.23f;
		baseSplashEffect		= 0f;
		baseSplashRadius		= 3f;
		baseRange				= 8.1f;
		baseDamageSiphon			= 0.43f;
		baseSlowDurationSiphon		= 0.28f;
		baseSlowSiphon				= 0.28f;
		baseAttackCooldownSiphon	= 4.2f;
		baseSplashDamageSiphon		= 0.45f;
		baseSplashEffectSiphon		= 0.32f;
		baseSplashRadiusSiphon		= 0.51f;
		baseRangeSiphon				= 0.07f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= false;
		baseDoesSlow				= true;
		baseDoesOnHit				= false;
		baseSplashHitsAir			= false;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Fault Lines";
						 text 		= "Increases base RANGE";
						 baseCost   = 500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 2; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Tectonics";
						 text 		= "Reduces base ATTACK COOLDOWN";
						 baseCost   = 500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 3; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Channel the Earth";
						 text 		= "Converts each point of PHYSICAL DAMAGE to 1.8 points of EARTH DAMAGE";
						 baseCost   = 2000;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.EARTH.ordinal()] += t.damageArray[DamageType.PHYSICAL.ordinal()] * 1.8f; t.damageArray[DamageType.PHYSICAL.ordinal()] = 0; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Living Earth";
						 text 		= "All EARTH tiles deal EARTH DAMAGE in a circle around them";
						 baseCost   = 5000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthEarth) t).detonationModifier = 0.10f; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "Hardened Earth";
						 text 		= "Doubles PHYSICAL DAMAGE";
						 baseCost 	= 1300;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] *= 2; }
					},
					new Upgrade() {
						{name		= "Magnitude";
						 text 		= "Increases base RANGE and base PHYSICAL DAMAGE";
						 baseCost   = 400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 1; t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 34; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Fragmentation";
						 text 		= "Increases base PHYSICAL DAMAGE and deals a portion of PHYSICAL DAMAGE as a Bleed";
						 baseCost   = 2500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 50; }
						 public void midSiphonUpgrade(Tower t) {  ((TowerEarthEarth) t).maxBleedStacks = 1; ((TowerEarthEarth) t).bleedModifier = 0.50f; ((TowerEarthEarth) t).bleedDuration = 18; ((TowerEarthEarth) t).bleedTiming = 3; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Grievous Wound";
						 text 		= "Creep affected by this Tower's Bleed have reduced PHYSICAL DAMAGE resistance";
						 baseCost   = 5000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthEarth) t).shredModifier = 0.04f; ((TowerEarthEarth) t).armorShredDuration = 18; ((TowerEarthEarth) t).maxShredStacks = 5; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				}
		};
	}}), 
	/**
	 * Towers of this type deal high amounts of damage and penetrate armor.
	 * <p>
	 * <ol>
	 * Track 1: Focuses on high range and fast shooting, allowing many different targets to be hit.
	 * <ol>
	 * <li> Reduces base ATTACK COOLDOWN
	 * <li> Increases base and non-base RANGE
	 * <li> Converts each point of PHYSICAL DAMAGE to 1.8 points of EARTH DAMAGE
	 * <li> All EARTH tiles deal EARTH DAMAGE in a circle around them
	 * </ol>
	 * Track 2: Focuses on increasing the physical damage of the tower.
	 * <ol>
	 * <li> Doubles PHYSICAL DAMAGE
	 * <li> Increases base RANGE and base PHYSICAL DAMAGE
	 * <li> Increases base PHYSICAL DAMAGE and deals a portion of PHYSICAL DAMAGE as a Bleed
	 * <li> Creep affected by this Tower's Bleed have reduced PHYSICAL DAMAGE resistance
	 * </ol>
	 * </ol>
	 */
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
		baseSplashDamage		= 0.25f;
		baseSplashEffect		= 0.25f;
		baseSplashRadius		= 2.2f;
		baseRange				= 8.6f;
		baseDamageSiphon			= 0.50f;
		baseSlowDurationSiphon		= 0.18f;
		baseSlowSiphon				= 0.21f;
		baseAttackCooldownSiphon	= 4.8f;
		baseSplashDamageSiphon		= 0.33f;
		baseSplashEffectSiphon		= 0.27f;
		baseSplashRadiusSiphon		= 0.59f;
		baseRangeSiphon				= 0.06f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
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
						 text 		= "Increases base and non-base RANGE";
						 baseCost   = 500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 1.1; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.range += 3.1; }
					},
					new Upgrade() {
						{name		= "Gather the Scraps";
						 text 		= "Attacks apply a Wealth debuff to minions";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthFire) t).wealthDuration = 30; ((TowerEarthFire) t).wealthModifier = 1.15f; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Motherload";
						 text 		= "Increases the duration and effectiveness of wealth";
						 baseCost   = 5000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthFire) t).wealthDuration += 20; ((TowerEarthFire) t).wealthModifier += 0.30; }
						 public void postSiphonUpgrade(Tower t) {  }
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
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthFire) t).shredModifier = 0.015f; ((TowerEarthFire) t).shredDuration = 15; ((TowerEarthFire) t).maxShredStacks = 10; }
						 public void postSiphonUpgrade(Tower t) {  }
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
		baseSplashDamage		= 0.15f;
		baseSplashEffect		= 0.15f;
		baseSplashRadius		= 1.3f;
		baseRange				= 8.6f;
		baseDamageSiphon			= 0.63f;
		baseSlowDurationSiphon		= 0.12f;
		baseSlowSiphon				= 0.12f;
		baseAttackCooldownSiphon	= 4.0f;
		baseSplashDamageSiphon		= 0.33f;
		baseSplashEffectSiphon		= 0.27f;
		baseSplashRadiusSiphon		= 0.59f;
		baseRangeSiphon				= 0.06f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Eon";
						 text 		= "Increases the base radius of the stalagmites";
						 baseCost   = 400;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthWater) t).areaRadius += 1; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Cave Building";
						 text 		= "Increases the radius even more and the range of the tower";
						 baseCost   = 1300;}
						 public void baseUpgrade(Tower t) {  t.baseAttributeList.baseRange += 0.4f; }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthWater) t).areaRadius += 1; }
						 public void postSiphonUpgrade(Tower t) { t.range += 2.4f;  }
					},
					new Upgrade() {
						{name		= "Echo";
						 text 		= "Splash Radius is added to the hit radius and range of this tower, and now applies splash damage to all affected";
						 baseCost   = 6200;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDoesSplash = true; }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthWater) t).areaRadius += t.splashRadius / 2; ((TowerEarthWater) t).areaRadius += t.range / 6; } //TODO: Quality affects these equations? (Somehow?)
						 public void postSiphonUpgrade(Tower t) {  } 
					},
					new Upgrade() {
						{name		= "Shattering";
						 text 		= "Increases splash siphon coefficients, and now applies on hits to all affected";
						 baseCost   = 5300;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadiusSiphon += 0.15f; t.baseAttributeList.baseSplashEffectSiphon += 0.15f; t.baseAttributeList.baseSplashDamageSiphon += 0.15f; }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthWater) t).doesOnHit = true; }
						 public void postSiphonUpgrade(Tower t) {  }
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
						 public void midSiphonUpgrade(Tower t) { for (int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++) { t.damageArray[i]*=2; } }
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
		baseSplashDamage		= 0.15f;
		baseSplashEffect		= 0.15f;
		baseSplashRadius		= 5f;
		baseRange				= 9.0f;
		baseDamageSiphon			= 0.39f;
		baseSlowDurationSiphon		= 0.10f;
		baseSlowSiphon				= 0.10f;
		baseAttackCooldownSiphon	= 5.2f;
		baseSplashDamageSiphon		= 0.46f;
		baseSplashEffectSiphon		= 0.37f;
		baseSplashRadiusSiphon		= 0.39f;
		baseRangeSiphon				= 0.07f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Explosive Force";
						 text 		= "Increases the splash damage and effectiveness";
						 baseCost   = 1350;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashDamage += .1; t.baseAttributeList.baseSplashEffect += .1; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.splashDamage += .1; t.splashEffect += .1; }
					},
					new Upgrade() {
						{name		= "Explosive Force";
						 text 		= "Increases the splash damage and effectiveness";
						 baseCost   = 1350;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashDamage += .1; t.baseAttributeList.baseSplashEffect += .1; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.splashDamage += .1; t.splashEffect += .1; }
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
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadius += 5; t.baseAttributeList.baseSplashHitsAir = true; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) {  }
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
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] += (13f / t.attackCooldown) * 40; } //TODO: Quality affects this equation? (Somehow?)
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
		baseSplashDamage		= 0.05f;
		baseSplashEffect		= 0.05f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		baseDamageSiphon			= 0.49f;
		baseSlowDurationSiphon		= 0.16f;
		baseSlowSiphon				= 0.18f;
		baseAttackCooldownSiphon	= 4.7f;
		baseSplashDamageSiphon		= 0.39f;
		baseSplashEffectSiphon		= 0.33f;
		baseSplashRadiusSiphon		= 0.33f;
		baseRangeSiphon				= 0.08f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Volcano";
						 text 		= "The tower now does AoE damage";
						 baseCost   = 2500;}
						 public void baseUpgrade(Tower t) { t.doesSplash = false; }
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
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=20; ((TowerFireEarth) t).poisonModifier=0.10f; ((TowerFireEarth) t).poisonDuration=18; ((TowerFireEarth) t).maxPoisonStacks = 1; ((TowerFireEarth) t).poisonTiming = 2; }
						 public void postSiphonUpgrade(Tower t) { t.range += 10f; t.damageArray[DamageType.FIRE.ordinal()] += 145; }
					},
				},
				{
					new Upgrade() {
						{name		= "Melt Armor";
						 text 		= "Attacks reduce armor by a flat amount";
						 baseCost 	= 1400;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireEarth) t).armorShredModifier = 1; ((TowerFireEarth) t).armorShredDuration = 20; ((TowerFireEarth) t).maxArmorShredStacks = 10; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Sear Flesh";
						 text 		= "Attacks reduce toughness by a flat amount";
						 baseCost   = 2400;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireEarth) t).toughnessShredModifier = 1; ((TowerFireEarth) t).toughnessShredDuration = 17; ((TowerFireEarth) t).maxToughnessShredStacks = 9; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Swelling";
						 text 		= "Attacks against afflicted enemies deal additional FIRE damage";
						 baseCost   = 3000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireEarth) t).DOHModifier = 0.05f; ((TowerFireEarth) t).DOHDuration = 25; ((TowerFireEarth) t).maxDOHStacks = 1; ((TowerFireEarth) t).doesOnHit = true; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Blood Boil";
						 text 		= "Deals additional FIRE damage as a percent of max health";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireEarth) t).percentMaxHealthModifier = 0.02f; }
						 public void postSiphonUpgrade(Tower t) {  }
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
		baseSlowArray			= new float[]{/*E*/0, /*F*/0.12f, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 12f;
		baseSplashDamage		= 0.25f;
		baseSplashEffect		= 0.25f;
		baseSplashRadius		= 2f;
		baseRange				= 8.5f;
		baseDamageSiphon			= 0.67f;
		baseSlowDurationSiphon		= 0.36f; //TODO: Really we should set some of these siphons to 0 if we don't want them doing slows or splashes or anything
		baseSlowSiphon				= 0.12f; //TODO: The alternative is that we can prevent some towers from doing slows or splashes
		baseAttackCooldownSiphon	= 4.8f;
		baseSplashDamageSiphon		= 0.44f;
		baseSplashEffectSiphon		= 0.38f;
		baseSplashRadiusSiphon		= 0.37f;
		baseRangeSiphon				= 0.09f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
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
						{name		= "";
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
						 public void midSiphonUpgrade(Tower t) { ((TowerFireFire) t).bleedModifier = 1; ((TowerFireFire) t).bleedDuration = 15; ((TowerFireFire) t).bleedTiming = 3; ((TowerFireFire) t).maxBleedStacks = 3; }
						 public void postSiphonUpgrade(Tower t) { }
					},
				},
				{
					new Upgrade() {
						{name		= "Spreading Flames";
						 text 		= "Increase base splash range and effectiveness";
						 baseCost 	= 1300;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadius += 1; t.baseAttributeList.baseSplashDamage += .10; t.baseAttributeList.baseSplashEffect += .10;}
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Uncontained";
						 text 		= "Increases base range, splash damage coefficient, and damage siphon coefficient";
						 baseCost   = 1900;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 1.2; t.baseAttributeList.baseSplashDamage += .15; t.baseAttributeList.baseDamageSiphon += 0.05; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Scorched Earth";
						 text 		= "Scorches the earth around the target, damaging all enemies that pass through it";
						 baseCost   = 5500;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireFire) t).effectPatchDuration = 12; ((TowerFireFire) t).effectPatchTiming = 3; ((TowerFireFire) t).effectPatchDamageModifier = 0.15f;  }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Burning Flesh";
						 text 		= "Scorched Earth reduces all elemental resistances";
						 baseCost   = 7000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireFire) t).effectPatchShredDuration = 14; ((TowerFireFire) t).effectPatchShredModifier = 0.0003f; ((TowerFireFire) t).maxShredStacks = 4; }
						 public void postSiphonUpgrade(Tower t) {   }
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
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/6, /*WA*/13, /*WI*/0, /*L*/0, /*D*/0, /*P*/5};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0.05f, /*WA*/0.12f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0.03f};
		baseAttackCooldown		= 13.1f;
		baseSplashDamage		= 0.10f;
		baseSplashEffect		= 0.25f;
		baseSplashRadius		= 1.1f;
		baseRange				= 8.5f;
		baseDamageSiphon			= 0.70f; //TODO: This seems too high, we would just have to make a FireFire and siphon from it with towers //TODO: This may mean reducing siphon coefficients
		baseSlowDurationSiphon		= 0.54f;
		baseSlowSiphon				= 0.22f;
		baseAttackCooldownSiphon	= 4.8f;
		baseSplashDamageSiphon		= 0.44f;
		baseSplashEffectSiphon		= 0.38f;
		baseSplashRadiusSiphon		= 0.37f;
		baseRangeSiphon				= 0.11f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Fission";
						 text 		= "Increases base damage of all types and siphon bonus";
						 baseCost   = 2700;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageSiphon += 0.05f; for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){DamageType d = DamageType.values()[i]; if (d!=DamageType.LIGHT || d!=DamageType.DARK) { t.baseAttributeList.baseDamageArray[i]+=13; }}}
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Amplification";
						 text 		= "Multiplies all types of damage";
						 baseCost   = 2200;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){DamageType d = DamageType.values()[i]; if (d!=DamageType.LIGHT || d!=DamageType.DARK) { t.damageArray[i]*=1.06f; }} }
						 public void postSiphonUpgrade(Tower t) { for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){DamageType d = DamageType.values()[i]; if (d!=DamageType.LIGHT || d!=DamageType.DARK) { t.damageArray[i]*=1.12f; }} }
					},
					new Upgrade() {
						{name		= "Superheated";
						 text 		= "Attacks ignore all types of damage resistance";
						 baseCost   = 3500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Projectile hits all creeps it passes through until reaching its target";
						 baseCost   = 6000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireWater) t).passThroughRadiusModifier = 0.5f; ((TowerFireWater) t).passThroughModifier = 0.4f; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "Chain Reaction";
						 text 		= "Increases base splash radius";
						 baseCost 	= 1400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadius += 1.2f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.splashRadius += 1.4f; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Increases splash radius and effectiveness";
						 baseCost   = 3400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadius += 0.5f; t.baseAttributeList.baseSplashDamage += 0.03f; t.baseAttributeList.baseSplashEffect += 0.03f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.splashRadius += 4; t.splashDamage += 0.06f; t.splashEffect += 0.06f; }
					},
					new Upgrade() {
						{name		= "Ground Zero";
						 text 		= "Leaves behind a zone that, when passed through, deals damage based on max health";
						 baseCost   = 4600;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireWater) t).patchLifetime = 18; ((TowerFireWater) t).patchTiming = 3; ((TowerFireWater) t).patchMaxHealthModifier = 0.00005f; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Gamma Radiation";
						 text 		= "Increases damage. Enemies in Ground Zero have their health and shield regeneration permanently set to zero";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WATER.ordinal()] += 25; t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()] += 25; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.damageArray[DamageType.WATER.ordinal()] += 50; t.damageArray[DamageType.FIRE.ordinal()] += 50;}
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
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/8, /*WA*/0, /*WI*/4, /*L*/0, /*D*/0, /*P*/5};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0.06f, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 10.7f;
		baseSplashDamage		= 0f;
		baseSplashEffect		= 0f;
		baseSplashRadius		= 0f;
		baseRange				= 8.8f;
		baseDamageSiphon			= 0.48f;
		baseSlowDurationSiphon		= 0.15f;
		baseSlowSiphon				= 0.38f;
		baseAttackCooldownSiphon	= 4.6f;
		baseSplashDamageSiphon		= 0.60f;
		baseSplashEffectSiphon		= 0.60f;
		baseSplashRadiusSiphon		= 0.60f;
		baseRangeSiphon				= 0.12f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= false;
		baseDoesSlow				= false;
		baseDoesOnHit				= false;
		baseSplashHitsAir			= false;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Global Warming";
						 text 		= "Increases base RANGE";
						 baseCost   = 550;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 2; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Dry Air";
						 text 		= "Greatly increases RANGE and base RANGE";
						 baseCost   = 1700;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 1.2f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.range += 4.8f; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Increases base FIRE and WIND damage and widens the laser";
						 baseCost   = 4500;}
						 public void baseUpgrade(Tower t) {  t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()]+=67f; t.baseAttributeList.baseDamageArray[DamageType.WIND.ordinal()]+= 34; t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()]+= 44; }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireWind) t).passThroughRadiusModifier += 0.05f; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Heat Column";
						 text 		= "Increases laser width and range, hits flying, and applies splash effects to affected creep";
						 baseCost   = 6000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireWind) t).passThroughRadiusModifier += 0.05f; ((TowerFireWind) t).doesSplash = true; t.hitsAir = true; }
						 public void postSiphonUpgrade(Tower t) {  t.range += 1f; }
					},
				},
				{
					new Upgrade() {
						{name		= "";
						 text 		= "Multiplies FIRE damage";
						 baseCost 	= 1300;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()] *= 1.5f; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Multiply all damage types";
						 baseCost   = 2900;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { for(int i=0; i<GameConstants.NUM_DAMAGE_TYPES; i++) { t.damageArray[i] *= 1.2; } }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Greatly increases siphon coefficients";
						 baseCost   = 7500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageSiphon += 0.10f; t.baseAttributeList.baseSplashDamageSiphon += 0.10f; t.baseAttributeList.baseSlowSiphon += 0.10f; t.baseAttributeList.baseSlowDurationSiphon += 0.10f; t.baseAttributeList.baseSplashEffectSiphon += 0.10f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Multiplies damage of all towers nearby by";
						 baseCost   = 8000;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) {  } 
						 public void postSiphonUpgrade(Tower t) { }
					},
				}
		};
	}}), 
	WATER_EARTH (new BaseAttributeList(){{ //TODO: Flower tower or something if this sucks
		//does large AoE slow
		name					= "Mud";
		downgradeType 			= WATER;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/10, /*F*/0, /*WA*/10, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 	= new int[]{/*E*/15, /*F*/0, /*WA*/15, /*WI*/0, /*L*/0, /*D*/0, /*P*/4};
		baseSlowArray			= new float[]{/*E*/0.10f, /*F*/0, /*WA*/0.15f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0.03f};
		baseAttackCooldown		= 12.5f;
		baseSplashDamage		= 0f;
		baseSplashEffect		= 0.10f;
		baseSplashRadius		= 1f;
		baseRange				= 8.5f;
		baseDamageSiphon			= 0.45f;
		baseSlowDurationSiphon		= 0.55f;
		baseSlowSiphon				= 0.58f;
		baseAttackCooldownSiphon	= 4.8f;
		baseSplashDamageSiphon		= 0.50f;
		baseSplashEffectSiphon		= 0.50f;
		baseSplashRadiusSiphon		= 0.50f;
		baseRangeSiphon				= 0.09f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= false;
		baseDoesSlow				= true;
		baseDoesOnHit				= false;
		baseSplashHitsAir			= false;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Flash Flood";
						 text 		= "Increases the base and non-base range";
						 baseCost   = 900;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 1.3f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.baseAttributeList.baseRange += 1.4f; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Reduces base and non-base attack cooldown";
						 baseCost   = 1300;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 1.9f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.attackCooldown -= 2.3f; }
					},
					new Upgrade() {
						{name		= "Churning River";
						 text 		= "Reduces enemy armor";
						 baseCost   = 3500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterEarth) t).shredModifier = 0.3f; ((TowerWaterEarth) t).shredDuration = 24; ((TowerWaterEarth) t).maxShredStacks = 1; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Heavy Rains";
						 text 		= "Hits air targets, doubles base slow duration, and..."; //TODO:
						 baseCost   = 2000;}
						 public void baseUpgrade(Tower t) { t.hitsAir = true; t.splashHitsAir = true; t.doesSplash = true; for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){t.slowDurationArray[i]*=2;} }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
				},
				{
					new Upgrade() {
						{name		= "Thickening Mud";
						 text 		= "Increases base slow";
						 baseCost 	= 1200;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSlowArray[DamageType.WATER.ordinal()] += .2f; t.baseAttributeList.baseSlowArray[DamageType.EARTH.ordinal()] += .2f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Cloud Cover";
						 text 		= "Doubles base EARTH and WATER slow duration";
						 baseCost   = 1500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSlowDurationArray[DamageType.WATER.ordinal()]*=2; t.baseAttributeList.baseSlowDurationArray[DamageType.EARTH.ordinal()]*=2; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Shape the Earth";
						 text 		= "Allows for modification of tile types"; //TODO: Implement this
						 baseCost   = 3500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Petrification";
						 text 		= "Permanently reduces maximum creep speed";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterEarth) t).permaSlowModifier = 0.005f; }
						 public void postSiphonUpgrade(Tower t) {  }
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
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/4, /*WA*/10, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0.02f, /*WA*/0.10f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 13.1f;
		baseSplashDamage		= 0.04f;
		baseSplashEffect		= 0.04f;
		baseSplashRadius		= 1f;
		baseRange				= 8.2f;
		baseDamageSiphon			= 0.45f;
		baseSlowDurationSiphon		= 0.55f;
		baseSlowSiphon				= 0.58f;
		baseAttackCooldownSiphon	= 4.6f;
		baseSplashDamageSiphon		= 0.34f;
		baseSplashEffectSiphon		= 0.37f;
		baseSplashRadiusSiphon		= 0.33f;
		baseRangeSiphon				= 0.10f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Watery Grave";
						 text 		= "Increases base WATER damage, slow, and slow duration";
						 baseCost   = 1900;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WATER.ordinal()]+=42; t.baseAttributeList.baseSlowArray[DamageType.WATER.ordinal()]+=0.11f; t.baseAttributeList.baseSlowDurationArray[DamageType.WATER.ordinal()]+=7; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Elemental Fury";
						 text 		= "Reduces base attack cooldown";
						 baseCost   = 1600;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 3.1f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Clarity";
						 text 		= "Reduces disruptor effects for a time";
						 baseCost   = 7500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterFire) t).disruptorSuppressionDuration = 50; ((TowerWaterFire) t).disruptorSuppressionPercent = 0.50f; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Cleansing";
						 text 		= "Increases the disruptor reduction and disables creep deathrattles for a time";
						 baseCost   = 8900;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterFire) t).disruptorSuppressionPercent = 1;  ((TowerWaterFire) t).deathrattleSuppressionDuration = 43; }
						 public void postSiphonUpgrade(Tower t) { }
					},
				},
				{
					new Upgrade() {
						{name		= "Boiling";
						 text 		= "Increases base FIRE damage and reduces base attack cooldown";
						 baseCost   = 1350;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()]+=47; t.baseAttributeList.baseAttackCooldown-=1.4f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Scalding";
						 text 		= "Increases base FIRE damage and splash radius";
						 baseCost   = 3100;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()]+=21; t.baseAttributeList.baseSplashRadius+=0.7f; }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=21f; t.splashRadius+=0.7f; }
						 public void postSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=21f; t.splashRadius+=0.7f;}
					},
					new Upgrade() {
						{name		= "Third Degree";
						 text 		= "Increases base and non-base FIRE and WATER damage and base damage splash coefficient";
						 baseCost   = 7300;}
						 public void baseUpgrade(Tower t) {  t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()]+=34; t.baseAttributeList.baseDamageArray[DamageType.WATER.ordinal()]+=34; t.baseAttributeList.baseSplashDamage+=0.075f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=158; }
					},
					new Upgrade() {
						{name		= "Superheated";
						 text 		= "Ignores creep FIRE and WATER resistances and increases damage siphon coefficient and base damage splash coefficient";
						 baseCost   = 7300;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageSiphon += 0.15f; t.baseAttributeList.baseSplashDamage += 0.075f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
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
		baseDamageArray  		= new float[]{/*E*/0, /*F*/0, /*WA*/20, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/20, /*WI*/0, /*L*/0, /*D*/0, /*P*/10};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0.30f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0.10f};
		baseAttackCooldown 		= 15.9f;
		baseSplashDamage 		= 0f;
		baseSplashEffect 		= 0.30f;
		baseSplashRadius 		= 2f;
		baseRange   			= 7.6f;
		baseDamageSiphon			= 0.36f;
		baseSlowDurationSiphon		= 0.60f;
		baseSlowSiphon				= 0.60f;
		baseAttackCooldownSiphon	= 5.3f;
		baseSplashDamageSiphon		= 0.43f;
		baseSplashEffectSiphon		= 0.65f;
		baseSplashRadiusSiphon		= 0.62f;
		baseRangeSiphon				= 0.11f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir    				= false;
		baseHitsGround   			= true;
		baseDoesSplash				= false;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades   				= new Upgrade[][]{
				{
					 new Upgrade() {
						 {name  	 = "First Frost";
				          text   	 = "Increases base RANGE";
				          baseCost   = 900;}
						  public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 2; }
						  public void midSiphonUpgrade(Tower t) { }
						  public void postSiphonUpgrade(Tower t) { }
				     },
				     new Upgrade() {
				    	 {name 	 	 = "Chilling Breath";
				    	  text  	 = "Increases base WATER SLOW Duration and range";
				    	  baseCost   = 1400;}
				    	  public void baseUpgrade(Tower t) { t.baseAttributeList.baseSlowDurationArray[DamageType.WATER.ordinal()] += 17; }
				    	  public void midSiphonUpgrade(Tower t) { }
						  public void postSiphonUpgrade(Tower t) { }
				     },
				     new Upgrade() {
				    	 {name 		 = "Chilled to the Bone";
				    	  text  	 = "Doubles WATER Slow potency and increases base range and reduces base attack cooldown";
				    	  baseCost   = 2700;}
				    	  public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 2; t.baseAttributeList.baseAttackCooldown -= 1.2f; }
				    	  public void midSiphonUpgrade(Tower t) { t.slowArray[DamageType.WATER.ordinal()] *= 2; }
						  public void postSiphonUpgrade(Tower t) { }
				     },
				     new Upgrade() {
				    	 {name 		 = "Sheer Cold";
				    	  text 		 = "Roots Enemies in place and attacks apply on hit effects"; 
				    	  baseCost   = 5000;}
				    	  public void baseUpgrade(Tower t) { t.hitsAir = true; }
				    	  public void midSiphonUpgrade(Tower t) { ((TowerWaterWater) t).snareDuration = 12; ((TowerWaterWater) t).doesOnHit = true; }
						  public void postSiphonUpgrade(Tower t) { t.range += 3; t.attackCooldown -= 1; }
				     },
				},
				{
				     new Upgrade() {
				      	 {name 		 = "Churning Waters";
				      	  text   	 = "Increases base pulse rate";
				      	  baseCost   = 600;}
				      	  public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 2.2f; }
				      	  public void midSiphonUpgrade(Tower t) { }
						  public void postSiphonUpgrade(Tower t) { }
				     },
				     new Upgrade() {
				    	 {name 		 = "Black Ice";
				    	  text   	 = "Increases base WATER damage";
				    	  baseCost   = 700;}
				    	  public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WATER.ordinal()] += 45; }
				    	  public void midSiphonUpgrade(Tower t) { }
						  public void postSiphonUpgrade(Tower t) { }
				     },
				     new Upgrade() {
				    	 {name  	 = "Poisoned Well";
				    	  text  	 = "Slowed Enemies take additional damage over time and halves enemy WATER resistance";
				    	  baseCost   = 4500;}
				    	  public void baseUpgrade(Tower t) {  }
				    	  public void midSiphonUpgrade(Tower t) { ((TowerWaterWater) t).bleedDuration = 12; ((TowerWaterWater) t).bleedTiming = 3; ((TowerWaterWater) t).maxBleedStacks = 5; ((TowerWaterWater) t).bleedModifier = 0.65f; ((TowerWaterWater) t).shredDuration = 8; ((TowerWaterWater) t).shredModifier = 0.5f; ((TowerWaterWater) t).maxShredStacks = 1; }
						  public void postSiphonUpgrade(Tower t) { }
				     },
				     new Upgrade() {
				    	 {name  	 = "Cleanse";
				    	  text   	 = "Consumes all damage over time effects on enemies hit and deals half their remaining damage";
				    	  baseCost   = 5000;}
				    	  public void baseUpgrade(Tower t) {  }
				    	  public void midSiphonUpgrade(Tower t) { ((TowerWaterWater) t).consumeModifier = 0.5f; }
						  public void postSiphonUpgrade(Tower t) { }
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
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/15, /*WI*/5, /*L*/0, /*D*/0, /*P*/7};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0.15f, /*WI*/0.05f, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 11.6f;
		baseSplashDamage		= 0.20f;
		baseSplashEffect		= 0.20f;
		baseSplashRadius		= 3.2f;
		baseRange				= 7.7f;
		baseDamageSiphon			= 0.44f;
		baseSlowDurationSiphon		= 0.44f;
		baseSlowSiphon				= 0.44f;
		baseAttackCooldownSiphon	= 4.6f;
		baseSplashDamageSiphon		= 0.23f;
		baseSplashEffectSiphon		= 0.22f;
		baseSplashRadiusSiphon		= 0.41f;
		baseRangeSiphon				= 0.09f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir					= true;
		baseHitsGround				= true;
		baseDoesSplash				= false;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Hypothermia";
						 text 		= "Gives a chance to apply a percent toughness shred";
						 baseCost   = 1000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterWind) t).toughnessWeight = 7; ((TowerWaterWind) t).toughShredDuration = 25; ((TowerWaterWind) t).toughShredModifier = 0.2f; ((TowerWaterWind) t).maxToughShredStacks = 3; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Chilled";
						 text 		= "Increases slow potencies";
						 baseCost   = 2000;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){ t.slowArray[DamageType.values()[i].ordinal()]*= 1.15f;}  }
						 public void postSiphonUpgrade(Tower t) { for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){ t.slowArray[DamageType.values()[i].ordinal()]*= 1.25f;} }
					},
					new Upgrade() {
						{name		= "Polar Vortex";
						 text 		= "Increases the chances of a toughness shred, its effectiveness, and increases base WATER damage";
						 baseCost   = 3500;}
						 public void baseUpgrade(Tower t) {  t.baseAttributeList.baseDamageArray[DamageType.WATER.ordinal()]+=54; }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterWind) t).toughnessWeight = 14; ((TowerWaterWind) t).toughShredModifier = 0.3f; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Increases base splash radius and range and gives a chance to apply a snare";
						 baseCost   = 7200;}
						 public void baseUpgrade(Tower t) {  t.baseAttributeList.baseRange += 2.1f; t.baseAttributeList.baseAttackCooldown -= 1.2f; }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterWind) t).snareDuration = 8; ((TowerWaterWind) t).snareWeight = 5; }
						 public void postSiphonUpgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] += 60f; }
					},
				},
				{
					new Upgrade() {
						{name		= "Pelting";
						 text 		= "Increases base PHYSICAL damage";
						 baseCost 	= 750;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 43; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Swirling Winds";
						 text 		= "Increases base and non-base range and splash radius";
						 baseCost   = 3400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadius += 0.8f; t.baseAttributeList.baseRange += 0.8f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.splashRadius += 0.8f; t.range += 0.8f; }
					},
					new Upgrade() {
						{name		= "Preserved in Ice";
						 text 		= "Gives a chance to grant a gold on hit buff to enemies and increases base WIND damage";
						 baseCost   = 5500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WIND.ordinal()]+=37f; }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterWind) t).goldOnHitDuration = 25; ((TowerWaterWind) t).maxGOHStacks = 3; ((TowerWaterWind) t).goldOnHitModifier = 1.3f; ((TowerWaterWind) t).goldOnHitWeight = 5; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Shattering Frost";
						 text 		= "Reduces attack cooldown and gives a chance to grant a WATER damage on hit debuff to enemies";
						 baseCost   = 7000;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WATER.ordinal()] += 29f; }
						 public void midSiphonUpgrade(Tower t) { t.attackCooldown /= 1.1f; ((TowerWaterWind) t).damageOnHitDuration = 20; ((TowerWaterWind) t).damageOnHitModifier = 0.05f; ((TowerWaterWind) t).maxDOHStacks = 3; ((TowerWaterWind) t).damageOnHitWeight = 5; }
						 public void postSiphonUpgrade(Tower t) {  }
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
		baseDamageArray			= new float[]{/*E*/10, /*F*/0, /*WA*/0, /*WI*/10, /*L*/0, /*D*/0, /*P*/30};
		baseSlowDurationArray 	= new int[]{/*E*/10, /*F*/0, /*WA*/0, /*WI*/10, /*L*/0, /*D*/0, /*P*/3};
		baseSlowArray			= new float[]{/*E*/0.10f, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 12.3f;
		baseSplashDamage		= 0.07f;
		baseSplashEffect		= 0.07f;
		baseSplashRadius		= 0f;
		baseRange				= 8.2f;
		baseDamageSiphon			= 0.44f;
		baseSlowDurationSiphon		= 0.44f;
		baseSlowSiphon				= 0.44f;
		baseAttackCooldownSiphon	= 4.6f;
		baseSplashDamageSiphon		= 0.23f;
		baseSplashEffectSiphon		= 0.22f;
		baseSplashRadiusSiphon		= 0.25f;
		baseRangeSiphon				= 0.09f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "";
						 text 		= "Reduces base ATTACK COOLDOWN";
						 baseCost   = 400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 1.8f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Desertification";
						 text 		= "Increases base RANGE and ATTACK COOLDOWN";
						 baseCost   = 800;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 0.9f; t.baseAttributeList.baseRange += 2.3f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Whipping Sands";
						 text 		= "Attacks now knockup affected targets";
						 baseCost   = 3500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindEarth) t).knockupDuration = 14; } //TODO: This seems super long for what is essentially a snare
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Cyclone";
						 text 		= "Tower now fires in a path and hits all enemies along the way";
						 baseCost   = 6000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindEarth) t).passThroughModifier = 1; ((TowerWindEarth) t).passThroughRadiusModifier = 0.15f; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "";
						 text 		= "Increases base RANGE";
						 baseCost 	= 800;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 2.3f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Erosion";
						 text 		= "Increases base EARTH damage";
						 baseCost   = 1450;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.EARTH.ordinal()]+=56f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Sand Blindness";
						 text 		= "Attacks now disorient targets";
						 baseCost   = 3500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindEarth) t).disorientDuration = 24; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Blasting Force";
						 text 		= "Multiplies EARTH and WIND damage and creeps move faster during disorient";
						 baseCost   = 6200;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.WIND.ordinal()]*=1.5f; t.damageArray[DamageType.EARTH.ordinal()]*=1.35f; ((TowerWindEarth) t).unslowDuration = 24; ((TowerWindEarth) t).unslowModifier = -1f; }
						 public void postSiphonUpgrade(Tower t) {  }
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
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/6, /*WA*/0, /*WI*/13, /*L*/0, /*D*/0, /*P*/5};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0.06f, /*WA*/0, /*WI*/0.11f, /*L*/0, /*D*/0, /*P*/0};
		baseCost				= 200;
		baseAttackCooldown		= 10.0f;
		baseSplashDamage		= 0.10f;
		baseSplashEffect		= 0.10f;
		baseSplashRadius		= 2f;
		baseRange				= 8.6f;
		baseDamageSiphon			= 0.58f;
		baseSlowDurationSiphon		= 0.41f;
		baseSlowSiphon				= 0.48f;
		baseAttackCooldownSiphon	= 4.6f;
		baseSplashDamageSiphon		= 0.38f;
		baseSplashEffectSiphon		= 0.39f;
		baseSplashRadiusSiphon		= 0.33f;
		baseRangeSiphon				= 0.12f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir					= true;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Forking";
						 text 		= "Increase the maximum chaining by 3";
						 baseCost   = 900;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindFire) t).maxChains += 3; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Conductivity";
						 text 		= "Increase all ELEMENTAL DAMAGE done by this tower";
						 baseCost   = 1400;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { for (int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++) { if(DamageType.values()[i] != DamageType.PHYSICAL) { t.damageArray[i]*=1.25; } } }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "No Mercy";
						 text 		= "The tower may chain to a target it has already hit and increases WIND damage";
						 baseCost   = 3700;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindFire) t).noDuplicates = false; }
						 public void postSiphonUpgrade(Tower t) { t.damageArray[DamageType.WIND.ordinal()] += 100;  }
					},
					new Upgrade() {
						{name		= "Superconducting";
						 text 		= "Damage and Effects now increase per chain";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindFire) t).chainPenalty = 1.10f; }
						 public void postSiphonUpgrade(Tower t) { }
					},
				},
				{
					new Upgrade() {
						{name		= "Cloud Cover";
						 text 		= "Increases the base RANGE";
						 baseCost 	= 600;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 2; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Brewing Storm";
						 text 		= "Increase the base FIRE and WIND damage";
						 baseCost   = 1650;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()] += 30; t.baseAttributeList.baseDamageArray[DamageType.WIND.ordinal()] += 30; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Short Circuiting";
						 text 		= "Drains a creep's SHIELD at twice the rate";
						 baseCost   = 2500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindFire) t).shieldDrainModifier = 2; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Electrocution";
						 text 		= "Shocks targets for a short duration";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {   }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindFire) t).snareDuration = 4; }
						 public void postSiphonUpgrade(Tower t) { }
					},
				}
		};
	}}),  
	WIND_WATER (new BaseAttributeList(){{
		//TODO: Something about interest rate maybe?
		//Life tower, though it doesn't really make sense with the elements involved (or does it)
		//Passively generates gold and siphons max health
		name					= "Life";
		downgradeType 			= WIND;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown		= 15f;
		baseSplashDamage		= 0f;
		baseSplashEffect		= 0f;
		baseSplashRadius		= 0f;
		baseRange				= 8f;
		baseDamageSiphon			= 1f;
		baseSlowDurationSiphon		= 1f;
		baseSlowSiphon				= 1f;
		baseAttackCooldownSiphon	= 4f;
		baseSplashDamageSiphon		= 1f;
		baseSplashEffectSiphon		= 1f;
		baseSplashRadiusSiphon		= 1f;
		baseRangeSiphon				= 0.16f;
		baseIsOnGround				= true;
		baseIsInAir				= false;
		baseHitsAir					= true;
		baseHitsGround				= true;
		baseDoesSplash				= false;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Flourishing I";
						 text 		= "Increases all siphon coefficients";
						 baseCost   = 1200;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageSiphon += 0.05f; t.baseAttributeList.baseSlowDurationSiphon += 0.05f; t.baseAttributeList.baseSlowSiphon += 0.05f; t.baseAttributeList.baseSplashDamageSiphon += 0.05f; t.baseAttributeList.baseSplashEffectSiphon += 0.05f; t.baseAttributeList.baseSplashRadiusSiphon += 0.05f; t.baseAttributeList.baseAttackCooldownSiphon += 0.50f; t.baseAttributeList.baseRangeSiphon += 0.03f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Flourishing II";
						 text 		= "Increases all siphon coefficients";
						 baseCost   = 2000;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageSiphon += 0.10f; t.baseAttributeList.baseSlowDurationSiphon += 0.10f; t.baseAttributeList.baseSlowSiphon += 0.10f; t.baseAttributeList.baseSplashDamageSiphon += 0.10f; t.baseAttributeList.baseSplashEffectSiphon += 0.10f; t.baseAttributeList.baseSplashRadiusSiphon += 0.10f; t.baseAttributeList.baseAttackCooldownSiphon += 1f; t.baseAttributeList.baseRangeSiphon += 0.06f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Lifebending";
						 text 		= "Increases base stats of all towers in range"; //TODO: Implement this
						 baseCost   = 3500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Eden";
						 text 		= "Passive gold generation, siphoning from this tower costs no gold, and reduces upgrade costs by 5%"; //TODO: How can I implement this
						 baseCost   = 10000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
				},
				{
					new Upgrade() {
						{name		= "Decay";
						 text 		= "Increases max health damage";
						 baseCost 	= 1400;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindWater) t).maxHealthModifier += 0.01; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Rot";
						 text 		= "Increases max health damage";
						 baseCost   = 3400;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindWater) t).maxHealthModifier += 0.02; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Suffering";
						 text 		= "Creeps deal damage to those around on death";
						 baseCost   = 3500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindWater) t).damageOnDeathModifier = 0.10f; ((TowerWindWater) t).explosionRadiusModifier = 0.15f; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Curse of Flesh"; //TODO:
						 text 		= "";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
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
		baseAttackCooldown		= 9.1f;
		baseSplashDamage		= 0f;
		baseSplashEffect		= 0f;
		baseSplashRadius		= 2.2f;
		baseRange				= 11f;
		baseDamageSiphon			= 0.5f;
		baseSlowDurationSiphon		= 0.43f;
		baseSlowSiphon				= 0.48f;
		baseAttackCooldownSiphon	= 4f;
		baseSplashDamageSiphon		= 0.46f;
		baseSplashEffectSiphon		= 0.22f;
		baseSplashRadiusSiphon		= 0.44f;
		baseRangeSiphon				= 0.10f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= true;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Pushback";
						 text 		= "Increases base PHYSICAL damage";
						 baseCost   = 1400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 45; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Focused Attacks";
						 text 		= "Sets the attack cooldown of this tower to 1 and removes all splash and slow effects but increases siphon coefficients";
						 baseCost   = 4500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashEffectSiphon += 0.06f; t.baseAttributeList.baseSplashDamageSiphon += 0.06f; t.baseAttributeList.baseSlowSiphon += 0.06f; t.baseAttributeList.baseSlowDurationSiphon += 0.06f; t.baseAttributeList.baseDamageSiphon += 0.06f; t.baseAttributeList.baseSplashRadiusSiphon += 0.06f; t.baseAttributeList.baseRangeSiphon += 0.03f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.splashRadius = 0; t.attackCooldown = 1; for (int i=0; i<GameConstants.NUM_DAMAGE_TYPES; i++) { t.slowDurationArray[i]=0; } }
					},
					new Upgrade() {
						{name		= "Trade Winds";
						 text 		= "Enemies hit by this tower apply a debuff that, when hit, grants towers reduced attack cooldown";
						 baseCost   = 5500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindWind) t).hastingModifier = 0.5f; ((TowerWindWind) t).hastingDuration = 20; ((TowerWindWind) t).maxHastingStacks = 1; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Windfall";
						 text 		= "Applies a stacking debuff, when hit, grants gold";
						 baseCost   = 6000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindWind) t).wealthDuration = 20; ((TowerWindWind) t).wealthModifier = 3f; ((TowerWindWind) t).maxWealthStacks = 3; }
						 public void postSiphonUpgrade(Tower t) { }
					},
				},
				{
					new Upgrade() {
						{name		= "Gale Force I";
						 text 		= "Increases base WIND damage";
						 baseCost 	= 600;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WIND.ordinal()]+=28; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Gale Force II";
						 text 		= "Increases base WIND damage";
						 baseCost   = 900;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WIND.ordinal()]+=38; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Turbulence";
						 text 		= "Attacks now ground flying creep. Creep that are grounded take extra PHYSICAL damage";
						 baseCost   = 3500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindWind) t).groundingModifier = 1.7f; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Wind Resistance";
						 text 		= "Deals additional PHYSICAL and WIND damage based on speed of creep and increases damage and range siphon coefficients";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {  t.baseAttributeList.baseDamageSiphon += 0.10f; t.baseAttributeList.baseRangeSiphon += 0.05f; }
						 public void midSiphonUpgrade(Tower t) { ((TowerWindWind) t).speedDamageModifier = 13f; }
						 public void postSiphonUpgrade(Tower t) { }
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
	//TODO: Reduce visibility of this and other getters?
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
	
	public static TowerType getTowerTypeFromDamage(DamageType d) {
		switch (d) {
		case EARTH:
			return EARTH;
		case FIRE:
			return FIRE;
		case WATER:
			return WATER;
		case WIND:
			return WIND;
		default:
			return null;	
		}
	}
	
	public static float getUpgradeCost(TowerType type, int path, int level) {
		return type.baseAttributeList.upgrades[path][level].baseCost;
	}

	BaseAttributeList getAttributeList() {
		return baseAttributeList;
	}
	
	public int getCost() {
		//TODO: Affected by global talents somehow, use the TowerManager?
		return baseAttributeList.baseCost;
	}
	
	TowerType getDowngradeType() {
		if (isBaseType()) { //TODO: Should we really have to check this here?
			return this;
		}
		return baseAttributeList.downgradeType;
	}

	public int getWidth() {
		return baseAttributeList.baseWidth;
	}

	public int getHeight() {
		return baseAttributeList.baseHeight;
	}
	
	public boolean isOnGround() {
		return baseAttributeList.baseIsOnGround;
	}
	
	public boolean isInAir() {
		return baseAttributeList.baseIsInAir;
	}

	float getRange() {
		return baseAttributeList.baseRange;
	}
	
	public boolean isBaseType() {
		return this == EARTH || this == FIRE || this == WATER || this == WIND;
	}
}
