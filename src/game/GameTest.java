package game;

import static org.junit.Assert.assertNotNull;

import levels.LevelManager;
import towers.TowerManager;
import towers.TowerType;

public class GameTest {
	public static void main(String[] args) {
		GameTest gt = new GameTest();
		gt.test();
	}

	private void test() {
		Game game = new Game();
		assertNotNull(game);
		LevelManager lManager = LevelManager.getInstance();
		TowerManager towerManager = TowerManager.getInstance();
		
		game.startRound();
		GameEvent event;
		
		boolean towerSpace = true;
		while (towerSpace) {
			towerSpace = false;
			towerSearch: {
				for (int x = 0; x < lManager.getMapWidth(); x++) {
					for (int y = 0; y < lManager.getMapHeight(); y++) {
						if (lManager.canBuild(TowerType.EARTH, x, y)) {
							towerSpace = true;
							towerManager.buyTower(lManager.getTile(x, y), TowerType.EARTH);
							break towerSearch;
						}
					}
				}
			}
		}
		
		round: {
			while (true) {
				game.gameTick();
				//System.out.println(game.toString());
				while ((event = game.getEvent()).getType() != GameEventType.NULL) {
					switch (event.getType()) {
					case CREEP_ESCAPED:
						System.out.println("Creep escaped");
						break;
					case PROJECTILE_FIRED:
						//System.out.println("Tower Fired: " + event.getObject().toString());
						break;
					case ROUND_FINISHED:
						System.out.println("Round complete!");
						break round;
					case CREEP_KILLED:
						System.out.println("Killed a creep");
						break;
					case TOWER_CREATED:
						System.out.println(event.getObject().toString());
						break;
					case PROJECTILE_DETONATED:
						//System.out.println("Projectile Detonated");
						break;
					default:
						break;
					}
				}
			}
		}
		assertNotNull(event);
	}
}
