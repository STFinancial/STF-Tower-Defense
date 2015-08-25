package creeps;

import java.util.ArrayList;

import projectileeffects.ArmorShred;
import projectileeffects.Consume;
import projectileeffects.ProjectileEffect;
import projectileeffects.Snare;
import levels.Updatable;
//TODO: Possibly make this a singleton if all creeps use the same one (at most we create only a few hundred of these, shouldn't be too much overhead)
//TODO: Optimization - This will need to be refactored away from float math if everything is too slow
/**
 * Contains the values for the damage resistances and slow resistances. Also includes toughness, shield, and snare immunity.
 * @author Timothy
 *
 */
final class CreepAttributes implements Updatable {
	private ArrayList<CreepEffect> effects = new ArrayList<CreepEffect>();
	
	private Creep parent;
	private float[] maxDamageResistsFlat; //the maximum value of the damage resistances
	private float[] currentResistReductionPercent; //the percent reduction for each resistance currently applied
	private float[] currentResistReductionFlat; //the flat reduction for each resistance currently applied
	private float[] currentDamageResistsFlat; //the current flat damage reduction values
	private float[] currentDamageResistsPercent; //the current damage reduction as a percent
	
	private float maxShield;
	private float currentShield;
	private float shieldRegenerationRate; //Shield increase per game tick
	
	private float maxToughness;
	private float currentToughnessReductionPercent;
	private float currentToughnessReductionFlat;
	private float currentToughness;
	
	private float[] maxSlowResists;
	private float[] currentSlowResists;
	
	private float maxHealth;
	private float currentHealth;
	private float healthRegenerationRate; //Health increase per game tick
	
	private float maxSpeed;
	private float currentSpeed;
	
	private boolean snareImmune;
	private int snareGrace;
	private int timeUntilSnare;
	
	CreepAttributes(Creep parent, float[] maxDamageResistsFlat, float[] maxSlowResists, float maxHealth, float healthRegenRate, float maxToughness, float maxShieldValue, float shieldRegenRate, boolean snareImmune) {
		this.parent = parent;
		effects = new ArrayList<CreepEffect>();
		//Damage Resists
		this.maxDamageResistsFlat = maxDamageResistsFlat;
		this.currentDamageResistsFlat = new float[currentDamageResistsFlat.length];
		this.currentDamageResistsPercent = new float[currentDamageResistsFlat.length];
		this.currentResistReductionPercent = new float[currentDamageResistsFlat.length];
		this.currentResistReductionFlat = new float[currentDamageResistsFlat.length];
		for (int i = 0; i < maxDamageResistsFlat.length; i++) {
			float resist = maxDamageResistsFlat[i];
			this.currentDamageResistsPercent[i] = resist / (resist + 100f);
			this.currentDamageResistsFlat[i] = resist;
		}
		//Shields
		this.maxShield = maxShieldValue;
		this.currentShield = maxShieldValue;
		this.shieldRegenerationRate = shieldRegenRate;
		
		//Toughness
		this.maxToughness = maxToughness;
		this.currentToughnessReductionPercent = 0;
		this.currentToughnessReductionFlat = 0;
		this.currentToughness = maxToughness;
		
		//Slow Resists
		this.maxSlowResists = maxSlowResists;
		this.currentSlowResists = new float[maxSlowResists.length];
		//TODO: Optimization - can consolidate this with the other for loop, sacrifice readability
		for (int i = 0; i < maxSlowResists.length; i++) {
			currentSlowResists[i] = maxSlowResists[i];
		}
		
		//Health
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.healthRegenerationRate = healthRegenRate;
		
		//Snare Immunity
		this.snareImmune = snareImmune;
		this.snareGrace = 15;
		this.timeUntilSnare = snareGrace;
	}
	
	/**
	 * Returns the flat resistance value for the specified DamageType.
	 * @param type - The DamageType to obtain the resistance value for.
	 * @return The flat resistance value for the DamageType.
	 */
	float getCurrentFlatResist(DamageType type) { return currentDamageResistsFlat[type.ordinal()]; }
	
	/**
	 * Returns, as a decimal, the percentage of damage resisted of the specified element. Values can be outside the range 0-1.
	 * @param type - The DamageType to obtain the resistance value for.
	 * @return The percent resistance value for the DamageType.
	 */
	float getCurrentPercentResist(DamageType type) { return currentDamageResistsPercent[type.ordinal()]; }
	/**
	 * Returns, as a decimal, the percentage of slow resisted of the specified element. Values can be outside the range 0-1.
	 * @param type - The DamageType to obtain the resistance value for.
	 * @return The percent resistance value for the slow of DamageType.
	 */
	float getCurrentSlowResist(DamageType type) { return currentSlowResists[type.ordinal()]; }
	/**
	 * @return The current health value of this creep.
	 */
	float getCurrentHealth() { return currentHealth; }
	/**
	 * @return The current toughness value of this creep.
	 */
	float getCurrentToughness() { return currentToughness; }
	/**
	 * @return The current shield value of this creep.
	 */
	float getCurrentShield() { return currentShield; }
	/**
	 * @return The current movement speed of the creep.
	 */
	float getCurrentSpeed() { return currentSpeed; }
	/**
	 * @return True if the creep is immune to snare, false if not.
	 */
	boolean isSnareImmune() { return snareImmune; }
	
	//TODO: We're switching this over to calculating all the damage? (then how to we ignore shield and shit)
	/**
	 * Reduces the health of the creep by the amount specified. This amount should be positive and should be post-resistance calculations.
	 * @param amount - The amount of damage to deal to the creep. This value should be positive.
	 * @return - The health of the creep after dealing the damage.
	 */
	float damage(float amount, float penPercent, float penFlat, boolean ignoresShield, float shieldDrainModifier, float toughPenPercent, float toughPenFlat) { 
		if (amount < 0) {
			currentHealth -= amount;
			if (currentHealth < 0) {
				currentHealth = 0;
			}
		}
		return currentHealth;
	}
	
	/**
	 * 
	 * @param type
	 * @param percentReduction
	 * @return
	 */
	float reducePercentResist(DamageType type, float percentReduction) {
		int index = type.ordinal();
		currentResistReductionPercent[index] += percentReduction;
		return updateDamageResistPercent(index);
	}
	
	float increasePercentResist(DamageType type, float percentIncrease) {
		int index = type.ordinal();
		currentResistReductionPercent[index] -= percentIncrease;
		return updateDamageResistPercent(index);
	}
	
	float reduceFlatResist(DamageType type, float amount) {
		int index = type.ordinal();
		currentResistReductionFlat[index] += amount;
		return updateDamageResistPercent(index);
	}
	
	float increaseFlatResist(DamageType type, float amount) {
		int index = type.ordinal();
		currentResistReductionFlat[index] -= amount;
		return updateDamageResistPercent(index);
	}
	
	private float updateDamageResistPercent(int index) {
		currentDamageResistsFlat[index] = maxDamageResistsFlat[index] - (maxDamageResistsFlat[index] * currentResistReductionPercent[index]) - currentResistReductionFlat[index];
		currentDamageResistsPercent[index] = currentDamageResistsFlat[index] / (currentDamageResistsFlat[index] + 100f);
		return currentDamageResistsPercent[index];
	}
	
	float reduceFlatToughness(float amount) {
		currentToughnessReductionFlat += amount;
		return updateToughness();
	}
	
	float increaseFlatToughness(float amount) {
		currentToughnessReductionFlat -= amount;
		return updateToughness();
	}
	
	float reducePercentToughness(float amount) {
		currentToughnessReductionPercent += amount;
		return updateToughness();
	}
	
	float increasePercentToughness(float amount) {
		currentToughnessReductionPercent -= amount;
		return updateToughness();
	}
	
	private float updateToughness() {
		currentToughness = maxToughness - (maxToughness * currentToughnessReductionPercent) - currentToughnessReductionFlat;
		return currentToughness;
	}
	
	float reduceFlatShield(float amount) {
		
	}
	
	float reducePercentShield(float amount) {
		
	}
	
	
	
	@Override public void update() {
		updateEffects();
		if (currentHealth > 0) {
			currentHealth += healthRegenerationRate;
			if (currentHealth >= maxHealth) {
				currentHealth = maxHealth;
			}
		}
		//TODO: Maybe make it so shield only regenerates after health is full?
		//TODO: Or after a certain amount of time after being hit.
		currentShield += shieldRegenerationRate;
		if (currentShield >= maxShield) {
			currentShield = maxShield;
		}
	}
	
	void addEffect(ProjectileEffect effect) {
		CreepEffect c;
		if (effect instanceof Consume) {
			//Need to proc it immediately because of how the iterators work
			//Can't remove bleeds while we're looping through the set
			effect.applyEffect(parent);
		} else if (effect.refreshable && (c = hasEffect(effect)) != null) {
			c.counter = 0;
		} else {
			this.effects.add(new CreepEffect(effect));
		}
	}
	
	void addAllEffects(ArrayList<ProjectileEffect> effects) {
		for (ProjectileEffect p : effects) {
			addEffect(p);
		}
	}
	
	private void updateEffects() {
		currentSpeed = maxSpeed;
		timeUntilSnare--;
		for (int i = 0; i < effects.size(); i++) {
			CreepEffect e = effects.get(i);
			if (e.timing != 0 && e.counter % e.timing == 0) {
				e.projectileEffect.applyEffect(parent);
			} else if (e.timing == 0 && e.counter == 0) {
				e.projectileEffect.applyEffect(parent);
			}
			if (e.counter >= e.lifetime) {
				e.projectileEffect.onExpire(parent);
				effects.remove(i--);
				continue;
			}
			e.counter++;
		}
	}
	
	private CreepEffect hasEffect(ProjectileEffect pe) {
		for (CreepEffect c: effects) {
			if (c.projectileEffect.equals(pe)) {
				return c;
			}
		}
		return null;
	}
}
