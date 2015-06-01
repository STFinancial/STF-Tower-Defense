package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class FireWaterTower extends Tower {

	public FireWaterTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerConstants.WIND_BASE_WIDTH, TowerConstants.WIND_BASE_HEIGHT);
		this.type = TowerType.WIND;
		this.baseAttackCoolDown = TowerConstants.WIND_BASE_FIRE_RATE;
		this.baseRange = TowerConstants.WIND_BASE_RANGE;
		this.damageArray[Constants.NUM_DAMAGE_TYPES] = TowerConstants.WIND_BASE_PHYSICAL_DAMAGE;
		this.damageArray[type.getDamageType().ordinal()] = TowerConstants.WIND_BASE_MAGIC_DAMAGE;
		this.baseFireRate = TowerConstants.WIND_BASE_FIRE_RATE;
		this.baseDamageSplash = TowerConstants.WIND_BASE_DAMAGE_SPLASH_COEFFICIENT;
		this.baseEffectSplash = TowerConstants.WIND_BASE_EFFECT_SPLASH_COEFFICIENT;
		this.baseSplashRadius = TowerConstants.WIND_BASE_SPLASH_RADIUS;
		this.hitsAir = TowerConstants.WIND_BASE_HITS_AIR;
		this.hitsGround = TowerConstants.WIND_BASE_HITS_GROUND;
		this.name = TowerConstants.WIND_BASE_NAME;
		this.slowArray[type.getDamageType().ordinal()] = TowerConstants.WIND_BASE_SLOW;
		this.slowDurationArray[type.getDamageType().ordinal()] = TowerConstants.WIND_BASE_SLOW_DURATION; 
		adjustTowerValues();
	}
}