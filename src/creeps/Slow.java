package creeps;

import projectiles.Projectile;

public class Slow extends ProjectileEffect implements Refreshable {
	/*
	 * Slow effect
	 */

	public Slow(int lifetime, float modifier, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, 0, damageType, parent);
	}

	@Override
	public void applyEffect() {
		creep.slow(damageType, modifier);
	}

	@Override
	public void onExpire() {
		creep.unslow(damageType, modifier);
	}

	@Override
	public ProjectileEffect clone() {
		return new Slow(lifetime, modifier, damageType, parent);
	}

	@Override
	public void refresh() {
		counter = lifetime;
	}
}
