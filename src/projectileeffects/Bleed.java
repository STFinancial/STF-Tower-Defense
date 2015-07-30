package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.DamageType;

public class Bleed extends ProjectileEffect {

	public Bleed(int lifetime, float modifier, int timing, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, timing, damageType, parent);
		refreshable = true;
	}

	@Override
	public void applyEffect(Creep creep) {
		float damageToDo = modifier - creep.toughness;
		//TODO: should this be affected by toughness and shield?
		
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

	@Override
	public ProjectileEffect clone() {
		return new Bleed(lifetime, modifier, timing, damageType, parent);
	}
}
