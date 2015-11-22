package projectiles;

import java.util.ArrayList;

import projectileeffects.Damage;
import projectileeffects.ProjectileEffect;
import projectileeffects.Slow;

import towers.Tower;
import towers.TowerManager;
import utilities.Circle;
import utilities.GameConstants;
import utilities.TrigHelper;
import creeps.AffixModifier;
import creeps.Creep;
import creeps.CreepManager;
import creeps.DamageType;
import game.GameObject;
import levels.LevelManager;
/*
 * Unit that is fired from a tower, contains information such as position/velocity, target area or target creep
 * as well as tower that fired the projectile. lastly contains the projectile effect which happens when the projectile lands or times out
 * PERHAPS an additional item for stuff like points (bullets) vs rings (aoe circle) vs cones (Flamethrower), or seperate class?
 * Also possibly a second class for passive boost from towers like attack speed in radius
 */
public abstract class Projectile extends GameObject {
	protected Tower parent;
	protected float x, y;
	protected float currentSpeed, speed;
	protected float size;
	protected float splashRadius;
	protected Circle hitBox;
	protected float[] resistPenPercent;
	protected float[] resistPenFlat;
	protected float toughPenPercent;
	protected float toughPenFlat;
	protected float shieldDrainModifier; //TODO: The value that this should be set at is a bit unclear.
	protected boolean ignoresShield;
	protected boolean hitsAir;
	protected boolean doesSplash;
	protected boolean doesSlow;
	protected boolean doesOnHit;
	protected boolean splashHitsAir;
	protected static ProjectileManager projManager = ProjectileManager.getInstance();
	protected static TowerManager towerManager = TowerManager.getInstance();
	protected static CreepManager creepManager = CreepManager.getInstance();
	protected static LevelManager levelManager = LevelManager.getInstance();
	
	protected boolean dud; //When creep is killed by something else or escapes before contact;
	protected double targetAngle; //For animation and to pass to projectiles when fired, Degrees, 0 = right, 90 = up
	protected double angleCos;
	protected double angleSin;
	
	protected Circle targetArea;
	protected Creep targetCreep;
	
	protected ArrayList<ProjectileEffect> creepEffects;
	protected ArrayList<ProjectileEffect> splashEffects;
	protected AffixModifier multiplier;
	
	/*
	 * Cloning Constructor
	 */
	protected Projectile(Tower parent, Projectile mold) {
		initializeStats(parent);
		mold.cloneStats(this);
	}
	
	protected Projectile(Tower parent) {
		initializeStats(parent);
		addGeneralEffects();
	}
	
	//TODO: This method is pretty questionable.
	private void addGeneralEffects() {
		DamageType[] types = DamageType.values();
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			if (towerManager.getDamage(parent, types[i]) != 0) {
				creepEffects.add(new Damage(towerManager.getDamage(parent, types[i]), types[i], this));
				if (doesSplash && towerManager.getDamageSplash(parent) != 0 && towerManager.getSplashRadius(parent) != 0) {
					splashEffects.add(new Damage(towerManager.getDamage(parent, types[i]) * towerManager.getDamageSplash(parent), types[i], this));
				}
			}
			float slow;
			if (doesSlow) {
				if ((slow = towerManager.getSlow(parent, types[i])) != 0) {
					if (slow >= 1) { slow = 0.99f; }
					creepEffects.add(new Slow(towerManager.getSlowDuration(parent, types[i]), slow, types[i], this));
					if (doesSplash && towerManager.getEffectSplash(parent) != 0 && towerManager.getSplashRadius(parent) != 0) {
						splashEffects.add(new Slow(towerManager.getSlowDuration(parent, types[i]), slow * towerManager.getEffectSplash(parent), types[i], this));
					}
				}
			}
			
		}
		//TODO this might change in baseattributelist... also we might change the size somewhere
		currentSpeed = speed = .20f;
	}
	
	//TODO: Could do a method here to take care of checking for onhit, hitsAir, etc. rather than having each individual method do it
	
	protected void initializeStats(Tower parent) {
		this.dud 					= false;
		this.parent					= parent;
		this.x 						= towerManager.getCenterX(parent);
		this.y 						= towerManager.getCenterY(parent);
		this.splashRadius		 	= towerManager.getSplashRadius(parent);
		this.hitsAir				= towerManager.hitsAir(parent);
		this.doesSplash				= towerManager.doesSplash(parent);
		this.doesSlow				= towerManager.doesSlow(parent);
		this.doesOnHit				= towerManager.doesOnHit(parent);
		this.splashHitsAir			= towerManager.splashHitsAir(parent);
		this.targetCreep			= null;
		this.targetArea				= null;
		this.angleCos				= 0;
		this.angleSin				= 0;
		this.targetAngle			= 0;
		
		this.size 					= 0.01f;
		this.toughPenPercent		= 0;
		this.toughPenFlat			= 0;
		this.shieldDrainModifier 	= 1;
		
		this.hitBox 				= new Circle(x, y, size);
		this.resistPenPercent 		= new float[GameConstants.NUM_DAMAGE_TYPES];
		this.resistPenFlat 			= new float[GameConstants.NUM_DAMAGE_TYPES];
		this.creepEffects 			= new ArrayList<ProjectileEffect>();
		this.splashEffects 			= new ArrayList<ProjectileEffect>();
		this.multiplier 			= new AffixModifier();
	}
	
	protected void cloneStats(Projectile p) {
		p.dud = false;
		p.size = size;
		p.speed = speed;
		p.parent = parent;
		p.creepEffects = creepEffects;
		p.splashEffects = splashEffects;
		p.x = towerManager.getCenterX(parent);
		p.y = towerManager.getCenterY(parent);
		p.splashRadius = splashRadius;
		p.multiplier = multiplier;
		p.resistPenFlat = resistPenFlat;
		p.resistPenPercent = resistPenPercent;
		p.toughPenPercent = toughPenPercent;
		p.toughPenFlat = toughPenFlat;
		p.shieldDrainModifier = shieldDrainModifier;
		p.ignoresShield = ignoresShield;
		if (targetArea != null) {
			p.targetArea = targetArea.clone();
		} else {
			p.targetArea = null;
		}
		p.targetAngle = targetAngle;
		p.targetCreep = targetCreep;
		p.angleCos = angleCos;
		p.angleSin = angleSin;
		p.hitsAir = hitsAir;
		p.splashHitsAir = splashHitsAir;
		p.doesSlow = doesSlow;
		p.doesSplash = doesSplash;
		p.doesOnHit = doesOnHit;
		p.hitBox = hitBox.clone();
	}
	
	/**
	 * Does a shallow clone. Object references still point to those of the baseProjectile from which this is cloned.
	 * This means that each time the projectile has changes made to what it does, we need to re-set the baseProjectile field in a tower because cloned projectiles will still reference old values otherwise.
	 */
	@Override
	public abstract Projectile clone();
	
	protected abstract int update();
	
	protected abstract boolean isDone();
	
	protected abstract void detonate();

	protected void setIgnoresShield(boolean ignoresShield) { this.ignoresShield = ignoresShield; }
	protected void setResistPenPercent(DamageType type, float modifier) { resistPenPercent[type.ordinal()] = modifier; }
	protected void setShieldDrainModifier(float modifier) { this.shieldDrainModifier = modifier; }
	protected void setSize(float size) { if (size >= 0) { this.size = size; } }
	protected void setSpeed(float speed) { this.speed = speed; }
	protected void setToughPenPercent(float modifier) { toughPenPercent = modifier; }
	
	protected boolean hitsAir() { return hitsAir; }
	protected boolean ignoresShield() { return ignoresShield; }
	protected float getShieldDrainModifier() { return shieldDrainModifier; }
	protected float getToughPen(boolean isFlat) { return (isFlat ? toughPenFlat : toughPenPercent); }
	protected float getResistPen(DamageType type, boolean isFlat) { return (isFlat ? resistPenFlat[type.ordinal()] : resistPenPercent[type.ordinal()]); }
	protected Tower getParent() { return parent; }
	protected float getX() { return x; }
	protected float getY() { return y; }
	
	protected void clearAllBasicEffects() {
		creepEffects.clear();
		splashEffects.clear();
	}
	
	protected void updateAngle() {
		if (targetCreep != null) {
			targetAngle = TrigHelper.angleBetween(x, y, creepManager.getX(targetCreep), creepManager.getY(targetCreep));
			angleCos = Math.cos(targetAngle);
			angleSin = Math.sin(targetAngle);
		} else if (targetArea != null) {
			targetAngle = TrigHelper.angleBetween(x, y, targetArea.getX(), targetArea.getY());
			angleCos = Math.cos(targetAngle);
			angleSin = Math.sin(targetAngle);
		} else {
			targetAngle = 0;
			angleCos = 0;
			angleSin = 0;
		}
	}
	
	protected void addSpecificCreepEffect(ProjectileEffect effect) {
		creepEffects.add(effect);
	}
	
	protected void addSpecificSplashEffect(ProjectileEffect effect) {
		splashEffects.add(effect);
	}
}
