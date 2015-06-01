package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class FireWaterTower extends Tower {

	public FireWaterTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.FIRE_WATER.getAttributeList());
		adjustTowerValues();
	}
}