package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public class MaxHealthDamage extends ProjectileEffect {
	public MaxHealthDamage(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent);
	}
	
	public ProjectileEffect clone() {
		return new MaxHealthDamage(modifier, damageType, parent);
	}

	@Override
	protected void applyEffect() {
		creep.damage(damageType, creep.getMaxHealth() * modifier, parent.getResistPen(damageType, false), parent.getResistPen(damageType, true), parent.ignoresShield(), parent.getShieldDrainModifier(), parent.getToughPen(false), parent.getToughPen(true));
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
