package towers;

import creeps.ElementType;
import projectiles.Damage;
import projectiles.Projectile;
import projectiles.ProjectileEffect;
import projectiles.Slow;
import levels.Level;
import maps.Tile;

public class BasicFireTower extends Tower {

	public BasicFireTower(Level level, Tile topLeft) {
		super(level, topLeft, TowerConstants.FIRE_BASE_WIDTH, TowerConstants.FIRE_BASE_HEIGHT,
				true, TowerConstants.FIRE_BASE_RANGE, TowerConstants.FIRE_BASE_PHYSICAL_DAMAGE, TowerConstants.FIRE_BASE_MAGIC_DAMAGE,
				TowerConstants.FIRE_BASE_FIRE_RATE, TowerConstants.FIRE_BASE_DAMAGE_SPLASH_COEFFICIENT, TowerConstants.FIRE_BASE_EFFECT_SPLASH_COEFFICIENT,
				TowerConstants.FIRE_BASE_SPLASH_RADIUS, TowerConstants.FIRE_BASE_HITS_AIR, TowerConstants.FIRE_BASE_HITS_GROUND, TowerConstants.FIRE_BASE_SLOW, 
				TowerConstants.FIRE_BASE_SLOW_DURATION, TowerConstants.FIRE_BASE_NAME);
		this.type = TowerType.FIRE;
	}

	@Override
	Projectile fireProjectile() {
		return duplicateProjectile(baseProjectile);
	}

	@Override
	public void roundInit() {
		// TODO Auto-generated method stub
		baseProjectile = new Projectile(this);
		
		ProjectileEffect effect = new Damage(physicalDamage, true);
		effect.elementType = ElementType.FIRE;
		baseProjectile.addEffect(effect);
		
		effect = new Damage(magicDamage, false);
		effect.elementType = ElementType.FIRE;
		baseProjectile.addEffect(effect);
		
		if (slow != 0) {
			effect = new Slow(slowDuration, slow);
			effect.elementType = ElementType.FIRE;//? OR SHOULD THIS BE THE TYPE THAT GIVES THE SLOW
			baseProjectile.addEffect(effect);
		}
		
		baseProjectile.currentSpeed = baseProjectile.speed = .20f;
	}

}
