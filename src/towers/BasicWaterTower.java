package towers;

import creeps.DamageType;
import projectiles.Damage;
import projectiles.Projectile;
import projectiles.ProjectileEffect;
import projectiles.Slow;
import levels.Level;
import maps.Tile;

public class BasicWaterTower extends Tower {

	public BasicWaterTower(Level level, Tile topLeft) {
		super(level, topLeft, true, TowerConstants.WATER_BASE_WIDTH, TowerConstants.WATER_BASE_HEIGHT);
		this.type = TowerType.WATER;
		this.attackCoolDown = TowerConstants.WATER_BASE_FIRE_RATE;
		this.range = TowerConstants.WATER_BASE_RANGE;
		this.physicalDamage = TowerConstants.WATER_BASE_PHYSICAL_DAMAGE;
		this.magicDamage = TowerConstants.WATER_BASE_MAGIC_DAMAGE;
		this.fireRate = TowerConstants.WATER_BASE_FIRE_RATE;
		this.damageSplash = TowerConstants.WATER_BASE_DAMAGE_SPLASH_COEFFICIENT;
		this.effectSplash = TowerConstants.WATER_BASE_EFFECT_SPLASH_COEFFICIENT;
		this.splashRadius = TowerConstants.WATER_BASE_SPLASH_RADIUS;
		this.hitsAir = TowerConstants.WATER_BASE_HITS_AIR;
		this.hitsGround = TowerConstants.WATER_BASE_HITS_GROUND;
		this.slow = TowerConstants.WATER_BASE_SLOW;
		this.name = TowerConstants.WATER_BASE_NAME;
		this.slowDuration = TowerConstants.WATER_BASE_SLOW_DURATION;
	}

	@Override
	Projectile fireProjectile() {
		return duplicateProjectile(baseProjectile);
	}

	@Override
	public void roundInit() {
		// TODO Auto-generated method stub
		baseProjectile = new Projectile(this);
		
		ProjectileEffect effect = new Damage(physicalDamage, DamageType.PHYSICAL);
		baseProjectile.addEffect(effect);
		
		effect = new Damage(magicDamage, DamageType.WATER);
		baseProjectile.addEffect(effect);
		
		if (slow != 0) {
			effect = new Slow(slowDuration, slow, DamageType.WATER);
			baseProjectile.addEffect(effect);
		}
		
		baseProjectile.currentSpeed = baseProjectile.speed = .20f;
	}

}
