package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

//TODO: Maybe clarify the name
public class Nullify extends ProjectileEffect {

	public Nullify(DamageType damageType, Projectile parent) {
		super(0, 0, 0, damageType, parent);
	}

	@Override
	public ProjectileEffect clone() {
		return new Nullify(damageType, parent);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

	@Override
	protected void applyEffect() {
		creep.nullify();
	}

	@Override
	public void onExpire() {
		return;
	}
}
