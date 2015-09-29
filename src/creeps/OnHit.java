package creeps;

import utilities.GameConstants;

final class OnHit extends Attribute {
	private float[] damageOnHit;
	private float goldOnHit;
	
	OnHit(CreepAttributes parent, float goldOnHit, float[] damageOnHit) {
		this.parent = parent;
		this.damageOnHit = damageOnHit;
		this.goldOnHit = goldOnHit;
	}
	
	float applyOnHits() { 
		for (int i = 0; i < damageOnHit.length; i++) {
			parent.damage(DamageType.values()[i], damageOnHit[i], 0, 0, false, 1, 0, 0);
		}
		return goldOnHit;
	}
	
	void increaseDamageOnHit(DamageType type, float amount) {
		damageOnHit[type.ordinal()] += amount;
	}
	
	void decreaseDamageOnHit(DamageType type, float amount) {
		damageOnHit[type.ordinal()] -= amount;
	}
	
	void increaseGoldOnHit(float amount) { goldOnHit += amount; }
	void decreaseGoldOnHit(float amount) { goldOnHit -= amount; }

	@Override
	Attribute clone(CreepAttributes parent) {
		return new OnHit(parent, 0, new float[GameConstants.NUM_DAMAGE_TYPES]);
	}
}
