package towers;

import creeps.Creep;
import projectiles.ProjectileBasic;
import levels.Level;
import maps.Tile;

public class TowerEarth extends Tower {
	
	public TowerEarth(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH, towerID);
		adjustClassSpecificBaseStats();
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
	}

	@Override
	public int update() {
		//TODO need to handle attack speeds of less than 1. We will need to fire two projectiles at once then.
		//TODO can fix this by making creep move slower and bumping up attack cooldowns if we have the resourcse
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = projManager.findTargetCreep(this, hitsAir);
			if (targetCreep != null) {
				((ProjectileBasic) baseProjectile).setTargetCreep(targetCreep);
				level.addProjectile(fireProjectile());
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
