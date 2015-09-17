package creeps;

import projectiles.Projectile;

public class PermaSlow extends ProjectileEffect {
	private boolean isFlat;
	
	public PermaSlow(float modifier, DamageType damageType, Projectile parent, boolean isFlat) {
		super(0, modifier, 0, damageType, parent);
		this.isFlat = isFlat;
	}

	@Override
	public ProjectileEffect clone() {
		return new PermaSlow(modifier, damageType, parent, isFlat);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

	@Override
	protected void applyEffect() {
		creep.reduceMaxSpeed(damageType, modifier, isFlat);
	}

	@Override
	public void onExpire() {
		return;
	}

}
