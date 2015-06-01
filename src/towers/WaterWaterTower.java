package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class WaterWaterTower extends Tower {

	public WaterWaterTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.WATER_WATER.getAttributeList());
		adjustTowerValues();
	}
}