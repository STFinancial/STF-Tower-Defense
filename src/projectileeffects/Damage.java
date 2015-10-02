package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public class Damage extends ProjectileEffect {
	public Damage(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent);
	}

	@Override
	protected void applyEffect() {
		creep.damage(damageType, modifier, parent.getResistPen(damageType, false), parent.getResistPen(damageType, true), parent.ignoresShield(), parent.getShieldDrainModifier(), parent.getToughPen(false), parent.getToughPen(true));
	}

	@Override
	public void onExpire() {
		return;
	}

	@Override
	public ProjectileEffect clone() {
		Damage d = new Damage(modifier, damageType, parent);
		return d;
	}

	@Override
	public void onApply() {
		applyEffect();
	}
}
