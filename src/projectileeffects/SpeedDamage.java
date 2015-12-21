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
		creepManager.damage(creep, damageType, creepManager.getCurrentSpeed(creep) * modifier, 
				projManager.getResistPen(parent, damageType, false), projManager.getResistPen(parent, damageType, true), 
				projManager.ignoresShield(parent), projManager.getShieldDrainModifier(parent), 
				projManager.getToughPen(parent, false), projManager.getToughPen(parent, true));
	}

	@Override
	public void onExpire() {
		return;
	}
}
