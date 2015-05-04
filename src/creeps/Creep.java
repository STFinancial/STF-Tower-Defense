package creeps;

import java.util.HashSet;

public class Creep {
	//we can decide how we want these stats to be
	public int speed;
	public int health;
	public int armor;
	public int earthResist, fireResist, windResist, waterResist;
	public int slowResist;
	
	public ElementType elementType;
	HashSet<CreepType> creepTypes = new HashSet<CreepType>();
	
	
}
