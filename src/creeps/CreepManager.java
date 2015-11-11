package creeps;

import java.util.ArrayList;

import levels.Level;
import levels.Updatable;

public class CreepManager implements Updatable {
	private final static CreepManager INSTANCE = new CreepManager();
	private Level level;
	private ArrayList<Creep> creeps;
	
	private CreepManager() {
		creeps = new ArrayList<Creep>();
	}
	public static CreepManager getInstance() { return INSTANCE; }
	
	public void setLevel(Level level) {
		if (this.level == null || this.level.equals(level)) {
			this.level = level;
			creeps = new ArrayList<Creep>();
		}
	}
	
	@Override
	public int update() {
		// TODO Auto-generated method stub
		return 0;
	}
}
