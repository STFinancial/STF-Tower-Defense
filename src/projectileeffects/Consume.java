package projectileeffects;

import java.util.ArrayList;
import java.util.Iterator;

import projectiles.Projectile;
import creeps.Creep;
import creeps.DamageType;

public class Consume extends ProjectileEffect {

	public Consume(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent);
	}

	@Override
	public ProjectileEffect clone() {
		return new Consume(modifier, damageType, parent);
	}

	@Override
	public void applyEffect(Creep creep) {
		creep.consumeBleeds(modifier);
		
	}

	@Override
	public void onExpire(Creep creep) {
		return;
	}

}
