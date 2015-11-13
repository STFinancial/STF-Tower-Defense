package levels;

import game.Game;
import game.GameEventType;
import players.Player;

public class LevelManager {
	private static final LevelManager INSTANCE = new LevelManager();
	private Level level;
	private Game game;
	
	private LevelManager() { }
	
	public static LevelManager getInstance() { return INSTANCE; }
	
	public void initialize(Game game, Player player, Map map) {
		level = new Level(player, map);
		this.game = game;
	}
	
	public void addGold(float amount) {
		level.addGol);
	}
	
	public Path getPath(boolean isFlying) {
		return level.getPath(isFlying);
	}
	
	//TODO: Not happy about how this works, I really don't like it returning int
	public int getVertexBelow(Vertex vertex) {
		return level.getVertexBelow(vertex);
	}
	
	public void reduceHealth(float amount) {
		level.reduceHealth(amount); //TODO: Creep manager accesses directly, why not here?
		if (level.getHealth() <= 0) {
			game.newEvent(GameEventType.HEALTH_ZERO, level);
		}
	}
}
