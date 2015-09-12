package creeps;

import projectiles.Projectile;

public class Snare extends ProjectileEffect {

	public Snare(int lifetime, DamageType damageType, Projectile parent) {
		super(lifetime, 0, 0, damageType, parent);
	}

	@Override
	protected void applyEffect() {
		creep.snare();
	}

	@Override
	public void onExpire() {
		creep.unsnare();
	}

	@Override
	public ProjectileEffect clone() {
		return new Snare(lifetime, damageType, parent);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

}
