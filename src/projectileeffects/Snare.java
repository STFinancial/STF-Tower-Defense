package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.DamageType;

public class Snare extends ProjectileEffect {

	public Snare(int lifetime, DamageType damageType, Projectile parent) {
		super(lifetime, 0, 0, damageType, parent);
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

	@Override
	public ProjectileEffect clone() {
		return new Snare(lifetime, damageType, parent);
	}

}
