package projectiles;

import java.util.HashSet;

import levels.Level;

import towers.Tower;

import creeps.Creep;

public class ProjectileAOE extends Projectile {
	private boolean doesSplash;
	private boolean doesOnHit;
	
	public ProjectileAOE(Tower parent, boolean doesSplash, boolean doesOnHit) {
		super(parent);
		this.doesSplash = doesSplash;
		speed = currentSpeed = 0f;
	}

	@Override
	public Projectile clone() {
		Projectile p = new ProjectileAOE(parent, doesSplash, doesOnHit);
		cloneStats(p);
		return p;
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
	public void detonate(Level level) {
		HashSet<Creep> creepInRange = guider.getCreepInRange(this, parent.range);
		for (Creep c: creepInRange) {
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
