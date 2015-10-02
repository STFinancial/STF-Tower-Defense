package projectiles;

import java.util.HashSet;

import creeps.Creep;
import levels.Level;
import towers.Tower;
import utilities.Circle;
import utilities.GameConstants;

public class ProjectilePassThroughArea extends Projectile implements TargetsArea {
	private Circle targetArea;
	private HashSet<Creep> passedThrough;
	private float distanceTraveled;
	private float distanceLimit;
	private float passThroughRadius;
	private boolean doesSplash;
	
	public ProjectilePassThroughArea (Tower parent, float distanceLimit, float passThroughRadius, boolean doesSplash) {
		super(parent);
		passedThrough = new HashSet<Creep>();
		targetArea = new Circle(parent.x, parent.y, 0);
		distanceTraveled = 0;
		this.distanceLimit = distanceLimit;
		this.passThroughRadius = passThroughRadius;
		this.doesSplash = doesSplash;
	}
	
	@Override
	public Projectile clone() {
		ProjectilePassThroughArea p = new ProjectilePassThroughArea(parent, distanceLimit, passThroughRadius, doesSplash);
		p.targetArea = targetArea.clone();
		return p;
	}

	@Override
	public int update() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDone() {
		if
	}

	@Override
	public void detonate(Level level) {
		return;
	}

	@Override
	public boolean setTargetArea(float x, float y) {
		targetArea.x = x;
		targetArea.y = y;
		return true;
	}

}
