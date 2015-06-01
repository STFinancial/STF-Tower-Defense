package projectiles;

import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.DamageType;

public class Damage extends DamageEffect {
	/*
	 * Plain damage
	 */

	public Damage(float modifier, DamageType damageType) {
		super(0, modifier, damageType);
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
