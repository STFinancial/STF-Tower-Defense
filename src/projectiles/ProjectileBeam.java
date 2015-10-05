package projectiles;

import creeps.Creep;
import towers.Tower;
import utilities.Circle;

//Stops at the first target it hits on the way to its destination
public final class ProjectileBeam extends Projectile implements TargetsArea {
	//TODO will have to add stuff so this animates better later
	private boolean doesSplash;
	
	private ProjectileBeam(Tower parent, Projectile mold, float targetAreaRadius, boolean doesSplash) {
		super(parent, mold);
		this.targetArea = new Circle(parent.x, parent.y, targetAreaRadius);
		this.speed = 0;
		this.currentSpeed = 0;
		this.targetAngle = 0;
		this.doesSplash = doesSplash;
	}
	
	public ProjectileBeam(Tower parent, float targetAreaRadius, boolean doesSplash) {
		super(parent);
		this.targetArea = new Circle(parent.x, parent.y, targetAreaRadius);
		this.speed = 0;
		this.currentSpeed = 0;
		this.targetAngle = 0;
		this.doesSplash = doesSplash;
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
		return new ProjectileBeam(parent, this, targetArea.radius, doesSplash);
	}

	@Override
	public int update() {
		return 0;
	}

	@Override
	public void detonate() {
		Creep c = guider.getFirstCreepRadially(x, y, size, (float) targetAngle, parent.hitsAir);
		if (c != null) {
			c.addAllEffects(creepEffects);
			if (doesSplash) {
				for (Creep splashCreep: guider.getOtherCreepInSplashRange(c, splashRadius, parent.hitsAir || parent.splashHitsAir)) {
					splashCreep.addAllEffects(splashEffects);
				}
			}
		}
	}
}
