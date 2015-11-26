package projectileeffects;

import projectiles.Projectile;
import creeps.DamageType;

public class Knockup extends ProjectileEffect {

	public Knockup(int lifetime, DamageType damageType, Projectile parent) {
		super(lifetime, 0, 0, damageType, parent);
	}

	@Override
	public ProjectileEffect clone() {
		return new Knockup(lifetime, damageType, parent);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

	@Override
	protected void applyEffect() {
		creepManager.knockup(creep, lifetime);
	}

	@Override
	public void onExpire() {
		return;
	}
}
