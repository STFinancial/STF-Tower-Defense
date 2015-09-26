package creeps;

import levels.Updatable;

final class Speed extends Attribute implements Updatable {
	//TODO: Flat speed reductions at some point in the future.
	//TODO: Some way to handle maxSpeed reductions. If we are reducing max speed by 5, then we do currentSpeed -= defaultSpeed * 5 / currentSpeed
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
	
	void slow(float amount, DamageType type) {
		if (amount == 1) {
			System.out.println("100% slow happening. Avoid this");
			return;
		}
		currentSpeed *= (1 - (amount * (1 - parent.getSlowResist(type))));
	}
	
	void unslow(float amount, DamageType type) {
		if (amount == 1) {
			return;
		}
		currentSpeed /= (1 - (amount * (1 - parent.getSlowResist(type))));
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
	
	void disorient(int duration) {
		if (disorientImmune) {
			return;
		} else if (timeUntilDisorient <= 0){
			isDisoriented = true;
			initialDisorientDuration = duration;
			currentDisorientDuration = duration;
			timeUntilDisorient = duration + disorientGrace;
		} else if (isDisoriented && duration > initialDisorientDuration) {
			int dif = duration - initialDisorientDuration;
			currentDisorientDuration += dif;
			timeUntilDisorient += dif;
		}
	}

	@Override
	public int update() {
		timeUntilSnare--;
		timeUntilDisorient--;
		if (currentSnareDuration-- == 0) { isSnared = false; }
		if (currentDisorientDuration-- == 0) { isDisoriented = false; }
		return (isDisoriented || isSnared ? -1 : 0);
	}
}
