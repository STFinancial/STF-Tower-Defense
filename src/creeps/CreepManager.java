package creeps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import game.Game;
import game.GameEventType;
import levels.LevelManager;

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
	
}
