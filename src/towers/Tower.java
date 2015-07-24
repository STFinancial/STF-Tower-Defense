package towers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import levels.Level;
import maps.Tile;
import creeps.Creep;
import creeps.DamageType;
import projectiles.*;
import utilities.Circle;
import utilities.Constants;
import utilities.TrigHelper;

public class Tower {
	//Positional Details
	protected Level level;
	public int x, y; //Top Left corner in Tile Coordinates
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
	public Circle placeToTarget;
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
//	public int snareDuration = 0;
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
		this.baseAttributeList = type.getAttributeList();
		this.upgradeTracks = new boolean[Constants.NUM_DAMAGE_TYPES][Constants.NUM_UPGRADE_PATHS][Constants.UPGRADE_PATH_LENGTH];
		this.level = level;
		this.width = baseAttributeList.baseWidth;
		this.height = baseAttributeList.baseHeight;
		this.type = baseAttributeList.type;
		this.cost = baseAttributeList.baseCost;
		this.type = baseAttributeList.type;
		this.x = topLeftTile.x;
		this.y = topLeftTile.y;
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

	public void update() {
		currentAttackCoolDown--;
		targetCreep = level.findTargetCreep(this);
//		if (baseAttributeList.targetsCreep) {
//			targetCreep = level.findTargetCreep(this);
//		} else {
//			targetCreep = level.findTargetCreep(this);
//		}
		if (targetCreep != null) {
			updateAngle(targetCreep);
			if (currentAttackCoolDown < 1) {
				level.addProjectile(fireProjectile());
				attackCarryOver += 1 - currentAttackCoolDown;
				currentAttackCoolDown = attackCoolDown;
				if (attackCarryOver > 1) {
					attackCarryOver -= 1;
					currentAttackCoolDown--;
				}
			}
		}
	}
	
	public void evolve(Tower source) {
		type = TowerType.getUpgrade(source.type, type);
		baseAttributeList = type.getAttributeList();
		updateTowerChain();
	}
	
	public void devolve() {
		type = baseAttributeList.downgradeType;
		baseAttributeList = type.getAttributeList();
		updateTowerChain();
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
	
	private static void BFSAdjust(Tower root) {
		Queue<Tower> openList = new LinkedList<Tower>();
		openList.addAll(root.siphoningTo);
		Tower current;
		Tower sf;
		while (!openList.isEmpty()) {
			current = openList.poll();
			current.adjustBaseStats();
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
			current.adjustUpgradeStats();
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
	
	private void adjustUpgradeStats() {
		if (siphoningFrom != null) {
			boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
			for (int track = 0; track < Constants.NUM_UPGRADE_PATHS; track++) {
				for (int uNum = 0; uNum < Constants.UPGRADE_PATH_LENGTH; uNum++) {
					if (progress[track][uNum]) {
						 baseAttributeList.upgrades[track][uNum].upgrade(this);
					}
				}
			}
		}
	}
	
	private void adjustTalentStats() {
		
	}
	
	//this should be called on any time we make changes to the tower
	private void adjustProjectileStats() {
		baseProjectile = new Projectile(this);
		baseProjectile.targetingType  = baseAttributeList.targetingType;
		baseProjectile.collisionType  = baseAttributeList.collisionType;
		baseProjectile.travelType 	  = baseAttributeList.travelType;
		DamageType damageType 		  = baseAttributeList.mainDamageType;
		baseProjectile.splashRadius   = splashRadius;
		ProjectileEffect effect;
		
		for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
			if (damageArray[i] != 0) {
				effect = new Damage(damageArray[i], DamageType.values()[i]);
				baseProjectile.addEffect(effect);
				if (damageSplash != 0) {
					effect = new Damage(damageArray[i] * damageSplash, DamageType.values()[i]);
					baseProjectile.addSplashEffect(effect);
				}
			}
			if (slowArray[i] != 0) {
				effect = new Slow(slowDurationArray[i], slowArray[i], DamageType.values()[i]);
				baseProjectile.addEffect(effect);
				if (effectSplash != 0) {
					effect = new Slow(slowDurationArray[i], slowArray[i] * effectSplash, DamageType.values()[i]);
					baseProjectile.addSplashEffect(effect);
				}
			}
		}
		//TODO this line will probably disappear when unique effects are added
//		if (snareDuration != 0) {
//			effect = new Snare(snareDuration, 0, damageType);
//			baseProjectile.addEffect(effect);
//		}
		//TODO this might change in baseattributelist
		//Set the speed
		baseProjectile.currentSpeed = baseProjectile.speed = .20f;
	}
	
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
	
	public void setTargetArea(float y, float x) {
		placeToTarget = new Circle(x, y, splashRadius);
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
