package towers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import levels.Level;
import levels.ProjectileGuider;
import levels.Updatable;
import maps.Tile;
import creeps.DamageType;
import projectiles.*;
import towers.TowerManager.Aura;
import utilities.Circle;
import utilities.GameConstants;
//TODO: Make strong comments for each tower type and tower (they can be the same, just make it).
public abstract class Tower implements Updatable {
	//Positional Details
	public Level level;
	public int x, y; //Top Left corner in Tile Coordinates
	public Tile topLeft;
	public float centerX, centerY;
	public Circle targetZone;
	public int width;
	public int height;
	public int cost;
	public TowerType type;

	//Targeting Details
	public static ProjectileGuider guider = ProjectileGuider.getInstance();
	public static TowerManager manager = TowerManager.getInstance();
	public TargetingModeType targetingType;
	public float targetX, targetY; //For ground spot target towers, in Tile coordinates

	//Misc.
	public int towerID;
	public Tower siphoningFrom;
	public ArrayList<Tower> siphoningTo;
	public Projectile baseProjectile;
	
	//Upgrading Information
	public boolean[][][] upgradeTracks;
	
	//Base Attributes
	BaseAttributeList baseAttributeList;
	
	//Current Attributes
	public float[] damageArray = new float[GameConstants.NUM_DAMAGE_TYPES];
	public float[] slowArray = new float[GameConstants.NUM_DAMAGE_TYPES];
	public int[] slowDurationArray = new int[GameConstants.NUM_DAMAGE_TYPES];
	public float attackCooldown;
	public float currentAttackCooldown; //Number of game ticks between tower shots, 0 for passive towers (beacons)
	public float attackCarryOver;
	public float damageSplash;
	public float effectSplash;
	public float splashRadius;
	public float range;
	public boolean hitsAir;
	public boolean splashHitsAir;
	public boolean hitsGround;
	public float damageSiphon;
	public float slowDurationSiphon;
	public float slowSiphon;
	public float attackCooldownSiphon;
	public float damageSplashSiphon;
	public float effectSplashSiphon;
	public float radiusSplashSiphon;
	public float rangeSiphon;
	
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
	
	public Tower(Level level, Tile topLeftTile, TowerType type, int towerID) {
		this.baseAttributeList = type.getAttributeList().clone();
		this.upgradeTracks = new boolean[GameConstants.NUM_DAMAGE_TYPES][GameConstants.NUM_UPGRADE_PATHS][GameConstants.UPGRADE_PATH_LENGTH];
		this.level = level;
		this.width = baseAttributeList.baseWidth;
		this.height = baseAttributeList.baseHeight;
		this.type = baseAttributeList.type;
		this.cost = baseAttributeList.baseCost;
		this.x = topLeftTile.x;
		this.y = topLeftTile.y;
		this.topLeft = topLeftTile;
		this.centerX = x + width / 2f;
		this.centerY = y + height / 2f;
		this.targetZone = new Circle(centerX, centerY, range);
		this.targetingType = TargetingModeType.FIRST;
		this.attackCarryOver = 0f;
		this.towerID = towerID;
		this.siphoningTo = new ArrayList<Tower>();
		this.splashHitsAir = false;
		this.qLevel = 0;
		updateTowerChain();
		System.out.println("Tower built at " + x + " , " + y + " (TOP LEFT TILE)");
	}
	
	public void increaseQuality() {
		++qLevel;
		updateTowerChain();
	}
	
	public Projectile fireProjectile() {
		return duplicateProjectile(baseProjectile);
	}

	public void updateTowerChain() {
		adjustSiphonChain(this);
	}
	
	protected static void adjustSiphonChain(Tower t) {
		BFSAdjust(manager.getRoot(t));
	}
	
	protected static void BFSAdjust(Tower root) {
		Queue<Tower> openList = new LinkedList<Tower>();
		openList.add(root);
		Tower current;
		while (!openList.isEmpty()) {
			current = openList.poll();
			openList.addAll(current.siphoningTo);
			manager.clearAuras(current);
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
			current.createAuras();
		}
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
		//TODO: Do I Want to be able to siphon siphon coefficients?
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			this.damageArray[i] += (int) (from.damageArray[i] * this.baseAttributeList.damageSiphon);
			this.slowArray[i] += from.slowArray[i] * this.baseAttributeList.slowSiphon;
			this.slowDurationArray[i] += (int) (from.slowDurationArray[i] * this.baseAttributeList.slowDurationSiphon);
		}
		this.range += from.range * this.rangeSiphon; //TODO: Decide if this is a good equation, do we want really short towers to pull down the range of others? Adds another dimension of complexity.
		this.attackCooldown -= (int) (this.attackCooldownSiphon - Math.sqrt(from.attackCooldown + this.baseAttributeList.attackCooldownSiphon)); //TODO: This value can currently be negative, is that wanted? Should really slow towers slow those around it?
		if (this.attackCooldown < 1) { this.attackCooldown = 1; }
		this.damageSplash += from.damageSplash * this.damageSplashSiphon;
		this.effectSplash += from.effectSplash * this.effectSplashSiphon;
		this.splashRadius += from.splashRadius * this.radiusSplashSiphon;
	}
	
	protected void adjustBaseStats() {
		attackCooldown 													= baseAttributeList.baseAttackCooldown;
		damageSplash 													= baseAttributeList.baseDamageSplash;
		effectSplash													= baseAttributeList.baseEffectSplash;
		splashRadius													= baseAttributeList.baseSplashRadius;
		range 															= baseAttributeList.baseRange;
		hitsAir															= baseAttributeList.hitsAir;
		hitsGround														= baseAttributeList.hitsGround;
		attackCarryOver													= 0;
		currentAttackCooldown											= 0;
		damageSiphon													= baseAttributeList.damageSiphon;
		slowDurationSiphon												= baseAttributeList.slowDurationSiphon;
		slowSiphon														= baseAttributeList.slowSiphon;
		attackCooldownSiphon											= baseAttributeList.attackCooldownSiphon;
		damageSplashSiphon												= baseAttributeList.damageSplashSiphon;
		effectSplashSiphon												= baseAttributeList.effectSplashSiphon;
		radiusSplashSiphon												= baseAttributeList.radiusSplashSiphon;
		rangeSiphon														= baseAttributeList.rangeSiphon;
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			slowArray[i] = baseAttributeList.baseSlowArray[i];
			damageArray[i] = baseAttributeList.baseDamageArray[i];
			slowDurationArray[i] = baseAttributeList.baseSlowDurationArray[i];
		}
	}
	
	private void adjustMidSiphonUpgrades() {
		if (siphoningFrom != null) {
			boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
			for (int track = 0; track < GameConstants.NUM_UPGRADE_PATHS; track++) {
				for (int uNum = 0; uNum < GameConstants.UPGRADE_PATH_LENGTH; uNum++) {
					if (progress[track][uNum]) {
						baseAttributeList.upgrades[track][uNum].postSiphonUpgrade(this);
					}
				}
			}
		}
	}
	
	private void adjustPostSiphonUpgrades() {
		if (siphoningFrom != null) {
			boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
			for (int track = 0; track < GameConstants.NUM_UPGRADE_PATHS; track++) {
				for (int uNum = 0; uNum < GameConstants.UPGRADE_PATH_LENGTH; uNum++) {
					if (progress[track][uNum]) {
						baseAttributeList.upgrades[track][uNum].postSiphonUpgrade(this);
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
	public void upgrade(int track) {
		for (int i = 0; i < GameConstants.UPGRADE_PATH_LENGTH; i++) {
			if (!upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()][track][i]) {
				upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()][track][i] = true;
				baseAttributeList.upgrades[track][i].baseUpgrade(this);
				updateTowerChain();
				return;
			}
		}
	}
	
	/**
	 * 
	 * @param track - Each tower has two upgrade tracks, and this specifies whether it is the upper (0), or lower (1) track that we want to upgrade
	 * @param playerGold - This is the amount of gold that the player has. This is passed in to be more clear about what the boolean returned from this function will mean
	 * @return A boolean value specifying whether the given track can be upgraded both in terms of whether the upgrade is available, and if the player has enough gold
	 */
	public boolean canUpgrade(int track, int playerGold) {
		if (siphoningFrom == null || baseAttributeList.upgrades == null) {
			return false;
		} else {
			int sfType = siphoningFrom.baseAttributeList.downgradeType.ordinal();
			for (int i = 0; i < GameConstants.UPGRADE_PATH_LENGTH; i++) {
				if (!upgradeTracks[sfType][track][i]) {
					if (baseAttributeList.upgrades[track][i].baseCost <= playerGold) {
						return true;
					}
					return false;
				}
			}
			return false;
		}
	}
	
	//TODO: Should this be in the manager?
	void increaseSiphons(float modifier) {
		damageSiphon += modifier;
		damageSplashSiphon +=modifier;
		effectSplashSiphon +=modifier;
		radiusSplashSiphon +=modifier / 2;
		rangeSiphon +=modifier / 4;
		slowSiphon +=modifier;
		slowDurationSiphon +=modifier;
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
		return towerID; //TODO: Is this good enough?
	}

	
}
