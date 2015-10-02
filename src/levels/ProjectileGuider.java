package levels;

import java.util.ArrayList;
import java.util.HashSet;

import projectiles.Projectile;
import utilities.Circle;
import creeps.Creep;

//TODO: What does this class actually do? I want to delegate the work to this class at some point instead of having level do it
public final class ProjectileGuider {
	private static final ProjectileGuider INSTANCE = new ProjectileGuider();
	private Level level;
	
	private ProjectileGuider() { }
	
	public static ProjectileGuider getInstance() {
		return ProjectileGuider.INSTANCE;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public HashSet<Creep> getCreepInRange(Projectile p, float range) {
		return level.getCreepInRange(p, range);
	}
	
	public HashSet<Creep> getCreepInRange(Circle area) {
		return level.getCreepInRange(area);
	}
	
	public HashSet<Creep> getOtherCreepInSplashRange(Creep creep, float range) {
		return level.getOtherCreepInSplashRange(creep, range);
	}

	public Creep getSingleCreepInRange(Creep creep, float range, ArrayList<Creep> visited) {
		return level.getSingleCreepInRange(creep, range, visited);
	}

	public HashSet<Creep> getCreepAdjacentToEarth() {
		return level.getCreepAdjacentToEarth();
	}
	
	public Creep getFirstCreepRadially(float x, float y, float angle) {
		return level.getFirstCreepRadially(x, y, angle);
	}
}
