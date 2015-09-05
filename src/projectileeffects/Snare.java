package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.DamageType;

public class Snare extends ProjectileEffect {

	public Snare(int lifetime, DamageType damageType, Projectile parent) {
		super(lifetime, 0, 0, damageType, parent);
	}

	@Override
	public void applyEffect(Creep creep) {
		creep.snare();
	}

	@Override
	public void onExpire(Creep creep) {
		creep.unsnare();
	}

	@Override
	public ProjectileEffect clone() {
		return new Snare(lifetime, damageType, parent);
	}

}
