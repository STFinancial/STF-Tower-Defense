package towers;

import levels.Tile;

final class TowerFactory {
	public static Tower generateTower(Tile topLeft, TowerType type, int towerID) {
		Tower t = null;
		switch (type) {
		case WIND:
			t = new TowerWind(topLeft, towerID);
			break;
		case EARTH:
			t = new TowerEarth(topLeft, towerID);
			break;
		case FIRE:
			t = new TowerFire(topLeft, towerID);
			break;
		case WATER:
			t = new TowerWater(topLeft, towerID);
			break;
		case WIND_WIND:
			t = new TowerWindWind(topLeft, towerID);
			break;
		case WIND_EARTH:
			t = new TowerWindEarth(topLeft, towerID);
			break;
		case WIND_FIRE:
			t = new TowerWindFire(topLeft, towerID);
			break;
		case WIND_WATER:
			t = new TowerWindWater(topLeft, towerID);
			break;
		case EARTH_WIND:
			t = new TowerEarthWind(topLeft, towerID);
			break;
		case EARTH_EARTH:
			t = new TowerEarthEarth(topLeft, towerID);
			break;
		case EARTH_FIRE:
			t = new TowerEarthFire(topLeft, towerID);
			break;
		case EARTH_WATER:
			t = new TowerEarthWater(topLeft, towerID);
			break;
		case FIRE_WIND:
			t = new TowerFireWind(topLeft, towerID);
			break;
		case FIRE_EARTH:
			t = new TowerFireEarth(topLeft, towerID);
			break;
		case FIRE_FIRE:
			t = new TowerFireFire(topLeft, towerID);
			break;
		case FIRE_WATER:
			t = new TowerFireWater(topLeft, towerID);
			break;
		case WATER_WIND:
			t = new TowerWaterWind(topLeft, towerID);
			break;
		case WATER_EARTH:
			t = new TowerWaterEarth(topLeft, towerID);
			break;
		case WATER_FIRE:
			t = new TowerWaterFire(topLeft, towerID);
			break;
		case WATER_WATER:
			t = new TowerWaterWater(topLeft, towerID);
			break;
		default:
			break;
		}
		return t;
	}
}
