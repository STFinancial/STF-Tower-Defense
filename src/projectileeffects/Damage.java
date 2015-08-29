package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.DamageType;

public class Damage extends ProjectileEffect {
	
	
	public Damage(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent);
	}

	public void applyEffect(Creep creep) {
		creep.damage(damageType, modifier, parent.resistPenPercent[damageType.ordinal()], parent.resistPenFlat[damageType.ordinal()], parent.ignoresShield, parent.shieldDrainModifier, parent.toughPenPercent, parent.toughPenFlat);
	}

	@Override
	public void onExpire(Creep creep) {
		return;
	}

	@Override
	public ProjectileEffect clone() {
		Damage d = new Damage(modifier, damageType, parent);
		return d;
	}
}
