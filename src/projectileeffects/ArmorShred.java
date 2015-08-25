package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.DamageType;
import creeps.Creep.CreepEffect;

public class ArmorShred extends ProjectileEffect {
	public ArmorShred(int lifetime, float modifier, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, 0, damageType, parent);
		refreshable = true;
	}

	@Override
	public void applyEffect(Creep creep) {
		if (modifier <= 1) {
			creep.resist[damageType.ordinal()] *= 1 - modifier;
		}
		
	}

	@Override
	public void onExpire(Creep creep) {
		if (modifier < 1) {
			creep.resist[damageType.ordinal()] /= 1 - modifier;
		} else if (modifier == 1) {
			//TODO this needs to be handled better if the creep has something that buffs their base armor
			creep.resist[damageType.ordinal()] = creep.elementType.baseResist()[damageType.ordinal()];
			for (CreepEffect c: creep.effects) {
				if (c.projectileEffect instanceof ArmorShred) {
					((ArmorShred) c.projectileEffect).applyEffect(creep);
				}
			}
			//TODO what if I want flat penetration
		}
	}

	@Override
	public ProjectileEffect clone() {
		return new ArmorShred(lifetime, modifier, damageType, parent);
	}


}
