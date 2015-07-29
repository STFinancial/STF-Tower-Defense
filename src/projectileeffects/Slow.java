package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.DamageType;

public class Slow extends ProjectileEffect {
	/*
	 * Slow effect
	 */

	public Slow(int lifetime, float modifier, DamageType elementType, Projectile parent) {
		super(lifetime, modifier, 0, elementType, parent);
	}

	@Override
	public void applyEffect(Creep creep) {
		creep.currentSpeed = creep.currentSpeed * (1 - (modifier * (1 - creep.slowResist)));
	}

	@Override
	public void onExpire(Creep creep) {
		creep.currentSpeed = creep.currentSpeed / (1 - (modifier * (1 - creep.slowResist)));
	}
}
