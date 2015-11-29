package projectiles;

import java.util.HashSet;

import creeps.Creep;
import towers.Tower;
import utilities.Circle;
import utilities.GameConstants;

public class ProjectileArea extends Projectile implements TargetsArea {
	protected ProjectileArea(Tower parent, Projectile mold, float targetAreaRadius) {
		super(parent, mold);
		this.targetAngle = 0;
		speed = currentSpeed = GameConstants.BASE_SPEED_PROJECTILE_AREA;
	}
	
	public ProjectileArea(Tower parent, float targetAreaRadius) {
		super(parent);
		this.targetArea = new Circle(towerManager.getCenterX(parent), towerManager.getCenterY(parent), targetAreaRadius);
		this.targetAngle = 0;
		speed = currentSpeed = GameConstants.BASE_SPEED_PROJECTILE_AREA;
	}

	@Override
	public Projectile clone() {
		return new ProjectileArea(parent, this, targetArea.getRadius());
	}

	@Override
	public int update() { return 0; }

	@Override
	public boolean isDone() { return true; } //TODO: Perhaps we want a speed? (I'm thinking no, but it's something to think about)

	@Override
	public void detonate() {
		HashSet<Creep> splashCreep = creepManager.getCreepInRange(targetArea, splashHitsAir);
		HashSet<Creep> nonSplashCreep = creepManager.getCreepInRange(targetArea, hitsAir);
		if (doesOnHit) {
			for (Creep c: nonSplashCreep) {
				creepManager.addAllEffects(c, creepEffects);
				creepManager.onProjectileCollision(c);
			}
		} else {
			for (Creep c: nonSplashCreep) {
				creepManager.addAllEffects(c, creepEffects);
			}
		}
		if (doesSplash) {
			for (Creep c: splashCreep) {
				creepManager.addAllEffects(c, splashEffects);
			}
		}
	}
	
	//TODO: Why does this return a boolean? We should check its validity elsewhere, not here.
	//NOTE: We may need to do some checking here though to see if the tower can hit flying (areas where only flying creeps can go)
	@Override
	public boolean setTargetArea(float x, float y) {
		//May need to construct a new circle in here at some point to check if it's a valid target location
		targetArea = new Circle(x, y, targetArea.getRadius());
		updateAngle();
		return true;
	}

}
