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
		creep.currentSpeed = creep.speed;
	}
}
