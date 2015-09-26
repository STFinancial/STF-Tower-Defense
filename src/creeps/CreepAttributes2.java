package creeps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import projectileeffects.Bleed;
import projectileeffects.ProjectileEffect;
import projectileeffects.Slow;

import utilities.GameConstants;
import levels.Updatable;

final class CreepAttributes2 implements Updatable {
	
	
	float consumeBleeds(float amount) {
		ArrayList<ProjectileEffect> s = new ArrayList<ProjectileEffect>();
		for (Iterator<ProjectileEffect> iterator = effects.iterator(); iterator.hasNext();) {
			ProjectileEffect e = iterator.next();
			if (e instanceof Bleed) {
				s.add(((Bleed) e).convertToDamage(amount, e.counter));
				iterator.remove();
			}
		}
		addAllEffects(s);
		return currentHealth;
	}
	
	float reduceMaxSpeed(DamageType type, float amount, boolean isFlat) {
		if (isFlat) {
			maxSpeed -= amount;
		} else {
			maxSpeed *= 1 - amount;	
		}
		if (currentSpeed > maxSpeed) {
			currentSpeed = maxSpeed;
		}
		return currentSpeed;
	}
	

	void nullify() {
		healthRegenerationRate = 0;
		currentHealthRegenerationRate = 0;
		shieldRegenerationRate = 0;
		currentShieldRegenerationRate = 0;
	}
}
