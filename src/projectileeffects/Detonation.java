package projectileeffects;

import java.util.HashSet;

import projectiles.Projectile;
import creeps.Creep;
import creeps.DamageType;

public class Detonation extends ProjectileEffect {

	public Detonation(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent);
	}

	@Override
	public void applyEffect(Creep creep) {
		HashSet<Creep> inRange = parent.level.getCreepAdjacentToEarth();
		for (Creep c: inRange) {
			c.damage(damageType, modifier, parent.resistPenPercent[damageType.ordinal()], parent.resistPenFlat[damageType.ordinal()], parent.ignoresShield, parent.shieldDrainModifier, parent.toughPenPercent, parent.toughPenFlat);
		}
	}

	@Override
	public void onExpire(Creep creep) {
		return;
	}

	@Override
	public ProjectileEffect clone() {
		return new Detonation(modifier, damageType, parent);
	}

}
