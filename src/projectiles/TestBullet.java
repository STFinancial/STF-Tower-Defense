package projectiles;

import creeps.Creep;

public class TestBullet extends ProjectileEffect {
	/*
	 * Just an example of a very simple damage projectile
	 */

	public TestBullet() {
		super(ProjectileEffectType.DAMAGE);
	}

	@Override
	public void applyEffect(Creep creep) {
		creep.health = creep.health - (int) modifier;
	}
}
