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
	public boolean targetsCreep; //False for targeting a ground spot;
	float targetX, targetY; //For ground spot target towers, in Tile coordinates
	public Creep targetCreep;
	float targetAngle; //For animation and to pass to projectiles when fired, Degrees, 0 = right, 90 = up
	public float splashRadius = 0;
	public float damageSplash;
	public float effectSplash;

	public ArrayList<ProjectileEffect> effects;
	
	public Projectile(Tower parent) {
		this.parent = parent;
		this.x = parent.centerX;
		this.y = parent.centerY;
		targetsCreep = parent.targetsCreep;
		targetX = parent.targetX;
		targetY = parent.targetY;
		targetCreep = parent.targetCreep;
		targetAngle = parent.targetAngle;
		effects = new ArrayList<ProjectileEffect>();
		hitBox = new Circle(x, y, size);
	}

	public void addEffect(ProjectileEffect effect) {
		effects.add(effect);
	}

	public void applyEffect(Creep creep) {
		creep.addAllEffects(effects);
	}
	
	public void update(){
		if (targetsCreep) {
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
		if (targetsCreep) {
			return hitBox.intersects(targetCreep.hitBox);
		}
		//TODO add area target part
		return false;
	}

	public void applySplashEffects(HashSet<Creep> creepInRange) {
		//TODO add effect splash or decide if that's something we even want
		ArrayList<ProjectileEffect> splashEffects = new ArrayList<ProjectileEffect>();
		DamageEffect d;
		for (ProjectileEffect eff: effects) {
			if (eff instanceof Damage) {
				d = new Damage(eff.modifier * damageSplash, ((Damage) eff).damageType, eff.elementType);
				splashEffects.add(d);
			}
		}
		for (Creep c: creepInRange) {
			c.addAllEffects(splashEffects);
		}
		//TODO this is *might* get rekt by garbage collect and will take a lot of time, calls for refactoring the "DamageEffect" and making damage nonobjects and just an int
	}

}
