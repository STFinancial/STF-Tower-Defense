package towers;

import projectiles.ProjectileArea;
import projectiles.TargetsArea;
import utilities.Circle;
import utilities.GameConstants;
import levels.Level;
import maps.Tile;

public class TowerEarthWater extends Tower implements TargetsArea {
	private Circle targetArea;
	private float areaRadius;
	private boolean doesSplash;
	private boolean doesOnHit;
	
	public TowerEarthWater(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH_WATER, towerID);
		//TODO this is kind of a lazy piece of code, figure out a better default position for the target area
		areaRadius = 2f;
		setTargetArea(x, y);
		this.doesOnHit = false;
		this.doesSplash = false;
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			doesSplash = true;
		}
		baseProjectile = new ProjectileArea(this, doesSplash, doesOnHit, areaRadius);
		((ProjectileArea) baseProjectile).setTargetArea(targetArea.x, targetArea.y);
	}

	@Override
	public boolean setTargetArea(float x, float y) {
		Circle t = new Circle(x, y, 0);
		if (t.intersects(targetZone) && !level.map.isOutside(x, y)) {
			targetArea = new Circle(x, y, areaRadius);
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

}
