package creeps;

import java.util.ArrayList;
import java.util.HashSet;

import levels.Path;
import levels.Updatable;
import maps.DirectionType;
import maps.Vertex;
import towers.Tower;
import utilities.Circle;

public class Creep implements Updatable {
	public int creepID;
	
	//Primary Stats
	public int healthCost; //Damage Player takes on escape
	public int goldValue; //Money player takes on kill
	public DamageType elementType; //FIRE AIR etc creep type 

	//Current Stats
	CreepAttributes attributes;

	//Secondary Stats
	public HashSet<CreepType> creepTypes = new HashSet<CreepType>();

	//Movement
	public Vertex currentVertex;
	public Vertex nextVertex;
	public DirectionType direction;
	public float xOff, yOff;
	public Path path;
	public int pathIndex;
	public float size = .4f; //Radius
	public Circle hitBox;

	//Fancy Effects
	public ArrayList<Creep> children;
	public int disruptorAmount;

	public Creep(float[] maxDamageResistsFlat, float[] maxSlowResists, float maxHealth, float healthRegenRate, float maxToughness, float maxShieldValue, float shieldRegenRate, boolean snareImmune, float maxSpeed, int healthCost, int goldValue, DamageType elementType) {
		this.attributes = new CreepAttributes(this, maxDamageResistsFlat, maxSlowResists, maxHealth, healthRegenRate, maxToughness, maxShieldValue, shieldRegenRate, snareImmune, maxSpeed);
		this.healthCost = healthCost;
		this.goldValue = goldValue;
		this.elementType = elementType;
		hitBox = new Circle(1, 1, size);
	}
	
	private Creep(CreepAttributes attributes, int healthCost, int goldValue, DamageType elementType) {
		this.attributes = attributes.clone();
		this.healthCost = healthCost;
		this.goldValue = goldValue;
		this.elementType = elementType;
		hitBox = new Circle(1, 1, size);
	}

	//TODO: do we want to add to different sides of the list based on the type of effect it is
	//The result of this would be that armor shreds are applied first
	//ENUM seems like the best solution eventually
	public void addEffect(ProjectileEffect effect) {
		attributes.addEffect(effect.clone());
	}

	public void addAllEffects(ArrayList<ProjectileEffect> effects) {
		for (ProjectileEffect e: effects) {
			attributes.addEffect(e.clone());
		}
	}
	
	public float damage(DamageType type, float amount, float penPercent, float penFlat, boolean ignoresShield, float shieldDrainModifier, float toughPenPercent, float toughPenFlat) {
		return attributes.damage(type, amount, penPercent, penFlat, ignoresShield, shieldDrainModifier, toughPenPercent, toughPenFlat);
	}
	
	public float slow(DamageType type, float amount) {
		return attributes.slow(type, amount);
	}
	
	public float unslow(DamageType type, float amount) {
		return attributes.unslow(type, amount);
	}
	
	public void snare() {
		attributes.snare();
	}
	
	public void unsnare() {
		attributes.unsnare();
	}
	
	public float consumeBleeds(float amount) {
		return attributes.consumeBleeds(amount);
	}
	
	public float reducePercentResist(DamageType type, float percentReduction) {
		return attributes.reducePercentResist(type, percentReduction);
	}
	
	public float increasePercentResist(DamageType type, float percentIncrease) {
		return attributes.increasePercentResist(type, percentIncrease);
	}
	
	public float reduceFlatResist(DamageType type, float amount) {
		return attributes.reduceFlatResist(type, amount);
	}
	
	public float increaseFlatResist(DamageType type, float amount) {
		return attributes.increaseFlatResist(type, amount);
	}
	
	public float reduceFlatToughness(float amount) {
		return attributes.reduceFlatToughness(amount);
	}
	
	public float increaseFlatToughness(float amount) {
		return attributes.increaseFlatToughness(amount);
	}
	
	public float reducePercentToughness(float amount) {
		return attributes.reducePercentToughness(amount);
	}
	
	public float increasePercentToughness(float amount) {
		return attributes.increasePercentToughness(amount);
	}

	public void addAffix(CreepType type) {
		creepTypes.add(type);
		if (type == CreepType.GIANT) {
			size = .7f;
			hitBox.radius = size;
		}
		if (type == CreepType.QUICK) {
			size = .2f;
			hitBox.radius = size;
		}
	}

	public ArrayList<Creep> death() {
		//TODO
		return children;
	}

	public boolean isDead() {
		return attributes.getCurrentHealth() <= 0;
	}

	public boolean is(CreepType type) {
		return creepTypes.contains(type);
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

	public int update() {
		updateMovement();
		updateHitBox();
		updateEffects();
		return 0;
	}

	private void updateEffects() {
		attributes.update();
	}

	private void updateMovement() {
		xOff += direction.x * attributes.getCurrentSpeed();
		yOff += direction.y * attributes.getCurrentSpeed();
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

	public void updateHitBox() {
		hitBox.x = currentVertex.x + xOff + 1;
		hitBox.y = currentVertex.y + yOff + 1;
	}

	public void setLocation(Creep c) {
		//Update freshly spawned deathrattle creep with parent's path
		this.currentVertex = c.currentVertex;
		this.nextVertex = c.nextVertex;
		this.direction = c.direction;
		this.xOff = c.xOff;
		this.yOff = c.yOff;
		this.path = c.path;
		this.pathIndex = c.pathIndex;
	}

	/*
	 * NOT A FULL CLONE, ONLY WORKS FOR CREEPS NOT YET SPAWNED
	 */
	public Creep clone() {
		Creep clone = new Creep(attributes, healthCost, goldValue, elementType);

		clone.creepTypes = new HashSet<CreepType>();
		for (CreepType type : this.creepTypes) {
			clone.creepTypes.add(type);
		}

		clone.size = this.size; //Radius
		clone.hitBox.radius = this.hitBox.radius;

		//Fancy Effects
		clone.children = new ArrayList<Creep>();
		if (this.children != null) {
			for (Creep child : this.children) {
				clone.children.add(child.clone());
			}
		}
		clone.disruptorAmount = this.disruptorAmount;

		return clone;
	}

	public String toString() {
		String string = "hp = " + attributes.getCurrentHealth() + ", toughness = " + attributes.getCurrentToughness() + " , speed = " + attributes.getCurrentSpeed();
		string += "\nelement = " + elementType + ", Modifiers: ";
		for (CreepType type : creepTypes) {
			string += " " + type;
			if (type == CreepType.DEATH_RATTLE) {
				string += "\n  " + children.size() + " Children: " + children.get(0).toString();
			}
		}
		return string;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Tower)) {
			return false;
		}
        Creep c = (Creep) o;
        return c.creepID == creepID;
	}
}
