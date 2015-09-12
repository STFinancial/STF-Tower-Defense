package creeps;

import projectiles.Projectile;

public class DamageOnHit extends ProjectileEffect {

	public DamageOnHit(int lifetime, float modifier, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, 0, damageType, parent);
	}

	@Override
	public ProjectileEffect clone() {
		return new DamageOnHit(lifetime, modifier, damageType, parent);
	}

	@Override
	public void applyEffect() {
		creep.damage(damageType, modifier, 1, 0, false, 0, 1, 0);
	}

	@Override
	public void onExpire() {
		return;
	}

}
