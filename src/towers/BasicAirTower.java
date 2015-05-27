package towers;

import creeps.ElementType;
import projectiles.Damage;
import projectiles.Projectile;
import projectiles.ProjectileEffect;
import projectiles.Slow;
import levels.Level;
import maps.Tile;

public class BasicAirTower extends Tower {

	public BasicAirTower(Level level, Tile topLeft){
		super(level, topLeft, TowerConstants.AIR_BASE_WIDTH, TowerConstants.AIR_BASE_HEIGHT,
				true, TowerConstants.AIR_BASE_RANGE, TowerConstants.AIR_BASE_PHYSICAL_DAMAGE, TowerConstants.AIR_BASE_MAGIC_DAMAGE,
				TowerConstants.AIR_BASE_FIRE_RATE, TowerConstants.AIR_BASE_DAMAGE_SPLASH_COEFFICIENT, TowerConstants.AIR_BASE_EFFECT_SPLASH_COEFFICIENT,
				TowerConstants.AIR_BASE_SPLASH_RADIUS, TowerConstants.AIR_BASE_HITS_AIR, TowerConstants.AIR_BASE_HITS_GROUND, TowerConstants.AIR_BASE_SLOW, 
				TowerConstants.AIR_BASE_SLOW_DURATION, TowerConstants.AIR_BASE_NAME);
		this.type = TowerType.AIR;
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
		effect.elementType = ElementType.WIND;
		baseProjectile.addEffect(effect);
		
		effect = new Damage(magicDamage, false);
		effect.elementType = ElementType.WIND;
		baseProjectile.addEffect(effect);
		
		if (slow != 0) {
			effect = new Slow(slowDuration, slow);
			effect.elementType = ElementType.WIND; //OR SHOULD THIS BE THE TYPE THAT GIVES THE SLOW
			baseProjectile.addEffect(effect);
		}
		
		baseProjectile.currentSpeed = baseProjectile.speed = .20f;
	}
}
