package towers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import levels.Level;
import maps.Tile;
import creeps.DamageType;
import projectiles.*;
import utilities.Circle;
import utilities.GameConstants;
import utilities.GameObject;
public abstract class Tower extends GameObject {
	//Positional Details
	protected Level level;
	protected int x, y; //Top Left corner in Tile Coordinates
	protected int width;
	protected int height;
	//int cost;
	protected float centerX, centerY;
	protected Tile topLeft;
	protected Circle targetZone;

	//Targeting Details
	protected static ProjectileManager projManager = ProjectileManager.getInstance();
	protected static TowerManager towerManager = TowerManager.getInstance();
	protected TargetingModeType targetingMode;
	protected float targetX, targetY; //For ground spot target towers, in Tile coordinates

	//Misc.
	protected TowerType type;
	private int towerID;
	protected Tower siphoningFrom;
	protected ArrayList<Tower> siphoningTo;
	protected Projectile baseProjectile;
	
	//Upgrading Information
	private boolean[][][] upgradeTracks;
	
	//Base Attributes
	protected BaseAttributeList baseAttributeList;
	
	//Current Attributes
	protected float[] damageArray = new float[GameConstants.NUM_DAMAGE_TYPES];
	protected float[] slowArray = new float[GameConstants.NUM_DAMAGE_TYPES];
	protected int[] slowDurationArray = new int[GameConstants.NUM_DAMAGE_TYPES];
	protected float attackCooldown;
	protected float currentAttackCooldown; //Number of game ticks between tower shots, 0 for passive towers (beacons)
	protected float attackCarryOver;
	protected float damageSplash;
	protected float effectSplash;
	protected float splashRadius;
	protected float range;
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
	
	protected Tower(Level level, Tile topLeftTile, TowerType type, int towerID) {
		this.baseAttributeList = type.getAttributeList().clone();
		this.upgradeTracks = new boolean[GameConstants.NUM_DAMAGE_TYPES][GameConstants.NUM_UPGRADE_PATHS][GameConstants.UPGRADE_PATH_LENGTH];
		this.level = level;
		this.width = baseAttributeList.baseWidth;
		this.height = baseAttributeList.baseHeight;
		this.type = baseAttributeList.type;
		this.x = topLeftTile.x;
		this.y = topLeftTile.y;
		this.topLeft = topLeftTile;
		this.centerX = x + width / 2f;
		this.centerY = y + height / 2f;
		this.targetZone = new Circle(centerX, centerY, range);
		this.targetingMode = TargetingModeType.FIRST;
		this.towerID = towerID;
		this.siphoningTo = new ArrayList<Tower>();
		this.qLevel = 0;
		updateTowerChain();
		System.out.println("Tower built at " + x + " , " + y + " (TOP LEFT TILE)");
	}
	
	protected void increaseQuality() {
		++qLevel;
		updateTowerChain();
	}
	
	protected boolean doesOnHit() { return doesOnHit; }
	protected boolean doesSlow() { return doesSlow; }
	protected boolean doesSplash() { return doesSplash; }
	protected float getCenterX() { return centerX; }
	protected float getCenterY() { return centerY; }
	protected float getDamage(DamageType type) { return damageArray[type.ordinal()]; }
	protected float getDamageSplash() { return damageSplash; }
	protected float getEffectSplash() { return effectSplash; }
	protected float getSlow(DamageType type) { return slowArray[type.ordinal()]; }
	protected int getSlowDuration(DamageType type) { return slowDurationArray[type.ordinal()]; }
	protected float getSplashRadius() { return splashRadius; }
	protected TargetingModeType getTargetingMode() { return targetingMode; }
	protected Circle getTargetZone() { return targetZone; }
	protected TowerType getType() { return type; }
	protected boolean hitsAir() { return hitsAir; }
	protected boolean splashHitsAir() { return splashHitsAir; }
	
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
	
	protected int getTowerID() { return towerID; }
	protected boolean[][][] getUpgradeTracks() { return upgradeTracks; }
	
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
			//TODO: current.adjustTalentStats();
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
			current.targetZone.radius = current.range;
		}
	}
	
	protected void siphon(Tower from) {
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			this.damageArray[i] += (int) (from.damageArray[i] * this.baseAttributeList.damageSiphon);
			this.slowArray[i] += from.slowArray[i] * this.baseAttributeList.slowSiphon;
			this.slowDurationArray[i] += (int) (from.slowDurationArray[i] * this.baseAttributeList.slowDurationSiphon);
		}
		this.range += from.range * this.rangeSiphon; 
		this.attackCooldown -= (int) (this.attackCooldownSiphon - Math.sqrt(from.attackCooldown + this.baseAttributeList.attackCooldownSiphon));
		if (this.attackCooldown < 1) { this.attackCooldown = 1; }
		this.damageSplash += from.damageSplash * this.damageSplashSiphon;
		this.effectSplash += from.effectSplash * this.effectSplashSiphon;
		this.splashRadius += from.splashRadius * this.radiusSplashSiphon;
	}
	
	protected void adjustBaseStats() {
		attackCooldown 			= baseAttributeList.baseAttackCooldown;
		damageSplash 			= baseAttributeList.baseDamageSplash;
		effectSplash			= baseAttributeList.baseEffectSplash;
		splashRadius			= baseAttributeList.baseSplashRadius;
		range 					= baseAttributeList.baseRange;
		hitsAir					= baseAttributeList.hitsAir;
		hitsGround				= baseAttributeList.hitsGround;
		doesSplash				= baseAttributeList.doesSplash;
		doesSlow				= baseAttributeList.doesSlow;
		doesOnHit				= baseAttributeList.doesOnHit;
		splashHitsAir			= baseAttributeList.splashHitsAir;
		attackCarryOver			= 0f;
		currentAttackCooldown	= 0f;
		damageSiphon			= baseAttributeList.damageSiphon;
		slowDurationSiphon		= baseAttributeList.slowDurationSiphon;
		slowSiphon				= baseAttributeList.slowSiphon;
		attackCooldownSiphon	= baseAttributeList.attackCooldownSiphon;
		damageSplashSiphon		= baseAttributeList.damageSplashSiphon;
		effectSplashSiphon		= baseAttributeList.effectSplashSiphon;
		radiusSplashSiphon		= baseAttributeList.radiusSplashSiphon;
		rangeSiphon				= baseAttributeList.rangeSiphon;
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			slowArray[i] = baseAttributeList.baseSlowArray[i];
			damageArray[i] = baseAttributeList.baseDamageArray[i];
			slowDurationArray[i] = baseAttributeList.baseSlowDurationArray[i];
		}
	}
	
	private void adjustMidSiphonUpgrades() {
		if (siphoningFrom != null) {
			boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
			for (int path = 0; path < GameConstants.NUM_UPGRADE_PATHS; path++) {
				for (int level = 0; level < GameConstants.UPGRADE_PATH_LENGTH; level++) {
					if (progress[path][level]) {
						baseAttributeList.upgrades[path][level].postSiphonUpgrade(this);
					}
				}
			}
		}
	}
	
	private void adjustPostSiphonUpgrades() {
		if (siphoningFrom != null) {
			boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
			for (int path = 0; path < GameConstants.NUM_UPGRADE_PATHS; path++) {
				for (int level = 0; level < GameConstants.UPGRADE_PATH_LENGTH; level++) {
					if (progress[path][level]) {
						baseAttributeList.upgrades[path][level].postSiphonUpgrade(this);
					}
				}
			}
		}
	}
	
	private void adjustCommonQuality() {
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			damageArray[i] *= (1 + (qDamage * qLevel));
			slowArray[i] += qSlow * qLevel;
			slowDurationArray[i] *= (1 + (qSlowDuration * qLevel));
		}
		attackCooldown -= qCooldown * qLevel;
		if (attackCooldown < 1) { attackCooldown = 1; }
		damageSplash += qDamageSplash * qLevel;
		effectSplash += qEffectSplash * qLevel;
		splashRadius += (1 + (qRadiusSplash * qLevel));
		range *= (1 + (qRange * qLevel));
	}
	
	private void adjustTalentStats() {
		//TODO:
	}
	
	protected abstract void adjustProjectileStats();
	protected abstract void adjustClassSpecificBaseStats();
	protected abstract void adjustClassSpecificQuality();
	protected void createAuras() { } //Can be overridden for a tower that actually has auras
	
	/**
	 * 
	 * @param path - 1 or 0 depending on the upgrade path
	 */
	protected void upgrade(int path) {
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
		}
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
		s = s.append("Tower of Type " + type + " at position " + x + ", " + y + "\n");
		s = s.append("TowerID: " + towerID);
		s = s.append("Damage: \n");
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			s.append("\t " + DamageType.values()[i] + " - " + damageArray[i] + "\n");
		}
		s.append("Slow: \n");
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			s.append("\t " + DamageType.values()[i] + " - " + slowArray[i] * 100 + "% for " + slowDurationArray[i] + "\n");
		}
		s.append("Tower Range: " + range); 
		s.append("Attack Cooldown: " + attackCooldown);
		s.append("Damage Splash Effectiveness: " + damageSplash * 100 + "%  Effect Splash Effectiveness: " + effectSplash * 100 + "%  Splash Radius: " + splashRadius);
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
		result = 31 * result + x;
		result = 31 * result + y;
		result = 31 * result + type.ordinal();
		result = 31 * result + (int) damageArray[result % 4];
		return result;
	}
}
