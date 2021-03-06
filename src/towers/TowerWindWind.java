package towers;

import creeps.DamageType;
import projectileeffects.Grounding;
import projectileeffects.Hasting;
import projectileeffects.SpeedDamage;
import projectileeffects.Wealth;
import projectiles.ProjectileBasic;
import utilities.GameConstants.UpgradePathType;
import levels.Tile;

public final class TowerWindWind extends Tower {
	float hastingModifier;
	int hastingDuration;
	int maxHastingStacks;
	private float qHastingModifier;
	private float qHastingDuration;
	private float qHastingStacks;
	
	float wealthModifier;
	int wealthDuration;
	int maxWealthStacks;
	private float qWealthModifier;
	private float qWealthDuration;
	private float qWealthStacks;
	
	float groundingModifier;
	float speedDamageModifier;
	private float qGrounding;
	private float qSpeedDamage;
	
	
	TowerWindWind(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.WIND_WIND, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
		if (upgradeHandler.hasPurchasedUpgrade(UpgradePathType.UPPER_PATH, 2)) {
			Hasting h = new Hasting(hastingDuration, hastingModifier, DamageType.WIND, baseProjectile);
			h.setMaxStacks(maxHastingStacks);
			projManager.addProjectileEffect(false, baseProjectile, h);
		}
		if (upgradeHandler.hasPurchasedUpgrade(UpgradePathType.UPPER_PATH, 3)) {
			Wealth w = new Wealth(wealthDuration, wealthModifier, DamageType.WIND, baseProjectile, true, true);
			w.setMaxStacks(maxWealthStacks);
			projManager.addProjectileEffect(false, baseProjectile, w);
		}
		if (upgradeHandler.hasPurchasedUpgrade(UpgradePathType.LOWER_PATH, 2)) {
			projManager.addProjectileEffect(false, baseProjectile, new Grounding(groundingModifier * damageArray[DamageType.PHYSICAL.ordinal()], DamageType.PHYSICAL, baseProjectile));
		}
		if (upgradeHandler.hasPurchasedUpgrade(UpgradePathType.LOWER_PATH, 3)) {
			projManager.addProjectileEffect(false, baseProjectile, new SpeedDamage(speedDamageModifier * damageArray[DamageType.WIND.ordinal()], DamageType.WIND, baseProjectile));
			projManager.addProjectileEffect(false, baseProjectile, new SpeedDamage(speedDamageModifier * damageArray[DamageType.PHYSICAL.ordinal()], DamageType.PHYSICAL, baseProjectile));
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
		this.hastingModifier = 0;
		this.hastingDuration= 0;
		this.maxHastingStacks = 0;
		
		this.wealthModifier = 0;
		this.wealthDuration = 0;
		this.maxWealthStacks = 0;
		
		this.groundingModifier = 0;
		this.speedDamageModifier = 1000f;
		
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.02f;
		this.qRange = 0f;
		
		this.qHastingModifier = 0.02f;
		this.qHastingDuration = 0.6f;
		this.qHastingStacks = 0;
		this.qWealthModifier = 0.25f;
		this.qWealthDuration = 1f;
		this.qWealthStacks = 0.3f;
		this.qGrounding = 0.1f;
		this.qSpeedDamage = 1f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		hastingModifier += qHastingModifier * qLevel;
		hastingDuration += (int) (qHastingDuration * qLevel);
		maxHastingStacks += (int) (qHastingStacks * qLevel);
		wealthModifier += qWealthModifier * qLevel;
		wealthDuration += (int) (qWealthDuration * qLevel);
		maxWealthStacks += (int) (qWealthStacks * qLevel);
		groundingModifier += qGrounding  * qLevel;
		speedDamageModifier += qSpeedDamage * qLevel;
	}

}
