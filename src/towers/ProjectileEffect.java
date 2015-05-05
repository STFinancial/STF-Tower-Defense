package towers;

import creeps.Creep;

/*
 * An on hit effect that can be applied from a projectile
 * information such as chance to hit, duration and effect are included
 */
public class ProjectileEffect {
	public enum ProjectileEffectType {
		DAMAGE, SLOW, SNARE, STUN, DAMAGE_OVER_TIME, MORTAL_STRIKE, ;
	}

	public ProjectileEffectType effectType;
	public float modifier;

	public ProjectileEffect(ProjectileEffectType type) {
		effectType = type;
	}

	public void applyEffect(Creep creep) {
	}

	public void setModifier(float modifier) {
		this.modifier = modifier;
	}
}
