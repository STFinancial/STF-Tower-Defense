package creeps;

import projectiles.Projectile;

public class Bleed extends ProjectileEffect implements Stackable {
	private float tickDamage;
	private int numStacks;
	private int maxStacks;

	public Bleed(int lifetime, float modifier, int timing, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, timing, damageType, parent);
		this.numStacks = 0;
		this.maxStacks = -1;
		this.tickDamage = modifier;
	}

	@Override
	public void applyEffect() {
		creep.damage(damageType, tickDamage, parent.resistPenPercent[damageType.ordinal()],
				parent.resistPenFlat[damageType.ordinal()], parent.ignoresShield, 
				parent.shieldDrainModifier, parent.toughPenPercent, parent.toughPenFlat);
	}

	@Override
	public void onExpire() {
		return;
	}

	@Override
	public ProjectileEffect clone() {
		return new Bleed(lifetime, modifier, timing, damageType, parent);
	}

	public Damage convertToDamage(float modifier, int counter) {
		int timeLeft = lifetime - counter;
		int ticks = (timeLeft / timing) + 1;
		return new Damage(modifier * ticks * this.tickDamage, damageType, parent);
	}

	@Override
	public void stack() {
		if (numStacks < maxStacks) {
			applyEffect();
			tickDamage += modifier;
		} else {
			counter = lifetime;
			applyEffect();
		}
	}

	@Override
	public void setMaxStacks(int stacks) {
		this.maxStacks = stacks;
	}
}
