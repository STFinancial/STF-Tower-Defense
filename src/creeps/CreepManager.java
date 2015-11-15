package creeps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import game.Game;
import game.GameEventType;
import levels.LevelManager;
import projectileeffects.ProjectileEffect;
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
	}
	
	public boolean isRoundFinished() {
		return creeps.size() == 0 && currentWave.stillSpawning();
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
		while (it.hasNext()) {
			c = it.next();
			if (c.isDead()) {
				game.newEvent(GameEventType.CREEP_KILLED, c);
				List<Creep> deathrattleChildren = c.onDeath();
				for (Creep drc: deathrattleChildren) {
					drc.setPath(c.path, c.currentIndex);
					creeps.add(drc);
					game.newEvent(GameEventType.CREEP_SPAWNED, drc);
				}
			}
		}
	}
	
	//General methods that are one line delegaters.
	public void addAllEffects(Creep creep, ArrayList<ProjectileEffect> effects) { creep.addAllEffects(effects); }
	public void addDeathrattleEffect(Creep creep, ProjectileEffect effect, Circle area) { creep.addDeathrattleEffect(effect, area); }
	public void addDeathrattleEffect(Creep creep, ProjectileEffect effect, Circle area, int duration) { creep.addDeathrattleEffect(effect, area, duration); }
	public void addEffect(Creep creep, ProjectileEffect effect) { creep.addEffect(effect); }
	public void consumeBleeds(Creep creep, float amount) { creep.consumeBleeds(amount); }
	public void damage(Creep creep, DamageType type, float amount, float penPercent, float penFlat, boolean ignoresShield, float shieldDrainModifier, float toughPenPercent, float toughPenFlat) { creep.damage(type, amount, penPercent, penFlat, ignoresShield, shieldDrainModifier, toughPenPercent, toughPenFlat); }
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

	
}
