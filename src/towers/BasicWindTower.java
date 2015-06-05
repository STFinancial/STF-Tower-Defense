package towers;

import creeps.DamageType;
import projectiles.Damage;
import projectiles.Projectile;
import projectiles.ProjectileEffect;
import projectiles.Slow;
import levels.Level;
import maps.Tile;

public class BasicWindTower extends Tower {

	public BasicWindTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerConstants.WIND_BASE_WIDTH, TowerConstants.WIND_BASE_HEIGHT);
		this.type = TowerType.WIND;
		this.attackCoolDown = TowerConstants.WIND_BASE_FIRE_RATE;
		this.range = TowerConstants.WIND_BASE_RANGE;
		this.physicalDamage = TowerConstants.WIND_BASE_PHYSICAL_DAMAGE;
		this.magicDamage = TowerConstants.WIND_BASE_MAGIC_DAMAGE;
		this.fireRate = TowerConstants.WIND_BASE_FIRE_RATE;
		this.damageSplash = TowerConstants.WIND_BASE_DAMAGE_SPLASH_COEFFICIENT;
		this.effectSplash = TowerConstants.WIND_BASE_EFFECT_SPLASH_COEFFICIENT;
		this.splashRadius = TowerConstants.WIND_BASE_SPLASH_RADIUS;
		this.hitsAir = TowerConstants.WIND_BASE_HITS_AIR;
		this.hitsGround = TowerConstants.WIND_BASE_HITS_GROUND;
		this.slow = TowerConstants.WIND_BASE_SLOW;
		this.name = TowerConstants.WIND_BASE_NAME;
		this.slowDuration = TowerConstants.WIND_BASE_SLOW_DURATION;
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
		
		effect = new Damage(magicDamage, DamageType.WIND);
		baseProjectile.addEffect(effect);
		
		if (slow != 0) {
			effect = new Slow(slowDuration, slow, DamageType.WIND); //OR SHOULD THIS BE THE TYPE THAT GIVES THE SLOW
			baseProjectile.addEffect(effect);
		}
		
		baseProjectile.currentSpeed = baseProjectile.speed = .20f;
	}
}
