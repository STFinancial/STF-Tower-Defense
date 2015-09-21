package towers;
//TODO: Fix imports of all classes.
//TODO: Tower targeting types that allow to target those that we can snare and disorient, etc.
//TODO: One tower could prevent deathrattle
import utilities.GameConstants;
import creeps.DamageType;
import projectiles.*;
//TODO can use a priority value instead of "isBase"
//EARTH, FIRE, WATER, WIND, LIGHT, DARK, PHYSICAL;
public enum TowerType {
	//TODO: I want to properly comment each of the enums so they are working for the javadoc and you can get information about each tower.
	//TODO: Instead of using "isBase" just modify the base values directly. This way we can mix. Need to clone the attribute lists for each tower then, but no biggie.
	EARTH (new BaseAttributeList(){{
		name                  	= "Earth";
		baseWidth			  	= 2;
		baseHeight			  	= 2;
		baseDamageArray		  	= new float[]{/*E*/10, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/60};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseCost			  	= 200;
		baseAttackCoolDown	  	= 15f;
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
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseCost				= 200;
		baseAttackCoolDown		= 12f;
		baseDamageSplash		= 0.25f;
		baseEffectSplash		= 0.25f;
		baseSplashRadius		= 1.5f;
		baseRange				= 7.5f;
		hitsAir					= true;
		hitsGround				= true;
		upgrades				= null;
	}}),
	WATER (new BaseAttributeList(){{
		name               	 	= "Water";
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/15, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseCost				= 200;
		baseAttackCoolDown		= 10f;
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
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/10, /*L*/0, /*D*/0, /*P*/10};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseCost				= 200;
		baseAttackCoolDown		= 5f;
		baseDamageSplash		= 0f;
		baseEffectSplash		= 0.10f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		hitsAir					= true;
		hitsGround				= true;
		upgrades				= null;
	}}),
	//TODO should the base stats of the towers be identical to their downgrade?
	EARTH_EARTH (new BaseAttributeList(){{
		//does an AOE earthquake? cannot hit flying
		name					= "Earthquake";
		downgradeType 			= EARTH;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/100};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCoolDown		= 15f;
		baseDamageSplash		= 0f;
		baseEffectSplash		= 0f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
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
						 public void upgrade(Tower t) { t.damageArray[DamageType.EARTH.ordinal()] += t.damageArray[DamageType.PHYSICAL.ordinal()] * 1.5f; t.damageArray[DamageType.PHYSICAL.ordinal()] = 0; }
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
						 baseCost 	= 1300;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] *= 2; }
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
						 public void upgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] += 50; }
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
		name 					= "Ironworks";
		downgradeType 			= EARTH;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/10, /*F*/10, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/50};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCoolDown		= 13f;
		baseDamageSplash		= 0f;
		baseEffectSplash		= 0f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Rapid Fire";
						 text 		= "Reduces base ATTACK COOLDOWN";
						 isBase		= true;
						 baseCost   = 400;}
						 public void upgrade(Tower t) { t.attackCoolDown -= 2; }
					},
					new Upgrade() {
						{name		= "Aerodynamics";
						 text 		= "Increases the range";
						 isBase		= false;
						 baseCost   = 500;}
						 public void upgrade(Tower t) { t.range += 4; }
					},
					new Upgrade() {
						{name		= "Gather the Scraps";
						 text 		= "Tower applies a wealth debuff to minions";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Motherload";
						 text 		= "Increases the duration and effectiveness of wealth";
						 isBase		= false;
						 baseCost   = 5000;}
						 public void upgrade(Tower t) { ((TowerEarthFire) t).wealthDuration += 20; ((TowerEarthFire) t).wealthModifier += .20; }
					},
				},
				{
					new Upgrade() {
						{name		= "Arms Race";
						 text 		= "Increases the base PHYSICAL damage";
						 isBase 	= true;
						 baseCost 	= 600;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] += 50; }
					},
					new Upgrade() {
						{name		= "Armory";
						 text 		= "Increases the PHYSICAL damage";
						 isBase		= false;
						 baseCost   = 500;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] *= 1.5; }
					},
					new Upgrade() {
						{name		= "FMJ";
						 text 		= "Attacks and splash ignores armor";
						 isBase		= false;
						 baseCost   = 1500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Piercing";
						 text 		= "Attacks apply a stacking armor shred";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) {  }
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
		baseAttackCoolDown		= 13f;
		baseDamageSplash		= 0f;
		baseEffectSplash		= 0f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
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
						 public void upgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] += 40; }
					},
					new Upgrade() {
						{name		= "Elemental Balance";
						 text 		= "Adds 40% of PHYSICAL damage as WATER damage";
						 isBase		= false;
						 baseCost   = 1500;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.EARTH.ordinal()] += t.damageArray[DamageType.PHYSICAL.ordinal()] * .4f; }
					},
					new Upgrade() {
						{name		= "Burial";
						 text 		= "Doubles the damage of this tower";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) { for (int i=0;i<GameConstants.NUM_DAMAGE_TYPES-1;i++) { t.damageArray[i]*=2; } }
					},
				}
		};
	}}),
	EARTH_WIND (new BaseAttributeList(){{
		//has enormous splash radius
		//basically "explodes" outward
		name					= "Fragmentation";
		downgradeType 			= EARTH;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/10, /*F*/0, /*WA*/0, /*WI*/20, /*L*/0, /*D*/0, /*P*/60};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCoolDown		= 14f;
		baseDamageSplash		= 0.10f;
		baseEffectSplash		= 0.10f;
		baseSplashRadius		= 5f;
		baseRange				= 9.0f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Explosive Force";
						 text 		= "Increases the splash damage and effectiveness";
						 isBase		= true;
						 baseCost   = 1350;}
						 public void upgrade(Tower t) { t.damageSplash += .2; t.effectSplash += .2; }
					},
					new Upgrade() {
						{name		= "Explosive Force";
						 text 		= "Increases the splash damage and effectiveness";
						 isBase		= true;
						 baseCost   = 1350;}
						 public void upgrade(Tower t) { t.damageSplash += .2; t.effectSplash += .2; }
					},
					new Upgrade() {
						{name		= "Blast Zone";
						 text 		= "Greatly increases the splash radius";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) { t.splashRadius += 5; }
					},
					new Upgrade() {
						{name		= "Ground Zero";
						 text 		= "Greatly increases the base splash radius";
						 isBase		= true;
						 baseCost   = 5000;}
						 public void upgrade(Tower t) { t.splashRadius += 5; }
					},
				},
				{
					new Upgrade() {
						{name		= "Howl";
						 text 		= "Reduces ATTACK COOLDOWN";
						 isBase 	= false;
						 baseCost 	= 1000;}
						 public void upgrade(Tower t) { t.attackCoolDown -= 3; }
					},
					new Upgrade() {
						{name		= "Brewing Storm";
						 text 		= "Increase base WIND damage";
						 isBase		= false;
						 baseCost   = 900;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.WIND.ordinal()] += 40; }
					},
					new Upgrade() {
						{name		= "Whipping Winds";
						 text 		= "Reduces ATTACK COOLDOWN and adds 50% of PHYSICAL damage as WIND damage";
						 isBase		= false;
						 baseCost   = 1500;}
						 public void upgrade(Tower t) { t.attackCoolDown -= 3; t.damageArray[DamageType.WIND.ordinal()] += 0.5 * t.damageArray[DamageType.PHYSICAL.ordinal()];}
					},
					new Upgrade() {
						{name		= "Sand Blast";
						 text 		= "Converts ATTACK COOLDOWN to PHYSICAL damage and now fires a blast of sand";
						 isBase		= true;
						 baseCost   = 8000;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.PHYSICAL.ordinal()] += (10f / t.attackCoolDown) * 40; }
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
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCoolDown		= 14f;
		baseDamageSplash		= 0.05f;
		baseEffectSplash		= 0.05f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Volcano";
						 text 		= "The tower now does AoE damage";
						 isBase		= false;
						 baseCost   = 2500;}
						 public void upgrade(Tower t) { }
					},
					new Upgrade() {
						{name		= "Eruption Force";
						 text 		= "Increase BASE range of the tower";
						 isBase		= true;
						 baseCost   = 1300;}
						 public void upgrade(Tower t) { t.range += 2.5f; }
					},
					new Upgrade() {
						{name		= "Pyroclastic Flow";
						 text 		= "Increases BASE damage and reduces BASE attack cooldown";
						 isBase		= true;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) { t.attackCoolDown -= 2.2; t.damageArray[DamageType.FIRE.ordinal()] += 33; t.damageArray[DamageType.PHYSICAL.ordinal()] += 33; }
					},
					new Upgrade() {
						{name		= "Supervolcano";
						 text 		= "Dramatically increases range and fire damage. Poisons all enemies.";
						 isBase		= false;
						 baseCost   = 7000;}
						 public void upgrade(Tower t) { t.range += 10f; t.damageArray[DamageType.FIRE.ordinal()] += 165; }
					},
				},
				{
					new Upgrade() {
						{name		= "Melt Armor";
						 text 		= "Attacks reduce armor by a flat amount";
						 isBase 	= false;
						 baseCost 	= 1400;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Sear Flesh";
						 text 		= "Attacks reduce toughness by a flat amount";
						 isBase		= false;
						 baseCost   = 2400;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Swelling";
						 text 		= "Attacks against afflicted enemies deal additional FIRE damage";
						 isBase		= false;
						 baseCost   = 3000;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Blood Boil";
						 text 		= "Deals additional FIRE damage as a percent of max health";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) {  }
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
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCoolDown		= 12f;
		baseDamageSplash		= 0.25f;
		baseEffectSplash		= 0.25f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		hitsAir					= false;
		hitsGround				= true;
		upgrades				= new Upgrade[][]{
				{
					new Upgrade() {
						{name		= "Stoke the Flames";
						 text 		= "Increases the base FIRE damage";
						 isBase		= true;
						 baseCost   = 500;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()] += 50; }
					},
					new Upgrade() {
						{name		= "Heating Up"; //TODO ugh
						 text 		= "Doubles FIRE damage";
						 isBase		= false;
						 baseCost   = 1700;}
						 public void upgrade(Tower t) { t.damageArray[DamageType.FIRE.ordinal()] *= 2; }
					},
					new Upgrade() {
						{name		= "Intensity";
						 text 		= "Increases then triples FIRE damage";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) {  t.damageArray[DamageType.FIRE.ordinal()] += 40; t.damageArray[DamageType.FIRE.ordinal()] *= 3; }
					},
					new Upgrade() {
						{name		= "Elemental Fire";
						 text 		= "Applies a bleed of each element type equal to a percentage of your FIRE damage"; //TODO ugh
						 isBase		= false;
						 baseCost   = 5000;}
						 public void upgrade(Tower t) {  }
					},
				},
				{
					new Upgrade() {
						{name		= "Spreading Flames";
						 text 		= "Increase base splash range and effectiveness";
						 isBase 	= true;
						 baseCost 	= 1300;}
						 public void upgrade(Tower t) { t.splashRadius += 1; t.damageSplash += .10; t.effectSplash += .10;}
					},
					new Upgrade() {
						{name		= "Uncontained";
						 text 		= "Increases base range and splash damage coefficient";
						 isBase		= true;
						 baseCost   = 1400;}
						 public void upgrade(Tower t) { t.range += 1.2; t.damageSplash += .15; }
					},
					new Upgrade() {
						{name		= "Scorched Earth";
						 text 		= "Scorches the earth around the target, damaging all enemies that pass through it";
						 isBase		= false;
						 baseCost   = 2500;}
						 public void upgrade(Tower t) {  }
					},
					new Upgrade() {
						{name		= "Burning Flesh";
						 text 		= "Scorched Earth reduces all elemental resistances";
						 isBase		= false;
						 baseCost   = 4000;}
						 public void upgrade(Tower t) {  }
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
		baseAttackCoolDown		= 12.3f;
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
	FIRE_WIND (new BaseAttributeList(){{
		//this tower fires a laser thing that passes through enemies and goes a fixed distance
		name					= "";
		downgradeType 			= FIRE;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCoolDown		= 12.3f;
		baseDamageSplash		= 0.25f;
		baseEffectSplash		= 0.25f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		hitsAir					= false;
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
	WATER_EARTH (new BaseAttributeList(){{
		//does large AoE slow
		name					= "Mud";
		downgradeType 			= WATER;
		baseWidth				= 2;
		baseHeight				= 2;
		baseDamageArray			= new float[]{/*E*/10, /*F*/0, /*WA*/10, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCoolDown		= 12.3f;
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
						 public void upgrade(Tower t) { t.attackCoolDown -= 5; }
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
		baseDamageArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseAttackCoolDown		= 12.3f;
		baseDamageSplash		= 0.25f;
		baseEffectSplash		= 0.25f;
		baseSplashRadius		= 0f;
		baseRange				= 8.5f;
		hitsAir					= false;
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
	WATER_WATER (new BaseAttributeList(){{
		//this tower does an aoe slow/freeze
		downgradeType 		= WATER;
		name     			= "Cold Snap";
		mainDamageType      = DamageType.WATER;
		baseWidth  			= 2;
		baseHeight   		= 2;
		baseDamageArray  	= new float[]{/*E*/0, /*F*/0, /*WA*/15, /*WI*/0, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
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
		mainDamageType      = DamageType.WATER;
		baseWidth			= 2;
		baseHeight			= 2;
		baseDamageArray		= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDuration	= 0;
		baseAttackCoolDown	= 12.3f;
		baseDamageSplash	= 0.25f;
		baseEffectSplash	= 0.25f;
		baseSplashRadius	= 0f;
		baseRange			= 8.5f;
		baseSlow			= 0f;//TODO remove baseSlow in favor of a base slow array
		hitsAir				= false;
		hitsGround			= true;
		upgrades			= new Upgrade[][]{
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
		name 				= "Sandstorm";
		downgradeType 		= WIND;
		mainDamageType      = DamageType.WIND;
		baseWidth			= 2;
		baseHeight			= 2;
		baseDamageArray		= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDuration	= 0;
		baseAttackCoolDown	= 12.3f;
		baseDamageSplash	= 0.25f;
		baseEffectSplash	= 0.25f;
		baseSplashRadius	= 0f;
		baseRange			= 8.5f;
		baseSlow			= 0f;//TODO remove baseSlow in favor of a base slow array
		hitsAir				= false;
		hitsGround			= true;
		upgrades			= new Upgrade[][]{
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
	WIND_FIRE (new BaseAttributeList(){{
		//this tower chains damage and removes shield (?)
		name 				= "Lightning";
		downgradeType 		= WIND;
		mainDamageType      = DamageType.WIND;
		baseWidth			= 2;
		baseHeight			= 2;
		baseDamageArray		= new float[]{/*E*/0, /*F*/10, /*WA*/0, /*WI*/25, /*L*/0, /*D*/0, /*P*/15};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
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
		name				= "Blizzard";
		downgradeType 		= WIND;
		mainDamageType      = DamageType.WIND;
		baseWidth			= 2;
		baseHeight			= 2;
		baseDamageArray		= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDuration	= 0;
		baseAttackCoolDown	= 12.3f;
		baseDamageSplash	= 0.25f;
		baseEffectSplash	= 0.25f;
		baseSplashRadius	= 0f;
		baseRange			= 8.5f;
		baseSlow			= 0f;//TODO remove baseSlow in favor of a base slow array
		hitsAir				= false;
		hitsGround			= true;
		upgrades			= new Upgrade[][]{
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
		name 				= "Gale";
		downgradeType 		= WIND;
		mainDamageType      = DamageType.WIND;
		baseWidth			= 2;
		baseHeight			= 2;
		baseDamageArray		= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDurationArray 	= new int[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowArray			= new float[]{/*E*/0, /*F*/0, /*WA*/0, /*WI*/0, /*L*/0, /*D*/0, /*P*/0};
		baseSlowDuration	= 0;
		baseAttackCoolDown	= 12.3f;
		baseDamageSplash	= 0.25f;
		baseEffectSplash	= 0.25f;
		baseSplashRadius	= 0f;
		baseRange			= 8.5f;
		baseSlow			= 0f;//TODO remove baseSlow in favor of a base slow array
		hitsAir				= false;
		hitsGround			= true;
		upgrades			= new Upgrade[][]{
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
