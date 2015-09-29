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
		applyEffect(); //TODO: Need to fix this. If we have multiple towers that disorient we want the largest one to be applied, so we somehow need to add additional time to the disorient if we have a better disorient
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
