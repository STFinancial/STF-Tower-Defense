package creeps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import game.Game;
import game.GameEventType;
import levels.LevelManager;
import projectileeffects.ProjectileEffect;
import towers.TargetingModeType;
import utilities.Circle;

public class CreepManager {
	private final static CreepManager INSTANCE = new CreepManager();
	private LevelManager levelManager;
	private Game game;
	private LinkedList<Creep> creeps;
	private ArrayList<Wave> creepWaves;
	private Wave currentWave;
	
	private CreepManager() { }
	
	public static CreepManager getInstance() { return INSTANCE; }
	
	public void initialize(Game game) {
		this.game = game;
		this.creeps = new LinkedList<Creep>();
		this.levelManager = LevelManager.getInstance();
	}
	
	public void startRound(int round) {
		currentWave = creepWaves.get(round);
		creeps.clear();
	}
	
	public void setCreepWaves(ArrayList<Wave> creepWaves) {
		if (creepWaves == null) {
			this.creepWaves = new ArrayList<Wave>();
			return;
		}
		this.creepWaves = creepWaves;
	}
	
	public boolean isRoundFinished() {
		return creeps.size() == 0 && !currentWave.stillSpawning();
	}
	
	public void updateCreepSpawn(int gameTick) {
		List<Creep> creepsToSpawn = currentWave.getNextCreeps(gameTick);
		for (Creep c: creepsToSpawn) {
			creeps.add(c);
			c.setPath(levelManager.getPath(c.isFlying()));
			game.newEvent(GameEventType.CREEP_SPAWNED, c);
		}
	}
	
	public void updateCreepMovement() {
		Iterator<Creep> it = creeps.iterator();
		Creep c;
		while (it.hasNext()) {
			c = it.next();
			c.update();
			if (c.currentVertex.isFinish()) {
				levelManager.reduceHealth(c.getCurrentHealthCost());
				game.newEvent(GameEventType.CREEP_ESCAPED, c);
				it.remove();
			}
		}
	}
	
	public void updateDeadCreep() {
		Iterator<Creep> it = creeps.iterator();
		Creep c;
		LinkedList<Creep> toAdd = new LinkedList<Creep>();
		while (it.hasNext()) {
			c = it.next();
			if (c.isDead()) {
				game.newEvent(GameEventType.CREEP_KILLED, c);
				List<Creep> deathrattleChildren = c.onDeath();
				for (Creep drc: deathrattleChildren) {
					drc.setPath(c.path, c.currentIndex);
					toAdd.add(drc);
				}
				it.remove();
			}
		}
		for (Creep drc: toAdd) {
			creeps.add(drc);
			game.newEvent(GameEventType.CREEP_SPAWNED, drc);
		}
		if (isRoundFinished()) {
			game.newEvent(GameEventType.ROUND_FINISHED, game);
		}
	}
	
	//General methods that are one line delegaters.
	public void addAllEffects(Creep creep, ArrayList<ProjectileEffect> effects) { creep.addAllEffects(effects); }
	public void addDeathrattleEffect(Creep creep, ProjectileEffect effect, Circle area, boolean hitsAir) { creep.addDeathrattleEffect(effect, area, hitsAir); }
	public void addDeathrattleEffect(Creep creep, ProjectileEffect effect, Circle area, int duration, boolean hitsAir) { creep.addDeathrattleEffect(effect, area, duration, hitsAir); }
	public void addEffect(Creep creep, ProjectileEffect effect) { creep.addEffect(effect); }
	public void consumeBleeds(Creep creep, float amount) { creep.consumeBleeds(amount); }
	public void damage(Creep creep, DamageType type, float amount, float penPercent, float penFlat, boolean ignoresShield, float shieldDrainModifier, float toughPenPercent, float toughPenFlat) { creep.damage(type, amount, penPercent, penFlat, ignoresShield, shieldDrainModifier, toughPenPercent, toughPenFlat); }
	public void disorient(Creep creep, int lifetime) { creep.disorient(lifetime); }
	public void ground(Creep creep) { creep.ground(); }
	public void increaseDamageOnHit(Creep creep, DamageType type, float amount) { creep.increaseDamageOnHit(type, amount); }
	public void increaseDamageResist(Creep creep, DamageType type, float amount, boolean isFlat) { creep.increaseDamageResist(type, amount, isFlat); }
	public void increaseGoldOnHit(Creep creep, float amount) { creep.increaseGoldOnHit(amount); }
	public void increaseGoldValue(Creep creep, float amount, boolean isFlat) { creep.increaseGoldValue(amount, isFlat); }
	public void increaseHasting(Creep creep, DamageType type, float amount) { creep.increaseHasting(type, amount); }
	public void increaseToughness(Creep creep, float amount, boolean isFlat) { creep.increaseToughness(amount, isFlat);	}
	public void knockup(Creep creep, int duration) { creep.knockup(duration); }
	public void nullify(Creep creep) { creep.nullify(); }
	public void reduceDamageOnHit(Creep creep, DamageType type, float amount) { creep.reduceDamageOnHit(type, amount); }
	public void reduceDamageResist(Creep creep, DamageType type, float amount, boolean isFlat) { creep.reduceDamageResist(type, amount, isFlat); }
	public void reduceGoldOnHit(Creep creep, float amount) { creep.reduceGoldOnHit(amount); }
	public void reduceGoldValue(Creep creep, float amount, boolean isFlat) { creep.reduceGoldValue(amount, isFlat); }
	public void reduceHasting(Creep creep, DamageType type, float amount) { creep.reduceHasting(type, amount); }
	public void reduceMaxSpeed(Creep creep, DamageType type, float amount, boolean isFlat) { creep.reduceMaxSpeed(type, amount, isFlat); }
	public void reduceToughness(Creep creep, float amount, boolean isFlat) { creep.reduceToughness(amount, isFlat);	}
	public void slow(Creep creep, DamageType type, float amount) { creep.slow(type, amount); }
	public void snare(Creep creep, int duration) { creep.snare(duration); }
	public void suppressDeathrattle(Creep creep, DamageType type, float modifier, int lifetime) { creep.suppressDeathrattle(type, modifier, lifetime); }
	public void suppressDisruption(Creep creep, DamageType type, float amount, boolean isFlat) { creep.suppressDisruption(type, amount, isFlat); }
	public void unslow(Creep creep, DamageType type, float amount) { creep.unslow(type, amount); }
	public void unsuppressDisruption(Creep creep, DamageType type, float amount, boolean isFlat) { creep.unsuppressDisruption(type, amount, isFlat); }

	//Public getter methods
	public float getCurrentSize(Creep c) { return c.getCurrentSize(); }
	public float getCurrentSpeed(Creep c) { return c.getCurrentSpeed(); }
	public float getCurrentHealthCost(Creep c) { return c.getCurrentHealthCost(); }
	public float getMaxHealth(Creep c) { return c.getMaxHealth(); }
	public float getX(Creep c) { return c.getX(); }
	public float getY(Creep c) { return c.getY(); }
	public boolean intersects(Creep c, Circle area) { return c.intersects(area); }
	public boolean isDead(Creep c) { return c.isDead(); }
	public boolean isFlying(Creep c) { return c.isFlying(); }
	
	public void onProjectileCollision(Creep c) { c.onProjectileCollision(); }

	
	//TODO: Using this method ends up duplicating a ton of work.
	//TODO: Could try keeping track of a creep that has been in range, and only when that creep is out of range or dead we search for a new one (good idea)
	public boolean isCreepInRange(Circle area, boolean hitsAir) {
		for (Creep c : creeps) {
			if (c.intersects(area)) {
				if (hitsAir) {
					return true;
				} else if (!c.isFlying()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Creep findTargetCreep(TargetingModeType targetingMode, Circle targetZone, boolean hitsAir) {
		Creep toTarget = null;
		ArrayList<Creep> inRange = new ArrayList<Creep>();
		for (Creep c : creeps) {
			if (c.intersects(targetZone)) {
				if (hitsAir) {
					inRange.add(c);
				} else if (!c.isFlying()) {
					inRange.add(c);
				}
			}
		}
		switch (targetingMode) {
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
	
	//TODO: Move away from hash sets here because iteration is too slow
	public HashSet<Creep> getCreepInRange(Circle area, boolean hitsAir) {
		HashSet<Creep> inRange = new HashSet<Creep>();
		for (Creep c: creeps) {
			if (c.intersects(area)) {
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
		Circle splash = new Circle(creep.xOff + creep.currentVertex.getX(), creep.yOff + creep.currentVertex.getY(), range);
		HashSet<Creep> inRange = new HashSet<Creep>();
		for (Creep c: creeps) {
			if (c.intersects(splash) && !c.equals(creep)) {
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
		Circle box = new Circle(creep.xOff + creep.currentVertex.getX(), creep.yOff + creep.currentVertex.getY(), range);
		if (visited == null) {
			for (Creep c: creeps) {
				if (c.intersects(box)) {
					if (hitsAir) {
						return c;
					} else if (!c.isFlying()) {
						return c;
					}
				}
			}
		} else {
			for (Creep c: creeps) {
				if (c.intersects(box) && !c.equals(creep) && !visited.contains(c)) {
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
	
	public Creep getFirstCreepRadially(float x, float y, float radius, float angle, boolean hitsAir) {
		//TODO: Is there a faster method for this? Should I check only the path intersection points?
		float xUnit = (float) Math.cos(angle) * 0.3f;
		float yUnit = (float) Math.sin(angle) * 0.3f;
		float currentX = x - xUnit;
		float currentY = y - yUnit;
		Creep c = null;
		while (levelManager.isOutside(currentX, currentY) || (c = intersectingCreep(new Circle(currentX, currentY, radius), hitsAir)) == null) { //TODO: We need to find a better than n^2 method I think, we will see.
			currentX -= xUnit;
			currentY -= yUnit;
		}
		return c;
	}
	
	private Creep intersectingCreep(Circle missile, boolean hitsAir) {
		for (Creep c: creeps) {
			if (c.intersects(missile)) {
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
	public String toString() {
		String s = new String(creeps.size() + " Creeps remaining.\n");
		for (Creep c: creeps) {
			s = s.concat(c.toString() + "\n");
		}
		return s;
	}
}
