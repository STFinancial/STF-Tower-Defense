package towers;

import creeps.Creep;
import projectiles.ProjectileBasic;
import projectiles.ProjectilePassThroughTarget;
import levels.Tile;

public final class TowerEarthWind extends Tower {
	float passThroughRadiusModifier;
	float passThroughModifier;
	
	private float qRadiusModifier;
	private float qModifier;
	
	TowerEarthWind(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.EARTH_WIND, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = getUpgradeTracks()[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[1][3]) {
			baseProjectile = new ProjectilePassThroughTarget(this, splashRadius * passThroughRadiusModifier, passThroughModifier, 1);
		} else {
			baseProjectile = new ProjectileBasic(this);
		}
	}

	@Override
	protected int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = creepManager.findTargetCreep(targetingMode, targetZone, hitsAir);
			if (targetCreep != null) {
				((ProjectileBasic) baseProjectile).setTargetCreep(targetCreep);
				projManager.addProjectile(fireProjectile());
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

	@Override
	protected void adjustClassSpecificBaseStats() {
		this.passThroughRadiusModifier = 0.18f;
		this.passThroughModifier = 0.40f;
		
		//TODO: Set appropriate values for all of the towers, should we do this in TowerType?
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.03f;
		this.qRange = 0.00f;
		
		this.qRadiusModifier = 0.02f;
		this.qModifier = 0.01f; 
	}

	@Override
	protected void adjustClassSpecificQuality() {
		this.passThroughModifier = qModifier * qLevel;
		this.passThroughRadiusModifier = qRadiusModifier * qLevel;
	}
}
