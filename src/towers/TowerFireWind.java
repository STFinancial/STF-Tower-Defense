package towers;

import creeps.DamageType;
import levels.Tile;
import projectiles.ProjectileArea;
import projectiles.ProjectilePassThroughArea;
import projectiles.TargetsArea;
import towers.TowerManager.TowerEffect;
import utilities.Circle;
import utilities.GameConstants;

public final class TowerFireWind extends Tower implements TargetsArea {
	private Circle targetArea;
	float passThroughRadiusModifier;
	float passThroughModifier;
	private float qPassThroughRadius;
	private float qPassThrough;

	float damageAuraModifier;
	float damageAuraRangeModifier;
	private float qDamageModifier;
	private float qDamageRange;
	
	TowerFireWind(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.FIRE_WIND, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectilePassThroughArea(this, range, passThroughRadiusModifier * range, passThroughModifier, 1); //TODO: Timing is curently 1, but not sure if that's whta I want
		((ProjectilePassThroughArea) baseProjectile).setTargetArea(targetArea.getX(), targetArea.getY());
	}

	@Override
	public boolean setTargetArea(float x, float y) {
		Circle t = new Circle(x, y, 0);
		if (t.intersects(targetZone) && !levelManager.isOutside(x, y)) {
			targetArea = new Circle(x, y, 0);
			((ProjectileArea) baseProjectile).setTargetArea(targetArea.getX(), targetArea.getY());
			return true;
		} else {
			return false;
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
		setTargetArea(centerX, centerY);
		this.passThroughModifier = 0.25f;
		this.passThroughRadiusModifier = 0.10f;
		
		this.damageAuraModifier = 0.02f;
		this.damageAuraRangeModifier = 0.60f;
		
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.02f;
		this.qRange = 0f;
		
		this.qPassThrough = 0.02f;
		this.qPassThroughRadius = 0.006f;
		
		this.qDamageModifier = 0.0015f;
		this.qDamageRange = 0.04f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		passThroughRadiusModifier += qPassThroughRadius * qLevel;
		passThroughModifier += qPassThrough * qLevel;
		
		damageAuraModifier += qDamageModifier * qLevel;
		damageAuraRangeModifier += qDamageRange * qLevel;
	}
	
	@Override
	protected void createAuras() {
		boolean[][] progress = getUpgradeTracks()[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[1][3]) {
			float sum = 0;
			for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
				sum += damageArray[i];
			}
			final float mod = damageAuraModifier * sum;
			TowerEffect effectApplication = (t) -> { 
				for (int i=0;i<GameConstants.NUM_DAMAGE_TYPES;++i) {
					if (i!=DamageType.LIGHT.ordinal() || i!=DamageType.DARK.ordinal()) {
						t.damageArray[i] += mod; //TODO: Is this what I want or just a percentage of the damage array?
					}
				}
			};
			TowerEffect effectRemoval = (t) -> { 
				for (int i=0;i<GameConstants.NUM_DAMAGE_TYPES;++i) {
					if (i!=DamageType.LIGHT.ordinal() || i!=DamageType.DARK.ordinal()) {
						t.damageArray[i] -= mod;
					}
				}
			};
			float auraRange = range * damageAuraRangeModifier;
			towerManager.createAura(this, new Circle(centerX, centerY, auraRange), effectApplication, effectRemoval);
		}
	}
}
