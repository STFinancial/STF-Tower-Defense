package projectiles;

import levels.Level;

import creeps.Creep;

import towers.Tower;
import utilities.TrigHelper;

public class ProjectileBasic extends Projectile implements TargetsCreep {
	protected Creep targetCreep;
	
	protected ProjectileBasic() {
		
	}
	
	public ProjectileBasic(Tower parent) {
		super(parent);
	}

	//TODO if something fucks up, make sure all the fields are being set properly
	@Override
	public Projectile clone() {
		ProjectileBasic p = new ProjectileBasic();
		p.dud = false;
		p.parent = parent;
		p.level = level;
		p.creepEffects = creepEffects;
		p.splashEffects = splashEffects;
		p.hitBox = hitBox;
		p.x = parent.centerX;
		p.y = parent.centerY;
		p.splashRadius = splashRadius;
		p.size = size;
		p.targetCreep = targetCreep;
		//this is only safe because we clone immediately before we fire
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
		if (dud) {
			return;
		}
		targetCreep.addAllEffects(creepEffects);
		for (Creep c: level.getOtherCreepInRange(targetCreep, splashRadius)) {
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
