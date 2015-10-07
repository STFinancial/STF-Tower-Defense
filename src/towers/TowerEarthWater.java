package towers;

import projectiles.ProjectileArea;
import projectiles.TargetsArea;
import utilities.Circle;
import levels.Level;
import maps.Tile;

public final class TowerEarthWater extends Tower implements TargetsArea {
	private Circle targetArea;
	
	float areaRadius;
	boolean doesSplash;
	boolean doesOnHit;
	
	public TowerEarthWater(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH_WATER, towerID);
		setTargetArea(centerX, centerY);
		adjustClassSpecificBaseStats();
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileArea(this, doesSplash, doesOnHit, areaRadius);
		((ProjectileArea) baseProjectile).setTargetArea(targetArea.x, targetArea.y);
	}

	@Override
	public boolean setTargetArea(float x, float y) {
		Circle t = new Circle(x, y, 0);
		if (t.intersects(targetZone) && !level.getMap().isOutside(x, y)) {
			targetArea = new Circle(x, y, areaRadius);
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
		this.areaRadius = 2f;
		
		this.doesOnHit = false;
		this.doesSplash = false;
	}

}
