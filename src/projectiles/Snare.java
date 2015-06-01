package projectiles;

import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.DamageType;

public class Snare extends ProjectileEffect {

	public Snare(int lifetime, float modifier, DamageType elementType) {
		super(lifetime, modifier, elementType);
	}

	@Override
	public void applyEffect(Creep creep, CreepEffect effect) {
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
				c.projectileEffect.applyEffect(creep, c);
			}
		}
	}

}
