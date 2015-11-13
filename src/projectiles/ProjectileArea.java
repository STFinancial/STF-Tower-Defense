package projectiles;

import java.util.HashSet;

import creeps.Creep;
import towers.Tower;
import utilities.Circle;

public class ProjectileArea extends Projectile implements TargetsArea {
	protected ProjectileArea(Tower parent, Projectile mold, float targetAreaRadius) {
		super(parent, mold);
		this.targetAngle = 0;
	}
	
	public ProjectileArea(Tower parent, float targetAreaRadius) {
		super(parent);
		this.targetArea = new Circle(parent.getCenterX(), parent.getCenterY(), targetAreaRadius);
		this.targetAngle = 0;
	}

	@Override
	public Projectile clone() {
		return new ProjectileArea(parent, this, targetArea.radius);
	}

	@Override
	public int update() { return 0; }

	@Override
	public boolean isDone() { return true; } //TODO: Perhaps we want a speed? (I'm thinking no, but it's something to think about)

	@Override
	public void detonate() {
		HashSet<Creep> splashCreep = projManager.getCreepInRange(targetArea, splashHitsAir);
		HashSet<Creep> nonSplashCreep = projManager.getCreepInRange(targetArea, hitsAir);
		if (doesOnHit) {
			for (Creep c: nonSplashCreep) {
				c.addAllEffects(creepEffects);
				c.onProjectileCollision();
			}
		} else {
			for (Creep c: nonSplashCreep) {
				c.addAllEffects(creepEffects);
			}
		}
		if (doesSplash) {
			for (Creep c: splashCreep) {
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
