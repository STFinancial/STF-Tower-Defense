package projectiles;

import java.util.HashSet;
import levels.Level;
import towers.Tower;
import creeps.Creep;

//TODO: Should there be a penalty modifier.

//This one pulses damage as it moves along its path
//This differs from ProjectilePassThroughArea in that we can control it's timing and it can "explode" at the end?
public class ProjectilePulse extends Projectile implements TargetsCreep {

	public ProjectilePulse(Tower parent, int pulseTiming) {
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
	public void detonate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTargetCreep(Creep c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Creep getTargetCreep() {
		// TODO Auto-generated method stub
		return null;
	}

}
