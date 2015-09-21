package creeps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import utilities.GameConstants;
import levels.Updatable;
//TODO: Possibly make this a singleton if all creeps use the same one (at most we create only a few hundred of these, shouldn't be too much overhead)
//TODO: Optimization - This will need to be refactored away from float math if everything is too slow
//TODO: Organize this a bit better.
/**
 * Contains the values for the damage resistances and slow resistances. Also includes toughness, shield, and snare immunity.
 * @author Timothy
 *
 */
final class CreepAttributes implements Updatable {
	private HashSet<ProjectileEffect> effects = new HashSet<ProjectileEffect>();
	
	private Creep parent;
	private float[] maxDamageResistsFlat; //the maximum value of the damage resistances
	private float[] currentResistReductionPercent; //the percent reduction for each resistance currently applied
	private float[] currentResistReductionFlat; //the flat reduction for each resistance currently applied
	private float[] currentDamageResistsFlat; //the current flat damage reduction values
	private float[] currentDamageResistsPercent; //the current damage reduction as a percent
	
	private float[] damageOnHitArray;
	
	private float maxShield;
	private float currentShield;
	private float shieldRegenerationRate; //Shield increase per game tick
	private float currentShieldRegenerationRate;
	
	private float maxToughness;
	private float currentToughnessReductionPercent;
	private float currentToughnessReductionFlat;
	private float currentToughness;
	
	private float[] slowResists;
	
	private float maxHealth;
	private float currentHealth;
	private float healthRegenerationRate; //Health increase per game tick
	private float currentHealthRegenerationRate;
	
	private float maxSpeed;
	private float currentSpeed;
	
	private boolean snareImmune;
	private int snareGrace;
	private int timeUntilSnare;
	private boolean isDisoriented;
	private boolean disorientImmune;
	private int disorientGrace;
	private int timeUntilDisorient;
	
	CreepAttributes(Creep parent, float[] maxDamageResistsFlat, float[] slowResists, float maxHealth, float healthRegenRate, float maxToughness, float maxShieldValue, float shieldRegenRate, boolean snareImmune, boolean disorientImmune, float maxSpeed) {
		this.parent = parent;
		effects = new HashSet<ProjectileEffect>();
		//Damage Resists
		this.maxDamageResistsFlat = maxDamageResistsFlat;
		this.currentDamageResistsFlat = new float[maxDamageResistsFlat.length];
		this.currentDamageResistsPercent = new float[maxDamageResistsFlat.length];
		this.currentResistReductionPercent = new float[maxDamageResistsFlat.length];
		this.currentResistReductionFlat = new float[maxDamageResistsFlat.length];
		for (int i = 0; i < maxDamageResistsFlat.length; i++) {
			float resist = maxDamageResistsFlat[i];
			this.currentDamageResistsPercent[i] = resist / (resist + GameConstants.RESIST_DENOMINATOR_VALUE);
			this.currentDamageResistsFlat[i] = resist;
		}
		
		this.damageOnHitArray = new float[maxDamageResistsFlat.length];
		
		//Shields
		this.maxShield = maxShieldValue;
		this.currentShield = maxShieldValue;
		this.shieldRegenerationRate = shieldRegenRate;
		this.currentShieldRegenerationRate = shieldRegenRate;
		
		//Toughness
		this.maxToughness = maxToughness;
		this.currentToughnessReductionPercent = 0;
		this.currentToughnessReductionFlat = 0;
		this.currentToughness = maxToughness;
		
		//Slow Resists
		this.slowResists = slowResists;
		
		//Health
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.healthRegenerationRate = healthRegenRate;
		this.currentHealthRegenerationRate = healthRegenRate;
		
		//Snare Immunity
		this.snareImmune = snareImmune;
		this.snareGrace = 30;
		this.timeUntilSnare = snareGrace;
		
		//Disorient Immunity
		this.isDisoriented = false;
		this.disorientImmune = disorientImmune;
		this.disorientGrace = 30;
		this.timeUntilDisorient = disorientGrace;
		
		this.maxSpeed = maxSpeed;
		this.currentSpeed = maxSpeed;
	}
	
	private CreepAttributes(CreepAttributes attributes) {
		this(attributes.parent, attributes.maxDamageResistsFlat, attributes.slowResists, attributes.maxHealth, attributes.healthRegenerationRate, attributes.maxToughness, attributes.maxShield, attributes.shieldRegenerationRate, attributes.snareImmune, attributes.disorientImmune, attributes.maxSpeed);
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
	float getSlowResist(DamageType type) { return slowResists[type.ordinal()]; }
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
	
	//TODO: Do we really want to apply toughness to damage of every type?
	/**
	 * Reduces the health of the creep by the amount specified. This amount should be positive and should be post-resistance calculations.
	 * @param amount - The amount of damage to deal to the creep. This value should be positive.
	 * @return The health of the creep after dealing the damage.
	 */
	float damage(DamageType type, float amount, float penPercent, float penFlat, boolean ignoresShield, float shieldDrainModifier, float toughPenPercent, float toughPenFlat) { 
		float flatResist = (currentDamageResistsFlat[type.ordinal()] * (1 - penPercent)) - penFlat;
		float damageModifier = 1 - (flatResist / (GameConstants.RESIST_DENOMINATOR_VALUE + flatResist));
		float damageToDo = amount * damageModifier;
		damageToDo = damageToDo - ((currentToughness * (1 - toughPenPercent)) - toughPenFlat);
		if (damageToDo > 0) {
			float shieldDamage = damageToDo * shieldDrainModifier;
			if (ignoresShield) {
				return currentHealth -= damageToDo;
			} else if (currentShield < shieldDamage) {
				float damageLeft = (shieldDamage - currentShield) / shieldDrainModifier;
				currentShield = 0;
				currentHealth -= damageLeft;
			} else {
				currentShield -= shieldDamage;
			}
		} 
		return currentHealth;
	}
	
	void snare() {
		if (!snareImmune && timeUntilSnare < 0) {
			timeUntilSnare = snareGrace; //TODO: Should this be set in unsnare? If so need to fix stuff in constructor too.
			currentSpeed = 0;			
		}
	}
	
	void unsnare() {
		currentSpeed = maxSpeed;
		//Need to reapply all the slows on it
		for (ProjectileEffect c: effects) {
			if (c instanceof Slow) {
				((Slow) c).applyEffect();
			}
		}
	}
	
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
	
	//TODO: Should I use this model for reducing resistances?
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
	
	boolean disorient() {
		if (!disorientImmune && timeUntilDisorient < 0) {
			timeUntilDisorient = disorientGrace; //TODO: Should this be done in un-disorient? If so need to fix stuff in constructor too.		
			this.isDisoriented = true;
			return true;
		}
		return false;
	}
	
	void undisorient() { isDisoriented = false;}
	
	boolean isDisoriented() { return isDisoriented; }
	
	void addOnHit(DamageType type, float amount) {
		damageOnHitArray[type.ordinal()] += amount;
	}
	
	void removeOnHit(DamageType type, float amount) {
		damageOnHitArray[type.ordinal()] -= amount;
	}
	
	/**
	 * Reduces the movement speed of the creep by the amount specified. This amount should be positive and less than 1. This is also a pre-resistance calculation.
	 * @param type - The type of slow.
	 * @param amount - The percent amount of slow represented as a float between 0 (inclusive) and 1 (exclusive)
	 * @return The movement speed after application of the slow.
	 */
	float slow(DamageType type, float amount) {
		return currentSpeed *= 1 - (amount * (1 - slowResists[type.ordinal()]));
	}
	
	/**
	 * Increases the movement speed of the creep by the amount specified. This amount should be positive and less than 1. This is also a pre-resistance calculation. This function should be called onExpire by the slow projectile effect
	 * @param type - The type of slow.
	 * @param amount - The percent amount of slow represented as a float between 0 (inclusive) and 1 (exclusive)
	 * @return The movement speed after application of the slow.
	 */
	float unslow(DamageType type, float amount) {
		return currentSpeed /= 1 - (amount * (1 - slowResists[type.ordinal()]));
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
		currentDamageResistsPercent[index] = currentDamageResistsFlat[index] / (currentDamageResistsFlat[index] + GameConstants.RESIST_DENOMINATOR_VALUE);
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
		return currentShield -= amount;
	}
	
	float reducePercentShield(float amount) {
		return currentShield *= 1 - amount;
	}
	
	float getMaxHealth() {	
		return maxHealth;
	}
	
	
	@Override public int update() {
		updateEffects();
		if (currentHealth > 0) {
			currentHealth += currentHealthRegenerationRate;
			if (currentHealth >= maxHealth) {
				currentHealth = maxHealth;
			}
		}
		//TODO: Maybe make it so shield only regenerates after health is full?
		//TODO: Or after a certain amount of time after being hit.
		currentShield += currentShieldRegenerationRate;
		if (currentShield >= maxShield) {
			currentShield = maxShield;
		}
		
		return (int) currentHealth;
	}
	
	@Override public CreepAttributes clone() {
		return new CreepAttributes(this);
	}
	
	void addEffect(ProjectileEffect effect) {
		effect.onApply();
		effects.add(effect);
		effect.setCreep(parent);
	}
	
	void addAllEffects(ArrayList<ProjectileEffect> effects) {
		for (ProjectileEffect p : effects) {
			addEffect(p);
		}
	}
	
	void onProjectileCollision() {
		
	}
	
	private void updateEffects() {
		timeUntilSnare--;
		timeUntilDisorient--;
		Iterator<ProjectileEffect> i = effects.iterator();
		while (i.hasNext()) {
			ProjectileEffect e = i.next();
			int updateVal = e.update();
			switch (updateVal) {
			case 1:
				break;
			case 0:
				break;
			case -1:
				i.remove();
				break;
			default:
				System.out.println("Shouldn't be reached, CreepAttributes.updateEffects");
				break;
			}
		}
	}
	
	boolean contains(ProjectileEffect pe) {
		return effects.contains(pe);
	}
	
	ProjectileEffect getEquivalent(ProjectileEffect pe) {
		for (ProjectileEffect e: effects) {
			if (e.equals(pe)) {
				return e;
			}
		}
		return null;
	}

	void nullify() {
		healthRegenerationRate = 0;
		currentHealthRegenerationRate = 0;
		shieldRegenerationRate = 0;
		currentShieldRegenerationRate = 0;
	}
}
