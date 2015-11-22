package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public class Slow extends ProjectileEffect implements Refreshable {
	/*
	 * Slow effect
	 */
	private boolean beenApplied;

	public Slow(int lifetime, float modifier, DamageType damageType, Projectile parent, boolean sharesStacks) {
		super(lifetime, modifier, 0, damageType, parent, sharesStacks);
		beenApplied = false;
	}

	@Override
	public void applyEffect() {
		creepManager.slow(creep, damageType, modifier);
	}

	@Override
	public void onExpire() {
		creepManager.unslow(creep, damageType, modifier);
	}

	@Override
	public ProjectileEffect clone() {
		return new Slow(lifetime, modifier, damageType, parent, sharesStacks);
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
