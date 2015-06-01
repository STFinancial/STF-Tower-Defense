package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class BasicFireTower extends Tower {

	public BasicFireTower(Level level, Tile topLeft) {
		super(level, topLeft, true, TowerType.FIRE.getAttributeList());
		adjustTowerValues();
	}
}
