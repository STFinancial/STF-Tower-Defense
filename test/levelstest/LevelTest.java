package levelstest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import game.Game;
import game.GameEvent;
import game.GameEventType;
import levels.Level;
import levels.LevelManager;
import levels.Map;
import levels.Tile;
import projectiles.ProjectileManager;
import towers.Tower;
import towers.TowerManager;
import towers.TowerType;

public class LevelTest {
	
	@Test
	public void testLevel() {
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
				while ((event = game.getEvent()).getType() != GameEventType.NULL) {
					switch (event.getType()) {
					case ROUND_FINISHED:
						break round;
					case CREEP_KILLED:
						System.out.println("Killed a creep");
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
