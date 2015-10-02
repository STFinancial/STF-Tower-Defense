package projectiles;

import levels.Level;
import creeps.Creep;
import towers.Tower;
import utilities.TrigHelper;

public class ProjectileBasic extends Projectile implements TargetsCreep {
	protected Creep targetCreep;
	
	public ProjectileBasic(Tower parent) {
		super(parent);
	}

	@Override
	public Projectile clone() {
		ProjectileBasic p = new ProjectileBasic(parent);
		cloneStats(p);
		p.targetCreep = targetCreep;
		return p;
	}

	@Override
	public int update() {
		if (targetCreep != null) {
			if (targetCreep.isDead()) {
				dud = true;
				return -1;
			} else {
				targetAngle = TrigHelper.angleBetween(x, y, targetCreep.hitBox.x, targetCreep.hitBox.y);
			}
		} else {
			dud = true;
			return -1;
		}
		x -= Math.cos(targetAngle) * currentSpeed;
		y -= Math.sin(targetAngle) * currentSpeed;
		hitBox.x = x;
		hitBox.y = y;
		return 0;
	}

	@Override
	public boolean isDone() {
		if (dud) {
			return true;
		}
		return hitBox.intersects(targetCreep.hitBox);
	}

	@Override
	public void detonate(Level level) {
		if (dud) {
			return;
		}
		targetCreep.addAllEffects(creepEffects);
		targetCreep.onProjectileCollision();
		for (Creep c: guider.getOtherCreepInSplashRange(targetCreep, splashRadius)) {
			c.addAllEffects(splashEffects);
		}
	}

	@Override
	public void setTargetCreep(Creep c) {
		targetCreep = c;
		targetAngle = TrigHelper.angleBetween(x, y, targetCreep.hitBox.x, targetCreep.hitBox.y);
	}

	@Override
	public Creep getTargetCreep() {
		return targetCreep;
	}
}
