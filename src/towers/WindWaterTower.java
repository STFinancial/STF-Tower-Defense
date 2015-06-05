package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class WindWaterTower extends Tower {

	public WindWaterTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.WIND_WATER.getAttributeList());
		adjustTowerValues();
	}
}