package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class EarthWaterTower extends Tower {

	public EarthWaterTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.EARTH_WATER.getAttributeList());
		adjustTowerValues();
	}
}