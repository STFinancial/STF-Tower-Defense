package towers;

import creeps.Creep;
import projectiles.ProjectileBasic;
import levels.Level;
import maps.Tile;

public class TowerEarth extends Tower implements TargetsCreep {
	public TowerEarth(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH, towerID);
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
