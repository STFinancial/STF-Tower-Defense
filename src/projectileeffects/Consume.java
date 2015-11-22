package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public class Consume extends ProjectileEffect {
	public Consume(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent, false);
	}

	@Override
	public ProjectileEffect clone() {
		return new Consume(modifier, damageType, parent);
	}

	@Override
	protected void applyEffect() {
		creepManager.consumeBleeds(creep, modifier);
	}

	@Override
	public void onExpire() {
		return;
	}

	@Override
	public void onApply() {
		applyEffect();
	}
}
