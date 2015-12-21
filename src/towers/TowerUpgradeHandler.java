package towers;

import utilities.GameConstants;
import utilities.GameConstants.UpgradePathType;
import utilities.GameConstants.UpgradeTrackType;
import towers.BaseAttributeList.Upgrade;

final class TowerUpgradeHandler {
	private Tower parent;
	private boolean[][][] upgradeProgress; //Don't think this is actually needed
	private int[][] nextUpgrade;
	private UpgradeTrackType defaultTrack;
	private boolean isBase;
	
	private final int NUM_TRACKS = GameConstants.NUM_UPGRADE_TRACKS;
	private final int NUM_PATHS = GameConstants.NUM_UPGRADE_PATHS;
	private final int NUM_UPGRADES = GameConstants.UPGRADE_PATH_LENGTH;
	
	TowerUpgradeHandler(Tower parent) {
		upgradeProgress = new boolean[NUM_TRACKS][NUM_PATHS][NUM_UPGRADES];
		nextUpgrade = new int[NUM_TRACKS][NUM_PATHS];
		this.parent = parent;
		defaultTrack = towerTypeToTrackType(parent.getType());
		isBase = parent.getType().isBaseType();
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
	 * Returns the gold value of all {@link Upgrade Upgrades} purchased for this {@link Tower},
	 * excluding those purchased for other siphon-buffed versions of this Tower.
	 * @return The gold value of Upgrades for this Tower track.
	 */
	float getCurrentTrackValue() {
		float goldValue = 0;
		if (isBase) { 
			return 0;
		} else {
			for (UpgradePathType path: UpgradePathType.values()) {
				for (int upgrade = 0; upgrade < nextUpgrade[defaultTrack.ordinal()][path.ordinal()]; upgrade++) {
					goldValue += TowerType.getUpgradeCost(defaultTrack.getTowerType(), path, upgrade) * GameConstants.BASE_TRACK_UPGRADE_REFUND_RATE;
				}
			}
		}
		return goldValue;
	}
	
	/**
	 * Returns the gold value of all {@link Upgrade Upgrades} purchased for this {@link Tower}.
	 * This includes those purchased for other siphon-buffed versions of this Tower.
	 * @return The gold value of Upgrades purchased by this Tower.
	 */
	float getAllTrackValue() {
		float goldValue = 0;
		for (UpgradeTrackType track: UpgradeTrackType.values()) {
			for (UpgradePathType path: UpgradePathType.values()) {
				for (int upgrade = 0; upgrade < NUM_UPGRADES; upgrade++) {
					goldValue += TowerType.getUpgradeCost(track.getTowerType(), path, upgrade);
				}
			}
		}
		return goldValue;
	}
	
	/**
	 * Removes the {@link Upgrade Upgrades} for this current {@link Tower} track.
	 * It does not undo any of the stats or refund gold, but simply sets the Upgrades as unpurchased.
	 */
	void removeTrackUpgrades() {
		if (!isBase) {
			for (UpgradePathType path: UpgradePathType.values()) {
				for (int upgrade = 0; upgrade < nextUpgrade[defaultTrack.ordinal()][path.ordinal()]; upgrade++) {
					upgradeProgress[defaultTrack.ordinal()][path.ordinal()][upgrade] = false;
				}
				nextUpgrade[defaultTrack.ordinal()][path.ordinal()] = 0;
			}
		}
 		
	}
	
	/**
	 * Returns the gold value of the next available {@link Upgrade} for this {@link UpgradePathType path}
	 * of the {@link Tower}.
	 * @param path - The path for which we will return the gold value of the next available Upgrade.
	 * @return The gold value of the next available Upgrade for this path, or -1 if all upgrades for this path have been purchased.
	 */
	float getUpgradeCost(UpgradePathType path) {
		if (isBase) {
			return -1;
		}
		if (nextUpgrade[defaultTrack.ordinal()][path.ordinal()] < NUM_UPGRADES) {
			return TowerType.getUpgradeCost(defaultTrack.getTowerType(), path, nextUpgrade[defaultTrack.ordinal()][path.ordinal()]);
		} else {
			return -1;
		}
	}
	
	/**
	 * This method checks if we've purchased a specified {@link Upgrade}.
	 * @param path - The {@link UpgradePathType Path} in which the Upgrade is.
	 * @param upgradeNum - The number of the Upgrade in the specified path. Numbering begins at 0.
	 * @return True if we have purchased the Upgrade for the current track and parameters.
	 */
	boolean hasPurchasedUpgrade(UpgradePathType path, int upgradeNum) {
		/* If the next upgrade is 1, we've purchased 0 */
		return nextUpgrade[defaultTrack.ordinal()][path.ordinal()] > upgradeNum;
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
		if (nextUpgrade[defaultTrack.ordinal()][path.ordinal()] == NUM_UPGRADES) {
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
		if (isBase) {
			return;
		}
		/* Find the next upgrade from the array and increment the value for later */
		int nextUpgradeNum = nextUpgrade[defaultTrack.ordinal()][path.ordinal()]++;
		defaultTrack.getTowerType().getUpgrade(path, nextUpgradeNum).baseUpgrade(parent);
	}
	
	/**
	 * This method should be called from inside {@link Tower} during the update on a Tower chain.
	 * It applies the portion of an {@link Upgrade} which occurs after a Tower siphons from its
	 * parent, but before its children siphon from it.
	 */
	void applyMidSiphonUpgrades() {
		if (!isBase) {
			for (UpgradePathType path: UpgradePathType.values()) {
				for (int upgrade = 0; upgrade < nextUpgrade[defaultTrack.ordinal()][path.ordinal()]; upgrade++) {
					defaultTrack.getTowerType().getUpgrade(path, upgrade).midSiphonUpgrade(parent);
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
				for (int upgrade = 0; upgrade < nextUpgrade[defaultTrack.ordinal()][path.ordinal()]; upgrade++) {
					defaultTrack.getTowerType().getUpgrade(path, upgrade).postSiphonUpgrade(parent);
				}
			}
		}
	}
	
	private UpgradeTrackType towerTypeToTrackType(TowerType type) {
		TowerType baseType = type.getDowngradeType();
		switch(baseType) {
		case EARTH:
			return UpgradeTrackType.EARTH;
		case FIRE:
			return UpgradeTrackType.FIRE;
		case WATER:
			return UpgradeTrackType.WATER;
		case WIND:
			return UpgradeTrackType.WIND;
		default:
			return null;
		}
	}
	
	/**
	 * This method is called in clone to apply baseUpgrade as needed after we have upgraded the tower to a new type.
	 * It applies base upgrades for all of the previously purchased upgrades.
	 */
	private void initAfterClone() {
		if (!isBase) {
			for (UpgradePathType path: UpgradePathType.values()) {
				for (int upgradeNum = 0; upgradeNum < nextUpgrade[defaultTrack.ordinal()][path.ordinal()]; upgradeNum++) {
					defaultTrack.getTowerType().getUpgrade(path, upgradeNum).baseUpgrade(parent);
				}
			}
		}
	}
}
