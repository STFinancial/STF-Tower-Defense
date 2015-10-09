package towers;

import levels.Level;
import maps.Tile;
import projectiles.ProjectileArea;
import projectiles.ProjectilePassThroughArea;
import projectiles.TargetsArea;
import utilities.Circle;

public final class TowerFireWind extends Tower implements TargetsArea {
	private Circle targetArea;
	float passThroughRadiusModifier;
	float passThroughModifier;
	private float qPassThroughRadius;
	private float qPassThrough;

	float siphonAuraModifier;
	float siphonAuraRangeModifier;
	float qSiphonModifier;
	float qSiphonRange;
	
	boolean doesSplash;
	
	public TowerFireWind(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.FIRE_WIND, towerID);
		adjustClassSpecificBaseStats();
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectilePassThroughArea(this, range, passThroughRadiusModifier * range, passThroughModifier, doesSplash, 1); //TODO: Timing is curently 1, but not sure if that's whta I want
		((ProjectilePassThroughArea) baseProjectile).setTargetArea(targetArea.x, targetArea.y);
	}

	@Override
	public boolean setTargetArea(float x, float y) {
		Circle t = new Circle(x, y, 0);
		if (t.intersects(targetZone) && !level.getMap().isOutside(x, y)) {
			targetArea = new Circle(x, y, 0);
			((ProjectileArea) baseProjectile).setTargetArea(targetArea.x, targetArea.y);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			level.addProjectile(fireProjectile());
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
		
		this.siphonAuraModifier = 0.02f;
		this.siphonAuraRangeModifier = 0.60f;
		
		this.doesSplash = false;
		
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
		
		this.qSiphonModifier = 0.0015f;
		this.qSiphonRange = 0.04f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		passThroughRadiusModifier += qPassThroughRadius * qLevel;
		passThroughModifier += qPassThrough * qLevel;
	}
}
