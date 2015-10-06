package towers;

import creeps.Creep;
import projectiles.ProjectileBasic;
import projectiles.ProjectilePassThroughTarget;
import levels.Level;
import maps.Tile;

public class TowerEarthWind extends Tower {
	float passThroughRadiusModifier;
	float passThroughModifier;
	
	public TowerEarthWind(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH_WIND, towerID);
		this.passThroughRadiusModifier = 0.18f;
		this.passThroughModifier = 0.40f;
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[1][3]) {
			baseProjectile = new ProjectilePassThroughTarget(this, splashRadius * passThroughRadiusModifier, passThroughModifier, false, 1);
		} else {
			baseProjectile = new ProjectileBasic(this);
		}
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
