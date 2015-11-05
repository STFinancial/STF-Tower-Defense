package creeps;

import levels.Level;
import projectileeffects.ProjectileEffect;
import utilities.Circle;


public final class CreepBuilder {
	private static final CreepBuilder INSTANCE = new CreepBuilder();
	private Level level;
	private int idGenerator = 0;;
	
	private Creep currentCreep;
	private CreepAttributes currentAttributes;
	private CreepBuilder() {}
	
	public static CreepBuilder getInstance() { return INSTANCE; }
	public void setInstanceLevel(Level level) { this.level = level; }
	private int getNextId() { return idGenerator++; }
	public void begin() {
		currentCreep = new Creep(level, getNextId());
		currentAttributes = new CreepAttributes(currentCreep);
	}
	public void addDeathrattleChild(Creep child) { currentAttributes.addDeathrattleChild(child); }
	public void addDeathrattleEffect(ProjectileEffect effect, Circle area) { currentAttributes.addDeathrattleEffect(effect, area); }
	public void addDeathrattleEffect(ProjectileEffect effect, Circle area, int defaultDuration) { currentAttributes.addDeathrattleEffect(effect, area, defaultDuration); }
	public void clearDeathrattle() { currentAttributes.clearDeathrattle(); }
	public void setHealthValues(float maxHealth, float defaultHealthRegen) { currentAttributes.setHealthValues(maxHealth, defaultHealthRegen); }
	public void setShieldValues(float maxShield, float defaultShieldRegen) { currentAttributes.setShieldValues(maxShield, defaultShieldRegen); }
	public void setDamageResists(float[] damageResists) { currentAttributes.setDamageResists(damageResists); }
	public void setSlowResists(float[] slowResists) { currentAttributes.setSlowResists(slowResists); }
	public void setSpeedValues(float defaultSpeed, boolean snareImmune, boolean disorientImmune, boolean knockupImmune, int gracePeriod) { currentAttributes.setSpeedValues(defaultSpeed, snareImmune, disorientImmune, knockupImmune, gracePeriod); }
	public void setToughnessValues(float defaultToughness) { currentAttributes.setToughnessValues(defaultToughness); }
	public void setDisruptionValues(float defaultDisruption) { currentAttributes.setDisruptionValues(defaultDisruption); }
	public void setGoldValue(float defaultGoldValue) { currentAttributes.setGoldValue(defaultGoldValue); }
	public void setOnHit(float goldOnHit, float cooldownOnHit, float[] damageOnHit) { currentAttributes.setOnHit(goldOnHit, cooldownOnHit, damageOnHit); }
	public void setSize(float defaultSize) { currentAttributes.setSize(defaultSize); }
	public void setHealthCost(float defaultHealthCost) { currentAttributes.setHealthCost(defaultHealthCost); }
	public void setTravel(boolean defaultIsFlying, boolean groundingImmune) { currentAttributes.setTravel(defaultIsFlying, groundingImmune); if (defaultIsFlying) { currentCreep.setPath(currentCreep.level.getFlyingPath()); } else { currentCreep.setPath(currentCreep.level.getGroundPath()); }}
	public Creep build() {
		currentCreep.setAttributes(currentAttributes.finishBuild()); //TODO: Change the finish build to throwing an exception?
		Creep built = currentCreep;
		currentCreep = null;
		return built;
	}
}
