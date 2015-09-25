package creeps;

import levels.Updatable;

final class CreepAttributes implements Updatable {
	private Creep parent;
	private Effects effects;
	private Health health;
	private DamageResistances damageResistances;
	private SlowResistances slowResistances;
	private Speed speed;
	
	CreepAttributes(Creep parent) { this.parent = parent; effects = new Effects(this); }
	
	void setHealthValues(float maxHealth, float maxHealthRegen) { health = new Health(this, maxHealth, maxHealthRegen); }
	void setDamageResists(float[] damageResists) { damageResistances = new DamageResistances(this, damageResists); }
	void setSlowResists(float[] slowResists) { slowResistances = new SlowResistances(this, slowResists); }
	void setSpeedValues(float defaultSpeed, boolean snareImmune, int snareGrace, boolean disorientImmune, int disorientGrace) { speed = new Speed(this, defaultSpeed, snareImmune, snareGrace, disorientImmune, disorientGrace); }
	
	
	
	@Override
	public int update() {
		effects.update();
		if (health.update() == -1) {
			return -1;
		}
		speed.update();
		return 0;
	}

}