package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.DamageType;

public class Snare extends ProjectileEffect {

	public Snare(int lifetime, float modifier, DamageType elementType, Projectile parent) {
		super(lifetime, modifier, 0, elementType, parent);
	}

	@Override
	public void applyEffect(Creep creep) {
		if (!creep.snareImmune) {
			creep.currentSpeed = 0;			
		}
	}

	@Override
	public void onExpire(Creep creep) {
		creep.currentSpeed = creep.speed;
		//Need to reapply all the slows on it
		for (CreepEffect c: creep.effects) {
			if (c.projectileEffect instanceof Slow) {
				((Snare) c.projectileEffect).applyEffect(creep);
			}
		}
	}

}
