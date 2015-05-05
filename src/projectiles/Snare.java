package projectiles;

import creeps.Creep;

public class Snare extends ProjectileEffect {
	private float speed;

	public Snare(int lifetime, float modifier) {
		super(lifetime, modifier);
	}

	@Override
	public void applyEffect(Creep creep) {
		if (speed != 0) {
			speed = creep.speed;
		}
		creep.speed = 0;
	}

	@Override
	public void onExpire(Creep creep) {
		// TODO Auto-generated method stub

	}

}
