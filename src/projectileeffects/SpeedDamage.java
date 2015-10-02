package projectileeffects;

import projectiles.Projectile;
import creeps.DamageType;

public class SpeedDamage extends ProjectileEffect {
	public SpeedDamage(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent);
	}

	@Override
	public ProjectileEffect clone() {
		return new SpeedDamage(modifier, damageType, parent);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

	@Override
	protected void applyEffect() {
		creep.damage(damageType, creep.getCurrentSpeed() * modifier, parent.getResistPen(damageType, false), parent.getResistPen(damageType, true), parent.ignoresShield(), parent.getShieldDrainModifier(), parent.getToughPen(false), parent.getToughPen(true));
	}

	@Override
	public void onExpire() {
		return;
	}
}
