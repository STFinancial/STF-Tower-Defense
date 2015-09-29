package towers;

import levels.Level;
import maps.Tile;

public class TowerWindEarth extends Tower {

	public TowerWindEarth(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		// TODO Auto-generated method stub

	}

	@Override
	public int update() {
		// TODO Auto-generated method stub
		return 0;
	}

}
