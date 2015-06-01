package projectiles;

import creeps.Creep;
import creeps.Creep.CreepEffect;
import creeps.DamageType;

public class Bleed extends DamageEffect {

	int timing;

	public Bleed(int lifetime, float modifier, int timing, DamageType damageType) {
		super(lifetime, modifier, damageType);
		this.timing = timing;
	}

	@Override
	public void applyEffect(Creep creep, CreepEffect effect) {
		if ((effect.counter) % timing == 0) {
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
