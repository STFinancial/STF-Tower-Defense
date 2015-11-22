package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

//TODO: Maybe clarify the name
public class Nullify extends ProjectileEffect {

	public Nullify(DamageType damageType, Projectile parent, boolean sharesStacks) {
		super(0, 0, 0, damageType, parent, sharesStacks);
	}

	@Override
	public ProjectileEffect clone() {
		return new Nullify(damageType, parent, sharesStacks);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

	@Override
	protected void applyEffect() {
		creepManager.nullify(creep);
	}

	@Override
	public void onExpire() {
		return;
	}
}
