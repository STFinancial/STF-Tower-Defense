package projectiles;

import java.util.HashSet;

import levels.Level;

import creeps.Creep;
import towers.Tower;
import utilities.Circle;
import utilities.TrigHelper;

public class ProjectileArea extends Projectile implements TargetsArea {
	private Circle targetArea;
	private boolean doesSplash;
	private boolean doesOnHit; //TODO: Should this be a legitimate field? Or should this just apply on hits.
	
	public ProjectileArea(Tower parent, boolean doesSplash, boolean doesOnHit, float targetAreaRadius) {
		super(parent);
		this.targetArea = new Circle(parent.x, parent.y, targetAreaRadius);
		this.doesSplash = doesSplash;
		this.doesOnHit = doesOnHit;
		this.targetAngle = 0;
	}

	@Override
	public Projectile clone() {
		ProjectileArea p = new ProjectileArea(parent, doesSplash, doesOnHit, targetArea.radius);
		cloneStats(p);
		return p;
	}

	@Override
	public int update() { return 0; }

	@Override
	public boolean isDone() { return true; }

	@Override
	public void detonate(Level level) {
		HashSet<Creep> creepInRange = guider.getCreepInRange(targetArea);
		for (Creep c: creepInRange) {
			c.addAllEffects(creepEffects);
			if (doesOnHit) {
				c.onProjectileCollision(); //TODO: Need to add this to all projectile types
			}
			if (doesSplash) {
				//TODO can change this to normal splash if needed
				//Also, is this the range we want to do or should the range be extended by the splash radius
				c.addAllEffects(splashEffects);
			}
		}
	}
	
	//TODO: Why does this return a boolean? We should check its validity elsewhere, not here.
	//NOTE: We may need to do some checking here though to see if the tower can hit flying
	@Override
	public boolean setTargetArea(float x, float y) {
		//May need to construct a new circle in here at some point to check if it's a valid target location
		targetArea.x = x;
		targetArea.y = y;
		targetAngle = TrigHelper.angleBetween(this.x, this.y, x, y);
		return true;
	}

}
