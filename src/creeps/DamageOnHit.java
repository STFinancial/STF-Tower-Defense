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
	protected void applyEffect() {
		creep.damage(damageType, modifier, 1, 0, false, 0, 1, 0);
	}

	@Override
	public void onExpire() {
		creep.removeOnHit(damageType, modifier);
	}

	@Override
	public void onApply() {
		creep.addOnHit(damageType, modifier);
	}

}
