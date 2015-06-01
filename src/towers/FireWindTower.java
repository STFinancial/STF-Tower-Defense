package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class FireWindTower extends Tower {

	public FireWindTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.FIRE_WIND.getAttributeList());
		adjustTowerValues();
	}
}