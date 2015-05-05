package creeps;

import java.util.ArrayList;
import java.util.HashSet;

import levels.Path;
import maps.DirectionType;
import maps.Vertex;
import projectiles.ProjectileEffect;
import towers.Tower;

public class Creep {
	//Primary Stats
	public int health;
	public int armor; //Flat damage reduction on hit
	public float speed; //In Tiles per Tick (Imagining .030 - .050 being a normal speed)
	public int healthCost; //Damage Player takes on escape
	public int goldValue; //Money player takes on kill

	//Secondary Stats
	public int earthResist, fireResist, windResist, waterResist;
	public int slowResist;
	public ElementType elementType;
	public HashSet<CreepType> creepTypes = new HashSet<CreepType>();

	//Current Stats
	public int currentArmor;
	public int currentHealth;
	public float currentSpeed;
	public ArrayList<ProjectileEffect> effects = new ArrayList<ProjectileEffect>();

	//Movement
	public Vertex currentVertex;
	public Vertex nextVertex;
	public DirectionType direction;
	public float xOff, yOff;
	public Path path;
	public int pathIndex;

	public Creep(int health, int armor, float speed, int healthCost, int goldValue, ElementType elementType) {
		this.health = health;
		this.armor = armor;
		this.speed = speed;
		this.healthCost = healthCost;
		this.goldValue = goldValue;
		this.elementType = elementType;
		currentHealth = health;
		currentArmor = armor;
		currentSpeed = speed;
	}

	public void addAffix(CreepType type) {
		creepTypes.add(type);
	}

	public boolean isGiant() {
		return creepTypes.contains(CreepType.GIANT);
	}

	public boolean isQuick() {
		return creepTypes.contains(CreepType.QUICK);
	}

	public boolean isFlying() {
		return creepTypes.contains(CreepType.FLYING);
	}

	public void setDestination(int index) {
		nextVertex = path.getVertex(index);
		direction = path.getDirection(index);
	}

	public void setLocation(int index) {
		currentVertex = path.getVertex(0);
		xOff = 0;
		yOff = 0;
	}

	public void setPath(Path path) {
		this.path = path;
		setLocation(0);
		setDestination(1);
		pathIndex = 1;
	}

	public void update() {
		updateMovement();
		updateEffects();
	}

	private void updateMovement() {
		xOff += direction.x * speed;
		yOff += direction.y * speed;
		if (xOff >= 1 || yOff >= 1 || xOff <= -1 || yOff <= -1) {
			//Back step, figure out how much speed was spent for movement
			float speedRemaining;

			if (xOff >= 1 || xOff <= -1) {
				if (xOff <= -1) {
					xOff = -xOff;
				}
				speedRemaining = (xOff - 1) / direction.x;
			} else {
				if (yOff <= -1) {
					yOff = -yOff;
				}
				speedRemaining = (yOff - 1) / direction.y;
			}

			//Move to the new vertex, then adjust our offset with the remaining speed
			currentVertex = nextVertex;
			pathIndex++;
			setDestination(pathIndex);

			xOff = direction.x * speedRemaining;
			yOff = direction.y * speedRemaining;
		}
	}

	private void updateEffects() {
		/* My proposed format for code
		for(int i = 0; i < effects.size() ; i++){
			ProjectileEffect e = effects.get(i);
			e.decrementDuration();
			if(e.expired()){
				e.end(this);
				effects.remove(e);
				i--;
			}
			e.applyAffect(this);
		}
		
		*/
		// TODO Auto-generated method stub

	}

	public boolean isDead() {
		return currentHealth < 1;
	}

	public void damage() {
		//TODO
		//Calculate damage done from armor/resistance
		//Figure out if any effect happens to attack
		//Figure out if killed
	}

	public void death() {
		//TODO
		//activate deathrattle and shit
	}
}
