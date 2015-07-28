package towers;

import projectiles.ProjectileAOE;
import levels.Level;
import maps.Tile;

public class TowerEarthEarth extends Tower {

	public TowerEarthEarth(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this);
	}

}
