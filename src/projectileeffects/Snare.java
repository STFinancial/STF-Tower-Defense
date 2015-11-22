package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public class Snare extends ProjectileEffect {

	public Snare(int lifetime, DamageType damageType, Projectile parent) {
		super(lifetime, 0, 0, damageType, parent, false);
	}

	@Override
	protected void applyEffect() {
		creepManager.snare(creep, lifetime);
	}

	@Override
	public void onExpire() {
		return;
	}

	@Override
	public ProjectileEffect clone() {
		return new Snare(lifetime, damageType, parent);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

}
