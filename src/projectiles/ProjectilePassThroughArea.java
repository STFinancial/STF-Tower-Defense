package projectiles;

import java.util.HashSet;

import creeps.Creep;
import towers.Tower;
import utilities.Circle;
import utilities.TrigHelper;

//TODO: Should there be a penalty modifier for passed through?
public final class ProjectilePassThroughArea extends Projectile implements TargetsArea {
	private HashSet<Creep> passedThrough;
	private float distanceTraveled;
	private float distanceLimit;
	private float passThroughRadius;
	private boolean doesSplash;
	
	private ProjectilePassThroughArea(Tower parent, Projectile mold, float distanceLimit, float passThroughRadius, boolean doesSplash) {
		super(parent, mold);
		this.passedThrough = new HashSet<Creep>();
		this.distanceTraveled = 0;
		this.distanceLimit = distanceLimit;
		this.passThroughRadius = passThroughRadius;
		this.doesSplash = doesSplash;
	}
	
	public ProjectilePassThroughArea (Tower parent, float distanceLimit, float passThroughRadius, boolean doesSplash) {
		super(parent);
		this.passedThrough = new HashSet<Creep>();
		this.targetArea = new Circle(parent.x, parent.y, 0);
		this.distanceTraveled = 0;
		this.distanceLimit = distanceLimit;
		this.passThroughRadius = passThroughRadius;
		this.doesSplash = doesSplash;
	}
	
	@Override
	public Projectile clone() {
		ProjectilePassThroughArea p = new ProjectilePassThroughArea(parent, this, distanceLimit, passThroughRadius, doesSplash);
		p.targetArea = targetArea.clone();
		return p;
	}

	@Override
	public int update() {
		double xOff = angleCos * currentSpeed;
		double yOff = angleSin * currentSpeed;
		x -= xOff;
		y -= yOff;
		if (level.getMap().isOutside(x, y)) {
			dud = true;
			return -1;
		}
		hitBox.x = x;
		hitBox.y = y;
		distanceTraveled += TrigHelper.pythagDistance(xOff, yOff);
		//TODO: The hits flying will be part of the refactor of the guider I think.
		for (Creep c: guider.getCreepInRange(this, passThroughRadius, parent.hitsAir)) { //TODO: Needs to utilize whether or not it hits air, currently just hits everything
			if (passedThrough.contains(c)) {
				continue;
			}
			c.addAllEffects(creepEffects);
			if (doesSplash) { //TODO: Again, is this the range that we want?
				c.addAllEffects(splashEffects);
			}
			passedThrough.add(c);
		}
		return 0;
	}

	@Override
	public boolean isDone() {
		return distanceTraveled >= distanceLimit || dud; 
	}

	@Override
	public void detonate() {
		return; //TODO: Might want to have an "explosion" at the end of this.
	}

	@Override
	public boolean setTargetArea(float x, float y) {
		targetArea.x = x;
		targetArea.y = y;
		updateAngle();
		return true;
	}

}
