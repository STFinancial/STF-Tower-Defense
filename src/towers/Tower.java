package towers;

import java.util.HashMap;

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
	public TargetingType targetingType;
	public float targetX, targetY; //For ground spot target towers, in Tile coordinates
	public Creep targetCreep;
	public float targetAngle; //For animation and to pass to projectiles when fired, Radians, 0 = right, pi / 2 = up

	//Misc.
	public Tower siphoningFrom;
	public Tower siphoningTo;
	public Projectile baseProjectile;
	public boolean checked;
	
	//Upgrading Information
	public boolean[][][] upgradeTracks;
	
	
	//Base Attributes
	public BaseAttributeList baseAttributeList;
	
	//Current Attributes
	public int[] damageArray = new int[Constants.NUM_DAMAGE_TYPES];
	public float[] slowArray = new float[Constants.NUM_DAMAGE_TYPES];
	public int[] slowDurationArray = new int[Constants.NUM_DAMAGE_TYPES];
	public int fireRate; //Still deciding if this will actually be the same as attack cooldown
	public int attackCoolDown;
	public int currentAttackCoolDown; //Number of game ticks between tower shots, 0 for passive towers (beacons)
	public int snareDuration = 0;
	public float damageSplash;
	public float effectSplash;
	public float splashRadius;
	public float range;
	public boolean hitsAir;
	public boolean hitsGround;
	
	//Siphoned Attributes
	public int[] siphDamageArray = new int[Constants.NUM_DAMAGE_TYPES];
	public float[] siphSlowArray = new float[Constants.NUM_DAMAGE_TYPES];
	public int[] siphSlowDurationArray = new int[Constants.NUM_DAMAGE_TYPES];
	public int siphFireRate; //Still deciding if this will actually be the same as attack cooldown
	public int siphAttackCoolDown;
	public float siphDamageSplash;
	public float siphEffectSplash;
	public float siphSplashRadius;
	public float siphRange;

	//TODO targetsCreep should move to the baseAttributeList
	public Tower(Level level, Tile topLeftTile, TowerType type) {
		this.baseAttributeList = type.getAttributeList();
		this.upgradeTracks = new boolean[Constants.NUM_DAMAGE_TYPES][baseAttributeList.upgrades.length][baseAttributeList.upgrades[0].length];
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
		this.targetingType = TargetingType.FIRST;
		adjustTowerValues();
		System.out.println("Tower built at " + x + " , " + y + " (TOP LEFT TILE)");
	}
	
	public Projectile fireProjectile() {
		return duplicateProjectile(baseProjectile);
	}

	public void update() {
		currentAttackCoolDown--;
		if (baseAttributeList.targetsCreep) {
			targetCreep = level.findTargetCreep(this);
		}
		if (targetCreep != null) {
			updateAngle(targetCreep);
			if (currentAttackCoolDown < 1) {
				level.addProjectile(fireProjectile());
				currentAttackCoolDown = (attackCoolDown * siphAttackCoolDown) / (attackCoolDown * siphAttackCoolDown);
			}
		}
	}

	public void adjustTowerValues() {
		adjustBaseStats();
		adjustSiphonChain(this);
		adjustProjectile();
	}
	
	protected static void adjustSiphonChain(Tower t) {
		if (t.siphoningTo == null) {
			recursiveSiphon(t, 0);
		} else {
			Tower currentTower = t;
			int counter = 1;
			t.checked = true;
			while (currentTower.siphoningTo != null) {
				//while we are not at the head
				currentTower = currentTower.siphoningTo;
				//move back 1
				counter++;
				//we've seen one more
				if (currentTower.checked) {
					//if we've checked it before, we hit a cycle
					recursiveSiphon(t, counter);
				}
				//mark that we checked it and keep going
				currentTower.checked = true;
			}
			recursiveSiphon(currentTower, 0);
		}
	}
	
	private static void recursiveSiphon(Tower t, int cycleLength) {
		t.checked = false;
		//set the siphoned stats to 0
		for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
			t.siphDamageArray[i] = 0;
			t.siphSlowArray[i] = 0;
			t.siphSlowDurationArray[i] = 0;
		}
		t.siphRange = 0;
		t.siphFireRate = 0;
		t.siphAttackCoolDown = 0;
		t.siphDamageSplash = 0;
		t.siphEffectSplash = 0;
		t.siphSplashRadius = 0;
		
		if (cycleLength != 0) {
			//It's a cycle
			Tower currentTower = t;
			int counter = cycleLength;
			//Aggregate the stats we are giving out
			while (counter != 0) {
				currentTower.checked = false;
				for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
					t.siphDamageArray[i] += currentTower.damageArray[i];
					t.siphSlowArray[i] += currentTower.slowArray[i];
					t.siphSlowDurationArray[i] += currentTower.slowDurationArray[i];
				}
				t.siphRange += currentTower.range;
				t.siphFireRate += currentTower.fireRate;
				t.siphAttackCoolDown += currentTower.attackCoolDown;
				t.siphDamageSplash += currentTower.damageSplash;
				t.siphEffectSplash += currentTower.effectSplash;
				t.siphSplashRadius += currentTower.splashRadius;
				currentTower = currentTower.siphoningFrom;
				counter--;
			}
			//stats per tower (could also cycle through and subtract for each tower
			for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
				t.siphDamageArray[i] /= cycleLength;
				t.siphSlowArray[i] /= cycleLength;
				t.siphSlowDurationArray[i] /= cycleLength;
			}
			t.siphRange /= cycleLength;
			t.targetArea.radius = t.range + t.siphRange;
			t.siphFireRate /= cycleLength;
			t.siphAttackCoolDown /= cycleLength;
			t.siphDamageSplash /= cycleLength;
			t.siphEffectSplash /= cycleLength;
			t.siphSplashRadius /= cycleLength;
			
			counter = cycleLength - 1;
			currentTower = t.siphoningFrom;
			while (counter != 0) {
				for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
					currentTower.siphDamageArray[i] = t.siphDamageArray[i];
					currentTower.siphSlowArray[i] = t.siphSlowArray[i];
					currentTower.siphSlowDurationArray[i] = t.siphSlowDurationArray[i];
				}
				currentTower.siphRange = t.siphRange;
				currentTower.targetArea.radius = currentTower.range + currentTower.siphRange;
				currentTower.siphFireRate = t.siphFireRate;
				currentTower.siphAttackCoolDown = t.siphAttackCoolDown;
				currentTower.siphDamageSplash = t.siphDamageSplash;
				currentTower.siphEffectSplash = t.siphEffectSplash;
				currentTower.siphSplashRadius = t.siphSplashRadius;
				currentTower = currentTower.siphoningFrom;
				counter--;
			}
		} else {
			Tower sf = t.siphoningFrom;
			//we are not in a cycle and we can just recurse normally
			if (sf == null) {
				return;
			}
			recursiveSiphon(sf, 0);
			for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
				t.siphDamageArray[i] = (int) ((sf.damageArray[i] + sf.siphDamageArray[i]) * Constants.SIPHON_BONUS_MODIFIER);
				t.siphSlowArray[i] = (sf.slowArray[i] + sf.siphSlowArray[i]) * Constants.SIPHON_BONUS_MODIFIER;
				t.siphSlowDurationArray[i] = (int) ((sf.slowDurationArray[i] + sf.siphSlowDurationArray[i]) * Constants.SIPHON_BONUS_MODIFIER);
			}
			t.siphRange = (sf.range + sf.siphRange) * Constants.SIPHON_BONUS_MODIFIER;
			t.targetArea.radius = t.range + t.siphRange;
			t.siphFireRate = (int) ((sf.fireRate + sf.siphFireRate) * Constants.SIPHON_BONUS_MODIFIER);
			t.siphAttackCoolDown = (int) ((sf.attackCoolDown + sf.siphAttackCoolDown) * Constants.SIPHON_BONUS_MODIFIER);
			t.siphDamageSplash = (sf.damageSplash + sf.siphDamageSplash) * Constants.SIPHON_BONUS_MODIFIER;
			t.siphEffectSplash = (sf.effectSplash + sf.siphEffectSplash) * Constants.SIPHON_BONUS_MODIFIER;
			t.siphSplashRadius = (sf.splashRadius + sf.siphSplashRadius) * Constants.SIPHON_BONUS_MODIFIER;
		}
	}
	
	
	protected void adjustBaseStats() {
		slowArray[baseAttributeList.mainDamageType.ordinal()] = baseAttributeList.baseSlow;
		damageArray[baseAttributeList.mainDamageType.ordinal()] = baseAttributeList.baseElementalDamage;
		damageArray[Constants.NUM_DAMAGE_TYPES - 1] = baseAttributeList.basePhysicalDamage;
		slowDurationArray[baseAttributeList.mainDamageType.ordinal()] = baseAttributeList.baseSlowDuration;
		fireRate = baseAttributeList.baseFireRate;
		attackCoolDown = baseAttributeList.baseAttackCoolDown;
		damageSplash = baseAttributeList.baseDamageSplash;
		effectSplash = baseAttributeList.baseEffectSplash;
		splashRadius = baseAttributeList.baseSplashRadius;
		range = baseAttributeList.baseRange;
		//TODO handle talents
		//apply all the upgrades that we have purchased
		if (siphoningFrom != null) {
			boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
			for (int track = 0; track < baseAttributeList.upgrades.length; track++) {
				for (int uNum = 0; uNum < baseAttributeList.upgrades[track].length; uNum++) {
					if (progress[track][uNum]) {
						 baseAttributeList.upgrades[track][uNum].upgrade(this);
					}
				}
			}
		}
		
	}
	
	//this should be called on any time we make changes to the tower
	protected void adjustProjectile() {
		baseProjectile = new Projectile(this);
		DamageType damageType = baseAttributeList.mainDamageType;
		ProjectileEffect effect;
		baseProjectile.splashRadius = splashRadius;
		for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
			if (damageArray[i] + siphDamageArray[i] != 0) {
				effect = new Damage(damageArray[i] + siphDamageArray[i], DamageType.values()[i]);
				baseProjectile.addEffect(effect);
				if (damageSplash + siphDamageSplash != 0) {
					effect = new Damage((damageArray[i] + siphDamageArray[i]) * (damageSplash + siphDamageSplash), DamageType.values()[i]);
					baseProjectile.addSplashEffect(effect);
				}
			}
			if (slowArray[i] + siphSlowArray[i] != 0) {
				effect = new Slow(slowDurationArray[i] + siphSlowDurationArray[i], slowArray[i] + siphSlowArray[i], DamageType.values()[i]);
				baseProjectile.addEffect(effect);
				if (effectSplash + siphEffectSplash != 0) {
					effect = new Slow((slowDurationArray[i] + siphSlowDurationArray[i])/ 2, (slowArray[i] + siphSlowArray[i]) * (effectSplash + siphEffectSplash), DamageType.values()[i]);
					baseProjectile.addSplashEffect(effect);
				}
			}
		}
		//TODO this line will probably disappear when unique effects are added
		if (snareDuration != 0) {
			effect = new Snare(snareDuration, 0, damageType);
			baseProjectile.addEffect(effect);
		}
		//Set the speed
		baseProjectile.currentSpeed = baseProjectile.speed = .20f;
	}
	

	//TODO Upgrades
	/**
	 * 
	 * @param path - 1 or 0 depending on the upgrade path
	 */
	public void upgrade(int track) {
		for (int i = 0; i < upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()][track].length; i++) {
			if (!upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()][track][i]) {
				upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()][track][i] = true;
				baseAttributeList.upgrades[track][i].upgrade(this);
				return;
			}
		}
	}
	
	public boolean canUpgrade(int track) {
		//TODO something to handle cost
		if (siphoningFrom == null || baseAttributeList.upgrades == null) {
			return false;
		} else {
			return upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()][track][baseAttributeList.upgrades[0].length];
		}
	}

	protected void updateAngle(Creep targetCreep) {
		targetAngle = TrigHelper.angleBetween(centerX, centerY, targetCreep.hitBox.x, targetCreep.hitBox.y);
	}

	protected Projectile duplicateProjectile(Projectile p) {
		Projectile newProj = new Projectile(this);
		newProj.effects.addAll(p.effects);
		newProj.splashEffects.addAll(p.splashEffects);
		newProj.currentSpeed = newProj.speed = p.speed; //TODO this logic might need to change if we have projectiles that speed up
		newProj.splashRadius = p.splashRadius;
		newProj.parent = this;
		return newProj;
	}

	public String toString() {
		return "Type: " + type + " at " + x + " , " + y;
	}
}
