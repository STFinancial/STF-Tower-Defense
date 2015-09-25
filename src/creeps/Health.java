package creeps;

import utilities.GameConstants;
import levels.Updatable;

/**
 * Keeps track of all health related values of the creep.
 * @author Timothy
 *
 */
final class Health extends Attribute implements Updatable {
	//TODO: One idea instead of doing division and multiplication is to store damage done flat and percent as values and calculate current health only as needed
	private float maxHealth;
	private float currentHealth;
	private float maxHealthRegen; //Should this be split into percent and flat?
	private float currentHealthRegen;
	
	Health(CreepAttributes parent, float maxHealth, float maxHealthRegen) {
		this.parent				= parent;
		this.maxHealth 			= maxHealth;
		this.maxHealthRegen 	= maxHealthRegen;
		this.currentHealth 		= maxHealth;
		this.currentHealthRegen = maxHealthRegen;
	}
	
	float getCurrentHealth() { return currentHealth; }
	float getMaxHealth() { return maxHealth; }
	boolean isDead() { return currentHealth <= 0; }
	
	//TODO: Why even have "reduce current Health"
	//TODO: This seems like a lot of indirection for something that happens so often
	//TODO: Optimization - If things are too slow then we can get rid of the complex division formulas
	float damage(DamageType type, float amount, float penPercent, float penFlat, boolean ignoresShield, float shieldDrainModifier, float toughPenPercent, float toughPenFlat) { 
		float flatResist = (parent.getDamageResistFlat(type) * (1 - penPercent)) - penFlat;
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
			currentHealth *= amount;
		}
		if (currentHealth > maxHealth) { currentHealth = maxHealth; }
		return currentHealth;
	}
	
	void reduceMaxHealth(float amount, boolean isFlat) {
		if (isFlat) {
			maxHealth -= amount;
		} else {
			maxHealth += amount;
		}
		if (maxHealth < currentHealth) { currentHealth = maxHealth; }
	}
	
	void increaseMaxHealth(float amount, boolean isFlat) {
		if (isFlat) {
			maxHealth += amount;
		} else {
			maxHealth *= amount;
		}
	}
	
	void reduceCurrentHealthRegen(float amount, boolean isFlat) {
		if (isFlat) {
			currentHealthRegen -= amount;
		} else {
			currentHealthRegen *= 1 - amount;
		}
		if (currentHealthRegen < 0) { currentHealthRegen = 0; }
	}
	
	void increaseCurrentHealthRegen(float amount, boolean isFlat) {
		if (isFlat) {
			currentHealthRegen += amount;
		} else {
			currentHealthRegen *= amount;
		}
		if (currentHealthRegen > maxHealthRegen) { currentHealthRegen = maxHealthRegen; }
	}
	
	void reduceMaxHealthRegen(float amount, boolean isFlat) {
		if (isFlat) {
			maxHealthRegen -= amount;
		} else {
			maxHealthRegen -= 1 - amount;
		}
		if (maxHealthRegen < 0) { maxHealthRegen = 0; }
		if (currentHealthRegen > maxHealthRegen) { currentHealthRegen = maxHealthRegen; }
	}
	
	void increaseMaxHealthRegen(float amount, boolean isFlat) {
		if (isFlat) {
			maxHealthRegen += amount;
		} else {
			maxHealthRegen *= amount;
		}
	}

	@Override
	public int update() {
		if (isDead()) { return -1; }
		currentHealth += currentHealthRegen;
		return 1;
	}
}
