package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class BasicEarthTower extends Tower {
	public BasicEarthTower(Level level, Tile topLeft) {
		super(level, topLeft, true, TowerConstants.EARTH_BASE_WIDTH, TowerConstants.EARTH_BASE_HEIGHT);
		this.type = TowerType.EARTH;
		this.baseAttackCoolDown = TowerConstants.EARTH_BASE_FIRE_RATE;
		this.baseRange = TowerConstants.EARTH_BASE_RANGE;
		this.damageArray[Constants.NUM_DAMAGE_TYPES] = TowerConstants.EARTH_BASE_PHYSICAL_DAMAGE;
		this.damageArray[type.getDamageType().ordinal()] = TowerConstants.EARTH_BASE_MAGIC_DAMAGE;
		this.baseFireRate = TowerConstants.EARTH_BASE_FIRE_RATE;
		this.baseDamageSplash = TowerConstants.EARTH_BASE_DAMAGE_SPLASH_COEFFICIENT;
		this.baseEffectSplash = TowerConstants.EARTH_BASE_EFFECT_SPLASH_COEFFICIENT;
		this.baseSplashRadius = TowerConstants.EARTH_BASE_SPLASH_RADIUS;
		this.hitsAir = TowerConstants.EARTH_BASE_HITS_AIR;
		this.hitsGround = TowerConstants.EARTH_BASE_HITS_GROUND;
		this.name = TowerConstants.EARTH_BASE_NAME;
		this.slowArray[type.getDamageType().ordinal()] = TowerConstants.EARTH_BASE_SLOW;
		this.slowDurationArray[type.getDamageType().ordinal()] = TowerConstants.EARTH_BASE_SLOW_DURATION; 
		this.adjustTowerValues();
	}
}
