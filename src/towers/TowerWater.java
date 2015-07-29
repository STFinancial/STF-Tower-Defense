package towers;

import creeps.Creep;
import levels.Level;
import maps.Tile;
import projectiles.ProjectileBasic;

public class TowerWater extends Tower implements TargetsCreep {
	public TowerWater(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WATER, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
	}

	@Override
	public void setTargetCreep(Creep c) {
		targetCreep = c;
	}

	@Override
	public Creep getTargetCreep() {
		return targetCreep;
	}

	@Override
	public void update() {
		currentAttackCoolDown--;
		if (currentAttackCoolDown < 1) {
			targetCreep = level.findTargetCreep(this);
			if (targetCreep != null) {
				updateAngle(targetCreep);
				level.addProjectile(fireProjectile());
				attackCarryOver += 1 - currentAttackCoolDown;
				currentAttackCoolDown = attackCoolDown;
				if (attackCarryOver > 1) {
					attackCarryOver -= 1;
					currentAttackCoolDown--;
				}
			}
		}
	}
}
