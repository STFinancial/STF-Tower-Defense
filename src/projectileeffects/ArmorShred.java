package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.DamageType;

public class ArmorShred extends ProjectileEffect {
	private boolean isFlat;
	
	public ArmorShred(int lifetime, float modifier, DamageType damageType, Projectile parent, boolean isFlat) {
		super(lifetime, modifier, 0, damageType, parent);
		refreshable = true;
		this.isFlat = isFlat;
	}

	@Override
	public void applyEffect(Creep creep) {
		if (isFlat) {
			creep.reduceFlatResist(damageType, modifier);
		} else {
			creep.reducePercentResist(damageType, modifier);
		}
	}

	@Override
	public void onExpire(Creep creep) {
		if (isFlat) {
			creep.increaseFlatResist(damageType, modifier);
		} else {
			creep.increasePercentResist(damageType, modifier);
		}
	}

	@Override
	public ProjectileEffect clone() {
		return new ArmorShred(lifetime, modifier, damageType, parent, isFlat);
	}


}
