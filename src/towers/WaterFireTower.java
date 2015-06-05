package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class WaterFireTower extends Tower {

	public WaterFireTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.WATER_FIRE.getAttributeList());
		adjustTowerValues();
	}
}