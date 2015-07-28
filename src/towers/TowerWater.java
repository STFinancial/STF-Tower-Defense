package towers;

import levels.Level;
import maps.Tile;
import projectiles.ProjectileBasic;

public class TowerWater extends Tower {

	public TowerWater(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
	}

}
