package creeps;

import projectiles.Projectile;

public abstract class Suppression extends ProjectileEffect{
	public Suppression(int lifetime, float modifier, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, 0, damageType, parent);
	}
	
	@Override
	public void onApply() {
		applyEffect();
	}
}
