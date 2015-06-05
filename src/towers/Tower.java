package towers;

import levels.Level;
import maps.Tile;
import creeps.Creep;
import creeps.DamageType;
import projectiles.*;
import utilities.Circle;
import utilities.Constants;
import utilities.TrigHelper;

public abstract class Tower {
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
	public boolean targetsCreep; //False for targeting a ground spot;
	public float targetX, targetY; //For ground spot target towers, in Tile coordinates
	public Creep targetCreep;
	public float targetAngle; //For animation and to pass to projectiles when fired, Radians, 0 = right, pi / 2 = up

	//Misc.
	public Tower siphoningFrom;
	public Tower siphoningTo;
	public Projectile baseProjectile;
	public boolean checked;
	public static Tower cycleEnd;
	
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
	//TODO do I want to siphon snare or "unique" effects?
	public int[] siphDamageArray = new int[Constants.NUM_DAMAGE_TYPES];
	public float[] siphSlowArray = new float[Constants.NUM_DAMAGE_TYPES];
	public int[] siphSlowDurationArray = new int[Constants.NUM_DAMAGE_TYPES];
	public int siphFireRate; //Still deciding if this will actually be the same as attack cooldown
	public int siphAttackCoolDown;
	public float siphDamageSplash;
	public float siphEffectSplash;
	public float siphSplashRadius;
	public float siphRange;

	public Tower(Level level, Tile topLeftTile, boolean targetsCreep, BaseAttributeList baseAttributeList) {
		this.baseAttributeList = baseAttributeList;
		this.level = level;
		this.width = baseAttributeList.baseWidth;
		this.height = baseAttributeList.baseHeight;
		this.type = baseAttributeList.type;
		this.cost = baseAttributeList.baseCost;
		this.x = topLeftTile.x;
		this.y = topLeftTile.y;
		this.centerX = x + width / 2f;
		this.centerY = y + height / 2f;
		this.targetArea = new Circle(centerX, centerY, range);
		this.targetsCreep = targetsCreep;
		this.targetingType = TargetingType.FIRST;
		System.out.println("Tower built at " + x + " , " + y + " (TOP LEFT TILE)");
	}
	
	public Projectile fireProjectile() {
		return duplicateProjectile(baseProjectile);
	}

	public void update() {
		currentAttackCoolDown--;
		if (targetsCreep) {
			targetCreep = level.findTargetCreep(this);
		}
		if (targetCreep != null) {
			updateAngle(targetCreep);
			if (currentAttackCoolDown < 1) {
				level.addProjectile(fireProjectile());
				currentAttackCoolDown = attackCoolDown;
			}
		}
	}

	public void adjustTowerValues() {
		cycleEnd = null;
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
		if (cycleLength != 0) {
			Tower currentTower = t;
			int counter = cycleLength;
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
			for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
				t.siphDamageArray[i] /= cycleLength;
				t.siphSlowArray[i] /= cycleLength;
				t.siphSlowDurationArray[i] /= cycleLength;
			}
			t.siphRange /= cycleLength;
			t.siphFireRate /= cycleLength;
			t.siphAttackCoolDown /= cycleLength;
			t.siphDamageSplash /= cycleLength;
			t.siphEffectSplash /= cycleLength;
			t.siphSplashRadius /= cycleLength;
			
			counter = cycleLength - 1;
			currentTower = t.siphoningFrom;
			while (counter != 0) {
				for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
					currentTower.siphDamageArray[i] += t.damageArray[i];
					currentTower.siphSlowArray[i] += t.slowArray[i];
					currentTower.siphSlowDurationArray[i] += t.slowDurationArray[i];
				}
				currentTower.siphRange += t.range;
				currentTower.siphFireRate += t.fireRate;
				currentTower.siphAttackCoolDown += t.attackCoolDown;
				currentTower.siphDamageSplash += t.damageSplash;
				currentTower.siphEffectSplash += t.effectSplash;
				currentTower.siphSplashRadius += t.splashRadius;
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
			t.siphFireRate = (int) ((sf.fireRate + sf.siphFireRate) * Constants.SIPHON_BONUS_MODIFIER);
			t.siphAttackCoolDown = (int) ((sf.attackCoolDown + sf.siphAttackCoolDown) * Constants.SIPHON_BONUS_MODIFIER);
			t.siphDamageSplash = (sf.damageSplash + sf.siphDamageSplash) * Constants.SIPHON_BONUS_MODIFIER;
			t.siphEffectSplash = (sf.effectSplash + sf.siphEffectSplash) * Constants.SIPHON_BONUS_MODIFIER;
			t.siphSplashRadius = (sf.splashRadius + sf.siphSplashRadius) * Constants.SIPHON_BONUS_MODIFIER;
		}
	}
	
	
	protected void adjustBaseStats() {
		//TODO deal with tower upgrades somehow
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
		//TODO handle talents and upgrades
		
	}
	
	//this should be called on any time we make changes to the tower
	//TODO factor in siphoned attributes into the projectile
	protected void adjustProjectile() {
		baseProjectile = new Projectile(this);
		DamageType damageType = baseAttributeList.mainDamageType;
		ProjectileEffect effect;
		baseProjectile.splashRadius = splashRadius;
		//TODO Optimize - At this point the base classes only have one damage type
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
					effect = new Slow(slowDurationArray[i] / 2, slowArray[i] * effectSplash, DamageType.values()[i]);
					baseProjectile.addSplashEffect(effect);
				}
			}
		}
		//Snare Effects
		if (snareDuration != 0) {
			effect = new Snare(snareDuration, 0, damageType);
			baseProjectile.addEffect(effect);
			
			//TODO do we really want snare to splash?
			//Maybe some types of snares? (short)
			if (effectSplash != 0) {
				effect = new Snare(snareDuration / 4, 0, damageType);
				baseProjectile.addSplashEffect(effect);
			}
		}
		//Set the speed
		baseProjectile.currentSpeed = baseProjectile.speed = .20f;
	}

	protected void updateAngle(Creep targetCreep) {
		targetAngle = TrigHelper.angleBetween(centerX, centerY, targetCreep.hitBox.x, targetCreep.hitBox.y);
	}

	protected Projectile duplicateProjectile(Projectile p) {
		Projectile newProj = new Projectile(this);
		//TODO for now we are just going to reference the effects of the baseProjecile
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
