package towers;

import utilities.GameConstants;
import creeps.DamageType;

//TODO: Change wording from "track" to "path"
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
	 * Basic {@link Tower} of the EARTH type.
	 * EARTH towers deal a large amount of {@link DamageType#PHYSICAL PHYSICAL} damage
	 * and have a large splash radius and a strong splash damage coefficient.
	 * However, they have a short
	 * range and a very low attack speed.
	 * @see DamageType#EARTH
	 */
	EARTH (new BaseAttributeList(){{
		name                  		= "Earth";
		downgradeType				= null;
		baseWidth			  		= 2;
		baseHeight			  		= 2;
		baseDamageArray		  		= new float[]{/*E*/10, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/60};
		baseSlowDurationArray 		= new int[]{/*E*/12, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/12};
		baseSlowArray				= new float[]{/*E*/0.10f, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0.05f};
		baseCost			  		= 750;
		baseAttackCooldown	  		= 15f;
		baseSplashDamage			= 0.2f;
		baseSplashEffect			= 0f;
		baseSplashRadius			= 2f;
		baseRange					= 6.5f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades					= null;
	}}),
	/**
	 * Basic {@link Tower} of the FIRE type.
	 * FIRE towers deal a large amount of non-physical {@link DamageType#FIRE FIRE} damage,
	 * attack fairly slowly, and have a medium range.
	 * Has very strong splash coefficients.
	 * @see DamageType#FIRE
	 */
	FIRE (new BaseAttributeList(){{
		name               	 		= "Fire";
		downgradeType				= null;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/0, /*F*/35, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 		= new int[]{/*E*/0, /*F*/10, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray				= new float[]{/*E*/0, /*F*/0.10f, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseCost					= 900;
		baseAttackCooldown			= 12f;
		baseSplashDamage			= 0.25f;
		baseSplashEffect			= 0.25f;
		baseSplashRadius			= 1.5f;
		baseRange					= 7.5f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades					= null;
	}}),
	/**
	 * Basic {@link Tower} of the WATER type. 
	 * WATER towers deal a medium to low amount of damage, 
	 * attack fairly quickly, and have a medium range, 
	 * but have a strong slow and a strong effect splash coefficient.
	 * @see DamageType#WATER
	 */
	WATER (new BaseAttributeList(){{
		name               	 		= "Water";
		downgradeType				= null;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/0, /*F*/0, /*WA*/15, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 		= new int[]{/*E*/0, /*F*/0, /*WA*/10, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray				= new float[]{/*E*/0, /*F*/0, /*WA*/0.18f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseCost					= 850;
		baseAttackCooldown			= 10f;
		baseSplashDamage			= 0f;
		baseSplashEffect			= 0.25f;
		baseSplashRadius			= 1f;
		baseRange					= 7.5f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades					= null;
	}}),
	/**
	 * Basic {@link Tower} of the WIND type.
	 * WIND towers deal low damage
	 * but have a very high fire rate and a
	 * large range. WIND towers can also hit
	 * flying targets.
	 * @see DamageType#WIND
	 */
	WIND (new BaseAttributeList(){{
		name               	 		= "Wind";
		downgradeType				= null;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/17, /*L*/0, /*D*/0, /*P*/17};
		baseSlowDurationArray 		= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/4, /*L*/0, /*D*/0, /*P*/4};
		baseSlowArray				= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0.05f, /*L*/0, /*D*/0, /*P*/0};
		baseCost					= 1000;
		baseAttackCooldown			= 8.4f;
		baseSplashDamage			= 0f;
		baseSplashEffect			= 0.10f;
		baseSplashRadius			= 2.3f;
		baseRange					= 8.6f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= true;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades					= null;
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
	 * <li> Increases base PHYSICAL DAMAGE and deals a portion of PHYSICAL DAMAGE as a {@link Bleed Bleed}
	 * <li> This Tower's Bleed also applies a PHYSICAL DAMAGE resistance {@link Shred}
	 * </ol>
	 * </ol>
	 */
	EARTH_EARTH (new BaseAttributeList(){{
		//does an AOE earthquake? cannot hit flying
		name						= "Earthquake";
		downgradeType 				= EARTH;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/100};
		baseSlowDurationArray 		= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray				= new float[]{/*E*/8, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/8};
		baseAttackCooldown			= 15f;
		baseSplashDamage			= 0.23f;
		baseSplashEffect			= 0f;
		baseSplashRadius			= 3f;
		baseRange					= 8.1f;
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
		upgrades					= new Upgrade[][]{
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
						 text 		= "This Tower's Bleed also applies a PHYSICAL DAMAGE resistance Shred";
						 baseCost   = 5000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthEarth) t).shredModifier = 0.04f; ((TowerEarthEarth) t).armorShredDuration = 18; ((TowerEarthEarth) t).maxShredStacks = 5; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				}
		};
	}}), 
	/**
	 * {@link Tower Towers} of this type deal high amounts of damage and penetrate armor.
	 * <p>
	 * <ol>
	 * <b>Track 1:</b> Focuses on high range and fast shooting, allowing many different targets to be hit.
	 * <ol>
	 * <li> Reduces base ATTACK COOLDOWN
	 * <li> Increases base and non-base RANGE
	 * <li> Attacks apply a {@link Wealth Wealth} debuff to {@link Creep Creeps}
	 * <li> Increases the duration and effectiveness of Wealth
	 * </ol>
	 * <b>Track 2:</b> Focuses on increasing the physical damage and armor penetration of the Tower.
	 * <ol>
	 * <li> Increases base PHYSICAL DAMAGE
	 * <li> Increases PHYSICAL DAMAGE
	 * <li> Attacks and splash ignores PHYSICAL DAMAGE resistance
	 * <li> Attacks apply a stacking PHYSICAL DAMAGE resistance {@link Shred Shred}
	 * </ol>
	 * </ol>
	 */
	EARTH_FIRE (new BaseAttributeList(){{
		//does tons of damage and armor penetration at some point?
		name 						= "Ironworks";
		downgradeType 				= EARTH;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/10, /*F*/10, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/50};
		baseSlowDurationArray 		= new int[]{/*E*/12, /*F*/7, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/10};
		baseSlowArray				= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown			= 13.1f;
		baseSplashDamage			= 0.25f;
		baseSplashEffect			= 0.25f;
		baseSplashRadius			= 2.2f;
		baseRange					= 8.6f;
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
		upgrades					= new Upgrade[][]{
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
						 text 		= "Increases the duration and effectiveness of Wealth";
						 baseCost   = 5000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthFire) t).wealthDuration += 20; ((TowerEarthFire) t).wealthModifier += 0.30; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "Arms Race";
						 text 		= "Increases base PHYSICAL DAMAGE";
						 baseCost 	= 600;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 50; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Armory";
						 text 		= "Increases PHYSICAL DAMAGE";
						 baseCost   = 500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 13; }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] *= 1.5; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "FMJ";
						 text 		= "Attacks and splash ignores PHYSICAL DAMAGE resistance";
						 baseCost   = 1500;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Piercing";
						 text 		= "Attacks apply a stacking PHYSICAL DAMAGE resistance Shred";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthFire) t).shredModifier = 0.015f; ((TowerEarthFire) t).shredDuration = 15; ((TowerEarthFire) t).maxShredStacks = 10; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				}
		};
	}}),
	/**
	 * This {@link Tower} hits an area of the map as specified by the user.
	 * <p>
	 * <ol>
	 * <b>Track 1:</b> Focuses on increasing the reach of the Tower, hitting more targets and applying damage more effectively.
	 * <ol> 
	 * <li> Increases base AREA RADIUS of the stalagmites
	 * <li> Increases base RANGE and non-base AREA RADIUS
	 * <li> A portion of the SPLASH RADIUS is added to the AREA RADIUS and RANGE, and now applies SPLASH DAMAGE to all targets
	 * <li> Increases splash siphon coefficients, and now applies on hits to all targets
	 * </ol>
	 * <b>Track 2:</b> Focuses in heavily increasing the damage of this Tower.
	 * <ol>
	 * <li> Increases base EARTH DAMAGE
	 * <li> Increases base PHYSICAL DAMAGE
	 * <li> Adds 40% of PHYSICAL DAMAGE as WATER DAMAGE
	 * <li> Doubles the DAMAGE of this tower
	 * </ol>
	 * </ol>
	 */
	EARTH_WATER (new BaseAttributeList(){{
		//this will do rupture spikes on a selected area
		name 						= "Stalagmite";
		downgradeType 				= EARTH;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/30, /*F*/0, /*WA*/30, /*WI*/0, /*L*/0, /*D*/0, /*P*/70};
		baseSlowDurationArray 		= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray				= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown			= 13f;
		baseSplashDamage			= 0.15f;
		baseSplashEffect			= 0.15f;
		baseSplashRadius			= 1.3f;
		baseRange					= 8.6f;
		baseDamageSiphon			= 0.63f;
		baseSlowDurationSiphon		= 0.12f;
		baseSlowSiphon				= 0.12f;
		baseAttackCooldownSiphon	= 4.0f;
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
		upgrades					= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Eon";
						 text 		= "Increases base AREA RADIUS of the stalagmites";
						 baseCost   = 400;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthWater) t).areaRadius += 1; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Cave Building";
						 text 		= "Increases base RANGE and non-base AREA RADIUS";
						 baseCost   = 1300;}
						 public void baseUpgrade(Tower t) {  t.baseAttributeList.baseRange += 0.4f; }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthWater) t).areaRadius += 1; }
						 public void postSiphonUpgrade(Tower t) { t.range += 2.4f;  }
					},
					new Upgrade() {
						{name		= "Echo";
						 text 		= "A portion of the SPLASH RADIUS is added to the AREA RADIUS and RANGE, and now applies SPLASH DAMAGE to all targets";
						 baseCost   = 6200;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDoesSplash = true; }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthWater) t).areaRadius += t.splashRadius / 2; ((TowerEarthWater) t).areaRadius += t.range / 6; } //TODO: Quality affects these equations? (Somehow?)
						 public void postSiphonUpgrade(Tower t) {  } 
					},
					new Upgrade() {
						{name		= "Shattering";
						 text 		= "Increases splash siphon coefficients, and now applies on hits to all targets";
						 baseCost   = 5300;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadiusSiphon += 0.15f; t.baseAttributeList.baseSplashEffectSiphon += 0.15f; t.baseAttributeList.baseSplashDamageSiphon += 0.15f; }
						 public void midSiphonUpgrade(Tower t) { ((TowerEarthWater) t).doesOnHit = true; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "Power of the Earth";
						 text 		= "Increases base EARTH DAMAGE";
						 baseCost 	= 600;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.EARTH.ordinal()] += 40; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Hardening";
						 text 		= "Increases base PHYSICAL DAMAGE";
						 baseCost   = 400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 40; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Elemental Balance";
						 text 		= "Adds 40% of PHYSICAL DAMAGE as WATER DAMAGE";
						 baseCost   = 2900;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.WATER.ordinal()] += t.damageArray[DamageType.PHYSICAL.ordinal()] * .4f; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Burial";
						 text 		= "Doubles the DAMAGE of this tower";
						 baseCost   = 9001;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { for (int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++) { t.damageArray[i]*=2; } }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				}
		};
	}}),
	/**
	 * This {@link Tower} "explodes" upon reaching its target. This Tower has a very large splash radius, splash damage
	 * coefficient, and splash effect coefficient.
	 * <p>
	 * <ol>
	 * <b>Track 1:</b> Focuses on further increasing the effectiveness of splash effects.
	 * <ol>
	 * <li> Increases base and non-base SPLASH DAMAGE and SPLASH EFFECT
	 * <li> Increases base and non-base SPLASH DAMAGE and SPLASH EFFECT
	 * <li> Greatly increases SPLASH RADIUS
	 * <li> Greatly increases base SPLASH RADIUS and splash effects now hit flying {@link Creep Creeps}
	 * </ol>
	 * <b>Track 2:</b> Focuses on reducing attack cooldown to better take advantage of splash effects.
	 * <ol>
	 * <li> Reduces base and non-base ATTACK COOLDOWN
	 * <li> Increases base WIND DAMAGE and base RANGE
	 * <li> Reduces ATTACK COOLDOWN and adds 50% of PHYSICAL DAMAGE as WIND DAMAGE
	 * <li> Converts ATTACK COOLDOWN to PHYSICAL DAMAGE and now fires a blast of sand
	 * </ol>
	 * </ol>
	 */
	EARTH_WIND (new BaseAttributeList(){{
		//has enormous splash radius
		//basically "explodes" outward
		//does not target flying but splash hits flying
		name						= "Fragmentation";
		downgradeType 				= EARTH;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/10, /*F*/0, /*WA*/0, /*WI*/20, /*L*/0, /*D*/0, /*P*/60};
		baseSlowDurationArray 		= new int[]{/*E*/9, /*F*/0, /*WA*/0, /*WI*/4, /*L*/0, /*D*/0, /*P*/8};
		baseSlowArray				= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown			= 14f;
		baseSplashDamage			= 0.15f;
		baseSplashEffect			= 0.15f;
		baseSplashRadius			= 5f;
		baseRange					= 9.0f;
		baseDamageSiphon			= 0.39f;
		baseSlowDurationSiphon		= 0.10f;
		baseSlowSiphon				= 0.10f;
		baseAttackCooldownSiphon	= 5.2f;
		baseSplashDamageSiphon		= 0.46f;
		baseSplashEffectSiphon		= 0.37f;
		baseSplashRadiusSiphon		= 0.39f;
		baseRangeSiphon				= 0.07f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= true;
		upgrades					= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Explosive Force";
						 text 		= "Increases base and non-base SPLASH DAMAGE and SPLASH EFFECT";
						 baseCost   = 1350;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashDamage += .1; t.baseAttributeList.baseSplashEffect += .1; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.splashDamage += .1; t.splashEffect += .1; }
					},
					new Upgrade() {
						{name		= "Explosive Force";
						 text 		= "Increases base and non-base SPLASH DAMAGE and SPLASH EFFECT";
						 baseCost   = 1350;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashDamage += .1; t.baseAttributeList.baseSplashEffect += .1; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.splashDamage += .1; t.splashEffect += .1; }
					},
					new Upgrade() {
						{name		= "Blast Zone";
						 text 		= "Greatly increases SPLASH RADIUS";
						 baseCost   = 2600;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { t.splashRadius *= 1.1f; }
						 public void postSiphonUpgrade(Tower t) { t.splashRadius += 3; }
					},
					new Upgrade() {
						{name		= "Ground Zero";
						 text 		= "Greatly increases base SPLASH RADIUS and splash effects now hit flying Creeps";
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
						 text 		= "Increases base WIND DAMAGE and base RANGE";
						 baseCost   = 1600;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WIND.ordinal()] += 44; t.baseAttributeList.baseRange += 2f;}
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Whipping Winds";
						 text 		= "Reduces ATTACK COOLDOWN and adds 50% of PHYSICAL DAMAGE as WIND DAMAGE";
						 baseCost   = 2150;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { t.attackCooldown -= 3; t.damageArray[DamageType.WIND.ordinal()] += 0.5 * t.damageArray[DamageType.PHYSICAL.ordinal()]; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Sand Blast";
						 text 		= "Converts ATTACK COOLDOWN to PHYSICAL DAMAGE and now fires a blast of sand";
						 baseCost   = 8000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] += (13f / t.attackCooldown) * 40; } //TODO: Quality affects this equation? (Somehow?)
						 public void postSiphonUpgrade(Tower t) { }
					},
				}
		};
	}}), 
	/**
	 * This {@link Tower} deals AoE after one upgrade and focuses on hitting
	 * many {@link Creep} with stat reductions.
	 * <p>
	 * <ol>
	 * <b>Track 1:</b> Focuses on dealing damage to a large number of targets
	 * <ol> 
	 * <li> Now does damage in an area around the Tower
	 * <li> Increases base RANGE
	 * <li> Increases base DAMAGE and reduces base ATTACK COOLDOWN
	 * <li> Dramatically increases RANGE and FIRE DAMAGE. {@link Bleed Poisons} all Creep hit
	 * </ol>
	 * <b>Track 2:</b> Focuses on searing away the resistances of the armor
	 * <ol>
	 * <li> Attacks {@link Shred} PHYSICAL DAMAGE resistance by a flat amount
	 * <li> Attacks {@link ToughnessShred Shred Toughness} by a flat amount
	 * <li> Attacks against poisoned enemies deal additional FIRE DAMAGE
	 * <li> Deals additional FIRE DAMAGE as a {@link MaxHealthDamage percent of max health}
	 * </ol>
	 * </ol>
	 */
	FIRE_EARTH (new BaseAttributeList(){{ //TODO: Needs work, doesn't synergize with self
		name						= "Magma";
		downgradeType 				= FIRE;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/20, /*F*/35, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/35};
		baseSlowDurationArray 		= new int[]{/*E*/11, /*F*/7, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray				= new float[]{/*E*/0.05f, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0.05f};
		baseAttackCooldown			= 14f;
		baseSplashDamage			= 0.05f;
		baseSplashEffect			= 0.05f;
		baseSplashRadius			= 0f;
		baseRange					= 8.5f;
		baseDamageSiphon			= 0.49f;
		baseSlowDurationSiphon		= 0.16f;
		baseSlowSiphon				= 0.18f;
		baseAttackCooldownSiphon	= 4.7f;
		baseSplashDamageSiphon		= 0.39f;
		baseSplashEffectSiphon		= 0.33f;
		baseSplashRadiusSiphon		= 0.33f;
		baseRangeSiphon				= 0.08f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades					= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Volcano";
						 text 		= "Now does damage in an area around the Tower";
						 baseCost   = 2500;}
						 public void baseUpgrade(Tower t) { t.doesSplash = false; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Eruption Force";
						 text 		= "Increases base RANGE";
						 baseCost   = 1300;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 2.5f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Pyroclastic Flow";
						 text 		= "Increases base DAMAGE and reduces base ATTACK COOLDOWN";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 2.2; t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()] += 33; t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 33; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Supervolcano";
						 text 		= "Dramatically increases RANGE and FIRE DAMAGE. Poisons all Creep hit";
						 baseCost   = 11000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=20; ((TowerFireEarth) t).poisonModifier=0.10f; ((TowerFireEarth) t).poisonDuration=18; ((TowerFireEarth) t).maxPoisonStacks = 1; ((TowerFireEarth) t).poisonTiming = 2; }
						 public void postSiphonUpgrade(Tower t) { t.range += 10f; t.damageArray[DamageType.FIRE.ordinal()] += 145; }
					},
				},
				{
					new Upgrade() {
						{name		= "Melt Armor";
						 text 		= "Attacks Shred PHYSICAL DAMAGE resistance by a flat amount";
						 baseCost 	= 1400;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireEarth) t).armorShredModifier = 1; ((TowerFireEarth) t).armorShredDuration = 20; ((TowerFireEarth) t).maxArmorShredStacks = 10; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Sear Flesh";
						 text 		= "Attacks Shred Toughness by a flat amount";
						 baseCost   = 2400;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireEarth) t).toughnessShredModifier = 1; ((TowerFireEarth) t).toughnessShredDuration = 17; ((TowerFireEarth) t).maxToughnessShredStacks = 9; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Swelling";
						 text 		= "Attacks against poisoned enemies deal additional FIRE DAMAGE";
						 baseCost   = 3000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireEarth) t).DOHModifier = 0.05f; ((TowerFireEarth) t).DOHDuration = 25; ((TowerFireEarth) t).maxDOHStacks = 1; ((TowerFireEarth) t).doesOnHit = true; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Blood Boil";
						 text 		= "Deals additional FIRE DAMAGE as a percent of max health";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireEarth) t).percentMaxHealthModifier = 0.02f; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				}
		};
	}}), 
	/**
	 * This {@link Tower} does TONS OF DAMAGE, mostly of FIRE, but of a lot of other types too.
	 * <p>
	 * <ol>
	 * <b>Track 1:</b> Focuses on vastly increasing the damage of this Tower
	 * <ol>
	 * <li> Increases base FIRE DAMAGE
	 * <li> Increases base FIRE DAMAGE then doubles FIRE DAMAGE
	 * <li> Increases then triples FIRE DAMAGE
	 * <li> Applies a {@link Bleed} of each {@link DamageType Damage Type} equal to a percentage of FIRE DAMAGE
	 * </ol>
	 * <b>Track 2:</b> Focuses on splash and reducing resistances
	 * <ol>
	 * <li> Increases base SPLASH RANGE, SPLASH EFFECT, and SPLASH DAMAGE
	 * <li> Increases base RANGE, SPLASH DAMAGE, and DAMAGE SIPHON coefficient
	 * <li> Scorches the earth around the target, damaging all {@link Creep} that pass through it
	 * <li> Scorched Earth reduces all elemental resistances
	 * </ol>
	 * </ol>
	 */
	FIRE_FIRE (new BaseAttributeList(){{
		//this tower ignites enemies with all types of elemental damage
		name						= "Greater Fire";
		downgradeType 				= FIRE;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/0, /*F*/70, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/25};
		baseSlowDurationArray 		= new int[]{/*E*/0, /*F*/14, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray				= new float[]{/*E*/0, /*F*/0.12f, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown			= 12f;
		baseSplashDamage			= 0.25f;
		baseSplashEffect			= 0.25f;
		baseSplashRadius			= 2f;
		baseRange					= 8.5f;
		baseDamageSiphon			= 0.67f;
		baseSlowDurationSiphon		= 0.36f;
		baseSlowSiphon				= 0.12f;
		baseAttackCooldownSiphon	= 4.8f;
		baseSplashDamageSiphon		= 0.44f;
		baseSplashEffectSiphon		= 0.38f;
		baseSplashRadiusSiphon		= 0.37f;
		baseRangeSiphon				= 0.09f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades					= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Stoke the Flames";
						 text 		= "Increases base FIRE DAMAGE";
						 baseCost   = 500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()] += 50; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Increases base FIRE DAMAGE then doubles FIRE DAMAGE";
						 baseCost   = 1700;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()] += 22; }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()] *= 2; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Intensity";
						 text 		= "Increases then triples FIRE DAMAGE";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) {  t.damageArray[DamageType.FIRE.ordinal()] += 40; t.damageArray[DamageType.FIRE.ordinal()] *= 3; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Elemental Fire";
						 text 		= "Applies a Bleed of each element type equal to a percentage of FIRE DAMAGE";
						 baseCost   = 8700;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireFire) t).bleedModifier = 1; ((TowerFireFire) t).bleedDuration = 15; ((TowerFireFire) t).bleedTiming = 3; ((TowerFireFire) t).maxBleedStacks = 3; }
						 public void postSiphonUpgrade(Tower t) { }
					},
				},
				{
					new Upgrade() {
						{name		= "Spreading Flames";
						 text 		= "Increases base SPLASH RANGE, SPLASH EFFECT, and SPLASH DAMAGE";
						 baseCost 	= 1300;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadius += 1; t.baseAttributeList.baseSplashDamage += .10; t.baseAttributeList.baseSplashEffect += .10;}
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Uncontained";
						 text 		= "Increases base RANGE, SPLASH DAMAGE, and DAMAGE SIPHON coefficient";
						 baseCost   = 1900;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 1.2; t.baseAttributeList.baseSplashDamage += .15; t.baseAttributeList.baseDamageSiphon += 0.05; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Scorched Earth";
						 text 		= "Scorches the earth around the target, damaging all Creep that pass through it";
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
	/**
	 * This {@link Tower} focuses on dealing a high amount of magic and splash damage.
	 * <p>
	 * <ol>
	 * <b>Track 1:</b> Focuses on increasing magic damage of the Tower
	 * <ol>
	 * <li> Increases base DAMAGE SIPHON and base DAMAGE of all types
	 * <li> Multiplies all types of DAMAGE
	 * <li> Attacks ignore all types of DAMAGE RESISTANCE
	 * <li> Attacks hits all {@link Creep Creeps} it passes through until reaching its target
	 * </ol>
	 * <b>Track 2:</b> Focuses on increasing the splash effectiveness.
	 * <ol>
	 * <li> Increases base and non-base SPLASH RADIUS
	 * <li> Increases base and non-base SPLASH RADIUS, SPLASH EFFECT, and SPLASH DAMAGE
	 * <li> Leaves behind a zone that, when passed through, deals {@link MaxHealthDamage DAMAGE based on max health}
	 * <li> Increases base and non-base DAMAGE. Enemies in Ground Zero have their {@link Nullify health and shield regeneration permanently set to zero}
	 * </ol>
	 * </ol>
	 */
	FIRE_WATER (new BaseAttributeList(){{
		//attacks ignore all types of defenses
		name						= "Plasma";
		downgradeType 				= FIRE;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/0, /*F*/70, /*WA*/50, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 		= new int[]{/*E*/0, /*F*/6, /*WA*/13, /*WI*/0, /*L*/0, /*D*/0, /*P*/5};
		baseSlowArray				= new float[]{/*E*/0, /*F*/0.05f, /*WA*/0.12f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0.03f};
		baseAttackCooldown			= 13.1f;
		baseSplashDamage			= 0.10f;
		baseSplashEffect			= 0.25f;
		baseSplashRadius			= 1.1f;
		baseRange					= 8.5f;
		baseDamageSiphon			= 0.70f;
		baseSlowDurationSiphon		= 0.54f;
		baseSlowSiphon				= 0.22f;
		baseAttackCooldownSiphon	= 4.8f;
		baseSplashDamageSiphon		= 0.44f;
		baseSplashEffectSiphon		= 0.38f;
		baseSplashRadiusSiphon		= 0.37f;
		baseRangeSiphon				= 0.11f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades					= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Fission";
						 text 		= "Increases base DAMAGE SIPHON and base DAMAGE of all types";
						 baseCost   = 2700;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageSiphon += 0.05f; for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){DamageType d = DamageType.values()[i]; if (d!=DamageType.LIGHT || d!=DamageType.DARK) { t.baseAttributeList.baseDamageArray[i]+=13; }}}
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Amplification";
						 text 		= "Multiplies all types of DAMAGE";
						 baseCost   = 2200;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){DamageType d = DamageType.values()[i]; if (d!=DamageType.LIGHT || d!=DamageType.DARK) { t.damageArray[i]*=1.06f; }} }
						 public void postSiphonUpgrade(Tower t) { for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){DamageType d = DamageType.values()[i]; if (d!=DamageType.LIGHT || d!=DamageType.DARK) { t.damageArray[i]*=1.12f; }} }
					},
					new Upgrade() {
						{name		= "Superheated";
						 text 		= "Attacks ignore all types of DAMAGE RESISTANCE";
						 baseCost   = 3500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Projectile hits all Creeps it passes through until reaching its target";
						 baseCost   = 6000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireWater) t).passThroughRadiusModifier = 0.5f; ((TowerFireWater) t).passThroughModifier = 0.4f; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "Chain Reaction";
						 text 		= "Increases base and non-base SPLASH RADIUS";
						 baseCost 	= 1400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadius += 1.2f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.splashRadius += 1.4f; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Increases base and non-base SPLASH RADIUS, SPLASH EFFECT, and SPLASH DAMAGE";
						 baseCost   = 3400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadius += 0.5f; t.baseAttributeList.baseSplashDamage += 0.03f; t.baseAttributeList.baseSplashEffect += 0.03f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.splashRadius += 4; t.splashDamage += 0.06f; t.splashEffect += 0.06f; }
					},
					new Upgrade() {
						{name		= "Ground Zero";
						 text 		= "Leaves behind a zone that, when passed through, deals DAMAGE based on max health";
						 baseCost   = 4600;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireWater) t).patchLifetime = 18; ((TowerFireWater) t).patchTiming = 3; ((TowerFireWater) t).patchMaxHealthModifier = 0.00005f; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Gamma Radiation";
						 text 		= "Increases DAMAGE. Enemies in Ground Zero have their health and shield regeneration permanently set to zero";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WATER.ordinal()] += 25; t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()] += 25; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.damageArray[DamageType.WATER.ordinal()] += 50; t.damageArray[DamageType.FIRE.ordinal()] += 50;}
					},
				}
		};
	}}), 
	/**
	 * This {@link Tower} fires a laser outward at a position specified by the user.
	 * Deals heavy FIRE damage and moderate WIND damage.
	 * <p>
	 * <ol>
	 * <b>Track 1:</b> Focuses on increasing the range and width of the laser.
	 * <ol>
	 * <li> Increases base RANGE
	 * <li> Greatly increases base and non-base RANGE
	 * <li> Increases base FIRE and WIND DAMAGE and widens the laser
	 * <li> Increases laser width and range, hits flying, and applies splash effects to affected {@link Creep}
	 * </ol>
	 * <b>Track 2:</b> Focuses on increasing the damage of the Tower and those around it.
	 * <ol>
	 * <li> Multiplies FIRE DAMAGE
	 * <li> Multiplies all DAMAGE types
	 * <li> Greatly increases SIPHON coefficients
	 * <li> Multiplies DAMAGE of all Towers nearby by
	 * </ol>
	 * </ol>
	 */
	FIRE_WIND (new BaseAttributeList(){{
		//this tower fires a laser thing that passes through enemies and goes a fixed distance
		name						= "Firestorm";
		downgradeType 				= FIRE;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/0, /*F*/40, /*WA*/0, /*WI*/5, /*L*/0, /*D*/0, /*P*/20};
		baseSlowDurationArray 		= new int[]{/*E*/0, /*F*/8, /*WA*/0, /*WI*/4, /*L*/0, /*D*/0, /*P*/5};
		baseSlowArray				= new float[]{/*E*/0, /*F*/0.06f, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown			= 10.7f;
		baseSplashDamage			= 0f;
		baseSplashEffect			= 0f;
		baseSplashRadius			= 0f;
		baseRange					= 8.8f;
		baseDamageSiphon			= 0.48f;
		baseSlowDurationSiphon		= 0.15f;
		baseSlowSiphon				= 0.38f;
		baseAttackCooldownSiphon	= 4.6f;
		baseSplashDamageSiphon		= 0.60f;
		baseSplashEffectSiphon		= 0.60f;
		baseSplashRadiusSiphon		= 0.60f;
		baseRangeSiphon				= 0.12f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= false;
		baseDoesSlow				= false;
		baseDoesOnHit				= false;
		baseSplashHitsAir			= false;
		upgrades					= new Upgrade[][]{
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
						 text 		= "Greatly increases base and non-base RANGE";
						 baseCost   = 1700;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 1.2f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.range += 4.8f; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Increases base FIRE and WIND DAMAGE and widens the laser";
						 baseCost   = 4500;}
						 public void baseUpgrade(Tower t) {  t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()]+=67f; t.baseAttributeList.baseDamageArray[DamageType.WIND.ordinal()]+= 34; t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()]+= 44; }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireWind) t).passThroughRadiusModifier += 0.05f; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Heat Column";
						 text 		= "Increases laser width and range, hits flying, and applies splash effects to affected Creep";
						 baseCost   = 6000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerFireWind) t).passThroughRadiusModifier += 0.05f; ((TowerFireWind) t).doesSplash = true; t.hitsAir = true; }
						 public void postSiphonUpgrade(Tower t) {  t.range += 1f; }
					},
				},
				{
					new Upgrade() {
						{name		= "";
						 text 		= "Multiplies FIRE DAMAGE";
						 baseCost 	= 1300;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()] *= 1.5f; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Multiplies all DAMAGE types";
						 baseCost   = 2900;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { for(int i=0; i<GameConstants.NUM_DAMAGE_TYPES; i++) { t.damageArray[i] *= 1.2; } }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Greatly increases SIPHON coefficients";
						 baseCost   = 7500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageSiphon += 0.10f; t.baseAttributeList.baseSplashDamageSiphon += 0.10f; t.baseAttributeList.baseSlowSiphon += 0.10f; t.baseAttributeList.baseSlowDurationSiphon += 0.10f; t.baseAttributeList.baseSplashEffectSiphon += 0.10f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Multiplies DAMAGE of all Towers nearby by";
						 baseCost   = 8000;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) {  } 
						 public void postSiphonUpgrade(Tower t) { }
					},
				}
		};
	}}), 
	/**
	 * This {@link Tower} slows heavily and can reduce resistances.
	 * <p>
	 * <ol>
	 * <b>Track 1:</b> Focuses on flooding the map to slow and destroy resistances
	 * <ol>
	 * <li> Increases base and non-base RANGE
	 * <li> Reduces base and non-base ATTACK COOLDOWN
	 * <li> Reduces {@link Creep} PHYSICAL DAMAGE resist
	 * <li> Hits air targets, doubles base SLOW DURATION, and...
	 * </ol>
	 * <b>Track 2:</b> Focuses on encasing Creep in mud, slowing their movement.
	 * <ol>
	 * <li> Increases base SLOW
	 * <li> Doubles base EARTH and WATER SLOW DURATION
	 * <li> Allows for modification of tile types
	 * <li> Permanently reduces maximum Creep speed
	 * </ol>
	 * </ol>
	 */
	WATER_EARTH (new BaseAttributeList(){{ //TODO: Flower tower or something if this sucks
		//does large AoE slow
		name						= "Mud";
		downgradeType 				= WATER;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/10, /*F*/0, /*WA*/10, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 		= new int[]{/*E*/15, /*F*/0, /*WA*/15, /*WI*/0, /*L*/0, /*D*/0, /*P*/4};
		baseSlowArray				= new float[]{/*E*/0.10f, /*F*/0, /*WA*/0.15f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0.03f};
		baseAttackCooldown			= 12.5f;
		baseSplashDamage			= 0f;
		baseSplashEffect			= 0.10f;
		baseSplashRadius			= 1f;
		baseRange					= 8.5f;
		baseDamageSiphon			= 0.45f;
		baseSlowDurationSiphon		= 0.55f;
		baseSlowSiphon				= 0.58f;
		baseAttackCooldownSiphon	= 4.8f;
		baseSplashDamageSiphon		= 0.50f;
		baseSplashEffectSiphon		= 0.50f;
		baseSplashRadiusSiphon		= 0.50f;
		baseRangeSiphon				= 0.09f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= false;
		baseDoesSlow				= true;
		baseDoesOnHit				= false;
		baseSplashHitsAir			= false;
		upgrades					= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Flash Flood";
						 text 		= "Increases base and non-base RANGE";
						 baseCost   = 900;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseRange += 1.3f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.baseAttributeList.baseRange += 1.4f; }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Reduces base and non-base ATTACK COOLDOWN";
						 baseCost   = 1300;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 1.9f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.attackCooldown -= 2.3f; }
					},
					new Upgrade() {
						{name		= "Churning River";
						 text 		= "Reduces Creep PHYSICAL DAMAGE resist";
						 baseCost   = 3500;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterEarth) t).shredModifier = 0.3f; ((TowerWaterEarth) t).shredDuration = 24; ((TowerWaterEarth) t).maxShredStacks = 1; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Heavy Rains";
						 text 		= "Hits air targets, doubles base SLOW DURATION, and..."; //TODO:
						 baseCost   = 2000;}
						 public void baseUpgrade(Tower t) { t.hitsAir = true; t.splashHitsAir = true; t.doesSplash = true; for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){t.slowDurationArray[i]*=2;} }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
				},
				{
					new Upgrade() {
						{name		= "Thickening Mud";
						 text 		= "Increases base SLOW";
						 baseCost 	= 1200;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSlowArray[DamageType.WATER.ordinal()] += .2f; t.baseAttributeList.baseSlowArray[DamageType.EARTH.ordinal()] += .2f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Cloud Cover";
						 text 		= "Doubles base EARTH and WATER SLOW DURATION";
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
						 text 		= "Permanently reduces maximum Creep speed";
						 baseCost   = 4000;}
						 public void baseUpgrade(Tower t) {  }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterEarth) t).permaSlowModifier = 0.005f; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				}
		};
	}}), 
	/**
	 * This {@link Tower} can vaporize enemies with heavy FIRE damage or purge off disruption and deathrattle effects.
	 * <p>
	 * <ol>
	 * <b>Track 1:</b> Focuses on purging the {@link Creep} of annoying effects.
	 * <ol>
	 * <li> Increases base WATER DAMAGE, SLOW, and SLOW DURATION
	 * <li> Reduces base ATTACK COOLDOWN
	 * <li> Reduces disruptor effects for a time
	 * <li> Increases the disruptor reduction and disables Creep deathrattles for a time
	 * </ol>
	 * <b>Track 2:</b> Focuses on increasing FIRE damage and splash.
	 * <ol>
	 * <li> Increases base FIRE damage and reduces base ATTACK COOLDOWN
	 * <li> Increases base FIRE DAMAGE and SPLASH RADIUS
	 * <li> Increases base and non-base FIRE and WATER DAMAGE and base SPLASH DAMAGE coefficient
	 * <li> Ignores Creep FIRE and WATER resistances and increases DAMAGE SIPHON coefficient and base SPLASH DAMAGE coefficient
	 * </ol>
	 * </ol>
	 */
	WATER_FIRE (new BaseAttributeList(){{
		//this tower applies an MR shred for a period of time
		name						= "Purging"; //(?)
		downgradeType 				= WATER;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/0, /*F*/50, /*WA*/40, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 		= new int[]{/*E*/0, /*F*/4, /*WA*/10, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray				= new float[]{/*E*/0, /*F*/0.02f, /*WA*/0.10f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown			= 13.1f;
		baseSplashDamage			= 0.04f;
		baseSplashEffect			= 0.04f;
		baseSplashRadius			= 1f;
		baseRange					= 8.2f;
		baseDamageSiphon			= 0.45f;
		baseSlowDurationSiphon		= 0.55f;
		baseSlowSiphon				= 0.58f;
		baseAttackCooldownSiphon	= 4.6f;
		baseSplashDamageSiphon		= 0.34f;
		baseSplashEffectSiphon		= 0.37f;
		baseSplashRadiusSiphon		= 0.33f;
		baseRangeSiphon				= 0.10f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= false;
		baseHitsGround				= true;
		baseDoesSplash				= true;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades					= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Watery Grave";
						 text 		= "Increases base WATER DAMAGE, SLOW, and SLOW DURATION";
						 baseCost   = 1900;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WATER.ordinal()]+=42; t.baseAttributeList.baseSlowArray[DamageType.WATER.ordinal()]+=0.11f; t.baseAttributeList.baseSlowDurationArray[DamageType.WATER.ordinal()]+=7; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Elemental Fury";
						 text 		= "Reduces base ATTACK COOLDOWN";
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
						 text 		= "Increases base FIRE damage and reduces base ATTACK COOLDOWN";
						 baseCost   = 1350;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()]+=47; t.baseAttributeList.baseAttackCooldown-=1.4f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Scalding";
						 text 		= "Increases base FIRE DAMAGE and SPLASH RADIUS";
						 baseCost   = 3100;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()]+=21; t.baseAttributeList.baseSplashRadius+=0.7f; }
						 public void midSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=21f; t.splashRadius+=0.7f; }
						 public void postSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=21f; t.splashRadius+=0.7f;}
					},
					new Upgrade() {
						{name		= "Third Degree";
						 text 		= "Increases base and non-base FIRE and WATER DAMAGE and base SPLASH DAMAGE coefficient";
						 baseCost   = 7300;}
						 public void baseUpgrade(Tower t) {  t.baseAttributeList.baseDamageArray[DamageType.FIRE.ordinal()]+=34; t.baseAttributeList.baseDamageArray[DamageType.WATER.ordinal()]+=34; t.baseAttributeList.baseSplashDamage+=0.075f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()]+=158; }
					},
					new Upgrade() {
						{name		= "Superheated";
						 text 		= "Ignores Creep FIRE and WATER resistances and increases DAMAGE SIPHON coefficient and base SPLASH DAMAGE coefficient";
						 baseCost   = 7300;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageSiphon += 0.15f; t.baseAttributeList.baseSplashDamage += 0.075f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
				}
		};
	}}),
	/**
	 * This {@link Tower} does enormous amounts of WATER {@link Slow} in area around it.
	 * At later stages can {@link Snare} and {@link Consume} damage over time effects.
	 * <p>
	 * <ol>
	 * <b>Track 1:</b> Focuses on increasing the slow and hitting more enemies
	 * <ol>
	 * <li> Increases base RANGE
	 * <li> Increases base WATER SLOW DURATION and RANGE
	 * <li> Doubles WATER SLOW potency and increases base RANGE and reduces base ATTACK COOLDOWN
	 * <li> {@link Snare Roots} Enemies in place and attacks apply on hit effects
	 * </ol>
	 * <b>Track 2:</b> Focuses on increasing the damage and cleansing enemies of damage on hit
	 * <ol>
	 * <li> Reduces base ATTACK COOLDOWN
	 * <li> Increases base WATER DAMAGE
	 * <li> Slowed Enemies take additional {@link Bleed damage over time} and halves enemy WATER resistance
	 * <li> Consumes all damage over time effects on enemies hit and deals half their remaining damage
	 * </ol>
	 * </ol>
	 */
	WATER_WATER (new BaseAttributeList(){{
		//this tower does an aoe slow/freeze
		downgradeType 				= WATER;
		name     					= "Cold Snap";
		baseWidth  					= 2;
		baseHeight   				= 2;
		baseDamageArray  			= new float[]{/*E*/0, /*F*/0, /*WA*/20, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 		= new int[]{/*E*/0, /*F*/0, /*WA*/20, /*WI*/0, /*L*/0, /*D*/0, /*P*/10};
		baseSlowArray				= new float[]{/*E*/0, /*F*/0, /*WA*/0.30f, /*WI*/0, /*L*/0, /*D*/0, /*P*/0.10f};
		baseAttackCooldown 			= 15.9f;
		baseSplashDamage 			= 0f;
		baseSplashEffect 			= 0.30f;
		baseSplashRadius 			= 2f;
		baseRange   				= 7.6f;
		baseDamageSiphon			= 0.36f;
		baseSlowDurationSiphon		= 0.60f;
		baseSlowSiphon				= 0.60f;
		baseAttackCooldownSiphon	= 5.3f;
		baseSplashDamageSiphon		= 0.43f;
		baseSplashEffectSiphon		= 0.65f;
		baseSplashRadiusSiphon		= 0.62f;
		baseRangeSiphon				= 0.11f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir    				= false;
		baseHitsGround   			= true;
		baseDoesSplash				= false;
		baseDoesSlow				= true;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= false;
		upgrades   					= new Upgrade[][]{
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
				    	  text  	 = "Increases base WATER SLOW DURATION and RANGE";
				    	  baseCost   = 1400;}
				    	  public void baseUpgrade(Tower t) { t.baseAttributeList.baseSlowDurationArray[DamageType.WATER.ordinal()] += 17; }
				    	  public void midSiphonUpgrade(Tower t) { }
						  public void postSiphonUpgrade(Tower t) { }
				     },
				     new Upgrade() {
				    	 {name 		 = "Chilled to the Bone";
				    	  text  	 = "Doubles WATER SLOW potency and increases base RANGE and reduces base ATTACK COOLDOWN";
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
				      	  text   	 = "Reduces base ATTACK COOLDOWN";
				      	  baseCost   = 600;}
				      	  public void baseUpgrade(Tower t) { t.baseAttributeList.baseAttackCooldown -= 2.2f; }
				      	  public void midSiphonUpgrade(Tower t) { }
						  public void postSiphonUpgrade(Tower t) { }
				     },
				     new Upgrade() {
				    	 {name 		 = "Black Ice";
				    	  text   	 = "Increases base WATER DAMAGE";
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
	/**
	 * This {@link Tower} fires a {@link ProjectileRandom projectile} that does a random effect to all creep in splash range.
	 * The effects improve as the Tower is upgraded.
	 * <p>
	 * <ol>
	 * <b>Track 1:</b> Focuses on the WATER aspect of this Tower, improving Slows and chilling enemies to reduce their Toughness.
	 * <ol>
	 * <li> Gives a chance to apply a percent {@link ToughnessShred Toughness Shred}
	 * <li> Increases SLOW potencies
	 * <li> Increases the chances of a toughness shred, its effectiveness, and increases base WATER DAMAGE
	 * <li> Increases base SPLASH RADIUS and RANGE and gives a chance to apply a {@link Snare}
	 * </ol>
	 * <b>Track 2:</b> Focuses on the WIND aspect of this Tower, prioritizing range, quick firing, and granting on hit bonuses
	 * <ol>
	 * <li> Increases base PHYSICAL DAMAGE
	 * <li> Increases base and non-base RANGE and SPLASH RADIUS
	 * <li> Gives a chance to grant a gold on hit buff to enemies and increases base WIND DAMAGE
	 * <li> Reduces ATTACK COOLDOWN and gives a chance to grant a WATER DAMAGE on hit debuff to enemies
	 * </ol>
	 * </ol>
	 */
	WATER_WIND (new BaseAttributeList(){{
		//Fires a missile that does one of three random effects. Gold, damage, slow, and additional effects
		name						= "Hail";
		downgradeType 				= WATER;
		baseWidth					= 2;
		baseHeight					= 2;
		baseDamageArray				= new float[]{/*E*/0, /*F*/0, /*WA*/20, /*WI*/20, /*L*/0, /*D*/0, /*P*/20};
		baseSlowDurationArray 		= new int[]{/*E*/0, /*F*/0, /*WA*/15, /*WI*/5, /*L*/0, /*D*/0, /*P*/7};
		baseSlowArray				= new float[]{/*E*/0, /*F*/0, /*WA*/0.15f, /*WI*/0.05f, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCooldown			= 11.6f;
		baseSplashDamage			= 0.20f;
		baseSplashEffect			= 0.20f;
		baseSplashRadius			= 3.2f;
		baseRange					= 7.7f;
		baseDamageSiphon			= 0.44f;
		baseSlowDurationSiphon		= 0.44f;
		baseSlowSiphon				= 0.44f;
		baseAttackCooldownSiphon	= 4.6f;
		baseSplashDamageSiphon		= 0.23f;
		baseSplashEffectSiphon		= 0.22f;
		baseSplashRadiusSiphon		= 0.41f;
		baseRangeSiphon				= 0.09f;
		baseIsOnGround				= true;
		baseIsInAir					= false;
		baseHitsAir					= true;
		baseHitsGround				= true;
		baseDoesSplash				= false;
		baseDoesSlow				= false;
		baseDoesOnHit				= true;
		baseSplashHitsAir			= true;
		upgrades					= new Upgrade[][]{
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
						 text 		= "Increases SLOW potencies";
						 baseCost   = 2000;}
						 public void baseUpgrade(Tower t) { }
						 public void midSiphonUpgrade(Tower t) { for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){ t.slowArray[DamageType.values()[i].ordinal()]*= 1.15f;}  }
						 public void postSiphonUpgrade(Tower t) { for(int i=0;i<GameConstants.NUM_DAMAGE_TYPES;i++){ t.slowArray[DamageType.values()[i].ordinal()]*= 1.25f;} }
					},
					new Upgrade() {
						{name		= "Polar Vortex";
						 text 		= "Increases the chances of a Toughness Shred, its effectiveness, and increases base WATER DAMAGE";
						 baseCost   = 3500;}
						 public void baseUpgrade(Tower t) {  t.baseAttributeList.baseDamageArray[DamageType.WATER.ordinal()]+=54; }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterWind) t).toughnessWeight = 14; ((TowerWaterWind) t).toughShredModifier = 0.3f; }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "";
						 text 		= "Increases base SPLASH RADIUS and RANGE and gives a chance to apply a Snare";
						 baseCost   = 7200;}
						 public void baseUpgrade(Tower t) {  t.baseAttributeList.baseRange += 2.1f; t.baseAttributeList.baseAttackCooldown -= 1.2f; }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterWind) t).snareDuration = 8; ((TowerWaterWind) t).snareWeight = 5; }
						 public void postSiphonUpgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] += 60f; }
					},
				},
				{
					new Upgrade() {
						{name		= "Pelting";
						 text 		= "Increases base PHYSICAL DAMAGE";
						 baseCost 	= 750;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.PHYSICAL.ordinal()] += 43; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Swirling Winds";
						 text 		= "Increases base and non-base RANGE and SPLASH RADIUS";
						 baseCost   = 3400;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseSplashRadius += 0.8f; t.baseAttributeList.baseRange += 0.8f; }
						 public void midSiphonUpgrade(Tower t) { }
						 public void postSiphonUpgrade(Tower t) { t.splashRadius += 0.8f; t.range += 0.8f; }
					},
					new Upgrade() {
						{name		= "Preserved in Ice";
						 text 		= "Gives a chance to grant a gold on hit buff to enemies and increases base WIND DAMAGE";
						 baseCost   = 5500;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WIND.ordinal()]+=37f; }
						 public void midSiphonUpgrade(Tower t) { ((TowerWaterWind) t).goldOnHitDuration = 25; ((TowerWaterWind) t).maxGOHStacks = 3; ((TowerWaterWind) t).goldOnHitModifier = 1.3f; ((TowerWaterWind) t).goldOnHitWeight = 5; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Shattering Frost";
						 text 		= "Reduces ATTACK COOLDOWN and gives a chance to grant a WATER DAMAGE on hit debuff to enemies";
						 baseCost   = 7000;}
						 public void baseUpgrade(Tower t) { t.baseAttributeList.baseDamageArray[DamageType.WATER.ordinal()] += 29f; }
						 public void midSiphonUpgrade(Tower t) { t.attackCooldown /= 1.1f; ((TowerWaterWind) t).damageOnHitDuration = 20; ((TowerWaterWind) t).damageOnHitModifier = 0.05f; ((TowerWaterWind) t).maxDOHStacks = 3; ((TowerWaterWind) t).damageOnHitWeight = 5; }
						 public void postSiphonUpgrade(Tower t) {  }
					},
				}
		};
	}}), 
	/**
	 * 
	 * <p>
	 * <ol>
	 * <b>Track 1:</b> 
	 * <ol>
	 * <li> 
	 * <li> 
	 * <li> 
	 * <li> 
	 * </ol>
	 * <b>Track 2:</b> 
	 * <ol>
	 * <li> 
	 * <li> 
	 * <li> 
	 * <li> 
	 * </ol>
	 * </ol>
	 */
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
