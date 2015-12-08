package creeps;

import utilities.GameConstants;

final class OnHit extends Attribute {
	private float[] damageOnHit;
	private float goldOnHit;
	private float cooldownOnHit; //Can implement this with disruption actually
	
	OnHit(CreepAttributes parent, float goldOnHit, float cooldownOnHit, float[] damageOnHit) {
		this.parent = parent;
		this.damageOnHit = damageOnHit;
		this.goldOnHit = goldOnHit;
		this.cooldownOnHit = cooldownOnHit;
	}
	
	float applyOnHits() { 
		for (int i = 0; i < damageOnHit.length; i++) {
			//TODO: Should this ignore stuff?
			parent.damage(DamageType.values()[i], damageOnHit[i], 0, 0, false, 1, 0, 0);
			//TODO: Cooldown on hit, remember not to reduce cooldown below 1?
		}
		return goldOnHit;
	}
	
	void increaseDamageOnHit(DamageType type, float amount) {
		damageOnHit[type.ordinal()] += amount;
	}
	
	void reduceDamageOnHit(DamageType type, float amount) {
		damageOnHit[type.ordinal()] -= amount;
	}
	
	void increaseGoldOnHit(float amount) { goldOnHit += amount; }
	void reduceGoldOnHit(float amount) { goldOnHit -= amount; }
	void increaseCDOnHit(float amount) { cooldownOnHit += amount; }
	void reduceCDOnHit(float amount) { cooldownOnHit -= amount; }

	@Override
	Attribute clone(CreepAttributes parent) {
		return new OnHit(parent, 0, cooldownOnHit, new float[GameConstants.NUM_DAMAGE_TYPES]);
	}

	@Override
	protected int update() {
		return 0;
	}
}
