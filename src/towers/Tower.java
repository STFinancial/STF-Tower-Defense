package towers;

public abstract class Tower {
	//these can be set after modifiers are factored in using the base values
	public int attackDamage;
	public float attackSpeed; //this can be number of attacks per second or we can just make it an int and have it stand for some arbitrary speed and use a formula
	//public int attackDamage; //we could enscapsulate this in the projectile
	public int attackCoolDown; //Number of game ticks between tower shots, 0 for passive towers (beacons)

	public int width;
	public int height;
}
