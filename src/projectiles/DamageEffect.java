package projectiles;

import creeps.DamageType;

public abstract class DamageEffect extends ProjectileEffect {

	public DamageEffect(int lifetime, float modifier, DamageType damageType) {
		super(lifetime, modifier, damageType);
	}

	public abstract boolean ignoresArmor();

	public abstract boolean ignoresShield();
}
