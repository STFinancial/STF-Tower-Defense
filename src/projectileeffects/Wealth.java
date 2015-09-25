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
		this.isFlat = isFlat;
		this.onHit = onHit;
	}

	@Override
	public ProjectileEffect clone() {
		return new Wealth(lifetime, modifier, damageType, parent, isFlat, onHit);
	}

	@Override
	protected void applyEffect() {
		//TODO if we want flat increases, we need to loop through the creep and unapply all the multipliers, decrement the gold value, then reapply them
		//TODO if we really want flat increases we need to give it the same treatment as resistances was given
		
		if (modifier > 0) {
			creep.goldValue *= modifier;
		}
	}

	@Override
	public void onExpire() {
		if (modifier > 0) {
			creep.goldValue /= modifier;
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
