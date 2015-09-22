package creeps;

import projectiles.Projectile;

public class Snare extends ProjectileEffect {

	public Snare(int lifetime, DamageType damageType, Projectile parent) {
		super(lifetime, 0, 0, damageType, parent);
	}

	@Override
	protected void applyEffect() {
		creep.snare(); //TODO: Need to fix this. If we have multiple towers that snare we want the largest one to be applied, so we somehow need to add additional time to the snare if we have a better snare
	}

	@Override
	public void onExpire() {
		creep.unsnare(); //TODO: If we implement multiple snares at once then this won't cut it.
	}

	@Override
	public ProjectileEffect clone() {
		return new Snare(lifetime, damageType, parent);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

}
