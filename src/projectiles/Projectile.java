package projectiles;

import java.util.ArrayList;
import java.util.HashSet;

import levels.Level;

import projectileeffects.AffixModifier;
import projectileeffects.CreepModifierEffect;
import projectileeffects.Damage;
import projectileeffects.ProjectileEffect;
import projectileeffects.Slow;

import towers.Tower;
import utilities.Circle;
import utilities.Constants;
import utilities.TrigHelper;
import creeps.Creep;
import creeps.DamageType;
/*
 * Unit that is fired from a tower, contains information such as position/velocity, target area or target creep
 * as well as tower that fired the projectile. lastly contains the projectile effect which happens when the projectile lands or times out
 * PERHAPS an additional item for stuff like points (bullets) vs rings (aoe circle) vs cones (Flamethrower), or seperate class?
 * Also possibly a second class for passive boost from towers like attack speed in radius
 */
public abstract class Projectile {
	public Tower parent;
	public float x, y;
	public float currentSpeed, speed;
	public float size = .01f;
	public float splashRadius;
	public Circle hitBox;
	
	public boolean dud; //When creep is killed by something else or escapes before contact;
	public Creep targetCreep;
	float targetAngle; //For animation and to pass to projectiles when fired, Degrees, 0 = right, 90 = up
	
	ArrayList<ProjectileEffect> creepEffects;
	ArrayList<ProjectileEffect> splashEffects;
	public AffixModifier multiplier;
	
	protected Projectile() {
		dud = false;
	}
	
	public Projectile(Tower parent) {
		dud = false;
		this.x = parent.centerX;
		this.y = parent.centerY;
		splashRadius = parent.splashRadius;
		hitBox = new Circle(x, y, size);
		creepEffects = new ArrayList<ProjectileEffect>();
		splashEffects = new ArrayList<ProjectileEffect>();
		targetAngle = parent.targetAngle;
		this.parent = parent;
		multiplier = new AffixModifier();
		addGeneralEffects();
		addSpecificEffects();
	}
	
	private void addGeneralEffects() {
		for (int i = 0; i < Constants.NUM_DAMAGE_TYPES; i++) {
			if (parent.damageArray[i] != 0) {
				creepEffects.add(new Damage(parent.damageArray[i], DamageType.values()[i]));
				if (parent.damageSplash != 0) {
					splashEffects.add(new Damage(parent.damageArray[i] * parent.damageSplash, DamageType.values()[i]));
				}
			}
			if (parent.slowArray[i] != 0) {
				creepEffects.add(new Slow(parent.slowDurationArray[i], parent.slowArray[i], DamageType.values()[i]));
				if (parent.effectSplash != 0) {
					splashEffects.add(new Slow(parent.slowDurationArray[i], parent.slowArray[i] * parent.effectSplash, DamageType.values()[i]));
				}
			}
		}
		//TODO this might change in baseattributelist... also we might change the size somewhere
		currentSpeed = speed = .20f;
	}
	
	public abstract Projectile clone();
	
	public abstract void update();
	
	public abstract boolean isDone();
	
	public abstract void detonate(Level level);

	protected abstract void addSpecificEffects();
}
