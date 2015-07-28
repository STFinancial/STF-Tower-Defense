package towers;

import levels.Level;
import maps.Tile;

public class TowerWindFire extends Tower {

	public TowerWindFire(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		// TODO Auto-generated method stub

	}

}
