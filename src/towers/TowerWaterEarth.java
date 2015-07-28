package towers;

import levels.Level;
import maps.Tile;

public class TowerWaterEarth extends Tower {

	public TowerWaterEarth(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		// TODO Auto-generated method stub

	}

}
