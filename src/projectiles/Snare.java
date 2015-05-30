package projectiles;

import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.ElementType;

public class Snare extends ProjectileEffect {

	public Snare(int lifetime, float modifier, ElementType elementType) {
		super(lifetime, modifier, elementType);
	}

	@Override
	public void applyEffect(Creep creep, CreepEffect effect) {
		// TODO Factor in snare resist
		creep.currentSpeed = 0;
	}

	@Override
	public void onExpire(Creep creep) {
		// TODO Auto-generated method stub

	}

}
