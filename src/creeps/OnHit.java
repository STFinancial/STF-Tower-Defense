package creeps;

final class OnHit extends Attribute {
	private float[] damageOnHit;
	private float goldOnHit;
	
	OnHit(CreepAttributes parent, float goldOnHit, float[] damageOnHit) {
		this.parent = parent;
		this.damageOnHit = damageOnHit;
		this.goldOnHit = goldOnHit;
	}
	
	
	//TODO: It's not *really* necessary to have two methods since I have 1 for many other methods that could use a split more
	void increaseDamageOnHit(float amount, DamageType type) {
		damageOnHit[type.ordinal()] += amount;
	}
	
	void decreaseDamageOnHit(float amount, DamageType type) {
		damageOnHit[type.ordinal()] -= amount;
	}
	
	void increaseGoldOnHit(float amount) { goldOnHit += amount; }
	void decreaseGoldOnHit(float amount) { goldOnHit -= amount; }
}
