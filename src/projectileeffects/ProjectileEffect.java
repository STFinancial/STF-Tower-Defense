package projectileeffects;

import creeps.Creep;
import creeps.DamageType;
import projectiles.Projectile;
import levels.Updatable;


/*
 * An on hit effect that can be applied from a projectile
 * information such as chance to hit, duration and effect are included
 */
public abstract class ProjectileEffect implements Updatable {
	protected float modifier;
	protected int lifetime;
	protected int timing;
	protected int counter;
	protected DamageType damageType;
	protected Projectile parent;
	protected Creep creep;

	public ProjectileEffect(int lifetime, float modifier, int timing, DamageType damageType, Projectile parent) {
		this.modifier = modifier;
		this.lifetime = lifetime;
		this.counter = lifetime;
		this.damageType = damageType;
		this.timing = timing;
		this.parent = parent;
	}

	public abstract ProjectileEffect clone();
	/**
	 * This function is called when the effect is first added to the creep. May call apply effect internally.
	 */
	public abstract void onApply();
	/**
	 * This function is what actually applies what the effect is supposed to do
	 */
	protected abstract void applyEffect(); //TODO: Reduce the visibility of this
	/**
	 * This function is called as the effect falls off the creep
	 */
	public abstract void onExpire();
	
	public boolean hitsAir() { return parent.hitsAir(); }
	
	public int update() {
		counter--;
		if (counter < 0) {
			//remove the effect and call on expire
			onExpire();
			return -1;
		} else if (timing != 0 && counter % timing == 0) {
			//apply the effect
			applyEffect();
			return 1;
		} else {
			//Do nothing
			return 0;
		}
	}
	
	public void setCreep(Creep creep) {	this.creep = creep; }
	
	public ProjectileEffect applyPenalty(float penalty) { modifier *= penalty; lifetime *= penalty; return this; }
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ProjectileEffect)) {
			return false;
		}
		ProjectileEffect p = (ProjectileEffect) o;
		return  p.modifier == modifier &&
				p.damageType == damageType &&
				p.getClass() == getClass() &&
				p.lifetime == lifetime &&
				p.timing == timing && 
				p.parent.getParent().towerID == parent.getParent().towerID; //TODO: Should this be part of the equality test, do we want similar debuffs from different towers to stack or not?
	}
	
	@Override
	public int hashCode() { //TODO: Optimization - Shorten this for hashset speed increase?
		int result = 17;
		result = 31 * result + damageType.ordinal();
		result = 31 * result + getClass().hashCode();
		result = 31 * result + (int) lifetime;
		result = 31 * result + (int) modifier;
		result = 31 * result + timing;
		result = 31 * result + parent.getParent().towerID;
		return result;
	}
}
