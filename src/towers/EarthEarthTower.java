package towers;

import projectiles.Projectile;
import levels.Level;
import maps.Tile;

public class EarthEarthTower extends Tower {

	public EarthEarthTower(Level level, Tile topLeftTile, int width,
			int height, int attackCoolDown, boolean targetsCreep, float range) {
		super(level, topLeftTile, width, height, attackCoolDown, targetsCreep, range);
		// TODO Auto-generated constructor stub
	}

	@Override
	Projectile fireProjectile() {
		// TODO Auto-generated method stub
		return null;
	}

}
