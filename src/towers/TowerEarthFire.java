package towers;

import levels.Level;
import maps.Tile;

public class TowerEarthFire extends Tower {

	public TowerEarthFire(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		// TODO Auto-generated method stub

	}

}
