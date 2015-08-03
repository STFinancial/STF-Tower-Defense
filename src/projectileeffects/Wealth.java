package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.DamageType;

public class Wealth extends ProjectileEffect {
	
	public Wealth(int lifetime, float modifier, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, 0, damageType, parent);
		refreshable = true;
	}

	@Override
	public ProjectileEffect clone() {
		return new Wealth(lifetime, modifier, damageType, parent);
	}

	@Override
	public void applyEffect(Creep creep) {
		//TODO if we want flat increases, we need to loop through the creep and unapply all the multipliers, decrement the gold value, then reapply them
		if (modifier > 0) {
			creep.goldValue *= modifier;
		}
	}

	@Override
	public void onExpire(Creep creep) {
		if (modifier > 0) {
			creep.goldValue /= modifier;
		}
	}
}
