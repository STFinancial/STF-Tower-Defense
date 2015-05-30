package towers;

import creeps.ElementType;
import projectiles.Damage;
import projectiles.DamageType;
import projectiles.Projectile;
import projectiles.ProjectileEffect;
import projectiles.Slow;
import levels.Level;
import maps.Tile;

public class BasicFireTower extends Tower {

	public BasicFireTower(Level level, Tile topLeft) {
		super(level, topLeft, true, TowerConstants.FIRE_BASE_WIDTH, TowerConstants.FIRE_BASE_HEIGHT);
		this.type = TowerType.FIRE;
		this.attackCoolDown = TowerConstants.FIRE_BASE_FIRE_RATE;
		this.range = TowerConstants.FIRE_BASE_RANGE;
		this.physicalDamage = TowerConstants.FIRE_BASE_PHYSICAL_DAMAGE;
		this.magicDamage = TowerConstants.FIRE_BASE_MAGIC_DAMAGE;
		this.fireRate = TowerConstants.FIRE_BASE_FIRE_RATE;
		this.damageSplash = TowerConstants.FIRE_BASE_DAMAGE_SPLASH_COEFFICIENT;
		this.effectSplash = TowerConstants.FIRE_BASE_EFFECT_SPLASH_COEFFICIENT;
		this.splashRadius = TowerConstants.FIRE_BASE_SPLASH_RADIUS;
		this.hitsAir = TowerConstants.FIRE_BASE_HITS_AIR;
		this.hitsGround = TowerConstants.FIRE_BASE_HITS_GROUND;
		this.slow = TowerConstants.FIRE_BASE_SLOW;
		this.name = TowerConstants.FIRE_BASE_NAME;
		this.slowDuration = TowerConstants.FIRE_BASE_SLOW_DURATION;
	}

	@Override
	Projectile fireProjectile() {
		return duplicateProjectile(baseProjectile);
	}

	@Override
	public void roundInit() {
		// TODO Auto-generated method stub
		baseProjectile = new Projectile(this);
		
		ProjectileEffect effect = new Damage(physicalDamage, DamageType.PHYSICAL, ElementType.FIRE);
		effect.elementType = ElementType.FIRE;
		baseProjectile.addEffect(effect);
		
		effect = new Damage(magicDamage, DamageType.MAGIC, ElementType.FIRE);
		effect.elementType = ElementType.FIRE;
		baseProjectile.addEffect(effect);
		
		if (slow != 0) {
			effect = new Slow(slowDuration, slow, ElementType.FIRE);
			effect.elementType = ElementType.FIRE;//? OR SHOULD THIS BE THE TYPE THAT GIVES THE SLOW
			baseProjectile.addEffect(effect);
		}
		
		baseProjectile.currentSpeed = baseProjectile.speed = .20f;
	}

}
