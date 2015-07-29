package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.DamageType;

public class Damage extends ProjectileEffect {

	public Damage(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent);
	}

	public void applyEffect(Creep creep) {
		float baseDamage = modifier;
		float damageToDo = baseDamage;
		//TODO: do we have separate effects for ignoring resistance/shield
		if (damageType == DamageType.PHYSICAL) {
			damageToDo = baseDamage * (1 - (((1 - parent.armorPenPercent) * creep.resist[damageType.ordinal()]) - parent.armorPenFlat));
		} else {
			damageToDo = baseDamage * (1 - (((1 - parent.resistPenPercent) * creep.resist[damageType.ordinal()]) - parent.resistPenFlat));
		}
		
		
		damageToDo -= (creep.toughness * parent.toughPenPercent) - parent.toughPenFlat;

		if (damageToDo < 0) {
			damageToDo = 0;
		}
		if (parent.ignoresShield) {
			creep.currentHealth -= damageToDo;
		} else if (creep.currentShield < damageToDo) {
			float damageLeft = damageToDo - creep.currentShield;
			creep.currentShield = 0;
			creep.currentHealth -= damageLeft;
		} else {
			creep.currentShield -= damageToDo;
		}
	}

	@Override
	public void onExpire(Creep creep) {
		return;
	}
}
