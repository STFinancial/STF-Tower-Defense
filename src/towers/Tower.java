package towers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import levels.Level;
import levels.Updatable;
import maps.Tile;
import creeps.Creep;
import projectiles.*;
import utilities.Circle;
import utilities.Constants;
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
	public Creep targetCreep;
	public float targetAngle; //For animation and to pass to projectiles when fired, Radians, 0 = right, pi / 2 = up

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
	public int[] damageArray = new int[Constants.NUM_DAMAGE_TYPES];
	public float[] slowArray = new float[Constants.NUM_DAMAGE_TYPES];
	public int[] slowDurationArray = new int[Constants.NUM_DAMAGE_TYPES];
	public float fireRate; //Still deciding if this will actually be the same as attack cooldown
	public float attackCoolDown;
	public float currentAttackCoolDown; //Number of game ticks between tower shots, 0 for passive towers (beacons)
	public float attackCarryOver;
	public float damageSplash;
	public float effectSplash;
	public float splashRadius;
	public float range;
	public boolean hitsAir;
	public boolean hitsGround;
	
	//Siphoned Attributes
//	public int[] siphDamageArray = new int[Constants.NUM_DAMAGE_TYPES];
//	public float[] siphSlowArray = new float[Constants.NUM_DAMAGE_TYPES];
//	public int[] siphSlowDurationArray = new int[Constants.NUM_DAMAGE_TYPES];
//	public float siphFireRate = 0; //Still deciding if this will actually be the same as attack cooldown
//	public float siphAttackCoolDown = 0;
//	public float siphDamageSplash = 0;
//	public float siphEffectSplash = 0;
//	public float siphSplashRadius = 0;
//	public float siphRange = 0;

	
	public Tower(Level level, Tile topLeftTile, TowerType type, int towerID) {
		//TODO since I changed the way base stats are modified I can remove this clone I think
		this.baseAttributeList = type.getAttributeList().clone();
		this.upgradeTracks = new boolean[Constants.NUM_DAMAGE_TYPES][Constants.NUM_UPGRADE_PATHS][Constants.UPGRADE_PATH_LENGTH];
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
		updateTowerChain();
		System.out.println("Tower built at " + x + " , " + y + " (TOP LEFT TILE)");
	}
	
	public Projectile fireProjectile() {
		return duplicateProjectile(baseProjectile);
	}

	public abstract void update();
	
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
	
	private static void BFSAdjust(Tower root) {
		Queue<Tower> openList = new LinkedList<Tower>();
		openList.addAll(root.siphoningTo);
		Tower current;
		Tower sf;
		while (!openList.isEmpty()) {
			current = openList.poll();
			current.adjustBaseStats();
			current.adjustBaseUpgradeStats();
			openList.addAll(current.siphoningTo);
			sf = current.siphoningFrom;
			for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
				current.damageArray[i] += (int) (sf.damageArray[i] * Constants.SIPHON_BONUS_MODIFIER);
				current.slowArray[i] += sf.slowArray[i] * Constants.SIPHON_BONUS_MODIFIER;
				current.slowDurationArray[i] += (int) ((sf.slowDurationArray[i] * Constants.SIPHON_BONUS_MODIFIER) / 2);
			}
			//TODO find a good equation for range siphoning
			current.range = Math.max(current.range, (current.range + sf.range) / 2);//should I really max this?
			current.targetArea.radius = current.range;
			
			//TODO find a good equation for fire rate siphoning
			current.fireRate = (int) ((sf.fireRate + current.fireRate) / 2);
			current.damageSplash += sf.damageSplash * Constants.SIPHON_BONUS_MODIFIER;
			current.effectSplash += sf.effectSplash * Constants.SIPHON_BONUS_MODIFIER;
			current.splashRadius += sf.splashRadius * Constants.SIPHON_BONUS_MODIFIER;
			current.adjustTalentStats();
			current.adjustNonBaseUpgradeStats();
			current.adjustProjectileStats();
		}
	}
	
	protected void adjustBaseStats() {
		slowArray[baseAttributeList.mainDamageType.ordinal()] 			= baseAttributeList.baseSlow;
		damageArray[baseAttributeList.mainDamageType.ordinal()] 		= baseAttributeList.baseElementalDamage;
		damageArray[Constants.NUM_DAMAGE_TYPES - 1] 					= baseAttributeList.basePhysicalDamage;
		slowDurationArray[baseAttributeList.mainDamageType.ordinal()] 	= baseAttributeList.baseSlowDuration;
		fireRate 														= baseAttributeList.baseFireRate;
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
			for (int track = 0; track < Constants.NUM_UPGRADE_PATHS; track++) {
				for (int uNum = 0; uNum < Constants.UPGRADE_PATH_LENGTH; uNum++) {
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
			for (int track = 0; track < Constants.NUM_UPGRADE_PATHS; track++) {
				for (int uNum = 0; uNum < Constants.UPGRADE_PATH_LENGTH; uNum++) {
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
		for (int i = 0; i < Constants.UPGRADE_PATH_LENGTH; i++) {
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
			for (int i = 0; i < Constants.UPGRADE_PATH_LENGTH; i++) {
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
	
	protected void updateAngle(Creep targetCreep) {
		targetAngle = TrigHelper.angleBetween(centerX, centerY, targetCreep.hitBox.x, targetCreep.hitBox.y);
	}

	protected Projectile duplicateProjectile(Projectile p) {
		return p.clone();
	}

	public String toString() {
		return "Type: " + type + " at " + x + " , " + y;
	}
	
	public boolean equals(Tower t) {
		return t.towerID == towerID;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Tower)) {
			return false;
		}
        Tower t = (Tower) o;
        return t.towerID == towerID;
	}
}
