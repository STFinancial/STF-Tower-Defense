package projectiles;

import java.util.HashSet;

import levels.Level;

import creeps.Creep;
import towers.Tower;
import utilities.Circle;

public class ProjectileArea extends Projectile implements TargetsArea {
	Circle targetArea;
	float radius;
	public boolean doesSplash;
	
	public ProjectileArea(Tower parent, float radius) {
		super(parent);
		this.radius = radius;
		doesSplash = false;
	}

	private ProjectileArea() {
		
	}

	@Override
	public Projectile clone() {
		ProjectileArea p = new ProjectileArea();
		p.targetArea = targetArea;
		p.doesSplash = doesSplash;
		return p;
	}

	@Override
	public int update() {
		return 0;
	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public void detonate(Level level) {
		HashSet<Creep> creepInRange = level.getCreepInRange(targetArea);
		for (Creep c: creepInRange) {
			c.addAllEffects(creepEffects);
			if (doesSplash) {
				//TODO can change this to normal splash if needed
				c.addAllEffects(splashEffects);
			}
		}
	}
	
	public void setTargetRadius(float radius) {
		this.radius = radius;
	}

	@Override
	public boolean setTargetArea(float x, float y) {
		targetArea = new Circle(x, y, radius);
		return true;
	}

}
