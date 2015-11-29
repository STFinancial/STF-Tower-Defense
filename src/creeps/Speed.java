package creeps;

final class Speed extends Attribute {
	//TODO: Flat speed reductions at some point in the future.
	private float defaultSpeed;
	private float currentSpeed;
	
	private int gracePeriod;
	private int timeUntilNextEffect;
	private int initialEffectDuration;
	private int currentEffectDuration;
	
	private boolean isSnared;
	private boolean snareImmune;
	
	private boolean isDisoriented;
	private boolean disorientImmune;
	
	private boolean isKnockup;
	private boolean knockupImmune;
	
	Speed(CreepAttributes parent, float defaultSpeed, boolean snareImmune, boolean disorientImmune, boolean knockupImmune, int gracePeriod) {
		this.parent				= parent;
		
		this.defaultSpeed 		= defaultSpeed;
		this.snareImmune 		= snareImmune;
		this.disorientImmune 	= disorientImmune;
		this.knockupImmune		= knockupImmune;
		
		this.gracePeriod		= gracePeriod;
		
		this.currentSpeed 	 	= defaultSpeed;
		this.isSnared			= false;
		this.isDisoriented		= false;
		this.isKnockup			= false;
		this.timeUntilNextEffect= 0;
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
	
	boolean snare(int duration) {
		if (snareImmune) { //Is immune
			return false;
		} else if (timeUntilNextEffect <= 0){ //Can be snared
			isSnared = true;
			initialEffectDuration = duration;
			currentEffectDuration = duration;
			timeUntilNextEffect = duration + gracePeriod;
			return true;
		} else if (isSnared && duration > initialEffectDuration) { //Is snared but this is longer
			int dif = duration - initialEffectDuration;
			currentEffectDuration += dif;
			timeUntilNextEffect += dif;
		}
		return false;
	}
	
	boolean disorient(int duration) {
		if (disorientImmune) {
			return false;
		} else if (timeUntilNextEffect <= 0){
			isDisoriented = true;
			initialEffectDuration = duration;
			currentEffectDuration = duration;
			timeUntilNextEffect = duration + gracePeriod;
			return true;
		} else if (isDisoriented && duration > initialEffectDuration) {
			int dif = duration - initialEffectDuration;
			currentEffectDuration += dif;
			timeUntilNextEffect += dif;
		}
		return false;
	}
	
	boolean knockup(int duration) {
		if (knockupImmune) { //Is immune
			return false;
		} else if (timeUntilNextEffect <= 0){ //Can be snared
			isKnockup = true;
			initialEffectDuration = duration;
			currentEffectDuration = duration;
			timeUntilNextEffect = duration + gracePeriod;
			parent.loft();
			return true;
		} else if (isKnockup && duration > initialEffectDuration) { //Is knockup but this is longer
			int dif = duration - initialEffectDuration;
			currentEffectDuration += dif;
			timeUntilNextEffect += dif;
		}
		return false;
	}

	@Override
	public int update() {
		timeUntilNextEffect--;
		if (currentEffectDuration-- == 0) { isSnared = false; isDisoriented = false; isKnockup = false; parent.setTravelToNormal(); }
		return (isDisoriented || isSnared ? -1 : 0);
	}

	@Override
	Attribute clone(CreepAttributes parent) {
		return new Speed(parent, defaultSpeed, snareImmune, disorientImmune, knockupImmune, gracePeriod);
	}
}
