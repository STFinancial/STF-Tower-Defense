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
	
	//Base Attributes
	public int[] baseDamageArray = new int[Constants.NUM_DAMAGE_TYPES];
	public float[] baseSlowArray = new float[Constants.NUM_DAMAGE_TYPES];
	public int[] baseSlowDurationArray = new int[Constants.NUM_DAMAGE_TYPES];
	public int baseFireRate;
	public int baseAttackCoolDown;
	public float baseDamageSplash;
	public float baseEffectSplash;
	public float baseSplashRadius;
	public float baseRange;
	
	//Current Attributes
	public TowerType type;
	public String name;
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

	abstract Projectile fireProjectile();

	public Tower(Level level, Tile topLeftTile, boolean targetsCreep, int width, int height) {
		this.level = level;
		this.width = width;
		this.height = height;
		x = topLeftTile.x;
		y = topLeftTile.y;
		centerX = x + width / 2f;
		centerY = y + height / 2f;
		targetArea = new Circle(centerX, centerY, range);
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
		adjustStats();
		adjustProjectile();
	}
	
	protected void adjustStats() {
		//TODO deal with tower upgrades somehow
		for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
			slowArray[i] = baseSlowArray[i];
			damageArray[i] = baseDamageArray[i];
			slowDurationArray[i] = baseSlowDurationArray[i];
		}
		fireRate = baseFireRate;
		attackCoolDown = baseAttackCoolDown;
		damageSplash = baseDamageSplash;
		effectSplash = baseEffectSplash;
		splashRadius = baseSplashRadius;
		range = baseRange;
		//TODO recursively handle siphon damage
	}
	
	//this should be called on any time we make changes to the tower
	protected void adjustProjectile() {
		baseProjectile = new Projectile(this);
		DamageType damageType = type.getDamageType();
		ProjectileEffect effect;
		//TODO Optimize
		for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
			if (damageArray[i] != 0) {
				effect = new Damage(damageArray[i], DamageType.values()[i]);
				baseProjectile.addEffect(effect);
				if (splashRadius != 0) {
					effect = new Damage(damageArray[i] * damageSplash, DamageType.values()[i]);
					baseProjectile.addSplashEffect(effect);
				}
			}
			if (slowDurationArray[i] != 0) {
				effect = new Slow(slowDurationArray[i], slowArray[i], DamageType.values()[i]);
				baseProjectile.addEffect(effect);
				if (splashRadius != 0) {
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
			if (splashRadius != 0) {
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
