package projectiles;

import creeps.Creep;

public class Snare extends ProjectileEffect {

	public Snare(int lifetime, float modifier) {
		super(lifetime, modifier);
	}

	@Override
	public void applyEffect(Creep creep) {
		creep.currentSpeed = 0;
	}

	@Override
	public void onExpire(Creep creep) {
		creep.currentSpeed = creep.speed;
	}
}
