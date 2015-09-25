package creeps;

import utilities.GameConstants;

final class DamageResistances extends Attribute {
	private float[] defaultResistsFlat; //the default value of the damage resistances
	private float[] reductionPercent; //the percent reduction for each resistance currently applied
	private float[] reductionFlat; //the flat reduction for each resistance currently applied
	private float[] currentResistsFlat; //the current flat damage reduction values
	private float[] currentResistsPercent; //the current damage reduction as a percent
	
	DamageResistances(CreepAttributes parent, float[] defaultDamageResists) {
		this.parent				= parent;
		int length 				= GameConstants.NUM_DAMAGE_TYPES;
		defaultResistsFlat 		= new float[length];
		reductionPercent 		= new float[length];
		reductionFlat 			= new float[length];
		currentResistsFlat 		= new float[length];
		currentResistsPercent 	= new float[length];
		for (int i = 0; i < length; ++i) {
			defaultResistsFlat[i] 	 = defaultDamageResists[i];
			currentResistsFlat[i] 	 = defaultDamageResists[i];
			currentResistsPercent[i] = currentResistsFlat[i] / (currentResistsFlat[i] + GameConstants.DAMAGE_RESIST_DENOMINATOR_VALUE);
		}
	}
	
	float getResistPercent(DamageType type) { return currentResistsPercent[type.ordinal()]; }
	float getResistFlat(DamageType type) { return currentResistsFlat[type.ordinal()]; }
	
	private float updateResistPercent(int index) {
		currentResistsFlat[index] = defaultResistsFlat[index] - (defaultResistsFlat[index] * reductionPercent[index]) - reductionFlat[index];
		currentResistsPercent[index] = currentResistsFlat[index] / (currentResistsFlat[index] + GameConstants.DAMAGE_RESIST_DENOMINATOR_VALUE);
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
}
