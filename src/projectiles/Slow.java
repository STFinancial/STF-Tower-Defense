package projectiles;

import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.ElementType;

public class Slow extends ProjectileEffect {
	/*
	 * Slow effect
	 */

	public Slow(int lifetime, float modifier, ElementType elementType) {
		super(lifetime, modifier, elementType);
	}

	@Override
	public void applyEffect(Creep creep, CreepEffect effect) {
		//TODO, figure diminishing returns idea for multiple slows
		creep.currentSpeed = creep.speed * modifier;
	}

	@Override
	public void onExpire(Creep creep) {
	}
}
