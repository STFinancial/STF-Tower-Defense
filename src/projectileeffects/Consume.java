package projectileeffects;

import java.util.ArrayList;
import java.util.Iterator;

import projectiles.Projectile;
import creeps.Creep;
import creeps.Creep.CreepEffect;
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
		ArrayList<ProjectileEffect> s = new ArrayList<ProjectileEffect>();
		for (Iterator<CreepEffect> iterator = creep.effects.iterator(); iterator.hasNext();) {
			CreepEffect e = iterator.next();
			if (e.projectileEffect instanceof Bleed) {
				s.add(((Bleed) e.projectileEffect).convertToDamage(modifier, e));
				iterator.remove();
			}
		}
		creep.addAllEffects(s);
	}

	@Override
	public void onExpire(Creep creep) {
		return;
	}

}
