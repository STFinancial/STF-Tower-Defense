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
	
	public Tile getTile(int x, int y) {
		return map.getTile(y, x);
	}
	
	public int getMapWidth() {
		return map.getWidth();
	}
	
	public int getMapHeight() {
		return map.getHeight();
	}
	
	//TODO: Not happy about how this works, I really don't like it returning int
	public int getVertexBelow(Vertex vertex) {
		return level.getVertexBelow(vertex);
	}
	
	public boolean isOutside(float x, float y) {
		return map.isOutside(x, y);
	}
	
	public boolean canBuild(TowerType type, int x, int y) {
		return level.canBuild(type, x, y);
	}
	
	/**
	 * Returns
	 * @param t
	 * @param upgradePath
	 * @return
	 */
	public boolean canUpgrade(Tower t, UpgradePathType upgradePath) {
		float totalGold = level.getGold();
		if (!TowerManager.getInstance().getType(t).isBaseType()) {
			/* Need to check size of Tower post Upgrade to see if it can fit (not sure we want this to change) */
			if (totalGold < TowerManager.getInstance().getUpgradeCost(t, upgradePath)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
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
