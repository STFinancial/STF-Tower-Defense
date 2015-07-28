package projectiles;

import java.util.HashSet;

import levels.Level;

import towers.Tower;

import creeps.Creep;

//This is like the tack tower from bloons
public class ProjectileAOE extends Projectile {

	public ProjectileAOE(Tower parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Projectile clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	protected void addSpecificEffects() {
		// TODO Auto-generated method stub

	}

	@Override
	public void detonate(Level level) {
		// TODO Auto-generated method stub
		
	}

}
