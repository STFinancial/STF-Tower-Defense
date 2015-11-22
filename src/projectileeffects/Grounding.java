package projectileeffects;

import projectiles.Projectile;
import creeps.DamageType;

public class Grounding extends ProjectileEffect {

	public Grounding(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent, false);
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
		creepManager.ground(creep);
		creepManager.damage(creep, damageType, creepManager.getCurrentSize(creep) * modifier, 
				projManager.getResistPen(parent, damageType, false), projManager.getResistPen(parent, damageType, true), 
				projManager.ignoresShield(parent), projManager.getShieldDrainModifier(parent), 
				projManager.getToughPen(parent, false), projManager.getToughPen(parent, true));
	}

	@Override
	public void onExpire() {
		return; //TODO: The decision needs to be made whether this effect can be temporary or not.
	}

}
