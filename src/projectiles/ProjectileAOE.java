package projectiles;

import java.util.HashSet;

import towers.Tower;
import utilities.Circle;
import utilities.GameConstants;
import creeps.Creep;

public class ProjectileAOE extends Projectile {
	private Circle targetZone;
	
	protected ProjectileAOE(Tower parent, Projectile mold) {
		super(parent, mold);
		this.targetZone = ((ProjectileAOE) mold).targetZone;
		speed = currentSpeed = GameConstants.BASE_SPEED_PROJECTILE_AOE;
	}
	
	public ProjectileAOE(Tower parent) {
		super(parent);
		this.targetZone = towerManager.getTargetZone(parent);
		speed = currentSpeed = GameConstants.BASE_SPEED_PROJECTILE_AOE;
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
		HashSet<Creep> splashCreep = creepManager.getCreepInRange(targetZone, splashHitsAir); //TODO: Do we only want to hit those in range of the tower with splash?
		HashSet<Creep> nonSplashCreep = creepManager.getCreepInRange(targetZone, hitsAir);
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

}
