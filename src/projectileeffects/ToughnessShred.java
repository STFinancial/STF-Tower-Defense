package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public class ToughnessShred extends ProjectileEffect implements Stackable {
	private boolean isFlat;
	private int numStacks;
	private int maxStacks;
	
	public ToughnessShred(int lifetime, float modifier, DamageType damageType, Projectile parent, boolean isFlat, boolean sharesStacks) {
		super(lifetime, modifier, 0, damageType, parent, sharesStacks);
		this.numStacks = 0;
		this.maxStacks = 1;
		this.isFlat = isFlat;
	}

	@Override
	public ProjectileEffect clone() {
		ToughnessShred a = new ToughnessShred(lifetime, modifier, damageType, parent, isFlat, sharesStacks);
		a.setMaxStacks(maxStacks);
		return a;
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

	@Override
	protected void applyEffect() {
		creepManager.reduceToughness(creep, modifier, isFlat);
	}

	@Override
	public void onExpire() {
		while (numStacks > 0) {
			creepManager.increaseToughness(creep, modifier, isFlat);
			numStacks--;
		}
	}

	@Override
	public void onApply() {
		applyEffect();
	}
}
