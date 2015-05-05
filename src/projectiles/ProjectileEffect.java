package projectiles;

import creeps.Creep;


/*
 * An on hit effect that can be applied from a projectile
 * information such as chance to hit, duration and effect are included
 */
public abstract class ProjectileEffect {
	Projectile parentProjectile;
	
	public float modifier = 0;
	public int lifetime = 0;

	public ProjectileEffect(float modifier) {
		this.modifier = modifier;
	}

	public ProjectileEffect(int lifetime, float modifier) {
		this(modifier);
		this.lifetime = lifetime;
	}

	public abstract void applyEffect(Creep creep);

	public abstract void onExpire(Creep creep);

	public void setModifier(float modifier) {
		this.modifier = modifier;
	}

	public void update(Creep creep) {
		if (lifetime == 0) {
			onExpire(creep);
		}
		else {
			applyEffect(creep);
		}
		lifetime--;
	}
	
	public boolean isExpired(){
		return lifetime < 0;
	}
}
