package towers;

import projectiles.ProjectileBasic;
import levels.Level;
import maps.Tile;

public class TowerEarth extends Tower {

	public TowerEarth(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
	}

}
