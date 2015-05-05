package projectiles;

import java.util.HashSet;
import java.util.Set;

import creeps.Creep;
/*
 * Unit that is fired from a tower, contains information such as position/velocity, target area or target creep
 * as well as tower that fired the projectile. lastly contains the projectile effect which happens when the projectile lands or times out
 * PERHAPS an additional item for stuff like points (bullets) vs rings (aoe circle) vs cones (Flamethrower), or seperate class?
 * Also possibly a second class for passive boost from towers like attack speed in radius
 */
public class Projectile {
	public int x, y;
	public int v;
	public Object graphic;

	public Set<ProjectileEffect> effects;

	public Projectile() {
		effects = new HashSet<ProjectileEffect>();
	}

	public void addEffect(ProjectileEffect effect) {
		effects.add(effect);
	}

	public void applyEffect(Creep creep) {
		for (ProjectileEffect effect : effects) {
			effect.applyEffect(creep);
		}
	}

	public void setGraphic(Object o) {
		graphic = o;
	}
}
