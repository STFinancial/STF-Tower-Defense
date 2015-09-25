package creeps;

final class Toughness extends Attribute {
	private float defaultToughness;
	private float currentReductionPercent;
	private float currentReductionFlat;
	private float currentToughness;
	
	Toughness(CreepAttributes parent, float defaultToughness) {
		this.parent = parent;
		this.defaultToughness = defaultToughness;
		this.currentToughness = defaultToughness;
		this.currentReductionFlat = 0;
		this.currentReductionPercent = 0;
	}
	
	float getCurrentToughness() { return currentToughness; }
	private float updateToughness() { return currentToughness = defaultToughness - (defaultToughness * currentReductionPercent) - currentReductionFlat; }
	
	float reduceToughness(float amount, boolean isFlat) {
		if (isFlat) {
			currentReductionFlat += amount;
		} else {
			currentReductionPercent += amount; 
		}
		return updateToughness();
	}
	
	float increaseToughness(float amount, boolean isFlat) {
		if (isFlat) {
			currentReductionFlat -= amount;
		} else {
			currentReductionPercent -= amount; 
		}
		return updateToughness();
	}
}
