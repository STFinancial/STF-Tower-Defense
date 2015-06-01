package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class EarthEarthTower extends Tower {

	public EarthEarthTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.EARTH_EARTH.getAttributeList());
		adjustTowerValues();
	}
}
