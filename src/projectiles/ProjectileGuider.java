package projectiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import towers.Tower;
import utilities.Circle;
import creeps.Creep;
import levels.Level;
import levels.Updatable;

//TODO: Should be moved into the projectiles class?
//TODO: Eventually this should be taking care of updating the projectiles and calling the proper functions in level
public final class ProjectileGuider implements Updatable {
	private static final ProjectileGuider INSTANCE = new ProjectileGuider();
	private Level level;
	private ArrayList<Projectile> projectiles;
	
	private ProjectileGuider() { projectiles = new ArrayList<Projectile>(); }
	
	public static ProjectileGuider getInstance() {
		return ProjectileGuider.INSTANCE;
	}
	
	public void setLevel(Level level) {
		this.level = level;
		//Need to clear out all the old junk
		projectiles = new ArrayList<Projectile>();
	}
	
	public void add(Projectile p) {
		projectiles.add(p);
	}
	
	//TODO: Using this method ends up duplicating a ton of work.
	//TODO: Could try keeping track of a creep that has been in range, and only when that creep is out of range or dead we search for a new one (good idea)
	public boolean isCreepInRange(Circle area, boolean hitsAir) {
		for (Creep c : level.getCreeps()) {
			if (c.hitBox.intersects(area)) {
				if (hitsAir) {
					return true;
				} else if (!c.isFlying()) {
					return true;
				}
			}
		}
		return false;
	}
	
	//TODO: I don't really want this to use tower directly
	public Creep findTargetCreep(Tower tower, boolean hitsAir) {
		Creep toTarget = null;
		ArrayList<Creep> inRange = new ArrayList<Creep>();
		for (Creep c : level.getCreeps()) {
			if (c.hitBox.intersects(tower.targetZone)) {
				if (hitsAir) {
					inRange.add(c);
				} else if (!c.isFlying()) {
					inRange.add(c);
				}
			}
		}
		switch (tower.targetingType) {
		case FIRST:
			int max = -1;
			for (Creep c : inRange) {
				if (c.currentIndex > max) {
					toTarget = c;
					max = c.currentIndex;
				}
			}
			break;
		case HIGHEST_HEALTH:
			break;
		case LAST:
			break;
		default:
			break;
		}
		return toTarget;
	}
	
	public HashSet<Creep> getCreepInRange(Projectile p, float range, boolean hitsAir) {
		HashSet<Creep> inRange = new HashSet<Creep>();
		Circle splash = new Circle(p.getX(), p.getY(), range);
		for (Creep c : level.getCreeps()) {
			if (c.hitBox.intersects(splash)) {
				if (hitsAir) {
					inRange.add(c);
				} else if (!c.isFlying()) {
					inRange.add(c);
				}
			}
		}
		return inRange;
	}
	
	public HashSet<Creep> getCreepInRange(Circle area, boolean hitsAir) {
		HashSet<Creep> inRange = new HashSet<Creep>();
		for (Creep c: level.getCreeps()) {
			if (c.hitBox.intersects(area)) {
				if (hitsAir) {
					inRange.add(c);
				} else if (!c.isFlying()) {
					inRange.add(c);
				}
			}
		}
		return inRange;
	}
	
	public HashSet<Creep> getOtherCreepInSplashRange(Creep creep, float range, boolean hitsAir) {
		Circle splash = new Circle(creep.xOff + creep.currentVertex.x, creep.yOff + creep.currentVertex.y, range);
		HashSet<Creep> inRange = new HashSet<Creep>();
		for (Creep c: level.getCreeps()) {
			if (c.hitBox.intersects(splash) && c.creepID != creep.creepID) {
				if (hitsAir) {
					inRange.add(c);
				} else if (!c.isFlying()) {
					inRange.add(c);
				}
			}
		}
		return inRange;
	}

	public Creep getSingleCreepInRange(Creep creep, float range, ArrayList<Creep> visited, boolean hitsAir) {
		Circle box = new Circle(creep.xOff + creep.currentVertex.x, creep.yOff + creep.currentVertex.y, range);
		if (visited == null) {
			for (Creep c: level.getCreeps()) {
				if (c.hitBox.intersects(box)) {
					if (hitsAir) {
						return c;
					} else if (!c.isFlying()) {
						return c;
					}
				}
			}
		} else {
			for (Creep c: level.getCreeps()) {
				if (c.hitBox.intersects(box) && c.creepID != creep.creepID && !visited.contains(c)) {
					if (hitsAir) {
						return c;
					} else if (!c.isFlying()) {
						return c;
					}
				}
			}
		}
		return null;
	}

	public HashSet<Creep> getGroundCreepAdjacentToEarth() {
		return level.getGroundCreepAdjacentToEarth();
	}
	
	public Creep getFirstCreepRadially(float x, float y, float radius, float angle, boolean hitsAir) {
		//TODO: Is there a faster method for this? Should I check only the path intersection points?
		float xUnit = (float) Math.cos(angle) * 0.3f;
		float yUnit = (float) Math.sin(angle) * 0.3f;
		float currentX = x - xUnit;
		float currentY = y - yUnit;
		Creep c = null;
		Circle missile = new Circle(currentX, currentY, radius);
		while (level.getMap().isOutside(currentX, currentY) || (c = intersectingCreep(missile, hitsAir)) == null) { //TODO: We need to find a better than n^2 method I think, we will see.
			currentX -= xUnit;
			currentY -= yUnit;
			missile.x = currentX;
			missile.y = currentY;
		}
		return c;
	}
	
	private Creep intersectingCreep(Circle missile, boolean hitsAir) {
		for (Creep c: level.getCreeps()) {
			if (c.hitBox.intersects(missile)) {
				if (hitsAir) {
					return c;
				} else if (!c.isFlying()) {
					return c;
				}
			}
		}
		return null;
	}

	@Override
	public int update() {
		LinkedList<Projectile> detonatedProj = new LinkedList<Projectile>();
		Iterator<Projectile> i = projectiles.iterator();
		Projectile p;
		while (i.hasNext()) {
			p = i.next();
			p.update();
			if (p.isDone()) {
				p.detonate();
				i.remove();
				detonatedProj.add(p);
			}
		}
		level.createProjectileDetonationEvents(detonatedProj);
		return 0;
	}
}
