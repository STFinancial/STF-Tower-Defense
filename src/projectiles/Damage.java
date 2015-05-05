package projectiles;

import creeps.Creep;

public class Damage extends ProjectileEffect {
	/*
	 * Plain damage
	 */

	public Damage(float modifier) {
		super(modifier);
	}

	@Override
	public void applyEffect(Creep creep) {
		creep.health = creep.health - (int) modifier;
	}
}
