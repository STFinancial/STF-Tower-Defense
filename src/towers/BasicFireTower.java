package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class BasicFireTower extends Tower {

	public BasicFireTower(Level level, Tile topLeft) {
		super(level, topLeft, true, TowerConstants.FIRE_BASE_WIDTH, TowerConstants.FIRE_BASE_HEIGHT);
		this.type = TowerType.FIRE;
		this.baseAttackCoolDown = TowerConstants.FIRE_BASE_FIRE_RATE;
		this.baseRange = TowerConstants.FIRE_BASE_RANGE;
		this.damageArray[Constants.NUM_DAMAGE_TYPES] = TowerConstants.FIRE_BASE_PHYSICAL_DAMAGE;
		this.damageArray[type.getDamageType().ordinal()] = TowerConstants.FIRE_BASE_MAGIC_DAMAGE;
		this.baseFireRate = TowerConstants.FIRE_BASE_FIRE_RATE;
		this.baseDamageSplash = TowerConstants.FIRE_BASE_DAMAGE_SPLASH_COEFFICIENT;
		this.baseEffectSplash = TowerConstants.FIRE_BASE_EFFECT_SPLASH_COEFFICIENT;
		this.baseSplashRadius = TowerConstants.FIRE_BASE_SPLASH_RADIUS;
		this.hitsAir = TowerConstants.FIRE_BASE_HITS_AIR;
		this.hitsGround = TowerConstants.FIRE_BASE_HITS_GROUND;
		this.name = TowerConstants.FIRE_BASE_NAME;
		this.slowArray[type.getDamageType().ordinal()] = TowerConstants.FIRE_BASE_SLOW;
		this.slowDurationArray[type.getDamageType().ordinal()] = TowerConstants.FIRE_BASE_SLOW_DURATION; 
		adjustTowerValues();
	}
}
