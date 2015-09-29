package creeps;

import levels.Updatable;

final class Speed extends Attribute implements Updatable {
	//TODO: Flat speed reductions at some point in the future.
	//TODO: What if we want to modify the grace periods
	private float defaultSpeed;
	private float currentSpeed;
	
	private int gracePeriod;
	
	private boolean isSnared;
	private boolean snareImmune;
	private int initialSnareDuration;
	private int currentSnareDuration;
	private int timeUntilSnare;
	
	private boolean isDisoriented;
	private boolean disorientImmune;
	private int initialDisorientDuration;
	private int currentDisorientDuration;
	private int timeUntilDisorient;
	
	private boolean isKnockup;
	private boolean knockupImmune;
	private int initialKnockupDuration;
	private int currentKnockupDuration;
	private int timeUntilKnockup;
	
	//TODO: Consolidate all into a single grace period
	Speed(CreepAttributes parent, float defaultSpeed, boolean snareImmune, boolean disorientImmune, boolean knockupImmune, int gracePeriod) {
		this.parent				= parent;
		
		this.defaultSpeed 		= defaultSpeed;
		this.snareImmune 		= snareImmune;
		this.snareGrace   	 	= snareGrace;
		this.disorientImmune 	= disorientImmune;
		this.disorientGrace  	= disorientGrace;
		this.knockupImmune		= knockupImmune;
		this.knockupGrace		= knockupGrace;
		
		this.currentSpeed 	 	= defaultSpeed;
		this.isSnared			= false;
		this.timeUntilSnare		= 0;
		this.isDisoriented		= false;
		this.timeUntilDisorient = 0;
		this.isKnockup			= false;
		this.timeUntilKnockup	= 0;
	}
	
	float getCurrentSpeed() { return (isSnared || isKnockup || currentSpeed < 0 ? 0 : currentSpeed); }
	boolean isDisoriented() { return isDisoriented; }
	boolean isSnared() { return isSnared; }
	boolean isKnockup() { return isKnockup; }
	
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
	
	//TODO: I think that these need to share a grace period
	boolean knockup(int duration) {
		if (knockupImmune) {
			return false;
		} else if {
			
		} else if {
			
		}
	}

	@Override
	public int update() {
		timeUntilSnare--;
		timeUntilDisorient--;
		timeUntilKnockup--;
		if (currentSnareDuration-- == 0) { isSnared = false; }
		if (currentDisorientDuration-- == 0) { isDisoriented = false; }
		if (currentKnockupDuration-- == 0) { isKnockup = false; }
		return (isDisoriented || isSnared ? -1 : 0);
	}

	@Override
	Attribute clone(CreepAttributes parent) {
		return new Speed(parent, defaultSpeed, snareImmune, snareGrace, disorientImmune, disorientGrace, knockupImmune, knockupGrace);
	}
}
