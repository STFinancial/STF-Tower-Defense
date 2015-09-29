package creeps;

import utilities.GameConstants;

final class SlowResistances extends Attribute {
	private float[] defaultResistsFlat;
	private float[] reductionPercent;
	private float[] reductionFlat;
	private float[] currentResistsFlat;
	private float[] currentResistsPercent;
	
	SlowResistances(CreepAttributes parent, float[] defaultSlowResists) {
		this.parent				= parent;
		int length 				= GameConstants.NUM_DAMAGE_TYPES;
		defaultResistsFlat 		= new float[length];
		reductionPercent 		= new float[length];
		reductionFlat 			= new float[length];
		currentResistsFlat 		= new float[length];
		currentResistsPercent 	= new float[length];
		for (int i = 0; i < length; ++i) {
			defaultResistsFlat[i] 	 = defaultSlowResists[i];
			currentResistsFlat[i] 	 = defaultSlowResists[i];
			currentResistsPercent[i] = currentResistsFlat[i] / (currentResistsFlat[i] + GameConstants.SLOW_RESIST_DENOMINATOR_VALUE);
		}
	}
	
	float getResistPercent(DamageType type) { return currentResistsPercent[type.ordinal()]; }
	
	private float updateResistPercent(int index) {
		currentResistsFlat[index] = defaultResistsFlat[index] - (defaultResistsFlat[index] * reductionPercent[index]) - reductionFlat[index];
		currentResistsPercent[index] = currentResistsFlat[index] / (currentResistsFlat[index] + GameConstants.SLOW_RESIST_DENOMINATOR_VALUE);
		return currentResistsPercent[index];
	}
	
	float reducePercentResist(DamageType type, float percentReduction) {
		int index = type.ordinal();
		reductionPercent[index] += percentReduction;
		return updateResistPercent(index);
	}
	
	float increasePercentResist(DamageType type, float percentIncrease) {
		int index = type.ordinal();
		reductionPercent[index] -= percentIncrease;
		return updateResistPercent(index);
	}
	
	float reduceFlatResist(DamageType type, float amount) {
		int index = type.ordinal();
		reductionFlat[index] += amount;
		return updateResistPercent(index);
	}

	float increaseFlatResist(DamageType type, float amount) {
		int index = type.ordinal();
		reductionFlat[index] -= amount;
		return updateResistPercent(index);
	}

	@Override
	Attribute clone(CreepAttributes parent) {
		return new SlowResistances(parent, defaultResistsFlat.clone());
	}
}
