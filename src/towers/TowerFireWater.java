package towers;

import java.util.ArrayList;

import creeps.Creep;
import creeps.DamageType;

import projectileeffects.MaxHealthDamage;
import projectileeffects.Nullify;
import projectileeffects.ProjectileEffect;
import projectiles.ProjectileBasic;
import projectiles.ProjectileEffectPatch;
import projectiles.ProjectilePassThroughTarget;
import utilities.GameConstants;
import levels.Tile;

public final class TowerFireWater extends Tower {
	float passThroughRadiusModifier;
	float passThroughModifier;
	private float qRadiusModifier;
	private float qPassModifier;
	
	int patchLifetime;
	int patchTiming;
	float patchMaxHealthModifier;
	private float qPatchLifetime;
	private float qPatchModifier;
	
	TowerFireWater(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.FIRE_WATER, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = getUpgradeTracks()[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			baseProjectile = new ProjectilePassThroughTarget(this, passThroughRadiusModifier * splashRadius, passThroughModifier, 1);
		} else if (progress[1][2]) {
			ArrayList<ProjectileEffect> effects = new ArrayList<ProjectileEffect>();
			effects.add(new MaxHealthDamage(patchMaxHealthModifier * damageArray[DamageType.WATER.ordinal()], DamageType.WATER, baseProjectile));
			effects.add(new MaxHealthDamage(patchMaxHealthModifier * damageArray[DamageType.FIRE.ordinal()], DamageType.FIRE, baseProjectile));
			if (progress[1][3]) {
				effects.add(new Nullify(DamageType.WATER, baseProjectile));
			}
			baseProjectile = new ProjectileEffectPatch(this, patchLifetime, patchTiming, splashRadius, effects);
		} else {
			baseProjectile = new ProjectileBasic(this);
		}
		if (progress[0][2]) {
			projManager.setIgnoresShield(baseProjectile, true);
			for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
				projManager.setResistPenPercent(baseProjectile, DamageType.values()[i], 1);
			}
			projManager.setToughPenPercent(baseProjectile, 1);
		}
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
			}
			return 1;
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
		
		this.passThroughRadiusModifier = 0;
		this.passThroughModifier = 0;
		this.qRadiusModifier = 0.02f;
		this.qPassModifier = 0.03f;
		
		this.patchLifetime = 0;
		this.patchTiming = 0;
		this.patchMaxHealthModifier = 0;
		this.qPatchLifetime = 0.8f;
		this.qPatchModifier = 0.000005f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		passThroughRadiusModifier += qRadiusModifier * qLevel;
		passThroughModifier += qPassModifier * qLevel;
		
		patchLifetime += (int) (qPatchLifetime * qLevel);
		patchMaxHealthModifier += qPatchModifier * qLevel;
	}
}
