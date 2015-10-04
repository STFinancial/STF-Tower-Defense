package towers;

import creeps.Creep;
import levels.Level;
import maps.Tile;
import projectiles.ProjectileBasic;

public class TowerFire extends Tower {

	public TowerFire(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.FIRE, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
	}

	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = guider.findTargetCreep(this, hitsAir);
			if (targetCreep != null) {
				((ProjectileBasic) baseProjectile).setTargetCreep(targetCreep);
				level.addProjectile(fireProjectile());
				attackCarryOver += 1 - currentAttackCooldown;
				currentAttackCooldown = attackCooldown;
				if (attackCarryOver > 1) {
					attackCarryOver -= 1;
					currentAttackCooldown--;
				}
			}
			return 1;
		}
		return 0;
	}

}
