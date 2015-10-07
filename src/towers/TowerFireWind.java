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

	float siphonAuraModifier;
	float siphonAuraRangeModifier;
	
	boolean doesSplash;
	
	public TowerFireWind(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.FIRE_WIND, towerID);
		setTargetArea(centerX, centerY);
		this.passThroughModifier = 0.25f;
		this.passThroughRadiusModifier = 0.10f;
		
		this.siphonAuraModifier = 0;
		this.siphonAuraRangeModifier = 0;
		
		this.doesSplash = false;
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
}
