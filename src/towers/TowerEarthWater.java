package towers;

import projectiles.ProjectileArea;
import projectiles.TargetsArea;
import utilities.Circle;
import levels.Level;
import maps.Tile;

public class TowerEarthWater extends Tower implements TargetsArea{
	Circle placeToTarget;
	
	public TowerEarthWater(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
		//TODO this is kind of a lazy piece of code, figure out a better default position for the target area
		placeToTarget = targetArea;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileArea(this);
	}

	@Override
	public void setTargetArea(float x, float y) {
		placeToTarget = new Circle(x, y, splashRadius);
	}

}
