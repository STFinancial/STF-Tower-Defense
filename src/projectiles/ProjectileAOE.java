package projectiles;

import java.util.HashSet;

import levels.Level;

import towers.Tower;

import creeps.Creep;

//This is like the tack tower from bloons
public class ProjectileAOE extends Projectile {

	public ProjectileAOE() {
		
	}
	
	public ProjectileAOE(Tower parent) {
		super(parent);
		speed = currentSpeed = 0f;
	}

	@Override
	public Projectile clone() {
		Projectile p = new ProjectileAOE();
		cloneStats(p);
		return p;
	}

	@Override
	public int update() {
		return 0;
	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public void detonate(Level level) {
		//TODO: how do we want to handle splash effects with this projectile type
		HashSet<Creep> creepInRange = guider.getCreepInRange(this, parent.range);
		for (Creep c: creepInRange) {
			c.addAllEffects(creepEffects);
		}
	}

}
