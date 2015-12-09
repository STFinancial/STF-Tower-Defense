package towers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import levels.LevelManager;
import levels.Tile;
import creeps.CreepManager;
import creeps.DamageType;
import game.GameObject;
import projectiles.*;
import utilities.Circle;
import utilities.GameConstants;
import utilities.GameConstants.UpgradePathType;

public abstract class Tower extends GameObject {
	//Positional Details
	private int width;
	private int height;
	//int cost;
	protected float centerX, centerY;
	private Tile topLeft;
	protected Circle targetZone;

	//Targeting Details
	protected static ProjectileManager projManager = ProjectileManager.getInstance();
	protected static TowerManager towerManager = TowerManager.getInstance();
	protected static CreepManager creepManager = CreepManager.getInstance();
	protected static LevelManager levelManager = LevelManager.getInstance();
	protected TargetingModeType targetingMode;
	protected float targetX, targetY; //For ground spot target towers, in Tile coordinates

	//Misc.
	protected TowerType type;
	private int towerID;
	protected Tower siphoningFrom;
	protected ArrayList<Tower> siphoningTo;
	protected Projectile baseProjectile;
	
	//Upgrading Information
	TowerUpgradeHandler upgradeHandler;
//	private boolean[][][] upgradeTracks; //TODO: This is a messy way of doing this, try abstracting it more
	private float costReduction;
	
	//Base Attributes
	protected BaseAttributeList baseAttributeList;
	
	//Current Attributes
	protected float[] damageArray = new float[GameConstants.NUM_DAMAGE_TYPES];
	protected float[] slowArray = new float[GameConstants.NUM_DAMAGE_TYPES];
	protected int[] slowDurationArray = new int[GameConstants.NUM_DAMAGE_TYPES];
	protected float attackCooldown;
	protected float currentAttackCooldown; //Number of game ticks between tower shots, 0 for passive towers (beacons)
	protected float attackCarryOver;
	protected float splashDamage;
	protected float splashEffect;
	protected float splashRadius;
	protected float range;
	protected boolean isInAir;
	protected boolean isOnGround;
	protected boolean hitsAir;
	protected boolean hitsGround;
	protected boolean doesSlow;
	protected boolean doesSplash;
	protected boolean doesOnHit;
	protected boolean splashHitsAir;
	protected float damageSiphon;
	protected float slowDurationSiphon;
	protected float slowSiphon;
	protected float attackCooldownSiphon;
	protected float damageSplashSiphon;
	protected float effectSplashSiphon;
	protected float radiusSplashSiphon;
	protected float rangeSiphon;
	
	//Quality Coefficients
	protected int qLevel;
	protected float qDamage;
	protected float qSlow;
	protected float qSlowDuration;
	protected float qCooldown;
	protected float qDamageSplash;
	protected float qEffectSplash;
	protected float qRadiusSplash;
	protected float qRange;
	
	//Talent Progress
	//TODO: Should this be abstracted a bit further?
	protected int[] talentProgress;
	
	protected Tower (Tile topLeftTile, TowerType type, int towerID) {
		this.baseAttributeList = type.getAttributeList().clone();
		this.upgradeHandler = new TowerUpgradeHandler(this);
		this.width = baseAttributeList.baseWidth;
		this.height = baseAttributeList.baseHeight;
		this.type = baseAttributeList.type;
		this.topLeft = topLeftTile;
		Circle tz = levelManager.getCenter(topLeftTile, width, height);
		this.centerX = tz.getX();
		this.centerY = tz.getY();
		this.targetZone = new Circle(centerX, centerY, range);
		this.targetingMode = TargetingModeType.FIRST;
		this.towerID = towerID;
		this.siphoningTo = new ArrayList<Tower>();
		this.qLevel = 0;
		this.talentProgress = new int[GameConstants.NUM_TOWER_TALENTS];
		this.costReduction = 0;
		updateTowerChain();
	}
	
	protected void increaseQuality() {
		++qLevel;
		updateTowerChain();
	}
	
	protected void increaseDamage(DamageType damageType, float amount, boolean isFlat) {
		if (isFlat) {
			damageArray[damageType.ordinal()] += amount;
		} else {
			damageArray[damageType.ordinal()] *= amount;
		}
	}
	
	protected void increaseSlow(DamageType damageType, float amount, boolean isFlat) {
		if (isFlat) {
			slowArray[damageType.ordinal()] += amount;
		} else {
			slowArray[damageType.ordinal()] *= amount;
		}
	}
	
	protected boolean doesOnHit() { return doesOnHit; }
	protected boolean doesSlow() { return doesSlow; }
	protected boolean doesSplash() { return doesSplash; }
	protected float getCenterX() { return centerX; }
	protected float getCenterY() { return centerY; }
	protected float getDamage(DamageType type) { return damageArray[type.ordinal()]; }
	protected float getDamageSplash() { return splashDamage; }
	protected float getEffectSplash() { return splashEffect; }
	protected int getHeight() { return height; }
	protected int getNumTalentPoints(int nodeID) { return talentProgress[nodeID]; }
	protected float getSlow(DamageType type) { return slowArray[type.ordinal()]; }
	protected int getSlowDuration(DamageType type) { return slowDurationArray[type.ordinal()]; }
	protected float getSplashRadius() { return splashRadius; }
	protected TargetingModeType getTargetingMode() { return targetingMode; }
	protected Circle getTargetZone() { return targetZone; }
	protected Tile getTopLeftTile() { return topLeft; }
	protected int getTowerID() { return towerID; }
	protected TowerType getType() { return type; }
	protected boolean[][][] getUpgradeTracks() { return upgradeTracks; }
	protected int getWidth() { return width; }
	protected boolean isInAir() { return isInAir; }
	protected boolean isOnGround() { return isOnGround; }
	protected boolean hitsAir() { return hitsAir; }
	protected boolean splashHitsAir() { return splashHitsAir; }
	protected void reduceUpgradeCost(float amount) { costReduction += amount; }
	
	protected float getTrackGoldValue() {
		float goldValue = 0;
		if (type.isBaseType()) { 
			return 0;
		} else {
			for (int path = 0; path < GameConstants.NUM_UPGRADE_PATHS; path++) {
				for (int level = 0; level < GameConstants.UPGRADE_PATH_LENGTH; level++) {
					goldValue += TowerType.getUpgradeCost(type, path, level) * GameConstants.BASE_TRACK_UPGRADE_REFUND_RATE;
				}
			}
		}
		return goldValue;
	}
	
	protected float getTotalGoldValue() {
		float goldValue = type.getDowngradeType().getCost() * GameConstants.BASE_TOWER_REFUND_RATE; //TODO: There should be costs for all tower types
		DamageType d;
		TowerType upgradeType;
		for (int dtype = 0; dtype < GameConstants.NUM_DAMAGE_TYPES; dtype++) {
			d = DamageType.values()[dtype];
			if (d.isBaseElemental()) { //if its a base elemental type
				for (int path = 0; path < GameConstants.NUM_UPGRADE_PATHS; path++) {
					for (int level = 0; level < GameConstants.UPGRADE_PATH_LENGTH; level++) {
						upgradeType = TowerType.getUpgrade(TowerType.getTowerTypeFromDamage(d), type.getDowngradeType());
						if (upgradeTracks[dtype][path][level]) { //if we've bought this upgrade at some time
							goldValue += TowerType.getUpgradeCost(upgradeType, path, level) * GameConstants.BASE_TOTAL_UPGRADE_REFUND_RATE;
						}
					}
				}
			}
		}
		return goldValue;
	}
	
	public float getUpgradeCost(UpgradePathType path) {
		
	}
	
	
	/* Called after siphoning a tower or desiphoning a tower */
	protected void setUpgradeTracks(boolean[][][] upgradeTracks) {
		this.upgradeTracks = upgradeTracks;
		if (type.isBaseType()) {
			return;
		} else {
			DamageType trackType = DamageType.getDamageTypeFromTower(siphoningFrom.getType().getDowngradeType());
			for (int path = 0; path < GameConstants.NUM_UPGRADE_PATHS; path++) {
				for (int level = 0; level < GameConstants.UPGRADE_PATH_LENGTH; level++) {
					if (upgradeTracks[trackType.ordinal()][path][level]) {
						baseAttributeList.upgrades[path][level].baseUpgrade(this);
					}
				}
			}
		}
	}
	
	protected void removeTrackUpgrades() {
		if (type.isBaseType()) {
			return;
		} else {
			//TODO: Need to somehow undo changes made to baseAttributeList. Can reclone it perhaps.
		}
	}
	
	protected Projectile fireProjectile() {
		return duplicateProjectile(baseProjectile);
	}

	protected void updateTowerChain() {
		adjustSiphonChain(this);
	}
	
	protected static void adjustSiphonChain(Tower t) {
		BFSAdjust(towerManager.getRoot(t));
	}
	
	protected static void BFSAdjust(Tower root) {
		Queue<Tower> openList = new LinkedList<Tower>();
		openList.add(root);
		Tower current;
		while (!openList.isEmpty()) {
			current = openList.poll();
			openList.addAll(current.siphoningTo);
			towerManager.clearAuras(current);
		}
		current = root;
		root.adjustBaseStats();
		openList.addAll(root.siphoningTo);
		root.adjustMidSiphonUpgrades();
		while (!openList.isEmpty()) {
			current = openList.poll();
			current.adjustBaseStats();
			current.adjustClassSpecificBaseStats();
			openList.addAll(current.siphoningTo);
			current.siphon(current.siphoningFrom);
			current.adjustCommonQuality();
			current.adjustMidSiphonUpgrades();
			current.adjustClassSpecificQuality(); //I think in every case none of these would increase siphon, but siphon can increase these. This needs to be second because upgrades sets these (if there is a conflict we need to move away from the upgrades modifyong the tower values and having that happen in the "baseTowerValues" department. We would just have if statements like we did originally
			current.adjustLocalTalentStats();
			//TODO current.adjustGlobalTalentStats();
			//order here matters, because some talents convert one damage to another, and so other multipliers might not work
			current.adjustPostSiphonUpgrades();
		}
		towerManager.createAuraChain(root);
		openList.add(root);
		while (!openList.isEmpty()) {
			current = openList.poll();
			openList.addAll(current.siphoningTo);
			if (current.attackCooldown < 1) { current.attackCooldown = 1; }
			current.currentAttackCooldown = current.attackCooldown;
			current.targetZone = new Circle(current.getCenterX(), current.getCenterY(), current.range);
			current.adjustProjectileStats();
			current.targetingMode = TargetingModeType.getDefaultTargetingMode(current.baseProjectile);
		}
	}
	
	protected void siphon(Tower from) {
		//TODO: There is a ton of inconsistency in this method. Why?
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			this.damageArray[i] += (int) (from.damageArray[i] * this.baseAttributeList.baseDamageSiphon);
			this.slowArray[i] += from.slowArray[i] * this.baseAttributeList.baseSlowSiphon;
			this.slowDurationArray[i] += (int) (from.slowDurationArray[i] * this.baseAttributeList.baseSlowDurationSiphon);
		}
		this.range += from.range * this.rangeSiphon; 
		this.attackCooldown -= (int) (this.attackCooldownSiphon - Math.sqrt(from.attackCooldown + this.baseAttributeList.baseAttackCooldownSiphon)); //TODO: Why am I accessing the base?
		if (this.attackCooldown < 1) { this.attackCooldown = 1; }
		this.splashDamage += from.splashDamage * this.damageSplashSiphon;
		this.splashEffect += from.splashEffect * this.effectSplashSiphon;
		this.splashRadius += from.splashRadius * this.radiusSplashSiphon;
	}
	
	protected void adjustBaseStats() {
		attackCooldown 			= baseAttributeList.baseAttackCooldown;
		splashDamage 			= baseAttributeList.baseSplashDamage;
		splashEffect			= baseAttributeList.baseSplashEffect;
		splashRadius			= baseAttributeList.baseSplashRadius;
		range 					= baseAttributeList.baseRange;
		hitsAir					= baseAttributeList.baseHitsAir;
		isInAir					= baseAttributeList.baseIsInAir;
		isOnGround				= baseAttributeList.baseIsOnGround;
		hitsGround				= baseAttributeList.baseHitsGround;
		doesSplash				= baseAttributeList.baseDoesSplash;
		doesSlow				= baseAttributeList.baseDoesSlow;
		doesOnHit				= baseAttributeList.baseDoesOnHit;
		splashHitsAir			= baseAttributeList.baseSplashHitsAir;
		attackCarryOver			= 0f;
		currentAttackCooldown	= 0f;
		damageSiphon			= baseAttributeList.baseDamageSiphon;
		slowDurationSiphon		= baseAttributeList.baseSlowDurationSiphon;
		slowSiphon				= baseAttributeList.baseSlowSiphon;
		attackCooldownSiphon	= baseAttributeList.baseAttackCooldownSiphon;
		damageSplashSiphon		= baseAttributeList.baseSplashDamageSiphon;
		effectSplashSiphon		= baseAttributeList.baseSplashEffectSiphon;
		radiusSplashSiphon		= baseAttributeList.baseSplashRadiusSiphon;
		rangeSiphon				= baseAttributeList.baseRangeSiphon;
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			slowArray[i] = baseAttributeList.baseSlowArray[i];
			damageArray[i] = baseAttributeList.baseDamageArray[i];
			slowDurationArray[i] = baseAttributeList.baseSlowDurationArray[i];
		}
	}
	
	private void adjustMidSiphonUpgrades() {
		upgradeHandler.applyMidSiphonUpgrades();
//		if (siphoningFrom != null) {
//			boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
//			for (int path = 0; path < GameConstants.NUM_UPGRADE_PATHS; path++) {
//				for (int level = 0; level < GameConstants.UPGRADE_PATH_LENGTH; level++) {
//					if (progress[path][level]) {
//						baseAttributeList.upgrades[path][level].postSiphonUpgrade(this);
//					}
//				}
//			}
//		}
	}
	
	private void adjustPostSiphonUpgrades() {
		upgradeHandler.applyPostSiphonUpgrades();
//		if (siphoningFrom != null) {
//			boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
//			for (int path = 0; path < GameConstants.NUM_UPGRADE_PATHS; path++) {
//				for (int level = 0; level < GameConstants.UPGRADE_PATH_LENGTH; level++) {
//					if (progress[path][level]) {
//						baseAttributeList.upgrades[path][level].postSiphonUpgrade(this);
//					}
//				}
//			}
//		}
	}
	
	private void adjustCommonQuality() {
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			damageArray[i] *= (1 + (qDamage * qLevel));
			slowArray[i] += qSlow * qLevel;
			slowDurationArray[i] *= (1 + (qSlowDuration * qLevel));
		}
		attackCooldown -= qCooldown * qLevel;
		if (attackCooldown < 1) { attackCooldown = 1; }
		splashDamage += qDamageSplash * qLevel;
		splashEffect += qEffectSplash * qLevel;
		splashRadius += (1 + (qRadiusSplash * qLevel));
		range *= (1 + (qRange * qLevel));
	}
	
	private void adjustLocalTalentStats() {
		TowerTalentTree.applyTalents(this);
	}
	
	
	protected abstract void adjustProjectileStats();
	protected abstract void adjustClassSpecificBaseStats();
	protected abstract void adjustClassSpecificQuality();
	protected void createAuras() { } //Can be overridden for a tower that actually has auras
	@Override protected abstract int update();
	
	/**
	 * 
	 * @param path - 1 or 0 depending on the upgrade path
	 */
	protected void upgrade(UpgradePathType path) {
		
		for (int level = 0; level < GameConstants.UPGRADE_PATH_LENGTH; level++) {
			if (!upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()][path][level]) {
				upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()][path][level] = true;
				baseAttributeList.upgrades[path][level].baseUpgrade(this);
				updateTowerChain();
				return;
			}
		}
	}
	
	/**
	 * 
	 * @param path - Each tower has two upgrade paths, and this specifies whether it is the upper (0), or lower (1) path that we want to upgrade
	 * @param playerGold - This is the amount of gold that the player has. This is passed in to be more clear about what the boolean returned from this function will mean
	 * @return A boolean value specifying whether the given path can be upgraded both in terms of whether the upgrade is available, and if the player has enough gold
	 */
	protected boolean canUpgrade(int path, int playerGold) { //TODO: Should we really pass player gold?
		if (siphoningFrom == null || baseAttributeList.upgrades == null) {
			return false;
		} else {
			int sfType = siphoningFrom.baseAttributeList.downgradeType.ordinal();
			for (int i = 0; i < GameConstants.UPGRADE_PATH_LENGTH; i++) {
				if (!upgradeTracks[sfType][path][i]) {
					if (baseAttributeList.upgrades[path][i].baseCost <= playerGold) {
						return true;
					}
					return false;
				}
			}
			return false;
		} //TODO: This method is shit, we should remove this or at least modify it. No way this should look at player gold.
	}
	
	//TODO: Should this be in the manager?
	protected void increaseSiphons(float modifier) {
		damageSiphon += modifier;
		damageSplashSiphon += modifier;
		effectSplashSiphon += modifier;
		radiusSplashSiphon += modifier / 2;
		rangeSiphon += modifier / 4;
		slowSiphon += modifier;
		slowDurationSiphon += modifier;
	}

	protected Projectile duplicateProjectile(Projectile p) {
		return p.clone();
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s = s.append("Tower of Type " + type + " at position " + topLeft.toString() + "\n");
		s = s.append("TowerID: " + towerID + "\n");
		s = s.append("Damage: \n");
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			s.append("\t " + DamageType.values()[i] + " - " + damageArray[i] + "\n");
		}
		s.append("Slow: \n");
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			s.append("\t " + DamageType.values()[i] + " - " + slowArray[i] * 100 + "% for " + slowDurationArray[i] + "\n");
		}
		s.append("Tower Range: " + range); 
		s.append(" Attack Cooldown: " + attackCooldown);
		s.append(" Damage Splash Effectiveness: " + splashDamage * 100 + "%  Effect Splash Effectiveness: " + splashEffect * 100 + "%  Splash Radius: " + splashRadius);
		return s.toString();
	}
	
	
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Tower)) {
			return false;
		}
        Tower t = (Tower) o;
        return t.towerID == towerID;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + towerID;
		result = 31 * result + (int) targetZone.getX();
		result = 31 * result + (int) targetZone.getY();
		result = 31 * result + type.ordinal();
		result = 31 * result + (int) damageArray[result % 4];
		return result;
	}
}
