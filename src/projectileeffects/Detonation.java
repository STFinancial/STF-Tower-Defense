package projectileeffects;

import java.util.HashSet;

import projectiles.Projectile;
import creeps.Creep;
import creeps.DamageType;

public class Detonation extends ProjectileEffect {

	public Detonation(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent);
	}

	@Override
	public void applyEffect(Creep creep) {
		HashSet<Creep> inRange = parent.level.getCreepAdjacentToEarth();
		for (Creep c: inRange) {
			float baseDamage = modifier;
			float damageToDo = baseDamage;
			//TODO: do we have separate effects for ignoring resistance/shield
			if (damageType == DamageType.PHYSICAL) {
				damageToDo = baseDamage * (1 - (((1 - parent.armorPenPercent) * c.resist[damageType.ordinal()]) - parent.armorPenFlat));
			} else {
				damageToDo = baseDamage * (1 - (((1 - parent.resistPenPercent) * c.resist[damageType.ordinal()]) - parent.resistPenFlat));
			}
			
			
			damageToDo -= (c.toughness * parent.toughPenPercent) - parent.toughPenFlat;

			if (damageToDo < 0) {
				damageToDo = 0;
			}
			if (parent.ignoresShield) {
				c.currentHealth -= damageToDo;
			} else if (c.currentShield < damageToDo) {
				float damageLeft = damageToDo - creep.currentShield;
				c.currentShield = 0;
				c.currentHealth -= damageLeft;
			} else {
				c.currentShield -= damageToDo;
			}
		}
	}

	@Override
	public void onExpire(Creep creep) {
		return;
	}

}
