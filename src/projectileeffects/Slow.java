package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.DamageType;

public class Slow extends ProjectileEffect {
	/*
	 * Slow effect
	 */

	public Slow(int lifetime, float modifier, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, 0, damageType, parent);
		refreshable = true;
	}

	@Override
	public void applyEffect(Creep creep) {
		creep.slow(damageType, modifier);
	}

	@Override
	public void onExpire(Creep creep) {
		creep.unslow(damageType, modifier);
	}

	@Override
	public ProjectileEffect clone() {
		return new Slow(lifetime, modifier, damageType, parent);
	}
}
