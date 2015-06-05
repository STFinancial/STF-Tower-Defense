package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class WindFireTower extends Tower {

	public WindFireTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.WIND_FIRE.getAttributeList());
		adjustTowerValues();
	}
}