package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

/**
 * This is where the enemies move randomly for a period of time.
 * @author Timothy
 *
 */
public class Disorient extends ProjectileEffect {
	public Disorient(int lifetime, DamageType damageType, Projectile parent) {
		super(lifetime, 0, 0, damageType, parent);
	}

	@Override
	public ProjectileEffect clone() {
		return new Disorient(lifetime, damageType, parent);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

	@Override
	protected void applyEffect() {
		creep.disorient(lifetime);
	}

	@Override
	public void onExpire() {
		return;
	}
}
