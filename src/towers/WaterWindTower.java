package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class WaterWindTower extends Tower {

	public WaterWindTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.WATER_WIND.getAttributeList());
		adjustTowerValues();
	}
}