package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.DamageType;


/*
 * An on hit effect that can be applied from a projectile
 * information such as chance to hit, duration and effect are included
 */
public abstract class ProjectileEffect {
	public float modifier = 0;
	public int lifetime = 0;
	public int timing;
	public DamageType damageType;
	public Projectile parent;

	public ProjectileEffect(int lifetime, float modifier, int timing, DamageType damageType, Projectile parent) {
		this.modifier = modifier;
		this.lifetime = lifetime;
		this.damageType = damageType;
		this.timing = timing;
		this.parent = parent;
	}

	public abstract void applyEffect(Creep creep);
	public abstract void onExpire(Creep creep);
}
