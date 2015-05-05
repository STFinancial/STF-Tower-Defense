package projectiles;

import creeps.Creep;

public class Slow extends ProjectileEffect {
	/*
	 * Slow effect
	 */
	private float creepSpeed = 0;

	public Slow(int lifetime, float modifier) {
		super(lifetime, modifier);
	}

	@Override
	public void applyEffect(Creep creep) {
		if (creepSpeed == 0) {
			creepSpeed = creep.speed;
		}
		else if (creep.speed == creepSpeed) {
			creep.speed = creep.speed * modifier;
		}
		lifetime--;
	}
}
