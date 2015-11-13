package creeps;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import levels.GameEventType;
import levels.Level;

public class CreepManager {
	private final static CreepManager INSTANCE = new CreepManager();
	private Level level;
	private LinkedList<Creep> creeps;
	private ArrayList<Wave> creepWaves;
	private Wave currentWave;
	
	private CreepManager() {
		creeps = new LinkedList<Creep>();
	}
	public static CreepManager getInstance() { return INSTANCE; }
	
	public void setLevel(Level level) {
		if (this.level == null || this.level.equals(level)) {
			this.level = level;
			creeps = new LinkedList<Creep>();
		}
	}
	
	public void startRound(int round) {
		currentWave = creepWaves.get(round);
	}
	
	public void updateCreepSpawn(int gameTick) {
		List<Creep> creepsToSpawn = currentWave.getNextCreeps(gameTick);
		for (Creep c: creepsToSpawn) {
			creeps.add(c);
			c.setPath(levelManager.assignPath(c, c.isFlying()));
			game.newEvent(GameEventType.CREEP_SPAWNED, c);
		}
	}
	
	public void updateCreepMovement() {
		
	}
	
}
