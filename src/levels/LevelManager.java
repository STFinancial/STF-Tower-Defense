package levels;

import java.util.HashSet;

import creeps.Creep;
import game.Game;
import game.GameEventType;
import players.Player;
import towers.Tower;
import towers.TowerManager;
import towers.TowerType;
import utilities.Circle;
import utilities.GameConstants.UpgradePathType;

public class LevelManager {
	private static final LevelManager INSTANCE = new LevelManager();
	private Level level;
	private Map map;
	private Game game;
	
	private LevelManager() { }
	
	public static LevelManager getInstance() { return INSTANCE; }
	
	public void initialize(Game game, Player player, Map map) {
		level = new Level(player, map);
		this.map = map;
		this.game = game;
	}
	
	public void startRound(int roundNum) {
		level.startRound(roundNum);
	}
	
	public void updateEffectPatches() {
		level.updateEffectPatches();
	}
	
	public void updateCreepAdjacentToEarth() {
		level.updateCreepAdjacentToEarth();
	}
	
	public void addEffectPatch(EffectPatch effectPatch) {
		level.addEffectPatch(effectPatch);
	}
	
	public void addGold(float amount) {
		level.addGold(amount);
	}
	
	public void removeGold(float amount) {
		level.removeGold(amount);
	}
	//TODO combine for "Buy Tower" or is that somewhere else?
	public void addTower(Tower t, Tile topLeft, int width, int height) {
		int x = topLeft.x;
		int y = topLeft.y;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map.getTile(y + j, x + i).addTower(t);
			}
		}
		level.updatePath();
	}
	
	public void removeTower(Tile topLeft, int width, int height) {
		int x = topLeft.x;
		int y = topLeft.y;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map.getTile(y + j, x + i).removeTower();
			}
		}
		level.updatePath();
	}
	
	public Circle getCenter(Tile topLeft, int width, int height) {
		return new Circle((topLeft.x + width) / 2f, (topLeft.y + height) / 2f, 0);
	}

	public Path getPath(boolean isFlying) {
		return level.getPath(isFlying);
	}
	
	public Path getProposedPath(boolean isFlying) {
		return level.getProposedPath(isFlying);
	}
	
	public Tile getTile(int x, int y) {
		return map.getTile(y, x);
	}
	
	public int getMapWidth() {
		return map.getWidth();
	}
	
	public int getMapHeight() {
		return map.getHeight();
	}
	
	public float getHealth() {
		return level.getHealth();
	}
	
	public float getGold() {
		return level.getGold();
	}
	
	//TODO: Not happy about how this works, I really don't like it returning int
	public int getVertexBelow(Vertex vertex) {
		return level.getVertexBelow(vertex);
	}
	
	public boolean isOutside(float x, float y) {
		return map.isOutside(x, y);
	}
	
	/**
	 * This function checks whether we are capable of building a {@link Tower} of the specified
	 * {@link TowerType type} at the provided location, which would be the top-left {@link Tile} of
	 * where the Tower would be.
	 * @param type - This is the TowerType of the Tower we want to build. 
	 * In general, Towers are all the same size, so this does not affect whether a path is possible,
	 * but it does change the gold value, and so we check to see if we have enough gold to build this type.
	 * Furthermore, types that are not base will return false when passed to this function, as those are not
	 * technically "built".
	 * @param x - The x value of the Tile that will be the upper left tile of this Tower.
	 * @param y - The y value of the Tile that will be the upper left tile of this Tower.
	 * @return True if building a Tower of this type with the upper left tile being in the specified location will not
	 * result in no possible air path or no possible ground path from the start to the finish, the type is a base type, and
	 * we have enough gold to build the Tower. Will return false if no such path exists, if the type is not a base type (as these
	 * cannot be built directly), or if we don't have enough gold to purchase the Tower.
	 */
	public boolean canBuild(TowerType type, int x, int y) {
		return level.canBuild(type, x, y);
	}
	
	/**
	 * This function checks whether we can {@link Upgrade} the specified {@link Tower}
	 * along the specified {@link UpgradePathType upgradePath}.
	 * @param t - The Tower we are attempting to Upgrade.
	 * @param upgradePath - The Upgrade path along which we want to Upgrade. This is currently
	 * UPPER_PATH and LOWER_PATH.
	 * @return True if the Tower can be upgraded and we have enough gold, false if not. Future Upgrades
	 * may change the size of the Tower or whether it takes up air or ground space. This function will need
	 * to change accordingly.
	 */
	public boolean canUpgrade(Tower t, UpgradePathType upgradePath) {
		return level.canUpgrade(t, upgradePath);
	}
	
	public boolean canSiphon(Tower from, Tower to) {
		return level.canSiphon(from, to);
	}
	
	
	public void reduceHealth(float amount) {
		level.reduceHealth(amount); //TODO: Creep manager accesses directly, why not here?
		if (level.getHealth() <= 0) {
			game.newEvent(GameEventType.HEALTH_ZERO, level);
		}
	}
	
	public HashSet<Creep> getCreepAdjacentToEarth(boolean isFlying) {
		return level.getCreepAdjacentToEarth(isFlying);
	}
	
}
