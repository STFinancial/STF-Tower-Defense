package projectiles;

import creeps.Creep;
import towers.Tower;

public class ProjectileBasic extends Projectile implements TargetsCreep {
	protected Creep targetCreep;
	
	protected ProjectileBasic(Tower parent, Projectile mold) {
		super(parent, mold);
	}
	
	public ProjectileBasic(Tower parent) {
		super(parent);
	}

	@Override
	public Projectile clone() {
		return new ProjectileBasic(parent, this);
	}

	@Override
	public int update() {
		if (targetCreep != null) {
			if (targetCreep.isDead()) {
				dud = true;
				return -1;
			} else {
				updateAngle();
			}
		} else {
			dud = true;
			return -1;
		}
		x -= angleCos * currentSpeed;
		y -= angleSin * currentSpeed;
		hitBox.x = x;
		hitBox.y = y;
		return 0;
	}

	@Override
	public boolean isDone() {
		if (dud) {
			return true;
		}
		return targetCreep.intersects(hitBox);
	}

	@Override
	public void detonate() {
		if (dud) {
			return;
		}
		targetCreep.addAllEffects(creepEffects);
		targetCreep.onProjectileCollision();
		for (Creep c: guider.getOtherCreepInSplashRange(targetCreep, splashRadius, splashHitsAir)) {
			c.addAllEffects(splashEffects);
		}
	}

	@Override
	public void setTargetCreep(Creep c) {
		targetCreep = c;
		updateAngle();
	}

	@Override
	public Creep getTargetCreep() {
		return targetCreep;
	}
}
