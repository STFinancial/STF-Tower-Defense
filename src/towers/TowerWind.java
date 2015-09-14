package towers;

import creeps.Creep;
import levels.Level;
import maps.Tile;
import projectiles.ProjectileBasic;

public class TowerWind extends Tower {
	public TowerWind(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WIND, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
	}

	@Override
	public int update() {
		//TODO need to handle attack speeds of less than 1. We will need to fire two projectiles at once then.
		//TODO can fix this by making creep move slower and bumping up attack cooldowns if we have the resourcse
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
				return 1;
			}
		}
		return 0;
	}

}
