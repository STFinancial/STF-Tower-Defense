package creeps;


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
	public void setHealthValues(float maxHealth, float maxHealthRegen) { currentAttributes.setHealthValues(maxHealth, maxHealthRegen); }
	public void setDamageResists(float[] damageResists) { currentAttributes.setDamageResists(damageResists); }
	void setSlowResists(float[] slowResists) { currentAttributes.setSlowResists(slowResists); }
	public void setSpeedValues(float defaultSpeed, boolean snareImmune, int snareGrace, boolean disorientImmune, int disorientGrace) { currentAttributes.setSpeedValues(defaultSpeed, snareImmune, snareGrace, disorientImmune, disorientGrace); }
	public Creep build() {
		currentCreep.setAttributes(currentAttributes);
		Creep built = currentCreep;
		currentCreep = null;
		return built;
	}
}
