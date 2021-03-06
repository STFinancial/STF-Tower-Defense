package towers;

import projectiles.ProjectileArea;
import projectiles.TargetsArea;
import utilities.Circle;
import levels.Tile;

public final class TowerEarthWater extends Tower implements TargetsArea {
	private Circle targetArea;
	
	float areaRadius;
	private float qAreaRadius;
	
	TowerEarthWater(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.EARTH_WATER, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileArea(this, areaRadius);
		((ProjectileArea) baseProjectile).setTargetArea(targetArea.getX(), targetArea.getY());
	}

	@Override
	public boolean setTargetArea(float x, float y) {
		Circle t = new Circle(x, y, 0);
		if (t.intersects(targetZone) && !levelManager.isOutside(x, y)) {
			targetArea = new Circle(x, y, areaRadius);
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
		this.areaRadius = 2f;
		setTargetArea(centerX, centerY);
		
		//TODO: Set appropriate values for all of the towers, should we do this in TowerType?
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.03f;
		this.qRange = 0.10f;
		
		//TODO: Do I want qAreaRadius?
		this.qAreaRadius = 0;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		areaRadius += qLevel * qAreaRadius;
	}
}
