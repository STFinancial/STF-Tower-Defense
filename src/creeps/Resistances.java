package creeps;

import projectileeffects.ArmorShred;
import projectileeffects.Snare;
import creeps.Creep.CreepEffect;
import levels.Updatable;
//TODO: Possibly make this a singleton if all creeps use the same one (at most we create only a few hundred of these, shouldn't be too much overhead)
//TODO: Optimization - This will need to be refactored away from float math if everything is too slow
/**
 * Contains the values for the damage resistances and slow resistances. Also includes toughness, shield, and snare immunity.
 * @author Timothy
 *
 */
final class Resistances implements Updatable {
	private Creep parent;
	private float[] maxDamageResistsFlat;
	private float[] currentDamageResistsFlat;
	private float[] currentDamageResistsPercent;
	
	private float maxShield;
	private float currentShield;
	private float shieldRegenerationRate; //Shield increase per game tick
	
	private float maxToughness;
	private float currentToughness;
	
	private float[] maxSlowResists;
	private float[] currentSlowResists;
	
	private float maxHealth;
	private float currentHealth;
	private float healthRegenerationRate; //Health increase per game tick
	
	private float maxSpeed;
	private float currentSpeed;
	
	private boolean snareImmune;
	
	Resistances(Creep parent, float[] maxDamageResistsFlat, float[] maxSlowResists, float maxHealth, float healthRegenRate, float maxToughness, float maxShieldValue, float shieldRegenRate, boolean snareImmune) {
		this.parent = parent;
		//Damage Resists
		this.maxDamageResistsFlat = maxDamageResistsFlat;
		this.currentDamageResistsFlat = new float[currentDamageResistsFlat.length];
		this.currentDamageResistsPercent = new float[currentDamageResistsFlat.length];
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
	 * @return True if the creep is immune to snare, false if not.
	 */
	boolean isSnareImmune() { return snareImmune; }

	/**
	 * Reduces the health of the creep by the amount specified. This amount should be positive and should be post-resistance calculations.
	 * @param amount - The amount of damage to deal to the creep. This value should be positive.
	 * @return - The health of the creep after dealing the damage.
	 */
	float reduceHealth(float amount) { 
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
	//TODO: this is wrong wrong wrong, we need to account for reductions of 1 on expiration\
	//TODO: Therefore I'm thinking about moving all the creep effects to this class and dealing with those in the update
	float modifyPercentResist(DamageType type, float percentReduction) {
		currentDamageResistsFlat[type.ordinal()] *= (1 - percentReduction);
		for (CreepEffect c: creep.effects) {
			if (c.projectileEffect instanceof ArmorShred) {
				((Snare) c.projectileEffect).applyEffect(creep);
			}
		}
	}
	
	float modifyFlatResist(DamageType type, float amount) {
		
	}
	
	float modifyFlatToughness(float amount) {
		
	}
	
	float modifyPercentToughness(float amount) {
		
	}
	
	float reduceFlatShield(float amount) {
		
	}
	
	float reducePercentShield(float amount) {
		
	}
	
	@Override public void update() {
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
}
