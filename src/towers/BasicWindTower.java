package towers;

import levels.Level;
import maps.Tile;

public class BasicWindTower extends Tower {
	
	public BasicWindTower(Level level, Tile topLeft){
		super(level, topLeft, true, TowerType.WIND.getAttributeList());
		adjustTowerValues();
	}
}
