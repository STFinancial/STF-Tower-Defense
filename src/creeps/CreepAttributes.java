package creeps;

import java.util.ArrayList;
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
	private Travel travel;
	
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
	void setSpeedValues(float defaultSpeed, boolean snareImmune, boolean disorientImmune, boolean knockupImmune, int gracePeriod) { speed = new Speed(this, defaultSpeed, snareImmune, disorientImmune, knockupImmune, gracePeriod); }
	void setToughnessValues(float defaultToughness) { toughness = new Toughness(this, defaultToughness); }
	void setDisruptionValues(float defaultDisruption) { disruption = new Disruption(this, defaultDisruption); }
	void setGoldValue(float defaultGoldValue) { goldValue = new GoldValue(this, defaultGoldValue); }
	void setDeathrattle(List<ProjectileEffect> effects, List<Creep> children) { deathrattle = new Deathrattle(this, effects, children); }
	void setOnHit(float goldOnHit, float cooldownOnHit, float[] damageOnHit) { onHit = new OnHit(this, goldOnHit, cooldownOnHit, damageOnHit); }
	void setSize(float defaultSize) { size = new Size(this, defaultSize); }
	void setHealthCost(float defaultHealthCost) { healthCost = new HealthCost(this, defaultHealthCost); }
	void setTravel(boolean defaultIsFlying, boolean groundingImmune) { travel = new Travel(this, defaultIsFlying, groundingImmune); }
	
	//Sets all fields that the builder forgot to
	CreepAttributes finishBuild() {
		if (health == null) { health = new Health(this, 0, 0); }
		if (shield == null) { shield = new Shield(this, 0, 0); }
		if (damageResistances == null) { damageResistances = new DamageResistances(this, new float[GameConstants.NUM_DAMAGE_TYPES]); }
		if (slowResistances == null) { slowResistances = new SlowResistances(this, new float[GameConstants.NUM_DAMAGE_TYPES]); }
		if (speed == null) { speed = new Speed(this, 0, false, false, false, 0); }
 		if (toughness == null) { toughness = new Toughness(this, 0); }
 		if (disruption == null) { disruption = new Disruption(this, 0); }
 		if (goldValue == null) { goldValue = new GoldValue(this, 0); }
 		if (deathrattle == null) { deathrattle = new Deathrattle(this, null, null); }
		if (onHit == null) { onHit = new OnHit(this, 0, 0, new float[GameConstants.NUM_DAMAGE_TYPES]); }
 		if (size == null) { size = new Size(this, 0); }
		if (healthCost == null) { healthCost = new HealthCost(this, 0); }
		if (travel == null) { travel = new Travel(this, false, false); }
		return this;
	}
	
	float onProjectileCollision() { return onHit.applyOnHits(); }
	
	//Simple delegation one liners
	void addAllEffects(ArrayList<ProjectileEffect> effects) { this.effects.addAllEffects(effects); }
	void addDeathrattleEffect(ProjectileEffect effect, int duration) { deathrattle.addDeathrattleEffect(effect, duration); }
	void addEffect(ProjectileEffect effect) { effects.addEffect(effect); }
	void consumeBleeds(float amount) { effects.consumeBleeds(amount); }
	void damage(DamageType type, float amount, float penPercent, float penFlat, boolean ignoresShield, float shieldDrainModifier, float toughPenPercent, float toughPenFlat) { health.damage(type, amount, penPercent, penFlat, ignoresShield, shieldDrainModifier, toughPenPercent, toughPenFlat); }
	boolean disorient(int duration) { return speed.disorient(duration); }
	boolean ground() { return travel.ground(); }
	void increaseCDOnHit(DamageType type, float amount) { onHit.increaseCDOnHit(amount); }
	void increaseDamageOnHit(DamageType type, float amount) { onHit.increaseDamageOnHit(type, amount); }
	void increaseDamageResist(DamageType type, float amount, boolean isFlat) { damageResistances.increaseResist(type, amount, isFlat); }
	void increaseGoldOnHit(float amount) { onHit.increaseGoldOnHit(amount); }
	void increaseGoldValue(float amount, boolean isFlat) { goldValue.increaseGoldValue(amount, isFlat); }
	void increaseToughness(float amount, boolean isFlat) { toughness.increaseToughness(amount, isFlat); }
	void loft() { travel.loft(); }
	void knockup(int duration) { speed.knockup(duration); }
	void nullify() { health.nullify(); shield.nullify(); }
	void reduceCDOnHit(DamageType type, float amount) { onHit.reduceCDOnHit(amount); }
	void reduceCurrentShield(float amount, boolean isFlat) { shield.reduceCurrentShield(amount, isFlat); }
	void reduceDamageOnHit(DamageType type, float amount) { onHit.reduceDamageOnHit(type, amount); }
	void reduceDamageResist(DamageType type, float amount, boolean isFlat) { damageResistances.reduceResist(type, amount, isFlat); }
	void reduceGoldOnHit(float amount) { onHit.reduceGoldOnHit(amount); }
	void reduceGoldValue(float amount, boolean isFlat) { goldValue.reduceGoldValue(amount, isFlat); }
	void reduceMaxSpeed(DamageType type, float amount, boolean isFlat) { speed.reduceMaxSpeed(type, amount, isFlat); }
	void reduceToughness(float amount, boolean isFlat) { toughness.reduceToughness(amount, isFlat); }
	void setTravelToDefault() { travel.setTravelToDefault(); }
	void slow(DamageType type, float amount) { speed.slow(type, amount); }
	void snare(int duration) { speed.snare(duration); }
	void suppressDeathrattle(float modifier, int lifetime) { deathrattle.suppressDeathrattle(modifier, lifetime); }
	void suppressDisruption(float amount, boolean isFlat) { disruption.suppressDisruption(amount, isFlat); }
	void unslow(DamageType type, float amount) { speed.unslow(type, amount); }
	void unsuppressDisruption(float amount, boolean isFlat) { disruption.unsuppressDisruption(amount, isFlat); }

	//Getters
	float getCurrentDamageResist(DamageType type, boolean isFlat) { return (isFlat? damageResistances.getResistFlat(type) : damageResistances.getResistPercent(type));}
	float getCurrentHealth() { return health.getCurrentHealth(); }
	float getCurrentHealthCost() { return healthCost.getHealthCost(); }
	float getCurrentShield() { return shield.getCurrentShield(); }
	float getCurrentSize() { return size.getCurrentSize(); }
	float getCurrentSpeed() { return speed.getCurrentSpeed(); }
	float getCurrentToughness() { return toughness.getCurrentToughness(); }
	float getDisruption() { return disruption.getDisruptionAmount(); }
	float getMaxHealth() { return health.getMaxHealth(); }
	float getSlowResist(DamageType type) { return slowResistances.getResistPercent(type); }
	boolean isDisoriented() { return speed.isDisoriented(); }
	boolean isFlying() { return travel.isFlying(); }
	
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
	
	//Clones attributes to their default states
	public CreepAttributes clone(Creep parent) {
		CreepAttributes attributes = new CreepAttributes(parent);
		attributes.goldValue = (GoldValue) goldValue.clone(this);
		attributes.size = (Size) size.clone(this);
		attributes.healthCost = (HealthCost) healthCost.clone(this);
		attributes.health = (Health) health.clone(this);
		attributes.shield = (Shield) shield.clone(this);
		attributes.damageResistances = (DamageResistances) damageResistances.clone(this);
		attributes.slowResistances = (SlowResistances) slowResistances.clone(this);
		attributes.speed = (Speed) speed.clone(this);
		attributes.toughness = (Toughness) toughness.clone(this);
		attributes.disruption = (Disruption) disruption.clone(this);
		attributes.deathrattle = (Deathrattle) deathrattle.clone(this);
		attributes.onHit = (OnHit) onHit.clone(this);
		attributes.travel = (Travel) travel.clone(this);
		return attributes;
	}
}