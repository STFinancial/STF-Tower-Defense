package projectiles;

import creeps.Creep;
import creeps.DamageType;

public abstract class CreepModifierEffect extends ProjectileEffect {

	abstract void applyEffectToCreep(Creep creep);
	
	public CreepModifierEffect(int lifetime, float modifier, int timing, DamageType damageType) {
		super(lifetime, modifier, timing, damageType);
	}
	

	@Override
	public void applyEffect(Creep creep, Projectile p) {
		applyEffectToCreep(creep);
	}

	@Override
	public void onExpire(Creep creep) {
		// TODO Auto-generated method stub
		
	}

}
