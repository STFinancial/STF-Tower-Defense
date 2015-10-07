package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public final class SiphonLife extends ProjectileEffect {
	private float goldModifier;
	
	public SiphonLife(float modifier, DamageType damageType, Projectile parent, float goldModifier) {
		super(0, modifier, 0, damageType, parent);
		this.goldModifier = goldModifier;
	}

	@Override
	public ProjectileEffect clone() {
		return new SiphonLife(modifier, damageType, parent, goldModifier);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

	@Override
	protected void applyEffect() {
		float maxHealth = creep.getMaxHealth();
		creep.damage(damageType, maxHealth * modifier, parent.getResistPen(damageType, false), parent.getResistPen(damageType, true), parent.ignoresShield(), parent.getShieldDrainModifier(), parent.getToughPen(false), parent.getToughPen(true));
		parent.level.addGold(maxHealth * goldModifier);
	}

	@Override
	public void onExpire() {
		return;
	}
}
