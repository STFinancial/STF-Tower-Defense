package towers;

import levels.Level;
import maps.Tile;

public class BasicWaterTower extends Tower {

	public BasicWaterTower(Level level, Tile topLeft) {
		super(level, topLeft, true, TowerType.WATER.getAttributeList());
		adjustTowerValues();
	}
}
