package projectiles;

import creeps.Creep;
import creeps.DamageType;

public abstract class ProjectileModifierEffect extends ProjectileEffect {
	
	abstract void applyEffectToProjectile(Projectile p);

	public ProjectileModifierEffect(float modifier, DamageType damageType) {
		super(0, modifier, 0, damageType);
	}

	@Override
	public void applyEffect(Creep creep, Projectile p) {
		applyEffectToProjectile(p);
	}

	@Override
	public void onExpire(Creep creep) {
		// TODO Auto-generated method stub
		
	}

	

}
