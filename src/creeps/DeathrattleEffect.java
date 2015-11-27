package creeps;

import game.GameObject;
import projectileeffects.ProjectileEffect;
import utilities.Circle;

class DeathrattleEffect extends GameObject {
	private ProjectileEffect effect;
	private Circle areaOfEffect;
	private int defaultDuration; //TODO: Implement some ability to refresh shit
	private int currentDuration;
	private boolean limited;
	private boolean hitsAir;
	
	DeathrattleEffect (ProjectileEffect effect, Circle area, int defaultDuration, boolean hitsAir) {
		this.effect = effect;
		this.areaOfEffect = area.clone();
		this.defaultDuration = defaultDuration;
		this.currentDuration = defaultDuration;
		this.hitsAir = hitsAir;
		if (defaultDuration < 0) {
			limited = false;
		} else {
			limited = true;
		}
	}
	
	void applyEffect(float modifier) { //TODO: Should this take a modifier?
		for (Creep c: CreepManager.getInstance().getCreepInRange(areaOfEffect, hitsAir)) {
			c.addEffect(effect);
		}
	}

	@Override
	public int update() {
		//TODO: These return values aren't used, so the duration is meaningless
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
		return new DeathrattleEffect(effect, areaOfEffect, defaultDuration, hitsAir);
	}
}
