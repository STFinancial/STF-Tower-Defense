package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class BasicWaterTower extends Tower {

	public BasicWaterTower(Level level, Tile topLeft) {
		super(level, topLeft, true, TowerConstants.WATER_BASE_WIDTH, TowerConstants.WATER_BASE_HEIGHT);
		this.type = TowerType.WATER;
		this.baseAttackCoolDown = TowerConstants.WATER_BASE_FIRE_RATE;
		this.baseRange = TowerConstants.WATER_BASE_RANGE;
		this.damageArray[Constants.NUM_DAMAGE_TYPES] = TowerConstants.WATER_BASE_PHYSICAL_DAMAGE;
		this.damageArray[type.getDamageType().ordinal()] = TowerConstants.WATER_BASE_MAGIC_DAMAGE;
		this.baseFireRate = TowerConstants.WATER_BASE_FIRE_RATE;
		this.baseDamageSplash = TowerConstants.WATER_BASE_DAMAGE_SPLASH_COEFFICIENT;
		this.baseEffectSplash = TowerConstants.WATER_BASE_EFFECT_SPLASH_COEFFICIENT;
		this.baseSplashRadius = TowerConstants.WATER_BASE_SPLASH_RADIUS;
		this.hitsAir = TowerConstants.WATER_BASE_HITS_AIR;
		this.hitsGround = TowerConstants.WATER_BASE_HITS_GROUND;
		this.name = TowerConstants.WATER_BASE_NAME;
		this.slowArray[type.getDamageType().ordinal()] = TowerConstants.WATER_BASE_SLOW;
		this.slowDurationArray[type.getDamageType().ordinal()] = TowerConstants.WATER_BASE_SLOW_DURATION; 
		adjustTowerValues();
	}
}
