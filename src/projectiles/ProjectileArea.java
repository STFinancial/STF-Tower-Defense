package projectiles;

import java.util.HashSet;

import creeps.Creep;
import towers.Tower;
import utilities.Circle;

public class ProjectileArea extends Projectile implements TargetsArea {
	protected boolean doesSplash;
	protected boolean doesOnHit; //TODO: Should this be a legitimate field? Or should this just apply on hits.
	
	protected ProjectileArea(Tower parent, Projectile mold, boolean doesSplash, boolean doesOnHit, float targetAreaRadius) {
		super(parent, mold);
		this.targetArea = new Circle(parent.x, parent.y, targetAreaRadius);
		this.doesSplash = doesSplash;
		this.doesOnHit = doesOnHit;
		this.targetAngle = 0;
	}
	
	public ProjectileArea(Tower parent, boolean doesSplash, boolean doesOnHit, float targetAreaRadius) {
		super(parent);
		this.targetArea = new Circle(parent.x, parent.y, targetAreaRadius);
		this.doesSplash = doesSplash;
		this.doesOnHit = doesOnHit;
		this.targetAngle = 0;
	}

	@Override
	public Projectile clone() {
		return new ProjectileArea(parent, this, doesSplash, doesOnHit, targetArea.radius);
	}

	@Override
	public int update() { return 0; }

	@Override
	public boolean isDone() { return true; } //TODO: Perhaps we want a speed? (I'm thinking no, but it's something to think about)

	@Override
	public void detonate() {
		HashSet<Creep> creepInRange = guider.getCreepInRange(targetArea, parent.hitsAir);
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
	//NOTE: We may need to do some checking here though to see if the tower can hit flying (areas where only flying creeps can go)
	@Override
	public boolean setTargetArea(float x, float y) {
		//May need to construct a new circle in here at some point to check if it's a valid target location
		targetArea.x = x;
		targetArea.y = y;
		updateAngle();
		return true;
	}

}
