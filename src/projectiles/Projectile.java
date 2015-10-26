package projectiles;

import java.util.ArrayList;

import projectileeffects.Damage;
import projectileeffects.ProjectileEffect;
import projectileeffects.Slow;

import levels.Updatable;


import towers.Tower;
import utilities.Circle;
import utilities.GameConstants;
import utilities.TrigHelper;
import creeps.AffixModifier;
import creeps.Creep;
import creeps.DamageType;
/*
 * Unit that is fired from a tower, contains information such as position/velocity, target area or target creep
 * as well as tower that fired the projectile. lastly contains the projectile effect which happens when the projectile lands or times out
 * PERHAPS an additional item for stuff like points (bullets) vs rings (aoe circle) vs cones (Flamethrower), or seperate class?
 * Also possibly a second class for passive boost from towers like attack speed in radius
 */
public abstract class Projectile implements Updatable {
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
	protected static ProjectileGuider guider = ProjectileGuider.getInstance();
	
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
	
	public Projectile(Tower parent) {
		initializeStats(parent);
		addGeneralEffects();
	}
	
	//TODO: This method is pretty questionable.
	private void addGeneralEffects() {
		DamageType[] types = DamageType.values();
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			if (parent.getDamage(types[i]) != 0) {
				creepEffects.add(new Damage(parent.getDamage(types[i]), types[i], this));
				if (doesSplash && parent.getDamageSplash() != 0 && parent.getSplashRadius() != 0) {
					splashEffects.add(new Damage(parent.getDamage(types[i]) * parent.getDamageSplash(), types[i], this));
				}
			}
			float slow;
			if (doesSlow) {
				if ((slow = parent.getSlow(types[i])) != 0) {
					if (slow >= 1) { slow = 0.99f; }
					creepEffects.add(new Slow(parent.getSlowDuration(types[i]), slow, types[i], this));
					if (doesSplash && parent.getEffectSplash() != 0 && parent.getSplashRadius() != 0) {
						splashEffects.add(new Slow(parent.getSlowDuration(types[i]), slow * parent.getEffectSplash(), types[i], this));
					}
				}
			}
			
		}
		//TODO this might change in baseattributelist... also we might change the size somewhere
		currentSpeed = speed = .20f;
	}
	
	protected void initializeStats(Tower parent) {
		this.dud 					= false;
		this.parent					= parent;
		this.x 						= parent.getCenterX();
		this.y 						= parent.getCenterY();
		this.splashRadius		 	= parent.getSplashRadius();
		this.hitsAir				= parent.hitsAir();
		this.doesSplash				= parent.doesSplash();
		this.doesSlow				= parent.doesSlow();
		this.doesOnHit				= parent.doesOnHit();
		this.splashHitsAir			= parent.splashHitsAir();
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
		p.x = parent.getCenterX();
		p.y = parent.getCenterY();
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
	public abstract Projectile clone();
	
	public abstract int update();
	
	public abstract boolean isDone();
	
	public abstract void detonate();

	public void setIgnoresShield(boolean ignoresShield) { this.ignoresShield = ignoresShield; }
	public void setResistPenPercent(DamageType type, float modifier) { resistPenPercent[type.ordinal()] = modifier; }
	public void setShieldDrainModifier(float modifier) { this.shieldDrainModifier = modifier; }
	public void setSize(float size) { if (size >= 0) { this.size = size; } }
	public void setSpeed(float speed) { this.speed = speed; }
	public void setToughPenPercent(float modifier) { toughPenPercent = modifier; }
	
	public boolean hitsAir() { return hitsAir; }
	public boolean ignoresShield() { return ignoresShield; }
	public float getShieldDrainModifier() { return shieldDrainModifier; }
	public float getToughPen(boolean isFlat) { return (isFlat ? toughPenFlat : toughPenPercent); }
	public float getResistPen(DamageType type, boolean isFlat) { return (isFlat ? resistPenFlat[type.ordinal()] : resistPenPercent[type.ordinal()]); }
	public Tower getParent() { return parent; }
	public float getX() { return x; }
	public float getY() { return y; }
	
	public void clearAllBasicEffects() {
		creepEffects.clear();
		splashEffects.clear();
	}
	
	protected void updateAngle() {
		if (targetCreep != null) {
			targetAngle = TrigHelper.angleBetween(x, y, targetCreep.hitBox.x, targetCreep.hitBox.y);
			angleCos = Math.cos(targetAngle);
			angleSin = Math.sin(targetAngle);
		} else if (targetArea != null) {
			targetAngle = TrigHelper.angleBetween(x, y, targetArea.x, targetArea.y);
			angleCos = Math.cos(targetAngle);
			angleSin = Math.sin(targetAngle);
		} else {
			targetAngle = 0;
			angleCos = 0;
			angleSin = 0;
		}
	}
	
	public void addSpecificCreepEffect(ProjectileEffect effect) {
		creepEffects.add(effect);
	}
	
	public void addSpecificSplashEffect(ProjectileEffect effect) {
		splashEffects.add(effect);
	}
}
