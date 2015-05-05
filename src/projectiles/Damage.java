package projectiles;

import creeps.Creep;
import creeps.ElementType;

public class Damage extends DamageEffect {
	/*
	 * Plain damage
	 */

	public Damage(float modifier) {
		super(modifier);
		elementType = ElementType.LIGHT;
	}

	@Override
	public void applyEffect(Creep creep) {
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
