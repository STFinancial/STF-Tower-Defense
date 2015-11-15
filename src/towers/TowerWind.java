package towers;

import creeps.Creep;
import levels.Tile;
import projectiles.ProjectileBasic;

public class TowerWind extends Tower {
	TowerWind(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.WIND, towerID);
		adjustClassSpecificBaseStats();
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
	}

	@Override
	protected int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = projManager.findTargetCreep(this, hitsAir);
			if (targetCreep != null) {
				((ProjectileBasic) baseProjectile).setTargetCreep(targetCreep);
				projManager.addProjectile(fireProjectile());
				attackCarryOver += 1 - currentAttackCooldown;
				currentAttackCooldown = attackCooldown;
				if (attackCarryOver > 1) {
					attackCarryOver -= 1;
					currentAttackCooldown--;
				}
				return 1;
			}
		}
		return 0;
	}

	@Override
	protected void adjustClassSpecificBaseStats() {
		//TODO: Should these base towers have a higher coefficient since they don't get other benefits?
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.02f;
		this.qRange = 0f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		return;
	}

}
