package creeps;

import java.util.List;

import projectileeffects.ProjectileEffect;


public final class CreepBuilder {
	private static final CreepBuilder INSTANCE = new CreepBuilder();
	
	private Creep currentCreep;
	private CreepAttributes currentAttributes;
	private CreepBuilder() {}
	
	public static CreepBuilder getInstance() { return INSTANCE; }
	public void begin() {
		currentCreep = new Creep();
		currentAttributes = new CreepAttributes(currentCreep);
	}
	public void setHealthValues(float maxHealth, float defaultHealthRegen) { currentAttributes.setHealthValues(maxHealth, defaultHealthRegen); }
	public void setShieldValues(float maxShield, float defaultShieldRegen) { currentAttributes.setShieldValues(maxShield, defaultShieldRegen); }
	public void setDamageResists(float[] damageResists) { currentAttributes.setDamageResists(damageResists); }
	public void setSlowResists(float[] slowResists) { currentAttributes.setSlowResists(slowResists); }
	public void setSpeedValues(float defaultSpeed, boolean snareImmune, int snareGrace, boolean disorientImmune, int disorientGrace) { currentAttributes.setSpeedValues(defaultSpeed, snareImmune, snareGrace, disorientImmune, disorientGrace); }
	public void setToughnessValues(float defaultToughness) { currentAttributes.setToughnessValues(defaultToughness); }
	public void setDisruptionValues(float defaultDisruption) { currentAttributes.setDisruptionValues(defaultDisruption); }
	public void setGoldValue(float defaultGoldValue) { currentAttributes.setGoldValue(defaultGoldValue); }
	public void setDeathrattle(ProjectileEffect effect, List<Creep> children) { currentAttributes.setDeathrattle(effect, children); }
	public void setOnHit(float goldOnHit, float cooldownOnHit, float[] damageOnHit) { currentAttributes.setOnHit(goldOnHit, cooldownOnHit, damageOnHit); }
	public void setSize(float defaultSize) { currentAttributes.setSize(defaultSize); }
	public void setHealthCost(float defaultHealthCost) { currentAttributes.setHealthCost(defaultHealthCost); }
	public void setTravel(boolean defaultIsFlying, boolean groundingImmune) { currentAttributes.setTravel(defaultIsFlying, groundingImmune); }
	
	public Creep build() {
		currentCreep.setAttributes(currentAttributes.finishBuild()); //TODO: Change the finish build to throwing an exception?
		Creep built = currentCreep;
		currentCreep = null;
		return built;
	}
}
