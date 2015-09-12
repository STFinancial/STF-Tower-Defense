package creeps;

import projectiles.Projectile;

public class Wealth extends ProjectileEffect {
	
	public Wealth(int lifetime, float modifier, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, 0, damageType, parent);
	}

	@Override
	public ProjectileEffect clone() {
		return new Wealth(lifetime, modifier, damageType, parent);
	}

	@Override
	protected void applyEffect() {
		//TODO if we want flat increases, we need to loop through the creep and unapply all the multipliers, decrement the gold value, then reapply them
		//TODO if we really want flat increases we need to give it the same treatment as resistances was given
		if (modifier > 0) {
			creep.goldValue *= modifier;
		}
	}

	@Override
	public void onExpire() {
		if (modifier > 0) {
			creep.goldValue /= modifier;
		}
	}

	@Override
	public void onApply() {
		applyEffect();
	}
}
