package creeps;

final class Travel extends Attribute {
	private boolean defaultIsFlying;
	private boolean currentlyIsFlying;
	private boolean groundingImmune;
	
	Travel(CreepAttributes parent, boolean isFlying, boolean groundingImmune) {
		this.parent = parent;
		this.defaultIsFlying = isFlying;
		this.currentlyIsFlying = isFlying;
		this.groundingImmune = groundingImmune;
	}
	
	boolean isFlying() { return currentlyIsFlying; }
	
	boolean ground() {
		return (groundingImmune ? currentlyIsFlying : (currentlyIsFlying = false));
	}
	
	@Override
	Attribute clone(CreepAttributes parent) {
		return new Travel(parent, defaultIsFlying, groundingImmune);
	}

}
