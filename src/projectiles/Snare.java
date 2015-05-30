package projectiles;

import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.ElementType;

public class Snare extends ProjectileEffect {

	public Snare(int lifetime, float modifier, ElementType elementType) {
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
		// TODO Auto-generated method stub
		creep.currentSpeed = creep.speed;
		for (CreepEffect c: creep.effects) {
			if (c.projectileEffect instanceof Slow) {
				c.projectileEffect.applyEffect(creep, c);
			}
		}
	}

}
