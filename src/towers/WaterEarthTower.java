package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class WaterEarthTower extends Tower {

	public WaterEarthTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.WATER_EARTH.getAttributeList());
		adjustTowerValues();
	}
}