package projectileeffects;

import projectiles.Projectile;
import creeps.DamageType;

public class Hasting extends ProjectileEffect implements Stackable {
	private int maxStacks;
	private int numStacks;
	
	public Hasting(int lifetime, float modifier, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, 0, damageType, parent);
		this.numStacks = 0;
		this.maxStacks = 1;
	}

	@Override
	public ProjectileEffect clone() {
		return new Hasting(lifetime, modifier, damageType, parent);
	}

	@Override
	public void onApply() {
		stack();
	}

	@Override
	protected void applyEffect() {
		creepManager.increaseHasting(creep, damageType, modifier);
	}

	@Override
	public void onExpire() {
		while (numStacks > 0) {
			creepManager.reduceHasting(creep, damageType, modifier);
			numStacks--;
		}
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
