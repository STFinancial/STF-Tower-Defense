package projectiles;

import creeps.ElementType;

public abstract class DamageEffect extends ProjectileEffect {
	public DamageType damageType;

	public DamageEffect(int lifetime, float modifier, DamageType damageType, ElementType elementType) {
		super(lifetime, modifier, elementType);
		this.damageType = damageType;
	}

	public abstract boolean ignoresArmor();

	public abstract boolean ignoresShield();
}
