package projectiles;

import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.ElementType;

public class Damage extends DamageEffect {
	/*
	 * Plain damage
	 */
	
	//TODO will decide if 

	public Damage(float modifier, DamageType damageType, ElementType elementType) {
		super(0, modifier, damageType, elementType);
	}

	@Override
	public void applyEffect(Creep creep, CreepEffect effect) {
		creep.damage(this);
	}

	@Override
	public boolean ignoresArmor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ignoresShield() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onExpire(Creep creep) {
		creep.damage(this);
	}
}
