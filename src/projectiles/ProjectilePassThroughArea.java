package projectiles;

import java.util.ArrayList;
import java.util.HashSet;

import creeps.Creep;
import projectileeffects.ProjectileEffect;
import towers.Tower;
import utilities.Circle;
import utilities.TrigHelper;

//TODO: Should there be a penalty modifier for passed through?
public final class ProjectilePassThroughArea extends Projectile implements TargetsArea {
	private HashSet<Creep> passedThrough;
	private ArrayList<ProjectileEffect> passThroughCreepEffects;
	private ArrayList<ProjectileEffect> passThroughSplashEffects;
	private float passThroughModifier;
	private float distanceTraveled;
	private float distanceLimit;
	private float passThroughRadius;
	private boolean doesSplash;
	private int pulseTiming;
	private int counter;
	
	private ProjectilePassThroughArea(Tower parent, Projectile mold, float distanceLimit, float passThroughRadius, float passThroughModifier, boolean doesSplash, int pulseTiming) {
		super(parent, mold);
		this.passedThrough = new HashSet<Creep>();
		this.distanceTraveled = 0;
		this.distanceLimit = distanceLimit;
		this.passThroughRadius = passThroughRadius;
		this.doesSplash = doesSplash;
		this.passThroughModifier = passThroughModifier;
		this.pulseTiming = pulseTiming;
		this.counter = 0;
	}
	
	public ProjectilePassThroughArea (Tower parent, float distanceLimit, float passThroughRadius, float passThroughModifier, boolean doesSplash, int pulseTiming) {
		super(parent);
		this.passedThrough = new HashSet<Creep>();
		this.targetArea = new Circle(parent.centerX, parent.centerY, 0);
		this.distanceTraveled = 0;
		this.distanceLimit = distanceLimit;
		this.passThroughRadius = passThroughRadius;
		this.doesSplash = doesSplash;
		this.passThroughModifier = passThroughModifier;
		this.pulseTiming = pulseTiming;
		this.counter = 0;
		for (ProjectileEffect e: creepEffects) {
			passThroughCreepEffects.add(e.clone().applyPenalty(passThroughModifier));
		}
		passThroughSplashEffects = new ArrayList<ProjectileEffect>(splashEffects.size());
		for (ProjectileEffect e: splashEffects) {
			passThroughSplashEffects.add(e.clone().applyPenalty(passThroughModifier));
		}
	}
	
	@Override
	public Projectile clone() {
		ProjectilePassThroughArea p = new ProjectilePassThroughArea(parent, this, distanceLimit, passThroughRadius, passThroughModifier, doesSplash, pulseTiming);
		p.passThroughCreepEffects = passThroughCreepEffects;
		p.passThroughSplashEffects = passThroughSplashEffects;
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
		if (++counter % pulseTiming == 0) { //Choosing not to pulse at first firing
			for (Creep c: guider.getCreepInRange(this, passThroughRadius, parent.hitsAir)) {
				if (passedThrough.contains(c)) {
					continue;
				}
				c.addAllEffects(passThroughCreepEffects);
				if (doesSplash) { //TODO: Again, is this the range that we want?
					//TODO: Does not utilize the splashHitsAir variable.
					c.addAllEffects(passThroughSplashEffects);
				}
				passedThrough.add(c);
			}
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
