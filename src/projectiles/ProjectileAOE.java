package projectiles;

import java.util.HashSet;

import towers.Tower;

import creeps.Creep;

public class ProjectileAOE extends Projectile {
	protected ProjectileAOE(Tower parent, Projectile mold) {
		super(parent, mold);
		speed = currentSpeed = 0f;
	}
	
	public ProjectileAOE(Tower parent) {
		super(parent);
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
		//TODO: This or any other detonate method DOES NOT account for the "hitsGround" variable. Should we remove that?
		HashSet<Creep> creepInRange = guider.getCreepInRange(this, parent.range, true);
		for (Creep c: creepInRange) {
			if (c.isFlying()) {
				if (parent.hitsAir) {
					c.addAllEffects(creepEffects);
					if (doesOnHit) {
						c.onProjectileCollision();
					}
					if (doesSplash) {
						c.addAllEffects(splashEffects);
					}
				} else {
					if (doesSplash && parent.splashHitsAir) {
						c.addAllEffects(splashEffects);
					}
				}
			} else {
				c.addAllEffects(creepEffects);
				if (doesOnHit) {
					c.onProjectileCollision();
				}
				if (doesSplash) { //TODO: Are these only the creep we want to hit, those in range of the tower?
					c.addAllEffects(splashEffects);
				}
			}
			
		}
	}

}
