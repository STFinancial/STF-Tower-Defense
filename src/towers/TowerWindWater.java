package towers;

import levels.Level;
import maps.Tile;

public class TowerWindWater extends Tower {

	public TowerWindWater(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WIND_WATER, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		// TODO Auto-generated method stub

	}

}
