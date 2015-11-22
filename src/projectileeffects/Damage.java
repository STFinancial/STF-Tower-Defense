package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public class Damage extends ProjectileEffect {
	public Damage(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent, false);
	}

	@Override
	protected void applyEffect() {
		creepManager.damage(creep, damageType, modifier, projManager.getResistPen(parent, damageType, false), 
				projManager.getResistPen(parent, damageType, true), projManager.ignoresShield(parent), 
				projManager.getShieldDrainModifier(parent), projManager.getToughPen(parent, false), projManager.getToughPen(parent, true));
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
