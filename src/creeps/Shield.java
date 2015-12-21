package creeps;

final class Shield extends Attribute {
	//TODO: Do we want to wait a period of time before shield starts to regenerate?
	private float defaultShield;
	private float maxShield; //TODO: Should we have a max shield value? What if we want something that grants shield to creeps all around it (we can just increase max shield it seems)
	private float currentShield;
	private float defaultShieldRegen; //Shield increase per game tick
	private float maxShieldRegen;
	private float regenReductionPercent;
	private float regenReductionFlat;
	private float currentShieldRegen;
	
	Shield(CreepAttributes parent, float maxShield, float defaultShieldRegen) {
		this.parent = parent;
		this.defaultShield = maxShield;
		this.maxShield = maxShield;
		this.currentShield = maxShield;
		this.defaultShieldRegen = defaultShieldRegen;
		this.maxShieldRegen = defaultShieldRegen;
		this.currentShieldRegen = defaultShieldRegen;
		this.regenReductionPercent = 0;
		this.regenReductionFlat = 0;
	}
	
	float getCurrentShield() { return currentShield; }
	float getMaxShield() { return maxShield; }
	
	//TODO: Optimization - Can remove the boolean and separate into two methods
	float reduceCurrentShield(float amount, boolean isFlat) {
		if (isFlat) {
			currentShield -= amount;	
		} else {
			currentShield *= 1 - amount;
		}
		if (currentShield < 0) { currentShield = 0; }
		return currentShield;
	}
	
	float increaseCurrentShield(float amount, boolean isFlat) {
		if (isFlat) {
			currentShield += amount;
		} else {
			currentShield *= 1 + amount;
		}
		if (currentShield > maxShield) { currentShield = maxShield; }
		return currentShield;
	}
	
	void reduceMaxShield(float amount, boolean isFlat) {
		if (isFlat) {
			maxShield -= amount;
		} else {
			maxShield *= 1 - amount;
		}
		if (maxShield < currentShield) { currentShield = maxShield; }
	}
	
	void increaseMaxShield(float amount, boolean isFlat) {
		if (isFlat) {
			maxShield += amount;
		} else {
			maxShield *= 1 + amount;
		}
	}
	
	private float updateShieldRegen() {
		currentShieldRegen = maxShieldRegen - (maxShieldRegen * regenReductionPercent) - regenReductionFlat;
		return (currentShieldRegen < 0 ? currentShieldRegen = 0 : currentShieldRegen);
	}
	
	void reduceShieldRegen(float amount, boolean isFlat) {
		if (isFlat) {
			regenReductionFlat += amount;
		} else {
			regenReductionPercent += amount;
		}
		updateShieldRegen();
	}
	
	void increaseShieldRegen(float amount, boolean isFlat) {
		if (isFlat) {
			regenReductionFlat -= amount;
		} else {
			regenReductionPercent -= amount;
		}
		updateShieldRegen();
	}
	
	void nullify() {
		maxShieldRegen = 0;
		updateShieldRegen();
	}

	@Override
	public int update() {
		currentShield += currentShieldRegen;
		if (currentShield > maxShield) {
			currentShield = maxShield;
		}
		return 0;
	}

	@Override
	Attribute clone(CreepAttributes parent) {
		return new Shield(parent, defaultShield, defaultShieldRegen);
	}
}
