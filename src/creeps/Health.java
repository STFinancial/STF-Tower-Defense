package creeps;

import utilities.GameConstants;
import levels.Updatable;

/**
 * Keeps track of all health related values of the creep.
 * @author Timothy
 *
 */
final class Health extends Attribute implements Updatable {
	private float maxHealth;
	private float defaultHealth;
	private float currentHealth;
	private float maxHealthRegen;
	private float defaultHealthRegen; //Should this be split into percent and flat?
	private float regenReductionPercent;
	private float regenReductionFlat;
	private float currentHealthRegen;
	
	Health(CreepAttributes parent, float maxHealth, float defaultHealthRegen) {
		this.parent				= parent;
		this.maxHealth 			= maxHealth;
		this.defaultHealth		= maxHealth;
		this.maxHealthRegen		= defaultHealthRegen;
		this.defaultHealthRegen 	= defaultHealthRegen;
		this.currentHealth 		= maxHealth;
		this.currentHealthRegen = defaultHealthRegen;
		this.regenReductionPercent = 0;
		this.regenReductionFlat = 0;
	}
	
	float getCurrentHealth() { return currentHealth; }
	float getMaxHealth() { return maxHealth; }
	boolean isDead() { return currentHealth <= 0; }
	
	//TODO: Why even have "reduce current Health"
	//TODO: This seems like a lot of indirection for something that happens so often
	//TODO: Optimization - If things are too slow then we can get rid of the complex division formulas
	//TODO: I thought the idea was to increase encapsulation, is this really accomplishing that?
	float damage(DamageType type, float amount, float penPercent, float penFlat, boolean ignoresShield, float shieldDrainModifier, float toughPenPercent, float toughPenFlat) { 
		float flatResist = (parent.getCurrentDamageResist(type, true) * (1 - penPercent)) - penFlat;
		float damageModifier = 1 - (flatResist / (GameConstants.DAMAGE_RESIST_DENOMINATOR_VALUE + flatResist));
		float damageToDo = amount * damageModifier;
		damageToDo = damageToDo - ((parent.getCurrentToughness() * (1 - toughPenPercent)) - toughPenFlat);
		if (damageToDo > 0) {
			float shieldDamage = damageToDo * shieldDrainModifier;
			float currentShield = parent.getCurrentShield();
			if (ignoresShield) {
				return reduceCurrentHealth(damageToDo, true);
			} else if (currentShield < shieldDamage) {
				float damageLeft = (shieldDamage - currentShield) / shieldDrainModifier;
				parent.reduceCurrentShield(currentShield, true);
				reduceCurrentHealth(damageLeft, true);
			} else if (shieldDamage != 0) {
				parent.reduceCurrentShield(shieldDamage, true);
			} else if (currentShield == 0) {
				reduceCurrentHealth(damageToDo, true);
			}
		} 
		return currentHealth;
	}
	
	//TODO: Optimization - Can remove the boolean and separate into two methods
	float reduceCurrentHealth(float amount, boolean isFlat) {
		if (isFlat) {
			currentHealth -= amount;	
		} else {
			currentHealth *= 1 - amount;
		}
		if (currentHealth < 0) { currentHealth = 0; }
		return currentHealth;
	}
	
	float increaseCurrentHealth(float amount, boolean isFlat) {
		if (isFlat) {
			currentHealth += amount;
		} else {
			currentHealth *= 1 + amount;
		}
		if (currentHealth > maxHealth) { currentHealth = maxHealth; }
		return currentHealth;
	}
	
	void reduceMaxHealth(float amount, boolean isFlat) {
		if (isFlat) {
			maxHealth -= amount;
		} else {
			maxHealth *= 1 - amount;
		}
		if (maxHealth < currentHealth) { currentHealth = maxHealth; }
	}
	
	void increaseMaxHealth(float amount, boolean isFlat) {
		if (isFlat) {
			maxHealth += amount;
		} else {
			maxHealth *= 1 + amount;
		}
	}
	
	private float updateHealthRegen() {
		currentHealthRegen = maxHealthRegen - (maxHealthRegen * regenReductionPercent) - regenReductionFlat;
		return (currentHealthRegen < 0 ? currentHealthRegen = 0 : currentHealthRegen);
	}
	
	void reduceHealthRegen(float amount, boolean isFlat) {
		if (isFlat) {
			regenReductionFlat += amount;
		} else {
			regenReductionPercent += amount;
		}
		updateHealthRegen();
	}
	
	void increaseHealthRegen(float amount, boolean isFlat) {
		if (isFlat) {
			regenReductionFlat -= amount;
		} else {
			regenReductionPercent -= amount;
		}
		updateHealthRegen();
	}
	
	void nullify() {
		//TODO: Should it be done this way?
		maxHealthRegen = 0;
		updateHealthRegen();
	}

	@Override
	public int update() {
		if (isDead()) { return -1; }
		currentHealth += currentHealthRegen;
		return 1;
	}

	Attribute clone(CreepAttributes parent) {
		return new Health(parent, defaultHealth, defaultHealthRegen);
	}
	
	
}
