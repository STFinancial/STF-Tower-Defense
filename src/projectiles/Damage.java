package projectiles;

import creeps.Creep;
import creeps.ElementType;

public class Damage extends ProjectileEffect {
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
	public void onExpire(Creep creep) {
		creep.damage(this);
	}
}
