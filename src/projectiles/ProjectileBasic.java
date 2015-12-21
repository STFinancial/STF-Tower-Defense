package projectiles;

import creeps.Creep;
import towers.Tower;
import utilities.Circle;
import utilities.GameConstants;

public class ProjectileBasic extends Projectile implements TargetsCreep {
	
	protected ProjectileBasic(Tower parent, Projectile mold) {
		super(parent, mold);
		speed = currentSpeed = GameConstants.BASE_SPEED_PROJECTILE_BASIC;
	}
	
	public ProjectileBasic(Tower parent) {
		super(parent);
		speed = currentSpeed = GameConstants.BASE_SPEED_PROJECTILE_BASIC;
	}

	@Override
	public Projectile clone() {
		return new ProjectileBasic(parent, this);
	}

	@Override
	public int update() {
		if (targetCreep != null) {
			//TODO: Need to change this from isDead to isGone, which returns if it either escaped or is dead
			if (creepManager.isDead(targetCreep)) {
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
		hitBox = new Circle(x, y, hitBox.getRadius()); // TODO: Really not happy about this, see if we can't create a hierarchy of circles, some of which we can update
		return 0;
	}

	@Override
	public boolean isDone() {
		if (dud) {
			return true;
		}
		return creepManager.intersects(targetCreep, hitBox);
	}

	@Override
	public void detonate() {
		if (dud) {
			return;
		}
		creepManager.addAllEffects(targetCreep, creepEffects);
		creepManager.onProjectileCollision(targetCreep);
		for (Creep c: creepManager.getOtherCreepInSplashRange(targetCreep, splashRadius, splashHitsAir)) {
			creepManager.addAllEffects(c, splashEffects);
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
