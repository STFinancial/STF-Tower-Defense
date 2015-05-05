package projectiles;

import creeps.Creep;
import creeps.ElementType;

public class Bleed extends DamageEffect {

	int timing;
	int counter = 0;

	public Bleed(int lifetime, float modifier, int timing) {
		super(lifetime, modifier);
		elementType = ElementType.EARTH;
		this.timing = timing;
	}

	@Override
	public void applyEffect(Creep creep) {
		if ((++counter) % timing == 0) {
			creep.damage(this);
		}
	}

	@Override
	public boolean ignoresArmor() {
		return true;
	}

	@Override
	public boolean ignoresShield() {
		return true;
	}

	@Override
	public void onExpire(Creep creep) {
	}
}
