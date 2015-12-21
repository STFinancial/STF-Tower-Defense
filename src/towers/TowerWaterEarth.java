package towers;

import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.PermaSlow;
import projectiles.ProjectileAOE;
import utilities.GameConstants.UpgradePathType;
import levels.Tile;

public final class TowerWaterEarth extends Tower {
	float shredModifier;
	int shredDuration;
	int maxShredStacks;
	private float qShredModifier;
	private float qShredDuration;
	private float qShredStacks;
	
	float permaSlowModifier;
	private float qPermaSlow;
	
	TowerWaterEarth(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.WATER_EARTH, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this);
		if (upgradeHandler.hasPurchasedUpgrade(UpgradePathType.UPPER_PATH, 2)) {
			ArmorShred a = new ArmorShred(shredDuration, shredModifier, DamageType.PHYSICAL, baseProjectile, false);
			a.setMaxStacks(maxShredStacks);
			projManager.addProjectileEffect(false, baseProjectile, a);
		}
		if (upgradeHandler.hasPurchasedUpgrade(UpgradePathType.LOWER_PATH, 3)) {
			projManager.addProjectileEffect(false, baseProjectile, new PermaSlow(permaSlowModifier, DamageType.EARTH, baseProjectile, true));
		}
	}

	@Override
	protected int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			projManager.addProjectile(fireProjectile());
			attackCarryOver += 1 - currentAttackCooldown;
			currentAttackCooldown = attackCooldown;
			if (attackCarryOver > 1) {
				attackCarryOver -= 1;
				currentAttackCooldown--;
			}
			return 1;
		}
		return 0;
	}

	@Override
	protected void adjustClassSpecificBaseStats() {
		this.maxShredStacks = 0;
		this.shredModifier = 0;
		this.shredDuration = 0;
		this.permaSlowModifier = 0.005f;
		
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.03f;
		this.qRange = 0.00f;
		
		this.qShredModifier = 0.03f;
		this.qShredDuration = 0.9f;
		this.qShredStacks = 0;
		
		this.qPermaSlow = 0.0004f;
		
	}
	
	//TODO: Do we want these q modifiers to be multiplicative in a lot of cases?

	@Override
	protected void adjustClassSpecificQuality() {
		shredModifier += qShredModifier * qLevel;
		shredDuration += (int) (qShredDuration * qLevel);
		maxShredStacks += (int) (qShredStacks * qLevel);
		
		permaSlowModifier += qPermaSlow * qLevel;
	}
}
