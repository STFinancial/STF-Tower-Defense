package creeps;

final class Size extends Attribute {
	private float size;
	
	
	Size(CreepAttributes parent, float defaultSize) {
		this.parent = parent;
		this.size = defaultSize;
	}
	
	float getSize() { return size; }
	
	void increaseSize(float amount, boolean isFlat) {
		size = isFlat ? size + amount : size * (1 + amount);
	}
	
	void decreaseSize(float amount, boolean isFlat) {
		size = isFlat ? size - amount : size * (1 - amount);
	}
}
