package towers;

import utilities.Constants;
import levels.Level;
import maps.Tile;

public class WindWindTower extends Tower {

	public WindWindTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.WIND_WIND.getAttributeList());
		adjustTowerValues();
	}
}