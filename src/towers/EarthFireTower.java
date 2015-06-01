package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class EarthFireTower extends Tower {

	public EarthFireTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.EARTH_FIRE.getAttributeList());
		adjustTowerValues();
	}
}