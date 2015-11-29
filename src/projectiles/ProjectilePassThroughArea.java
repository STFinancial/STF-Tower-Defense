package projectiles;

import java.util.ArrayList;
import java.util.HashSet;

import creeps.Creep;
import levels.LevelManager;
import projectileeffects.ProjectileEffect;
import towers.Tower;
import utilities.Circle;
import utilities.GameConstants;
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
	private int pulseTiming;
	private int counter;
	
	private ProjectilePassThroughArea(Tower parent, Projectile mold, float distanceLimit, float passThroughRadius, float passThroughModifier, int pulseTiming) {
		super(parent, mold);
		this.passedThrough = new HashSet<Creep>();
		this.distanceTraveled = 0;
		this.distanceLimit = distanceLimit;
		this.passThroughRadius = passThroughRadius;
		this.passThroughModifier = passThroughModifier;
		this.pulseTiming = pulseTiming;
		this.counter = 0;
		speed = currentSpeed = GameConstants.BASE_SPEED_PROJECTILE_PASS_THROUGH_AREA;
	}
	
	public ProjectilePassThroughArea (Tower parent, float distanceLimit, float passThroughRadius, float passThroughModifier, int pulseTiming) {
		super(parent);
		this.passedThrough = new HashSet<Creep>();
		this.targetArea = new Circle(towerManager.getCenterX(parent), towerManager.getCenterY(parent), 0);
		this.distanceTraveled = 0;
		this.distanceLimit = distanceLimit;
		this.passThroughRadius = passThroughRadius;
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
		speed = currentSpeed = GameConstants.BASE_SPEED_PROJECTILE_PASS_THROUGH_AREA;
	}
	
	@Override
	public Projectile clone() {
		ProjectilePassThroughArea p = new ProjectilePassThroughArea(parent, this, distanceLimit, passThroughRadius, passThroughModifier, pulseTiming);
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
		if (LevelManager.getInstance().isOutside(x, y)) {
			dud = true;
			return -1;
		}
		hitBox = new Circle(x, y, hitBox.getRadius());
		distanceTraveled += TrigHelper.pythagDistance(xOff, yOff);
		if (++counter % pulseTiming == 0) { //Choosing not to pulse at first firing
			//TODO: Is this the type of circle that we want? Why isn't this just the hitbox?
			for (Creep c: creepManager.getCreepInRange(new Circle(x, y, passThroughRadius), hitsAir)) {
				if (passedThrough.contains(c)) {
					continue;
				}
				creepManager.addAllEffects(c, passThroughCreepEffects);
				if (doesOnHit) {
					creepManager.onProjectileCollision(c);
				}
				//TODO: Do we want the for loop to be inside this if statement instead?
				if (doesSplash) { //TODO: Again, is this the range that we want?
					if (splashHitsAir) {
						creepManager.addAllEffects(c, passThroughSplashEffects);
					} else {
						if (!creepManager.isFlying(c)) {
							creepManager.addAllEffects(c, passThroughSplashEffects);
						}
					}
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
		targetArea = new Circle(x, y, targetArea.getRadius());
		updateAngle();
		return true;
	}

}
