package projectiles;

import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.DamageType;

public class Slow extends ProjectileEffect {
	/*
	 * Slow effect
	 */

	public Slow(int lifetime, float modifier, DamageType elementType) {
		super(lifetime, modifier, elementType);
	}

	@Override
	public void applyEffect(Creep creep, CreepEffect effect) {
		//TODO, figure diminishing returns idea for multiple slows
		creep.currentSpeed = creep.currentSpeed * (1 - (modifier * (1 - creep.slowResist)));
	}

	@Override
	public void onExpire(Creep creep) {
		creep.currentSpeed = creep.currentSpeed / (1 - (modifier * (1 - creep.slowResist)));
	}
}
