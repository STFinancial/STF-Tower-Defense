package projectiles;

import levels.Level;

import creeps.Creep;

import towers.Tower;
import utilities.TrigHelper;

public class ProjectileBasic extends Projectile {
	
	private ProjectileBasic() {
		super();
	}
	
	public ProjectileBasic(Tower parent) {
		super(parent);
		targetCreep = parent.targetCreep;
	}

	@Override
	public Projectile clone() {
		Projectile p = new ProjectileBasic();
		p.parent = parent;
		p.creepEffects = creepEffects;
		p.splashEffects = splashEffects;
		p.hitBox = hitBox;
		p.x = parent.centerX;
		p.y = parent.centerY;
		p.splashRadius = splashRadius;
		p.size = size;
		p.targetAngle = targetAngle;
		p.multiplier = multiplier;
		return p;
	}

	@Override
	public void update() {
		if (targetCreep != null) {
			if (targetCreep.isDead()) {
				dud = true;
				return;
			} else {
				targetAngle = TrigHelper.angleBetween(x, y, targetCreep.hitBox.x, targetCreep.hitBox.y);
			}
		} else {
			dud = true;
			return;
		}
		x -= Math.cos(targetAngle) * currentSpeed;
		y -= Math.sin(targetAngle) * currentSpeed;
		hitBox.x = x;
		hitBox.y = y;
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
		targetCreep.addAllEffects(creepEffects);
		for (Creep c: level.getCreepInRange(this, splashRadius)) {
			c.addAllEffects(splashEffects);
		}
	}

	@Override
	protected void addSpecificEffects() {
		
	}

}
