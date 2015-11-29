package projectiles;

import creeps.Creep;
import towers.Tower;
import utilities.Circle;
import utilities.GameConstants;

//Stops at the first target between tower and destination
public final class ProjectileBeam extends Projectile implements TargetsArea {
	//TODO will have to add stuff so this animates better later
	//TODO: Want to have one that fires like a projectile basic but stops at the first thing?
	
	private ProjectileBeam(Tower parent, Projectile mold, float targetAreaRadius) {
		super(parent, mold);
		this.speed = 0;
		speed = currentSpeed = GameConstants.BASE_SPEED_PROJECTILE_BEAM;
		this.targetAngle = 0;
	}
	
	public ProjectileBeam(Tower parent, float targetAreaRadius) {
		super(parent);
		this.targetArea = new Circle(towerManager.getCenterX(parent), towerManager.getCenterY(parent), targetAreaRadius);
		this.speed = 0;
		speed = currentSpeed = GameConstants.BASE_SPEED_PROJECTILE_BEAM;
		this.targetAngle = 0;
	}
	
	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public boolean setTargetArea(float x, float y) {
		targetArea = new Circle(x, y, targetArea.getRadius());
		updateAngle();
		return true;
	}

	@Override
	public Projectile clone() {
		return new ProjectileBeam(parent, this, targetArea.getRadius());
	}

	@Override
	public int update() {
		return 0;
	}

	@Override
	public void detonate() {
		Creep c = creepManager.getFirstCreepRadially(x, y, size, (float) targetAngle, hitsAir);
		if (c != null) {
			creepManager.addAllEffects(c, creepEffects);
			if (doesSplash) {
				for (Creep splashCreep: creepManager.getOtherCreepInSplashRange(c, splashRadius, splashHitsAir)) {
					creepManager.addAllEffects(splashCreep, splashEffects);
				}
			}
		}
	}
}
