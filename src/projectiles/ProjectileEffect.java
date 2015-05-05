package projectiles;

import creeps.Creep;
import creeps.ElementType;


/*
 * An on hit effect that can be applied from a projectile
 * information such as chance to hit, duration and effect are included
 */
public abstract class ProjectileEffect {
	Projectile parentProjectile;

	public float modifier = 0;
	public int lifetime = 0;
	public ElementType elementType;

	public ProjectileEffect(float modifier) {
		this.modifier = modifier;
	}

	public ProjectileEffect(int lifetime, float modifier) {
		this(modifier);
		this.lifetime = lifetime;
	}

	public abstract void applyEffect(Creep creep);

	public boolean isExpired(){
		return lifetime < 0;
	}

	public abstract void onExpire(Creep creep);

	public void setModifier(float modifier) {
		this.modifier = modifier;
	}

	public void setParent(Projectile projectile) {
		parentProjectile = projectile;
	}

	public boolean update(Creep creep) {
		if (lifetime == 0) {
			onExpire(creep);
		}
		else if (lifetime > 0) {
			applyEffect(creep);
		}
		lifetime--;
		return lifetime < 0;
	}
}
