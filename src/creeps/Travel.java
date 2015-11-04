package creeps;

final class Travel extends Attribute {
	private boolean defaultIsFlying;
	private boolean normalIsFlying; //need this because we can't change the default because that's what we use to clone
	private boolean currentlyIsFlying;
	private boolean groundingImmune;
	
	Travel(CreepAttributes parent, boolean isFlying, boolean groundingImmune) {
		this.parent = parent;
		this.defaultIsFlying = isFlying;
		this.normalIsFlying = isFlying;
		this.currentlyIsFlying = isFlying;
		this.groundingImmune = groundingImmune;
	}
	
	boolean isFlying() { return currentlyIsFlying; }
	void loft() { currentlyIsFlying = true; }
	void setTravelToNormal() { currentlyIsFlying = normalIsFlying; }
	
	boolean ground() {
		if (groundingImmune) {
			return currentlyIsFlying;
		} else {
			normalIsFlying = false;
			currentlyIsFlying = false;
			return false;
		}
	}
	
	@Override
	Attribute clone(CreepAttributes parent) {
		return new Travel(parent, defaultIsFlying, groundingImmune);
	}
}
