package testing;

import towers.Tower;
import towers.TowerType;
import levels.Level;

public class SimpleTest {

	public static void main(String[] args) {

		Level level = new Level();
		Tower t = null;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (level.canBuild(TowerType.FIRE, j, i)) {
					t = level.buyTower(TowerType.FIRE, j, i);
					i = 10;
					j = 10;
				}
			}
		}

		if(t != null){
			System.out.println("Tower t: " + t);
		}
	}

}
