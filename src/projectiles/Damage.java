package projectiles;

import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.DamageType;

public class Damage extends ProjectileEffect {
	/*
	 * Plain damage
	 */

	public Damage(float modifier, DamageType damageType) {
		super(0, modifier, 0, damageType);
	}

	@Override
	public void applyEffect(Creep creep) {
		float baseDamage = modifier;
		float damageToDo = baseDamage;
		//TODO: do we have separate effects for ignoring resistance/shield
		damageToDo = baseDamage * (1 - creep.resist[damageType.ordinal()]);
		damageToDo -= creep.toughness;

		if (damageToDo < 0) {
			damageToDo = 0;
		}
		if (creep.currentShield < damageToDo) {
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
