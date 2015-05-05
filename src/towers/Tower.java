package towers;

import creeps.Creep;
import creeps.ElementType;
import projectiles.Projectile;

public abstract class Tower {
	public int attackCoolDown; //Number of game ticks between tower shots, 0 for passive towers (beacons)

	int x, y; //Top Left corner in Tile Coordinates
	
	public int width;
	public int height;
	public int cost;
	
	public boolean targetsCreep; //False for targeting a ground spot;
	float targetX, targetY; //For ground spot target towers, in Tile coordinates
	Creep targetCreep;
	float targetAngle; //For animation and to pass to projectiles when fired
	
	abstract Projectile fireProjectile();
}
