package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public class MaxHealthDamage extends ProjectileEffect {
	public MaxHealthDamage(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent, false);
	}
	
	public ProjectileEffect clone() {
		return new MaxHealthDamage(modifier, damageType, parent);
	}

	@Override
	protected void applyEffect() {
		creepManager.damage(creep, damageType, creepManager.getMaxHealth(creep) * modifier, 
				projManager.getResistPen(parent, damageType, false), projManager.getResistPen(parent, damageType, true), 
				projManager.ignoresShield(parent), projManager.getShieldDrainModifier(parent), 
				projManager.getToughPen(parent, false), projManager.getToughPen(parent, true));
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
