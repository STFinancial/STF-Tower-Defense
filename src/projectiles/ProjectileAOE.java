package projectiles;

import java.util.HashSet;

import towers.Tower;
import utilities.Circle;
import creeps.Creep;

public class ProjectileAOE extends Projectile {
	private Circle targetZone;
	
	protected ProjectileAOE(Tower parent, Projectile mold) {
		super(parent, mold);
		this.targetZone = parent.getTargetZone();
		speed = currentSpeed = 0f;
	}
	
	public ProjectileAOE(Tower parent) {
		super(parent);
		this.targetZone = parent.getTargetZone();
		speed = currentSpeed = 0f;
	}

	@Override
	public Projectile clone() {
		return new ProjectileAOE(parent, this);
	}

	@Override
	public int update() {
		return 0;
	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public void detonate() {
		//TODO: What about hitsGround?
		HashSet<Creep> splashCreep = guider.getCreepInRange(targetZone, splashHitsAir); //TODO: Do we only want to hit those in range of the tower with splash?
		HashSet<Creep> nonSplashCreep = guider.getCreepInRange(targetZone, hitsAir);
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

}
