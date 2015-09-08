package creeps;

import projectiles.Projectile;

public class Consume extends ProjectileEffect {

	public Consume(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent);
	}

	@Override
	public ProjectileEffect clone() {
		return new Consume(modifier, damageType, parent);
	}

	@Override
	public void applyEffect() {
		creep.consumeBleeds(modifier);
	}

	@Override
	public void onExpire() {
		return;
	}

}
