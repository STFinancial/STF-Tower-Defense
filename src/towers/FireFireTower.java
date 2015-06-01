package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class FireFireTower extends Tower {

	public FireFireTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.FIRE_FIRE.getAttributeList());
		adjustTowerValues();
	}
}