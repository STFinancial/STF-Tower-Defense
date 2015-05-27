package towers;

import projectiles.Projectile;
import levels.Level;
import maps.Tile;

public class FireFireTower extends Tower {

	public FireFireTower(Level level, Tile topLeft){
		super(level, topLeft, TowerConstants.AIR_BASE_WIDTH, TowerConstants.AIR_BASE_HEIGHT,
				true, TowerConstants.AIR_BASE_RANGE, TowerConstants.AIR_BASE_PHYSICAL_DAMAGE, TowerConstants.AIR_BASE_MAGIC_DAMAGE,
				TowerConstants.AIR_BASE_FIRE_RATE, TowerConstants.AIR_BASE_DAMAGE_SPLASH_COEFFICIENT, TowerConstants.AIR_BASE_EFFECT_SPLASH_COEFFICIENT,
				TowerConstants.AIR_BASE_SPLASH_RADIUS, TowerConstants.AIR_BASE_HITS_AIR, TowerConstants.AIR_BASE_HITS_GROUND, TowerConstants.AIR_BASE_SLOW, 
				TowerConstants.AIR_BASE_SLOW_DURATION, TowerConstants.AIR_BASE_NAME);
		this.type = TowerType.AIR;
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
