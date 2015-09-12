package towers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import levels.Level;
import levels.Updatable;
import maps.Tile;
import creeps.Creep;
import creeps.DamageType;
import projectiles.*;
import utilities.Circle;
import utilities.GameConstants;
import utilities.TrigHelper;

public abstract class Tower implements Updatable {
	//TODO: want to implement something like "quality" so that we can continually upgrade the base stats of a tower with gold (so towers don't cap out)
	//Positional Details
	public Level level;
	public int x, y; //Top Left corner in Tile Coordinates
	public Tile topLeft;
	public float centerX, centerY;
	public Circle targetArea;
	public int width;
	public int height;
	public int cost;
	public TowerType type;

	//Targeting Details
	public TargetingModeType targetingType;
	public float targetX, targetY; //For ground spot target towers, in Tile coordinates
//	public Creep targetCreep;
//	public float targetAngle; //For animation and to pass to projectiles when fired, Radians, 0 = right, pi / 2 = up

	//Misc.
	public int towerID;
	public Tower siphoningFrom;
	public ArrayList<Tower> siphoningTo;
	public Projectile baseProjectile;
	
	//Upgrading Information
	public boolean[][][] upgradeTracks;
	
	
	//Base Attributes
	public BaseAttributeList baseAttributeList;
	
	//Current Attributes
	public float[] damageArray = new float[GameConstants.NUM_DAMAGE_TYPES];
	public float[] slowArray = new float[GameConstants.NUM_DAMAGE_TYPES];
	public int[] slowDurationArray = new int[GameConstants.NUM_DAMAGE_TYPES];
	public float attackCoolDown;
	public float currentAttackCoolDown; //Number of game ticks between tower shots, 0 for passive towers (beacons)
	public float attackCarryOver;
	public float damageSplash;
	public float effectSplash;
	public float splashRadius;
	public float range;
	public float siphonBonus;
	public boolean hitsAir;
	public boolean hitsGround;
	
	//Siphoned Attributes
//	public int[] siphDamageArray = new int[Constants.NUM_DAMAGE_TYPES];
//	public float[] siphSlowArray = new float[Constants.NUM_DAMAGE_TYPES];
//	public int[] siphSlowDurationArray = new int[Constants.NUM_DAMAGE_TYPES];
//	public float siphAttackCoolDown = 0;
//	public float siphDamageSplash = 0;
//	public float siphEffectSplash = 0;
//	public float siphSplashRadius = 0;
//	public float siphRange = 0;

	
	public Tower(Level level, Tile topLeftTile, TowerType type, int towerID) {
		//TODO since I changed the way base stats are modified I can remove this clone I think
		this.baseAttributeList = type.getAttributeList().clone();
		this.upgradeTracks = new boolean[GameConstants.NUM_DAMAGE_TYPES][GameConstants.NUM_UPGRADE_PATHS][GameConstants.UPGRADE_PATH_LENGTH];
		this.level = level;
		this.width = baseAttributeList.baseWidth;
		this.height = baseAttributeList.baseHeight;
		this.type = baseAttributeList.type;
		this.cost = baseAttributeList.baseCost;
		this.type = baseAttributeList.type;
		this.x = topLeftTile.x;
		this.y = topLeftTile.y;
		this.topLeft = topLeftTile;
		this.centerX = x + width / 2f;
		this.centerY = y + height / 2f;
		this.targetArea = new Circle(centerX, centerY, range);
		this.targetingType = TargetingModeType.FIRST;
		this.attackCarryOver = 0f;
		this.towerID = towerID;
		this.siphoningTo = new ArrayList<Tower>();
		this.siphonBonus = GameConstants.SIPHON_BONUS_MODIFIER;
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
		openList.addAll(root.siphoningTo);
		Tower current;
		while (!openList.isEmpty()) {
			current = openList.poll();
			current.adjustBaseStats();
			current.adjustBaseUpgradeStats();
			openList.addAll(current.siphoningTo);
			current.siphon(current.siphoningFrom);
			current.adjustTalentStats();
			//order here matters, because some talents convert one damage to another, and so other multipliers might not work
			current.adjustNonBaseUpgradeStats();
			current.adjustProjectileStats();
			current.currentAttackCoolDown = current.attackCoolDown;
			current.targetArea.radius = current.range;
		}
	}
	
	//TODO: technically we don't need to pass this as a parameter
	protected void siphon(Tower from) {
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			this.damageArray[i] += (int) (from.damageArray[i] * this.siphonBonus);
			this.slowArray[i] += from.slowArray[i] * this.siphonBonus;
			this.slowDurationArray[i] += (int) ((from.slowDurationArray[i] * this.siphonBonus) / 2);
		}
		//TODO find a good equation for range siphoning
		this.range = Math.max(this.range, (this.range + from.range) / 2);//should I really max this?
		this.targetArea.radius = this.range;
		
		//TODO find a good equation for fire rate siphoning
		this.attackCoolDown = (int) ((from.attackCoolDown + this.attackCoolDown) / 2);
		this.damageSplash += from.damageSplash * this.siphonBonus;
		this.effectSplash += from.effectSplash * this.siphonBonus;
		this.splashRadius += from.splashRadius * this.siphonBonus;
	}
	
	protected void adjustBaseStats() {
		slowArray[baseAttributeList.mainDamageType.ordinal()] 			= baseAttributeList.baseSlow;
		damageArray 													= baseAttributeList.baseDamageArray;
		slowDurationArray[baseAttributeList.mainDamageType.ordinal()] 	= baseAttributeList.baseSlowDuration;
		attackCoolDown 													= baseAttributeList.baseAttackCoolDown;
		damageSplash 													= baseAttributeList.baseDamageSplash;
		effectSplash													= baseAttributeList.baseEffectSplash;
		splashRadius													= baseAttributeList.baseSplashRadius;
		range 															= baseAttributeList.baseRange;
		hitsAir															= baseAttributeList.hitsAir;
		hitsGround														= baseAttributeList.hitsGround;
		attackCarryOver													= 0;
		currentAttackCoolDown											= 0;
	}
	
	private void adjustBaseUpgradeStats() {
		if (siphoningFrom != null) {
			boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
			for (int track = 0; track < GameConstants.NUM_UPGRADE_PATHS; track++) {
				for (int uNum = 0; uNum < GameConstants.UPGRADE_PATH_LENGTH; uNum++) {
					if (progress[track][uNum] && baseAttributeList.upgrades[track][uNum].isBase) {
						 baseAttributeList.upgrades[track][uNum].upgrade(this);
					}
				}
			}
		}
	}
	
	private void adjustNonBaseUpgradeStats() {
		if (siphoningFrom != null) {
			boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
			for (int track = 0; track < GameConstants.NUM_UPGRADE_PATHS; track++) {
				for (int uNum = 0; uNum < GameConstants.UPGRADE_PATH_LENGTH; uNum++) {
					if (progress[track][uNum] && !baseAttributeList.upgrades[track][uNum].isBase) {
						 baseAttributeList.upgrades[track][uNum].upgrade(this);
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
		s.append("Attack Cooldown: " + attackCoolDown);
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
