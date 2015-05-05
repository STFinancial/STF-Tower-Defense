package projectiles;

public abstract class DamageEffect extends ProjectileEffect {

	public DamageEffect(float modifier) {
		super(modifier);
	}

	public DamageEffect(int lifetime, float modifier) {
		super(lifetime, modifier);
	}

	public abstract boolean ignoresArmor();

	public abstract boolean ignoresShield();
}
