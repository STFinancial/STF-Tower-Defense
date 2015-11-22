package levels;

import creeps.Creep;
import game.Game;
import game.GameEventType;
import players.Player;
import towers.Tower;
import utilities.Circle;

public class LevelManager {
	private static final LevelManager INSTANCE = new LevelManager();
	private Level level;
	private Map map;
	private Game game;
	
	private LevelManager() { }
	
	public static LevelManager getInstance() { return INSTANCE; }
	
	public void initialize(Game game, Player player, Map map) {
		level = new Level(player, map);
		this.game = game;
	}
	
	public void addEffectPatch(EffectPatch effectPatch) {
		level.addEffectPatch(effectPatch);
	}
	
	public void addGold(float amount) {
		level.addGol);
	}
	
	public void addTower(Tower t, Tile topLeft, int width, int height) {
		int x = topLeft.x;
		int y = topLeft.y;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map.getTile(y + j, x + i).addTower(t);
			}
		}
	}
	
	public Circle getCenter(Tile topLeft, int width, int height) {
		return new Circle((topLeft.x + width) / 2f, (topLeft.y + height) / 2f, 0);
	}
	
	public Path getPath(boolean isFlying) {
		return level.getPath(isFlying);
	}
	
	public Tile getTile(int x, int y) {
		return map.getTile(y, x);
	}
	
	//TODO: Not happy about how this works, I really don't like it returning int
	public int getVertexBelow(Vertex vertex) {
		return level.getVertexBelow(vertex);
	}
	
	public boolean isOutside(float x, float y) {
		return map.isOutside(x, y);
	}
	
	
	public void reduceHealth(float amount) {
		level.reduceHealth(amount); //TODO: Creep manager accesses directly, why not here?
		if (level.getHealth() <= 0) {
			game.newEvent(GameEventType.HEALTH_ZERO, level);
		}
	}
	
	public void removeTower(Tile topLeft, int width, int height) {
		int x = topLeft.x;
		int y = topLeft.y;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map.getTile(y + j, x + i).removeTower();
			}
		}
	}
	
	
}
