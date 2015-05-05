package projectiles;

import creeps.Creep;


/*
 * An on hit effect that can be applied from a projectile
 * information such as chance to hit, duration and effect are included
 */
public abstract class ProjectileEffect {
	public ProjectileEffectType effectType;
	public float modifier;

	public ProjectileEffect(ProjectileEffectType type) {
		effectType = type;
	}

	public abstract void applyEffect(Creep creep);

	public void setModifier(float modifier) {
		this.modifier = modifier;
	}
}