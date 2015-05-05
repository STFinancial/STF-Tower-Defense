package towers;

import java.util.HashSet;
import java.util.Set;
/*
 * Unit that is fired from a tower, contains information such as position/velocity, target area or target creep
 * as well as tower that fired the projectile. lastly contains the projectile effect which happens when the projectile lands or times out
 * PERHAPS an additional item for stuff like points (bullets) vs rings (aoe circle) vs cones (Flamethrower), or seperate class?
 * Also possibly a second class for passive boost from towers like attack speed in radius
 */
public class Projectile {
	public int x, y;
	public int v;

	public Set<ProjectileEffect> effects;

	public Projectile() {
		effects = new HashSet<ProjectileEffect>();
	}


}
