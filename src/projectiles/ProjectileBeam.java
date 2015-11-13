package projectiles;

import creeps.Creep;
import towers.Tower;
import utilities.Circle;

//Stops at the first target it hits on the way to its destination
public final class ProjectileBeam extends Projectile implements TargetsArea {
	//TODO will have to add stuff so this animates better later
	
	private ProjectileBeam(Tower parent, Projectile mold, float targetAreaRadius) {
		super(parent, mold);
		this.speed = 0;
		this.currentSpeed = 0;
		this.targetAngle = 0;
	}
	
	public ProjectileBeam(Tower parent, float targetAreaRadius) {
		super(parent);
		this.targetArea = new Circle(parent.getCenterX(), parent.getCenterY(), targetAreaRadius);
		this.speed = 0;
		this.currentSpeed = 0;
		this.targetAngle = 0;
	}
	
	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public boolean setTargetArea(float x, float y) {
		targetArea.x = x;
		targetArea.y = y;
		updateAngle();
		return true;
	}

	@Override
	public Projectile clone() {
		return new ProjectileBeam(parent, this, targetArea.radius);
	}

	@Override
	public int update() {
		return 0;
	}

	@Override
	public void detonate() {
		Creep c = projManager.getFirstCreepRadially(x, y, size, (float) targetAngle, hitsAir);
		if (c != null) {
			c.addAllEffects(creepEffects);
			if (doesSplash) {
				for (Creep splashCreep: projManager.getOtherCreepInSplashRange(c, splashRadius, splashHitsAir)) {
					splashCreep.addAllEffects(splashEffects);
				}
			}
		}
	}
}
