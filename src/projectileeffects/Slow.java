package projectileeffects;

import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.DamageType;

public class Slow extends CreepModifierEffect {
	/*
	 * Slow effect
	 */

	public Slow(int lifetime, float modifier, DamageType elementType) {
		super(lifetime, modifier, 0, elementType);
	}

	@Override
	void applyEffectToCreep(Creep creep) {
		creep.currentSpeed = creep.currentSpeed * (1 - (modifier * (1 - creep.slowResist)));
	}

	@Override
	public void onExpire(Creep creep) {
		creep.currentSpeed = creep.currentSpeed / (1 - (modifier * (1 - creep.slowResist)));
	}
}
