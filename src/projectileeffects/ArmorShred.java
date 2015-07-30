package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.DamageType;

public class ArmorShred extends ProjectileEffect {
	
	public ArmorShred(int lifetime, float modifier, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, 0, damageType, parent);
		refreshable = true;
	}

	@Override
	public void applyEffect(Creep creep) {
		creep.resist[damageType.ordinal()] *= 1 - modifier;
	}

	@Override
	public void onExpire(Creep creep) {
		if (modifier != 1) {
			creep.resist[damageType.ordinal()] /= 1 - modifier;
		}
	}

	@Override
	public ProjectileEffect clone() {
		return new ArmorShred(lifetime, modifier, damageType, parent);
	}


}
