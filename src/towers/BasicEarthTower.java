package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class BasicEarthTower extends Tower {
	public BasicEarthTower(Level level, Tile topLeft) {
		super(level, topLeft, true, TowerType.EARTH.getAttributeList());
		adjustTowerValues();
	}
}
