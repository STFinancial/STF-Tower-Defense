package creeps;

import java.util.HashSet;

import projectiles.Projectile;

public class Detonation extends ProjectileEffect {
	public Detonation(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent);
	}

	@Override
	protected void applyEffect() {
		HashSet<Creep> inRange = parent.guider.getCreepAdjacentToEarth();
		for (Creep c: inRange) {
			c.damage(damageType, modifier, parent.resistPenPercent[damageType.ordinal()], parent.resistPenFlat[damageType.ordinal()], parent.ignoresShield, parent.shieldDrainModifier, parent.toughPenPercent, parent.toughPenFlat);
		}
	}

	@Override
	public void onExpire() {
		return;
	}

	@Override
	public ProjectileEffect clone() {
		return new Detonation(modifier, damageType, parent);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

}
