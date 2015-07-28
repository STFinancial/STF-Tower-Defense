package projectileeffects;

import projectiles.Projectile;
import creeps.Creep;
import creeps.Creep.CreepEffect;
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

	public ProjectileEffect(int lifetime, float modifier, int timing, DamageType damageType) {
		this.modifier = modifier;
		this.lifetime = lifetime;
		this.damageType = damageType;
		this.timing = timing;
		//timeRemaining = lifetime;
	}

	public abstract void applyEffect(Creep creep, Projectile p);

//	public boolean isExpired(){
//		return timeRemaining < 0;
//	}

	public abstract void onExpire(Creep creep);

	public void setModifier(float modifier) {
		this.modifier = modifier;
	}

//	public boolean update(Creep creep) {
//		if (timeRemaining == 0) {
//			onExpire(creep);
//		}
//		else if (timeRemaining > 0) {
//			applyEffect(creep);
//		}
//		timeRemaining--;
//		return timeRemaining < 0;
//	}
}
