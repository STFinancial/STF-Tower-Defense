package creeps;

final class HealthCost extends Attribute {
	private float defaultHealthCost;
	private float healthCost;
	
	HealthCost(CreepAttributes parent, float defaultHealthCost) {
		this.parent = parent;
		this.defaultHealthCost = defaultHealthCost;
		this.healthCost = defaultHealthCost;
	}
	
	float getHealthCost() { return healthCost; }
	
	void increaseHealthCost(float amount, boolean isFlat) {
		healthCost = isFlat ? healthCost + amount : healthCost * (1 + amount);
	}
	
	void decreaseSize(float amount, boolean isFlat) {
		healthCost = isFlat ? healthCost - amount : healthCost * (1 - amount);
	}

	@Override
	Attribute clone(CreepAttributes parent) {
		return new HealthCost(parent, defaultHealthCost);
	}

	@Override
	protected int update() {
		return 0;
	}
}
