package towers;

import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.Bleed;
import projectileeffects.Consume;
import projectileeffects.Snare;
import projectiles.ProjectileAOE;
import utilities.GameConstants.UpgradePathType;
import levels.Tile;

public final class TowerWaterWater extends Tower {
	int snareDuration;
	private float qSnareDuration;
	
	int shredDuration;
	float shredModifier;
	int maxShredStacks;
	private float qShredDuration;
	private float qShredModifier;
	private float qShredStacks;
	
	int bleedDuration;
	float bleedModifier;
	int bleedTiming;
	int maxBleedStacks;
	private float qBleedDuration;
	private float qBleedModifier;
	private float qBleedStacks;
	
	float consumeModifier;
	private float qConsume;
	
	TowerWaterWater(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.WATER_WATER, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this);
		if (upgradeHandler.hasPurchasedUpgrade(UpgradePathType.UPPER_PATH, 3)) {
			projManager.addProjectileEffect(false, baseProjectile, new Snare(snareDuration, DamageType.WATER, baseProjectile));
		}
		if (upgradeHandler.hasPurchasedUpgrade(UpgradePathType.LOWER_PATH, 2)) {
			ArmorShred a = new ArmorShred(shredDuration, shredModifier, DamageType.WATER, baseProjectile, false);
			a.setMaxStacks(maxShredStacks);
			projManager.addProjectileEffect(false, baseProjectile, a);
			Bleed b = new Bleed(bleedDuration, damageArray[DamageType.WATER.ordinal()] * bleedModifier, bleedTiming, DamageType.WATER, baseProjectile);
			b.setMaxStacks(maxBleedStacks);
			projManager.addProjectileEffect(false, baseProjectile, b);
		}
		if (upgradeHandler.hasPurchasedUpgrade(UpgradePathType.LOWER_PATH, 3)) {
			projManager.addProjectileEffect(false, baseProjectile, new Consume(damageArray[DamageType.WATER.ordinal()] * consumeModifier, DamageType.WATER, baseProjectile));
		}
	}

	@Override
	protected int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			if (creepManager.isCreepInRange(targetZone, hitsAir)) {
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
		this.snareDuration = 0;
		
		this.shredDuration = 0;
		this.shredModifier = 0;
		this.maxShredStacks = 0;
		
		this.bleedDuration = 0;
		this.bleedModifier = 0;
		this.maxBleedStacks = 0;
		this.bleedTiming = 3;
		
		this.consumeModifier = 0;

		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.02f;
		this.qRange = 0f;
		
		this.qSnareDuration = 0.3f;
		this.qShredDuration = 0.3f;
		this.qShredModifier = 0.02f;
		this.qShredStacks = 0;
		this.qBleedDuration = 0.5f;
		this.qBleedModifier = 0.06f;
		this.qBleedStacks = 0.5f;
		this.qConsume = 0.05f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		snareDuration += (int) (qSnareDuration * qLevel);
		shredDuration += (int) (qShredDuration * qLevel);
		shredModifier += qShredModifier * qLevel;
		maxShredStacks += (int) (qShredStacks * qLevel);
		bleedDuration += (int) (qBleedDuration * qLevel);
		bleedModifier += qBleedModifier * qLevel;
		maxBleedStacks += (int) (qBleedStacks * qLevel);
		consumeModifier += qConsume * qLevel;
	}

}
