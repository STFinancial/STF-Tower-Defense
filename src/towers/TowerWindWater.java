package towers;

import java.util.ArrayList;

import creeps.DamageType;
import levels.Tile;
import projectileeffects.Damage;
import projectileeffects.Explosion;
import projectileeffects.ProjectileEffect;
import projectileeffects.SiphonLife;
import projectiles.ProjectileAOE;
import utilities.GameConstants;

public final class TowerWindWater extends Tower {
	//TODO: Finish implementing this tower
	float maxHealthModifier;
	float goldModifier;
	private float qMaxHealth;
	private float qGold;
	
	float damageOnDeathModifier;
	float explosionRadiusModifier;
	private float qDODModifier;
	private float qDODRange;

	TowerWindWater(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.WIND_WATER, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this);
		projManager.clearAllBasicEffects(baseProjectile);
		projManager.addProjectileEffect(false, baseProjectile, new SiphonLife(maxHealthModifier, DamageType.WATER, baseProjectile, goldModifier));
	
		boolean[][] progress = getUpgradeTracks()[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			
		}
		if (progress[0][3]) {
			
		}
		if (progress[1][2]) {
			ArrayList<ProjectileEffect> dod = new ArrayList<ProjectileEffect>(GameConstants.NUM_DAMAGE_TYPES);
			for (DamageType type: DamageType.values()) {
				Damage d = new Damage(damageOnDeathModifier * damageArray[type.ordinal()], type, baseProjectile);
				dod.add(d);
			}
			projManager.addProjectileEffect(false, baseProjectile, new Explosion(DamageType.PHYSICAL, baseProjectile, explosionRadiusModifier * range, dod));
		}
		if (progress[1][3]) {
			
		}
	}

	@Override
	protected int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			if (projManager.isCreepInRange(targetZone, hitsAir)) {
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
		this.maxHealthModifier = 0.01f;
		this.goldModifier = 0.00005f;
		
		this.damageOnDeathModifier = 0;
		this.explosionRadiusModifier = 0;
		
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.02f;
		this.qRange = 0f;
		
		this.qMaxHealth = 0.002f;
		this.qGold = 0.000003f;
		this.qDODModifier = 0.01f;
		this.qDODRange = 0.02f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		maxHealthModifier += qMaxHealth * qLevel;
		goldModifier += qGold * qLevel;
		damageOnDeathModifier += qDODModifier * qLevel;
		explosionRadiusModifier += qDODRange * qLevel;
	}
}
