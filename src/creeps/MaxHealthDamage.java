package creeps;

import projectiles.Projectile;

public class MaxHealthDamage extends ProjectileEffect {

	public MaxHealthDamage(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent);
	}
	
	public ProjectileEffect clone() {
		return new MaxHealthDamage(modifier, damageType, parent);
	}

	@Override
	public void applyEffect() {
		creep.damage(damageType, creep.getMaxHealth() * modifier, parent.resistPenPercent[damageType.ordinal()], parent.resistPenFlat[damageType.ordinal()], parent.ignoresShield, parent.shieldDrainModifier, parent.toughPenPercent, parent.toughPenFlat);
	}

	@Override
	public void onExpire() {
		applyEffect();
	}
	
	

}
