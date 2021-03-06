package towers;

import projectileeffects.Snare;
import projectiles.ProjectileChain;
import utilities.GameConstants.UpgradePathType;
import creeps.Creep;
import creeps.DamageType;
import levels.Tile;

public final class TowerWindFire extends Tower {
	int maxChains;
	private float qMaxChains;
	
	float chainPenalty;
	int snareDuration;
	private float qChainPenalty;
	private float qSnareDuration;
	
	boolean noDuplicates;
	
	float shieldDrainModifier;
	private float qShieldDrain;
	
	TowerWindFire(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.WIND_FIRE, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileChain(this, maxChains, (float) Math.sqrt(range), chainPenalty, noDuplicates);
		projManager.setShieldDrainModifier(baseProjectile, shieldDrainModifier);
		if (upgradeHandler.hasPurchasedUpgrade(UpgradePathType.LOWER_PATH, 3)) {
			projManager.addProjectileEffect(false, baseProjectile, new Snare(snareDuration, DamageType.WIND, baseProjectile));
		}
	}
	
	@Override
	protected int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = creepManager.findTargetCreep(targetingMode, targetZone, hitsAir);
			if (targetCreep != null) {
				((ProjectileChain) baseProjectile).setTargetCreep(targetCreep);
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
		this.maxChains = 3;
		this.chainPenalty = 0.75f;
		this.snareDuration = 0;
		this.noDuplicates = true;
		this.shieldDrainModifier = 1;
		
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.02f;
		this.qRange = 0f;
		
		this.qMaxChains = 0.2f;
		this.qChainPenalty = 0.01f;
		this.qSnareDuration = 0.25f;
		this.qShieldDrain = 0.05f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		maxChains += (int) (qMaxChains * qLevel);
		chainPenalty += qChainPenalty * qLevel;
		snareDuration += (int) (qSnareDuration * qLevel);
		shieldDrainModifier += qShieldDrain * qLevel;
	}
}
