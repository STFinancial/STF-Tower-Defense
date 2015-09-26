package creeps;

import java.util.List;

import projectileeffects.ProjectileEffect;
import utilities.GameConstants;
import levels.Updatable;

final class CreepAttributes implements Updatable {
	private Creep parent;
	private Effects effects;
	
	private GoldValue goldValue;
	private Size size;
	private HealthCost healthCost;
	
	private Health health;
	private Shield shield;
	private DamageResistances damageResistances;
	private SlowResistances slowResistances;
	private Speed speed;
	private Toughness toughness;
	private Disruption disruption;
	private Deathrattle deathrattle;
	private OnHit onHit;
	
	CreepAttributes(Creep parent) { this.parent = parent; effects = new Effects(this); }
	void setHealthValues(float maxHealth, float defaultHealthRegen) { health = new Health(this, maxHealth, defaultHealthRegen); }
	void setShieldValues(float maxShield, float defaultShieldRegen) { shield = new Shield(this, maxShield, defaultShieldRegen); }
	void setDamageResists(float[] damageResists) { damageResistances = new DamageResistances(this, damageResists); }
	void setSlowResists(float[] slowResists) { slowResistances = new SlowResistances(this, slowResists); }
	void setSpeedValues(float defaultSpeed, boolean snareImmune, int snareGrace, boolean disorientImmune, int disorientGrace) { speed = new Speed(this, defaultSpeed, snareImmune, snareGrace, disorientImmune, disorientGrace); }
	void setToughnessValues(float defaultToughness) { toughness = new Toughness(this, defaultToughness); }
	void setDisruptionValues(float defaultDisruption) { disruption = new Disruption(this, defaultDisruption); }
	void setGoldValue(float defaultGoldValue) { goldValue = new GoldValue(this, defaultGoldValue); }
	void setDeathrattle(ProjectileEffect effect, List<Creep> children) { deathrattle = new Deathrattle(this, effect, children); }
	void setOnHit(float goldOnHit, float[] damageOnHit) { onHit = new OnHit(this, goldOnHit, damageOnHit); }
	void setSize(float defaultSize) { size = new Size(this, defaultSize); }
	void setHealthCost(float defaultHealthCost) { healthCost = new HealthCost(this, defaultHealthCost); }
	
	//Sets all fields that the builder forgot to
	CreepAttributes finishBuild() {
		if (health == null) { health = new Health(this, 0, 0); }
		if (shield == null) { shield = new Shield(this, 0, 0); }
		if (damageResistances == null) { damageResistances = new DamageResistances(this, new float[GameConstants.NUM_DAMAGE_TYPES]); }
		if (slowResistances == null) { slowResistances = new SlowResistances(this, new float[GameConstants.NUM_DAMAGE_TYPES]); }
		if (speed == null) { speed = new Speed(this, 0, false, 0, false, 0); }
 		if (toughness == null) { toughness = new Toughness(this, 0); }
 		if (disruption == null) { disruption = new Disruption(this, 0); }
 		if (goldValue == null) { goldValue = new GoldValue(this, 0); }
 		if (deathrattle == null) { deathrattle = new Deathrattle(this, null, null); } //Should I pass in null values, what if we want to add something to it later?
		if (onHit == null) { onHit = new OnHit(this, 0, new float[GameConstants.NUM_DAMAGE_TYPES]); }
 		if (size == null) { size = new Size(this, 0); }
		if (healthCost == null) { healthCost = new HealthCost(this, 0); }
		return this;
	}
	
	@Override
	public int update() {
		effects.update();
		if (health.update() == -1) {
			return -1;
		}
		shield.update();
		speed.update();
		deathrattle.update();
		return 0;
	}
	public void addOnHit(DamageType type, float amount) {
		// TODO Auto-generated method stub
		
	}
	public void consumeBleeds(float amount) {
		// TODO Auto-generated method stub
		
	}
	public void damage(DamageType type, float amount, float penPercent,
			float penFlat, boolean ignoresShield, float shieldDrainModifier,
			float toughPenPercent, float toughPenFlat) {
		// TODO Auto-generated method stub
		
	}
	public void increaseResist(DamageType type, float amount, boolean isFlat) {
		// TODO Auto-generated method stub
		
	}
	public void increaseToughness(float amount, boolean isFlat) {
		// TODO Auto-generated method stub
		
	}
	public void nullify() {
		// TODO Auto-generated method stub
		
	}
	public void reduceMaxSpeed(DamageType type, float amount, boolean isFlat) {
		// TODO Auto-generated method stub
		
	}
	public void reduceResist(DamageType type, float amount, boolean isFlat) {
		// TODO Auto-generated method stub
		
	}
	public void reduceToughness(float amount, boolean isFlat) {
		// TODO Auto-generated method stub
		
	}
	public void removeOnHit(DamageType type, float amount) {
		// TODO Auto-generated method stub
		
	}
	public void slow(DamageType type, float amount) {
		// TODO Auto-generated method stub
		
	}
	public void snare() {
		// TODO Auto-generated method stub
		
	}
	public void suppressDeathrattle(float modifier, int lifetime) {
		// TODO Auto-generated method stub
		
	}
	public void suppressDisruption(float amount, boolean isFlat) {
		// TODO Auto-generated method stub
		
	}
	public void unslow(DamageType type, float amount) {
		// TODO Auto-generated method stub
		
	}
	public void unsuppressDisruption(float amount, boolean isFlat) {
		// TODO Auto-generated method stub
		
	}

}