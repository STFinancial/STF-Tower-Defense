package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;
//TODO: Stackable?
public class Wealth extends ProjectileEffect implements Stackable {
	//TODO: Add isFlat and onHit to constructor so we can do flat amounts on hit
	
	private boolean isFlat;
	private boolean onHit;
	private int maxStacks;
	private int numStacks;
	
	public Wealth(int lifetime, float modifier, DamageType damageType, Projectile parent, boolean isFlat, boolean onHit) {
		super(lifetime, modifier, 0, damageType, parent);
		this.numStacks = 0;
		this.maxStacks = 1;
		this.isFlat = isFlat;
		this.onHit = onHit;
	}

	@Override
	public ProjectileEffect clone() {
		return new Wealth(lifetime, modifier, damageType, parent, isFlat, onHit);
	}

	@Override
	protected void applyEffect() {
		if (onHit) {
			creepManager.increaseGoldOnHit(creep, modifier);
		} else {
			creepManager.increaseGoldValue(creep, modifier, isFlat);
		}
	}

	@Override
	public void onExpire() {
		if (lifetime == 0) {
			return;
		}
		if (onHit) {
			creepManager.reduceGoldOnHit(creep, modifier);
		} else {
			creepManager.reduceGoldValue(creep, modifier, isFlat);
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
