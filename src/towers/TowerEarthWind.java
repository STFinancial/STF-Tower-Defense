package towers;

import creeps.Creep;
import projectiles.ProjectileBasic;
import projectiles.ProjectileBeam;
import levels.Level;
import maps.Tile;

public class TowerEarthWind extends Tower {

	public TowerEarthWind(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[1][3]) {
			baseProjectile = new ProjectileBeam(this);
		} else {
			baseProjectile = new ProjectileBasic(this);
		}
	}

	@Override
	public int update() {
		
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[1][3]) {
			Creep targetCreep = level.findTargetCreep(this);
			if (targetCreep != null) {
				((ProjectileBeam) baseProjectile).setTargetCreep(targetCreep);
				level.addProjectile(fireProjectile());
			}
			return 1;
		} else {
			currentAttackCooldown--;
			if (currentAttackCooldown < 1) {
				Creep targetCreep = level.findTargetCreep(this);
				if (targetCreep != null) {
					//TODO is there a better way than casting, perhaps changing the method signature of the fire projectile
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

}
