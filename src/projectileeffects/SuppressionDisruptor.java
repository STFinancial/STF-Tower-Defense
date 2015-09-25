package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;
public final class SuppressionDisruptor extends Suppression{
	private boolean isFlat;
	
	public SuppressionDisruptor(int lifetime, float modifier, DamageType damageType, Projectile parent, boolean isFlat) {
		super(lifetime, modifier, damageType, parent);
		this.isFlat = isFlat;
	}

	@Override
	public ProjectileEffect clone() {
		return new SuppressionDisruptor(lifetime, modifier, damageType, parent, isFlat);
	}

	@Override
	protected void applyEffect() {
		creep.suppressDisruptionPercent(modifier, damageType);
	}

	@Override
	public void onExpire() {
		creep.unsuppressDisruptionPercent(modifier, damageType);
	}
}
