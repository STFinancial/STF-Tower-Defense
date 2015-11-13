package creeps;

final class Disruption extends Attribute {
	private float defaultDisruptorAmount;
	private float currentReductionPercent;
	private float currentReductionFlat;
	private float currentDisruptorAmount;
	
	Disruption(CreepAttributes parent, float defaultDisruptorAmount) {
		this.parent = parent;
		this.defaultDisruptorAmount = defaultDisruptorAmount;
		this.currentDisruptorAmount = defaultDisruptorAmount;
		this.currentReductionFlat = 0;
		this.currentReductionPercent = 0;
	}
	
	float getDisruptionAmount() { return currentDisruptorAmount; }
	
	void suppressDisruption(float amount, boolean isFlat) {
		if (isFlat) {
			currentReductionFlat += amount;
		} else {
			currentReductionPercent += amount;
		}
		updateDisruption();
	}
	
	void unsuppressDisruption(float amount, boolean isFlat) {
		if (isFlat) {
			currentReductionFlat -= amount;
		} else {
			currentReductionPercent -= amount;
		}
		updateDisruption();
	}
	
	private float updateDisruption() {
		currentDisruptorAmount = defaultDisruptorAmount - (defaultDisruptorAmount * currentReductionPercent) - currentReductionFlat;
		return currentDisruptorAmount;
	}

	@Override
	Attribute clone(CreepAttributes parent) {
		return new Disruption(parent, defaultDisruptorAmount);
	}

	@Override
	protected int update() {
		return 0;
	}
}
