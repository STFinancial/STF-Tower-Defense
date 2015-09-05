package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.DamageType;

public class Bleed extends ProjectileEffect {

	public Bleed(int lifetime, float modifier, int timing, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, timing, damageType, parent);
		refreshable = true;
	}

	@Override
	public void applyEffect(Creep creep) {
		creep.damage(damageType, modifier, parent.resistPenPercent[damageType.ordinal()],
				parent.resistPenFlat[damageType.ordinal()], parent.ignoresShield, 
				parent.shieldDrainModifier, parent.toughPenPercent, parent.toughPenFlat);
	}

	@Override
	public void onExpire(Creep creep) {
		return;
	}

	@Override
	public ProjectileEffect clone() {
		return new Bleed(lifetime, modifier, timing, damageType, parent);
	}

	public Damage convertToDamage(float modifier, int counter) {
		int timeLeft = lifetime - counter;
		int ticks = (timeLeft / timing) + 1;
		return new Damage(modifier * ticks * this.modifier, damageType, parent);
	}
}
