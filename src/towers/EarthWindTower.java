package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class EarthWindTower extends Tower {

	public EarthWindTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.EARTH_WIND.getAttributeList());
		adjustTowerValues();
	}
}