package towers;

import levels.Level;
import maps.Tile;

public class TowerFactory {
	public static Tower generateTower(Level level, Tile topLeft, TowerType type, int towerID) {
		Tower t = null;
		switch (type) {
		case WIND:
			t = new TowerWind(level, topLeft, towerID);
			break;
		case EARTH:
			t = new TowerEarth(level, topLeft, towerID);
			break;
		case FIRE:
			t = new TowerFire(level, topLeft, towerID);
			break;
		case WATER:
			t = new TowerWater(level, topLeft, towerID);
			break;
		case WIND_WIND:
			t = new TowerWindWind(level, topLeft, towerID);
			break;
		case WIND_EARTH:
			t = new TowerWindEarth(level, topLeft, towerID);
			break;
		case WIND_FIRE:
			t = new TowerWindFire(level, topLeft, towerID);
			break;
		case WIND_WATER:
			t = new TowerWindWater(level, topLeft, towerID);
			break;
		case EARTH_WIND:
			t = new TowerEarthWind(level, topLeft, towerID);
			break;
		case EARTH_EARTH:
			t = new TowerEarthEarth(level, topLeft, towerID);
			break;
		case EARTH_FIRE:
			t = new TowerEarthFire(level, topLeft, towerID);
			break;
		case EARTH_WATER:
			t = new TowerEarthWater(level, topLeft, towerID);
			break;
		case FIRE_WIND:
			t = new TowerFireWind(level, topLeft, towerID);
			break;
		case FIRE_EARTH:
			t = new TowerFireEarth(level, topLeft, towerID);
			break;
		case FIRE_FIRE:
			t = new TowerFireFire(level, topLeft, towerID);
			break;
		case FIRE_WATER:
			t = new TowerFireWater(level, topLeft, towerID);
			break;
		case WATER_WIND:
			t = new TowerWaterWind(level, topLeft, towerID);
			break;
		case WATER_EARTH:
			t = new TowerWaterEarth(level, topLeft, towerID);
			break;
		case WATER_FIRE:
			t = new TowerWaterFire(level, topLeft, towerID);
			break;
		case WATER_WATER:
			t = new TowerWaterWater(level, topLeft, towerID);
			break;
		default:
			break;
		}
		return t;
	}
}
