package towers;

import levels.Level;
import maps.Tile;
import projectiles.ProjectileBasic;

public class TowerWind extends Tower {

	public TowerWind(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
	}

}
