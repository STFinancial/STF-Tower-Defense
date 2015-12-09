package towers;

import utilities.GameConstants;
import utilities.GameConstants.UpgradePathType;
import towers.BaseAttributeList.Upgrade;

class TowerUpgradeHandler {
	private Tower parent;
	private boolean[][][] upgradeProgress; //Don't think this is actually needed
	private int[][] nextUpgrade;
	private TowerType defaultTrackType;
	private int defaultTrack;
	private boolean isBase;
	
	private final int NUM_TRACKS = GameConstants.NUM_DAMAGE_TYPES;
	private final int NUM_PATHS = GameConstants.NUM_UPGRADE_PATHS;
	private final int NUM_UPGRADES = GameConstants.UPGRADE_PATH_LENGTH;
	
	TowerUpgradeHandler(Tower parent) {
		upgradeProgress = new boolean[NUM_TRACKS][NUM_PATHS][NUM_UPGRADES];
		nextUpgrade = new int[NUM_TRACKS][NUM_PATHS];
		this.parent = parent;
		defaultTrackType = parent.getType();
		defaultTrack = towerTypeToIndex(defaultTrackType);
		isBase = defaultTrackType.isBaseType();
	}
	
	/**
	 * Returns a clone of the calling instance, storing all {@link Upgrade} progress.
	 * This should be called when a Tower is upgraded or downgraded {@link TowerType type}.
	 * This allows the new Tower to have a copy of the upgrade history in case it's downgraded and re-upgraded.
	 * @param newParent - The new {@link Tower} parent that this handler belongs to.
	 * @return A clone of the calling instance, storing all upgrade progress.
	 */
	TowerUpgradeHandler clone(Tower newParent) {
		/* Create the new instance with the new parent */
		TowerUpgradeHandler tuh = new TowerUpgradeHandler(newParent);
		
		/* Copy over the data in the arrays */
		for (int track = 0; track < NUM_TRACKS; track++) {
			for (int path = 0; path < NUM_PATHS; path++) {
				tuh.nextUpgrade[track][path] = nextUpgrade[track][path];
				for (int upgrade = 0; upgrade < NUM_UPGRADES; upgrade++) {
					tuh.upgradeProgress[track][path][upgrade] = upgradeProgress[track][path][upgrade];
				}
			}
		}
		
		/* Apply base upgrades as needed */
		tuh.initAfterClone();
		
		return tuh;
	}
	
	/**
	 * Returns true if the path has any more unpurchased {@link Upgrade Upgrades}.
	 * This method is agnostic about cost, gold amount, and changes in map state,
	 * and thus checking for such things should be used in addition to this method.
	 * @param path - The path we are looking to see if we can upgrade
	 * @return True if the path has any more unpurchased Upgrades, false otherwise.
	 */
	boolean canUpgrade(UpgradePathType path) {
		/* If it's a base TowerType, we can't upgrade it */
		if (isBase) {
			return false;
		}
		/* If we've reached the end of this path, then we can't upgrade it */
		if (nextUpgrade[defaultTrack][path.ordinal()] == NUM_UPGRADES) {
			return false;
		}
		/* This is currently all the criteria for being able to upgrade */
		return true;
	}
	
	/**
	 * Upgrades the parent {@link Tower} and applies the baseUpgrade.
	 * This method does not call update on the Tower chain, and thus should
	 * be done elsewhere, preferably in the method that calls this.
	 * @param path - The path which we are applying the next {@link Upgrade} to.
	 * @see {@link Upgrade#baseUpgrade(Tower)}
	 */
	void upgrade(UpgradePathType path) {
		/* Find the next upgrade from the array and increment the value for later */
		int nextUpgradeNum = nextUpgrade[defaultTrack][path.ordinal()]++;
		defaultTrackType.getUpgrade(path, nextUpgradeNum).baseUpgrade(parent);
	}
	
	/**
	 * This method should be called from inside {@link Tower} during the update on a Tower chain.
	 * It applies the portion of an {@link Upgrade} which occurs after a Tower siphons from its
	 * parent, but before its children siphon from it.
	 */
	void applyMidSiphonUpgrades() {
		if (!isBase) {
			for (UpgradePathType path: UpgradePathType.values()) {
				for (int upgrade = 0; upgrade < nextUpgrade[defaultTrack][path.ordinal()]; upgrade++) {
					defaultTrackType.getUpgrade(path, upgrade).midSiphonUpgrade(parent);
				}
			}
		}
	}
	
	/**
	 * This method should be called from inside {@link Tower} during the update on a Tower chain.
	 * It applies the portion of an {@link Upgrade} which occurs after all Towers have completed their siphon.
	 */
	void applyPostSiphonUpgrades() {
		if (!isBase) {
			for (UpgradePathType path: UpgradePathType.values()) {
				for (int upgrade = 0; upgrade < nextUpgrade[defaultTrack][path.ordinal()]; upgrade++) {
					defaultTrackType.getUpgrade(path, upgrade).postSiphonUpgrade(parent);
				}
			}
		}
	}
	
	private int towerTypeToIndex(TowerType type) {
		TowerType baseType = type.getDowngradeType();
		switch(baseType) {
		case EARTH:
			return 0;
		case FIRE:
			return 1;
		case WATER:
			return 2;
		case WIND:
			return 3;
		default:
			return -1;
		}
	}
	
	/**
	 * This method is called in clone to apply baseUpgrade as needed after we have upgraded the tower to a new type.
	 * It applies base upgrades for all of the previously purchased upgrades.
	 */
	private void initAfterClone() {
		if (!isBase) {
			for (UpgradePathType path: UpgradePathType.values()) {
				for (int upgradeNum = 0; upgradeNum < nextUpgrade[defaultTrack][path.ordinal()]; upgradeNum++) {
					defaultTrackType.getUpgrade(path, upgradeNum).baseUpgrade(parent);
				}
			}
		}
	}
}
