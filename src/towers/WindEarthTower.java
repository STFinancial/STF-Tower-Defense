package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class WindEarthTower extends Tower {

	public WindEarthTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.WIND_EARTH.getAttributeList());
		adjustTowerValues();
	}
}