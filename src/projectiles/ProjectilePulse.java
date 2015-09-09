package projectiles;

import java.util.HashSet;

import levels.Level;

import towers.Tower;

import creeps.Creep;

//This one pulses damage as it moves along its path
public class ProjectilePulse extends Projectile {

	public ProjectilePulse(Tower parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Projectile clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void detonate(Level level) {
		// TODO Auto-generated method stub

	}

}
