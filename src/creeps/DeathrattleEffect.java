package creeps;

import levels.Updatable;
import projectileeffects.ProjectileEffect;
import projectiles.ProjectileGuider;
import utilities.Circle;

class DeathrattleEffect implements Updatable {
	private ProjectileEffect effect;
	private Circle areaOfEffect;
	private int defaultDuration; //TODO: Implement some ability to refresh shit
	private int currentDuration;
	private boolean limited;
	
	DeathrattleEffect (ProjectileEffect effect, Circle area, int defaultDuration) {
		this.effect = effect;
		this.areaOfEffect = area.clone();
		this.defaultDuration = defaultDuration;
		this.currentDuration = defaultDuration;
		if (defaultDuration < 0) {
			limited = false;
		} else {
			limited = true;
		}
	}
	
	void applyEffect(float modifier) { //TODO: Should this take a modifier?
		for (Creep c: ProjectileGuider.getInstance().getCreepInRange(areaOfEffect, effect.hitsAir())) {
			c.addEffect(effect);
		}
	}

	@Override
	public int update() {
		if (limited) {
			if (--currentDuration < 0) {
				return -1;
			}
			return 0;
		}
		return 0;
	}
	
	@Override
	public DeathrattleEffect clone() {
		return new DeathrattleEffect(effect, areaOfEffect, defaultDuration);
	}
}
