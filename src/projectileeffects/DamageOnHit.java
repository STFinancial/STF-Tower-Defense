package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public class DamageOnHit extends ProjectileEffect implements Stackable {
	private int maxStacks;
	private int numStacks;
	
	public DamageOnHit(int lifetime, float modifier, DamageType damageType, Projectile parent, boolean sharesStacks) {
		super(lifetime, modifier, 0, damageType, parent, sharesStacks);
		this.maxStacks = 1;
		this.numStacks = 0;
	}

	@Override
	public ProjectileEffect clone() {
		return new DamageOnHit(lifetime, modifier, damageType, parent, sharesStacks);
	}

	@Override
	protected void applyEffect() {
		creepManager.increaseDamageOnHit(creep, damageType, modifier);
	}

	@Override
	public void onExpire() {
		while (numStacks > 0) {
			creepManager.reduceDamageOnHit(creep, damageType, modifier);
			numStacks--;
		}
	}

	@Override
	public void onApply() {
		stack();
	}

	@Override
	public void stack() {
		if (numStacks < maxStacks) {
			applyEffect();
			numStacks++;
		}
		counter = lifetime;
	}

	@Override
	public void setMaxStacks(int stacks) {
		this.maxStacks = stacks;
	}

}
