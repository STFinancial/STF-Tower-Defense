package creeps;

import levels.Updatable;

final class Speed extends Attribute implements Updatable {
	//TODO: Flat speed reductions at some point in the future.
	private float defaultSpeed;
	private float currentSpeed;
	
	private boolean isSnared;
	private boolean snareImmune;
	private int initialSnareDuration;
	private int currentSnareDuration;
	private int snareGrace;
	private int timeUntilSnare;
	
	private boolean isDisoriented;
	private boolean disorientImmune;
	private int initialDisorientDuration;
	private int currentDisorientDuration;
	private int disorientGrace;
	private int timeUntilDisorient;
	
	Speed(CreepAttributes parent, float defaultSpeed, boolean snareImmune, int snareGrace, boolean disorientImmune, int disorientGrace) {
		this.parent				= parent;
		
		this.defaultSpeed 		= defaultSpeed;
		this.snareImmune 		= snareImmune;
		this.snareGrace   	 	= snareGrace;
		this.disorientImmune 	= disorientImmune;
		this.disorientGrace  	= disorientGrace;
		
		this.currentSpeed 	 	= defaultSpeed;
		this.isSnared			= false;
		this.timeUntilSnare		= 0;
		this.isDisoriented		= false;
		this.timeUntilDisorient = 0;
	}
	
	float getCurrentSpeed() { return (isSnared || currentSpeed < 0 ? 0 : currentSpeed); }
	boolean isDisoriented() { return isDisoriented; }
	
	void slow(DamageType type, float amount) {
		if (amount == 1) {
			System.out.println("100% slow happening. Avoid this");
			return;
		}
		currentSpeed *= (1 - (amount * (1 - parent.getSlowResist(type))));
	}
	
	void unslow(DamageType type, float amount) {
		if (amount == 1) {
			return;
		}
		currentSpeed /= (1 - (amount * (1 - parent.getSlowResist(type))));
	}
	
	void reduceMaxSpeed(DamageType type, float amount, boolean isFlat) {
		//TODO: Do we want slow resists to mitigate this?
		if (amount == 1) {
			System.out.println("100% slow happening. Avoid this");
			return;
		}
		if (isFlat) {
			currentSpeed -= amount * (1 - (defaultSpeed / currentSpeed));
		} else {
			//reduce max speed by 20 ewe need to reduce that percent
			currentSpeed *= (1 - (amount * (1 - (defaultSpeed / currentSpeed))));
		}
	}
	
	void snare(int duration) {
		if (snareImmune) { //Is immune
			return;
		} else if (timeUntilSnare <= 0){ //Can be snared
			isSnared = true;
			initialSnareDuration = duration;
			currentSnareDuration = duration;
			timeUntilSnare = duration + snareGrace;
		} else if (isSnared && duration > initialSnareDuration) { //Is snared but this is longer
			int dif = duration - initialSnareDuration;
			currentSnareDuration += dif;
			timeUntilSnare += dif;
		}
	}
	
	boolean disorient(int duration) {
		if (disorientImmune) {
			return false;
		} else if (timeUntilDisorient <= 0){
			isDisoriented = true;
			initialDisorientDuration = duration;
			currentDisorientDuration = duration;
			timeUntilDisorient = duration + disorientGrace;
			return true;
		} else if (isDisoriented && duration > initialDisorientDuration) {
			int dif = duration - initialDisorientDuration;
			currentDisorientDuration += dif;
			timeUntilDisorient += dif;
		}
		return false;
	}

	@Override
	public int update() {
		timeUntilSnare--;
		timeUntilDisorient--;
		if (currentSnareDuration-- == 0) { isSnared = false; }
		if (currentDisorientDuration-- == 0) { isDisoriented = false; }
		return (isDisoriented || isSnared ? -1 : 0);
	}

	@Override
	Attribute clone(CreepAttributes parent) {
		return new Speed(parent, defaultSpeed, snareImmune, snareGrace, disorientImmune, disorientGrace);
	}
}
