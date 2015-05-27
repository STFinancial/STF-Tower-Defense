package towers;

import creeps.ElementType;
import projectiles.Damage;
import projectiles.Projectile;
import projectiles.ProjectileEffect;
import projectiles.Slow;
import levels.Level;
import maps.Tile;

public class BasicEarthTower extends Tower {
	//TODO this is a total clusterfuck
	public BasicEarthTower(Level level, Tile topLeft) {
		super(level, topLeft, TowerConstants.EARTH_BASE_WIDTH, TowerConstants.EARTH_BASE_HEIGHT,
				true, TowerConstants.EARTH_BASE_RANGE, TowerConstants.EARTH_BASE_PHYSICAL_DAMAGE, TowerConstants.EARTH_BASE_MAGIC_DAMAGE,
				TowerConstants.EARTH_BASE_FIRE_RATE, TowerConstants.EARTH_BASE_DAMAGE_SPLASH_COEFFICIENT, TowerConstants.EARTH_BASE_EFFECT_SPLASH_COEFFICIENT,
				TowerConstants.EARTH_BASE_SPLASH_RADIUS, TowerConstants.EARTH_BASE_HITS_AIR, TowerConstants.EARTH_BASE_HITS_GROUND, TowerConstants.EARTH_BASE_SLOW, 
				TowerConstants.EARTH_BASE_SLOW_DURATION, TowerConstants.EARTH_BASE_NAME);
		this.type = TowerType.EARTH;
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
		effect.elementType = ElementType.EARTH;
		baseProjectile.addEffect(effect);
		
		effect = new Damage(magicDamage, false);
		effect.elementType = ElementType.EARTH;
		baseProjectile.addEffect(effect);
		
		if (slow != 0) {
			effect = new Slow(slowDuration, slow);
			effect.elementType = ElementType.EARTH; //OR SHOULD THIS BE THE TYPE THAT GIVES THE SLOW
			baseProjectile.addEffect(effect);
		}
		
		baseProjectile.currentSpeed = baseProjectile.speed = .20f;
	}

}
