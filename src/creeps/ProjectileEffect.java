package creeps;

import projectiles.Projectile;
import levels.Updatable;


/*
 * An on hit effect that can be applied from a projectile
 * information such as chance to hit, duration and effect are included
 */
public abstract class ProjectileEffect implements Updatable {
	//TODO: Reduce visibility
	public float modifier = 0;
	public int lifetime = 0;
	public int timing;
	public int counter;
	public DamageType damageType;
	public Projectile parent;
	public Creep creep;

	public ProjectileEffect(int lifetime, float modifier, int timing, DamageType damageType, Projectile parent) {
		this.modifier = modifier;
		this.lifetime = lifetime;
		this.damageType = damageType;
		this.timing = timing;
		this.counter = lifetime;
		this.parent = parent;
	}

	public abstract ProjectileEffect clone();
	public abstract void onApply();
	public abstract void applyEffect();
	public abstract void onExpire();
	
	
	public int update() {
		counter--;
		if (timing == 0 || counter % timing == 0) {
			//apply the effect
			return 1;
		} else if (counter < 0) {
			//remove the effect and call on expire
			return -1;
		} else {
			//Do nothing
			return 0;
		}
	}
	
	public void setCreep(Creep creep) {	this.creep = creep; }
}
