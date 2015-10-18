package creeps;

final class GoldValue extends Attribute {
	private float defaultGoldValue;
	private float reductionPercent;
	private float reductionFlat;
	private float currentGoldValue;
	
	GoldValue(CreepAttributes parent, float defaultGoldValue) {
		this.defaultGoldValue = defaultGoldValue;
		this.reductionFlat = 0;
		this.reductionPercent = 0;
		this.currentGoldValue = defaultGoldValue;
	}
	
	float getGoldValue() { return currentGoldValue; }
	
	void reduceGoldValue(float amount, boolean isFlat) {
		if (isFlat) {
			reductionFlat += amount;
		} else {
			reductionPercent += amount;
		}
		updateGoldValue();
	}
	
	void increaseGoldValue(float amount, boolean isFlat) {
		if (isFlat) {
			reductionFlat -= amount;
		} else {
			reductionPercent -= amount;
		}
		updateGoldValue();
	}
	
	private float updateGoldValue() {
		currentGoldValue = defaultGoldValue - (defaultGoldValue * reductionPercent) - reductionFlat;
		return currentGoldValue;
	}

	@Override
	Attribute clone(CreepAttributes parent) {
		return new GoldValue(parent, defaultGoldValue);
	}
}
