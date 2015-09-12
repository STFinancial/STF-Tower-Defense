package creeps;

import projectiles.Projectile;

public class Slow extends ProjectileEffect implements Refreshable {
	/*
	 * Slow effect
	 */
	private boolean beenApplied;

	public Slow(int lifetime, float modifier, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, 0, damageType, parent);
		beenApplied = false;
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

	@Override
	public void onApply() {
		if (beenApplied) {
			refresh();
		} else {
			applyEffect();
		}
	}
}
