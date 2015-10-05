package towers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import levels.Level;
import levels.ProjectileGuider;
import levels.Updatable;
import maps.Tile;
import creeps.DamageType;
import projectiles.*;
import utilities.Circle;
import utilities.GameConstants;
//TODO: Go through towers and make them final (?) and make all their fields private/protected depending on what I choose
//TODO: Make strong comments for each tower type and tower (they can be the same, just make it).
//TODO: Should the tower classes be final?
public abstract class Tower implements Updatable {
	//TODO: want to implement something like "quality" so that we can continually upgrade the base stats of a tower with gold (so towers don't cap out)
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
		updateTowerChain();
		System.out.println("Tower built at " + x + " , " + y + " (TOP LEFT TILE)");
	}
	
	public Projectile fireProjectile() {
		return duplicateProjectile(baseProjectile);
	}

	public void updateTowerChain() {
		adjustSiphonChain(this);
	}
	
	protected static void adjustSiphonChain(Tower t) {
		Tower current = t;
		while (current.siphoningFrom != null) {
			current = current.siphoningFrom;
		}
		BFSAdjust(current);
	}
	
	protected static void BFSAdjust(Tower root) {
		Queue<Tower> openList = new LinkedList<Tower>();
		root.adjustBaseStats();
		openList.addAll(root.siphoningTo);
		root.adjustMidSiphonUpgrades();
		Tower current;
		while (!openList.isEmpty()) {
			current = openList.poll();
			current.adjustBaseStats();
			openList.addAll(current.siphoningTo);
			current.siphon(current.siphoningFrom);
			current.adjustMidSiphonUpgrades();
		}
		openList.add(root);
		while (!openList.isEmpty()) {
			current = openList.poll();
			openList.addAll(current.siphoningTo);
			//TODO: current.adjustTalentStats();
			//order here matters, because some talents convert one damage to another, and so other multipliers might not work
			current.adjustPostSiphonUpgrades();
			current.adjustProjectileStats();
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
		this.range += from.range * this.baseAttributeList.rangeSiphon; //TODO: Decide if this is a good equation, do we want really short towers to pull down the range of others? Adds another dimension of complexity.
		this.attackCooldown -= (int) (this.baseAttributeList.attackCooldownSiphon - Math.sqrt(from.attackCooldown + this.baseAttributeList.attackCooldownSiphon)); //TODO: This value can currently be negative, is that wanted? Should really slow towers slow those around it?
		if (this.attackCooldown < 1) { this.attackCooldown = 1; }
		this.damageSplash += from.damageSplash * this.baseAttributeList.damageSplashSiphon;
		this.effectSplash += from.effectSplash * this.baseAttributeList.effectSplashSiphon;
		this.splashRadius += from.splashRadius * this.baseAttributeList.radiusSplashSiphon;
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
	
	private void adjustTalentStats() {
		
	}
	
	protected abstract void adjustProjectileStats();
	
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
		return towerID;
	}
}
