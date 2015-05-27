package projectiles;

public abstract class DamageEffect extends ProjectileEffect {
	public boolean physical;
	
	public DamageEffect(float modifier, boolean physical) {
		super(modifier);
		this.physical = physical;
	}

	public DamageEffect(int lifetime, float modifier, boolean physical) {
		super(lifetime, modifier);
		this.physical = physical;
	}

	public abstract boolean ignoresArmor();

	public abstract boolean ignoresShield();
}
