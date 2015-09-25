package projectiles;

import java.util.ArrayList;

import projectileeffects.Damage;
import projectileeffects.ProjectileEffect;
import projectileeffects.Slow;

import levels.Level;
import levels.ProjectileGuider;
import levels.Updatable;


import towers.Tower;
import utilities.Circle;
import utilities.GameConstants;
import creeps.AffixModifier;
import creeps.DamageType;
/*
 * Unit that is fired from a tower, contains information such as position/velocity, target area or target creep
 * as well as tower that fired the projectile. lastly contains the projectile effect which happens when the projectile lands or times out
 * PERHAPS an additional item for stuff like points (bullets) vs rings (aoe circle) vs cones (Flamethrower), or seperate class?
 * Also possibly a second class for passive boost from towers like attack speed in radius
 */
public abstract class Projectile implements Updatable {
	public Tower parent;
	public Level level;
	public float x, y;
	public float currentSpeed, speed;
	public float size = .01f;
	public float splashRadius;
	public Circle hitBox;
	public float[] resistPenPercent;
	public float[] resistPenFlat;
	public float toughPenPercent = 0f;
	public float toughPenFlat = 0;
	public float shieldDrainModifier = 1;
	public boolean ignoresShield;
	protected ProjectileGuider guider;
	
	public boolean dud; //When creep is killed by something else or escapes before contact;
	float targetAngle; //For animation and to pass to projectiles when fired, Degrees, 0 = right, 90 = up
	
	ArrayList<ProjectileEffect> creepEffects;
	ArrayList<ProjectileEffect> splashEffects;
	public AffixModifier multiplier;
	
	protected Projectile() {
		//This is called from the clone method only
		this.guider = ProjectileGuider.getInstance();
	}
	
	public Projectile(Tower parent) {
		this.dud 			= false;
		this.parent			= parent;
		this.level 			= parent.level;
		this.x 				= parent.centerX;
		this.y 				= parent.centerY;
		this.splashRadius 	= parent.splashRadius;
		this.guider			= ProjectileGuider.getInstance();
		
		hitBox = new Circle(x, y, size);
		resistPenPercent = new float[GameConstants.NUM_DAMAGE_TYPES];
		resistPenFlat = new float[GameConstants.NUM_DAMAGE_TYPES];
		creepEffects = new ArrayList<ProjectileEffect>();
		splashEffects = new ArrayList<ProjectileEffect>();
		multiplier = new AffixModifier();
		
		addGeneralEffects();
	}
	
	private void addGeneralEffects() {
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			if (parent.damageArray[i] != 0) {
				creepEffects.add(new Damage(parent.damageArray[i], DamageType.values()[i], this));
				if (parent.damageSplash != 0 && parent.splashRadius != 0) {
					splashEffects.add(new Damage(parent.damageArray[i] * parent.damageSplash, DamageType.values()[i], this));
				}
			}
			if (parent.slowArray[i] != 0) {
				creepEffects.add(new Slow(parent.slowDurationArray[i], parent.slowArray[i], DamageType.values()[i], this));
				if (parent.effectSplash != 0 && parent.splashRadius != 0) {
					splashEffects.add(new Slow(parent.slowDurationArray[i], parent.slowArray[i] * parent.effectSplash, DamageType.values()[i], this));
				}
			}
		}
		//TODO this might change in baseattributelist... also we might change the size somewhere
		currentSpeed = speed = .20f;
	}
	
	protected void cloneStats(Projectile p) {
		p.dud = false;
		p.size = size;
		p.speed = speed;
		p.parent = parent;
		p.level = level;
		p.creepEffects = creepEffects;
		p.splashEffects = splashEffects;
		p.x = parent.centerX;
		p.y = parent.centerY;
		p.splashRadius = splashRadius;
		p.multiplier = multiplier;
		p.resistPenFlat = new float[GameConstants.NUM_DAMAGE_TYPES];
		p.resistPenPercent = new float[GameConstants.NUM_DAMAGE_TYPES];
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			p.resistPenPercent[i] = resistPenPercent[i];
			p.resistPenFlat[i] = resistPenFlat[i];
		}
		p.toughPenPercent = toughPenPercent;
		p.toughPenFlat = toughPenFlat;
		p.shieldDrainModifier = shieldDrainModifier;
		p.ignoresShield = ignoresShield;
		p.targetAngle = targetAngle;
	}
	
	public abstract Projectile clone();
	
	public abstract int update();
	
	public abstract boolean isDone();
	
	public abstract void detonate(Level level);

	public void addSpecificCreepEffect(ProjectileEffect effect) {
		creepEffects.add(effect);
	}
	
	public void addSpecificSplashEffect(ProjectileEffect effect) {
		splashEffects.add(effect);
	}
}
