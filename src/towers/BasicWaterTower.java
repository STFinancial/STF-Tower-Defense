package towers;

import creeps.ElementType;
import projectiles.Damage;
import projectiles.Projectile;
import projectiles.ProjectileEffect;
import projectiles.Slow;
import levels.Level;
import maps.Tile;

public class BasicWaterTower extends Tower {

	public BasicWaterTower(Level level, Tile topLeft) {
		super(level, topLeft, TowerConstants.WATER_BASE_WIDTH, TowerConstants.WATER_BASE_HEIGHT,
				true, TowerConstants.WATER_BASE_RANGE, TowerConstants.WATER_BASE_PHYSICAL_DAMAGE, TowerConstants.WATER_BASE_MAGIC_DAMAGE,
				TowerConstants.WATER_BASE_FIRE_RATE, TowerConstants.WATER_BASE_DAMAGE_SPLASH_COEFFICIENT, TowerConstants.WATER_BASE_EFFECT_SPLASH_COEFFICIENT,
				TowerConstants.WATER_BASE_SPLASH_RADIUS, TowerConstants.WATER_BASE_HITS_AIR, TowerConstants.WATER_BASE_HITS_GROUND, TowerConstants.WATER_BASE_SLOW, 
				TowerConstants.WATER_BASE_SLOW_DURATION, TowerConstants.WATER_BASE_NAME);
		this.type = TowerType.WATER;
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
		effect.elementType = ElementType.WATER;
		baseProjectile.addEffect(effect);
		
		effect = new Damage(magicDamage, false);
		effect.elementType = ElementType.WATER;
		baseProjectile.addEffect(effect);
		
		if (slow != 0) {
			effect = new Slow(slowDuration, slow);
			effect.elementType = ElementType.WATER;//?
			baseProjectile.addEffect(effect);
		}
		
		baseProjectile.currentSpeed = baseProjectile.speed = .20f;
	}

}
