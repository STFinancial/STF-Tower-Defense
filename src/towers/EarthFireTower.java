package towers;

import projectiles.Projectile;
import levels.Level;
import maps.Tile;

public class EarthFireTower extends Tower {

	public EarthFireTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerConstants.EARTH_BASE_WIDTH, TowerConstants.EARTH_BASE_HEIGHT);
		this.type = TowerType.EARTH;
		this.attackCoolDown = TowerConstants.EARTH_BASE_FIRE_RATE;
		this.range = TowerConstants.EARTH_BASE_RANGE;
		this.physicalDamage = TowerConstants.EARTH_BASE_PHYSICAL_DAMAGE;
		this.magicDamage = TowerConstants.EARTH_BASE_MAGIC_DAMAGE;
		this.fireRate = TowerConstants.EARTH_BASE_FIRE_RATE;
		this.damageSplash = TowerConstants.EARTH_BASE_DAMAGE_SPLASH_COEFFICIENT;
		this.effectSplash = TowerConstants.EARTH_BASE_EFFECT_SPLASH_COEFFICIENT;
		this.splashRadius = TowerConstants.EARTH_BASE_SPLASH_RADIUS;
		this.hitsAir = TowerConstants.EARTH_BASE_HITS_AIR;
		this.hitsGround = TowerConstants.EARTH_BASE_HITS_GROUND;
		this.slow = TowerConstants.EARTH_BASE_SLOW;
		this.name = TowerConstants.EARTH_BASE_NAME;
		this.slowDuration = TowerConstants.EARTH_BASE_SLOW_DURATION;
	}

	@Override
	Projectile fireProjectile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void roundInit() {
		// TODO Auto-generated method stub
		
	}

}
