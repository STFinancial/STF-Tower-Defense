package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class FireEarthTower extends Tower {

	public FireEarthTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.FIRE_EARTH.getAttributeList());
		adjustTowerValues();
	}
}