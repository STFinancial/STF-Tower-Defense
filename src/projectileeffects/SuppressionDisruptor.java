package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;
public final class SuppressionDisruptor extends Suppression{
	private boolean isFlat;
	
	public SuppressionDisruptor(int lifetime, float modifier, DamageType damageType, Projectile parent, boolean isFlat, boolean sharesStacks) {
		super(lifetime, modifier, damageType, parent, sharesStacks);
		this.isFlat = isFlat;
	}

	@Override
	public ProjectileEffect clone() {
		return new SuppressionDisruptor(lifetime, modifier, damageType, parent, isFlat, sharesStacks);
	}

	@Override
	protected void applyEffect() {
		creepManager.suppressDisruption(creep, damageType, modifier, isFlat);
	}

	@Override
	public void onExpire() {
		creepManager.unsuppressDisruption(creep, damageType, modifier, isFlat);
	}
}
