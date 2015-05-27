package projectiles;

import creeps.Creep;
import creeps.Creep.CreepEffect;

public class Snare extends ProjectileEffect {

	public Snare(int lifetime, float modifier) {
		super(lifetime, modifier);
	}

	@Override
	public void applyEffect(Creep creep, CreepEffect effect) {
		creep.currentSpeed = 0;
	}

	@Override
	public void onExpire(Creep creep) {
		creep.currentSpeed = creep.speed;
	}
}
