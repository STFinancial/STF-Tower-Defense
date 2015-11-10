package levelstest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import levels.Level;
import maps.Map;
import maps.Tile;
import projectiles.ProjectileGuider;
import towers.Tower;
import towers.TowerManager;
import towers.TowerType;

public class LevelTest {
	static Level l;
	static Map m;
	static TowerManager t;
	static ProjectileGuider p;
	static ArrayList<Tower> towers;
	static HashMap<Tile, Tower> towerPositions;
	
	@BeforeClass
	public static void initialize() {
		l = new Level();
		p = ProjectileGuider.getInstance();
		t = TowerManager.getInstance();
		m = l.getMap();
		towers = new ArrayList<Tower>();
		towerPositions = new HashMap<Tile, Tower>();
	}
	
	@Test
	public void testBuyEarthTower() {
		Tower tow = null;
		int towX = 0;
		int towY = 0;
		search: {
			for (int x = 0; x < m.getWidth(); x++) {
				for (int y = 0; y < m.getHeight(); y++) {
					if (l.canBuild(TowerType.EARTH, y, x)) {
						towX = x;
						towY = y;
						tow = l.buyTower(TowerType.EARTH, y, x);
						break search;
					}
				}
			}
		}
		assertNotNull(tow);
		towerPositions.put(m.getTile(towY, towX), tow);
		towers.add(tow);
		assertNotNull(t.getTower(towX, towY));
	}
	
	@Test
	public void testBuyFireTower() {
		Tower tow = null;
		int towX = 0;
		int towY = 0;
		search: {
			for (int x = 0; x < m.getWidth(); x++) {
				for (int y = 0; y < m.getHeight(); y++) {
					if (l.canBuild(TowerType.FIRE, y, x)) {
						towX = x;
						towY = y;
						tow = l.buyTower(TowerType.FIRE, y, x);
						break search;
					}
				}
			}
		}
		assertNotNull(tow);
		towerPositions.put(m.getTile(towY, towX), tow);
		towers.add(tow);
		assertNotNull(t.getTower(towX, towY));
	}
	
	@Test
	public void testKillCreeps() {
		search: {
		
		}
	}
}
