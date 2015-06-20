package projectiles;

import java.util.ArrayList;
import java.util.HashSet;

import towers.Tower;
import utilities.Circle;
import utilities.TrigHelper;
import creeps.Creep;
/*
 * Unit that is fired from a tower, contains information such as position/velocity, target area or target creep
 * as well as tower that fired the projectile. lastly contains the projectile effect which happens when the projectile lands or times out
 * PERHAPS an additional item for stuff like points (bullets) vs rings (aoe circle) vs cones (Flamethrower), or seperate class?
 * Also possibly a second class for passive boost from towers like attack speed in radius
 */
public class Projectile {
	public Tower parent;
	public float x, y;
	public float currentSpeed, speed;
	public float size = .01f;
	public Circle hitBox;
	
	public boolean dud = false; //When creep is killed by something else or escapes before contact;
	float targetX, targetY; //For ground spot target towers, in Tile coordinates
	public Creep targetCreep;
	float targetAngle; //For animation and to pass to projectiles when fired, Degrees, 0 = right, 90 = up
	public float splashRadius = 0;
	public TargetingType targetingType;
	public TravelType travelType;
	public CollisionType collisionType;
	
	ArrayList<ProjectileEffect> creepEffects;
	ArrayList<ProjectileEffect> splashEffects;
	ArrayList<ProjectileEffect> modEffects;
	public AffixModifier multiplier;
	
	public Projectile(Tower parent) {
		this.parent = parent;
		this.x = parent.centerX;
		this.y = parent.centerY;
		//targetsCreep = parent.targetsCreep;
		targetX = parent.targetX;
		targetY = parent.targetY;
		targetCreep = parent.targetCreep;
		targetAngle = parent.targetAngle;
		creepEffects = new ArrayList<ProjectileEffect>();
		splashEffects = new ArrayList<ProjectileEffect>();
		modEffects = new ArrayList<ProjectileEffect>();
		hitBox = new Circle(x, y, size);
	}
	
	public Projectile clone() {
		Projectile p = new Projectile(parent);
		p.creepEffects.addAll(creepEffects);
		p.splashEffects.addAll(splashEffects);
		for (ProjectileEffect pe: modEffects) {
			p.addEffect(pe);
		}
		p.currentSpeed = p.speed = speed; //TODO this logic might need to change if we have projectiles that speed up
		p.splashRadius = splashRadius;
		p.parent = parent;
		p.multiplier = multiplier;
		p.targetingType = targetingType;
		p.collisionType = collisionType;
		p.travelType = travelType;
		return p;
	}

	public void addEffect(ProjectileEffect effect) {
		if (effect instanceof CreepModifierEffect) {
			creepEffects.add(effect);
		} else if (effect instanceof ProjectileModifierEffect) {
			((ProjectileModifierEffect) effect).applyEffectToProjectile(this);
			modEffects.add(effect);
		}
		
	}
	
	public void addSplashEffect(ProjectileEffect effect) {
		splashEffects.add(effect);
	}

	public void applyEffects(Creep creep) {
		creep.addAllEffects(creepEffects);
	}
	
	public void update(){
		if (targetingType == TargetingType.CREEP) {
			if (targetCreep != null) {
				if (targetCreep.isDead()) {
					dud = true;
					return;
				} else {
					targetAngle = TrigHelper.angleBetween(x, y, targetCreep.hitBox.x, targetCreep.hitBox.y);
				}
			} else {
				dud = true;
				return;
			}
		}
		x -= Math.cos(targetAngle) * currentSpeed;
		y -= Math.sin(targetAngle) * currentSpeed;
		hitBox.x = x;
		hitBox.y = y;
	}
	
	public boolean isDone(){
		if (dud) {
			return true;
		}
		if (targetingType == TargetingType.CREEP) {
			return hitBox.intersects(targetCreep.hitBox);
		}
		//TODO add area target part
		return false;
	}

	public void applySplashEffects(HashSet<Creep> creepInRange) {
		for (Creep c: creepInRange) {
			c.addAllEffects(splashEffects);
		}
	}
}
