package creeps;

import projectiles.Projectile;

public class ToughnessShred extends ProjectileEffect implements Stackable {
	private boolean isFlat;
	private int numStacks;
	private int maxStacks;
	
	public ToughnessShred(int lifetime, float modifier, DamageType damageType, Projectile parent, boolean isFlat) {
		super(lifetime, modifier, 0, damageType, parent);
		this.numStacks = 0;
		this.maxStacks = -1;
		this.isFlat = isFlat;
	}

	@Override
	public ProjectileEffect clone() {
		ToughnessShred a = new ToughnessShred(lifetime, modifier, damageType, parent, isFlat);
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
	public void applyEffect() {
		if (isFlat) {
			creep.reduceFlatToughness(modifier);
		} else {
			creep.reducePercentToughness(modifier);
		}
	}

	@Override
	public void onExpire() {
		if (isFlat) {
			while (numStacks > 0) {
				creep.increaseFlatToughness(modifier);
				numStacks--;
			}
		} else {
			while (numStacks > 0) {
				creep.increasePercentToughness(modifier);
				numStacks--;
			}
		}
	}
}
