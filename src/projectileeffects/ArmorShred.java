package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public class ArmorShred extends ProjectileEffect implements Stackable {
	private boolean isFlat;
	private int numStacks;
	private int maxStacks;
	
	public ArmorShred(int lifetime, float modifier, DamageType damageType, Projectile parent, boolean isFlat) {
		super(lifetime, modifier, 0, damageType, parent);
		this.numStacks = 0;
		this.maxStacks = 1;
		this.isFlat = isFlat;
	}

	@Override
	public ProjectileEffect clone() {
		ArmorShred a = new ArmorShred(lifetime, modifier, damageType, parent, isFlat);
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
	public void onApply() {
		stack();
	}
	
	@Override
	protected void applyEffect() {
		creep.reduceDamageResist(damageType, modifier, isFlat);
	}

	@Override
	public void onExpire() {
		while (numStacks > 0) {
			creep.increaseDamageResist(damageType, modifier, isFlat);
			numStacks--;
		}
	}
}
