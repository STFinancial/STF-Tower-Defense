package towers;

import creeps.Creep;
import levels.Level;
import maps.Tile;
import projectiles.ProjectileBasic;

public class TowerWater extends Tower{
	public TowerWater(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WATER, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
	}

	@Override
	public void update() {
		currentAttackCoolDown--;
		if (currentAttackCoolDown < 1) {
			Creep targetCreep = level.findTargetCreep(this);
			if (targetCreep != null) {
				//TODO is there a better way than casting, perhaps changing the method signature of the fire projectile
				((ProjectileBasic) baseProjectile).setTargetCreep(targetCreep);
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
