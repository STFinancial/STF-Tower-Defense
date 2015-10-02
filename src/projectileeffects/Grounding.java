package projectileeffects;

import projectiles.Projectile;
import creeps.DamageType;

public class Grounding extends ProjectileEffect {

	public Grounding(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent);
	}

	@Override
	public ProjectileEffect clone() {
		return new Grounding(modifier, damageType, parent);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

	@Override
	protected void applyEffect() {
		creep.ground();
		creep.damage(damageType, creep.getCurrentSize() * modifier, parent.getResistPen(damageType, false), parent.getResistPen(damageType, true), parent.ignoresShield(), parent.getShieldDrainModifier(), parent.getToughPen(false), parent.getToughPen(true));
	}

	@Override
	public void onExpire() {
		return; //TODO: The decision needs to be made whether this effect can be temporary or not.
	}

}
