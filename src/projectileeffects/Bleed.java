package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public class Bleed extends ProjectileEffect implements Stackable {
	private float tickDamage;
	private int numStacks;
	private int maxStacks;

	public Bleed(int lifetime, float modifier, int timing, DamageType damageType, Projectile parent, boolean sharesStacks) {
		super(lifetime, modifier, timing, damageType, parent, sharesStacks);
		this.numStacks = 0;
		this.maxStacks = 1;
		this.tickDamage = modifier;
	}

	@Override
	protected void applyEffect() {
		creepManager.damage(creep, damageType, tickDamage, projManager.getResistPen(parent, damageType, false),
				projManager.getResistPen(parent, damageType, true), projManager.ignoresShield(parent), 
				projManager.getShieldDrainModifier(parent), projManager.getToughPen(parent, false), projManager.getToughPen(parent, true));
	}

	@Override
	public void onExpire() {
		return;
	}

	@Override
	public ProjectileEffect clone() {
		return new Bleed(lifetime, modifier, timing, damageType, parent, sharesStacks);
	}

	public Damage convertToDamage(float modifier) {
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
			applyEffect();
		}
		counter = lifetime;
	}

	@Override
	public void setMaxStacks(int stacks) {
		this.maxStacks = stacks;
	}

	@Override
	public void onApply() {
		stack();
	}
}
