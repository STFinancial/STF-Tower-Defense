package creeps;

final class Size extends Attribute {
	private float defaultSize;
	private float size;
	
	Size(CreepAttributes parent, float defaultSize) {
		this.parent = parent;
		this.defaultSize = defaultSize;
		this.size = defaultSize;
	}
	
	float getCurrentSize() { return size; }
	
	void increaseSize(float amount, boolean isFlat) {
		size = isFlat ? size + amount : size * (1 + amount);
	}
	
	void decreaseSize(float amount, boolean isFlat) {
		size = isFlat ? size - amount : size * (1 - amount);
	}

	@Override
	Attribute clone(CreepAttributes parent) {
		return new Size(parent, defaultSize);
	}
}
