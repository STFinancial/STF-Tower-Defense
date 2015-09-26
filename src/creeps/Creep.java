package creeps;

import java.util.ArrayList;
import java.util.HashSet;

import projectileeffects.ProjectileEffect;

import levels.Path;
import levels.Updatable;
import maps.DirectionType;
import maps.Vertex;
import towers.Tower;
import utilities.Circle;

public class Creep implements Updatable {
	public int creepID;

	//TODO: Move this to attributes?
	public DamageType elementType; //FIRE AIR etc creep type 

	//Current Stats
	CreepAttributes attributes;

	//Secondary Stats
	public HashSet<CreepType> creepTypes = new HashSet<CreepType>();

	//Movement
	public Vertex currentVertex;
	public DirectionType direction;
	public int previousIndex;
	public int currentIndex;
	public int nextIndex;
	public float xOff, yOff;
	public Path path;
	public Circle hitBox;

	Creep() {};
	
	void setAttributes(CreepAttributes attributes) { this.attributes = attributes; }

	
	
	public void onProjectileCollision() {
		//TODO: This needs to be called by projectiles and needs to do disruption effects
		attributes.onProjectileCollision();
	}
	
	//TODO: Public interface methods that simply delegate to the attributes layer.
	public void addDamageOnHit(DamageType type, float amount) { attributes.addOnHit(type, amount); }
	public void consumeBleeds(float amount) { attributes.consumeBleeds(amount); }
	public void damage(DamageType type, float amount, float penPercent, float penFlat, boolean ignoresShield, float shieldDrainModifier, float toughPenPercent, float toughPenFlat) { attributes.damage(type, amount, penPercent, penFlat, ignoresShield, shieldDrainModifier, toughPenPercent, toughPenFlat); }
	public void increaseResist(DamageType type, float amount, boolean isFlat) { attributes.increaseResist(type, amount, isFlat); }
	public void increaseToughness(float amount, boolean isFlat) { attributes.increaseToughness(amount, isFlat);	}
	public void nullify() { attributes.nullify(); } //TODO: Maybe want modifier in the future
	public void reduceMaxSpeed(DamageType type, float amount, boolean isFlat) { attributes.reduceMaxSpeed(type, amount, isFlat); }
	public void reduceResist(DamageType type, float amount, boolean isFlat) { attributes.reduceResist(type, amount, isFlat); }
	public void reduceToughness(float amount, boolean isFlat) { attributes.reduceToughness(amount, isFlat);	}
	public void removeDamageOnHit(DamageType type, float amount) { attributes.removeOnHit(type, amount); }
	public void slow(DamageType type, float amount) { attributes.slow(type, amount); }
	public void snare() { attributes.snare(); } //TODO: May want to attach type if some creeps are immune to this
	public void suppressDeathrattle(DamageType type, float modifier, int lifetime) { attributes.suppressDeathrattle(modifier, lifetime); }
	public void suppressDisruption(DamageType type, float amount, boolean isFlat) { attributes.suppressDisruption(amount, isFlat); }
	public void unslow(DamageType type, float amount) { attributes.unslow(type, amount); }
	public void unsuppressDisruption(DamageType type, float amount, boolean isFlat) { attributes.unsuppressDisruption(amount, isFlat); }

	public void disorient(int lifetime) { 
		if (attributes.disorient(lifetime)) {
			int temp = previousIndex;
			previousIndex = nextIndex;
			nextIndex = temp;
			updateDirection();
		}
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

	/**
	 * Sets the creep's location at the beginning of the path. This assumes that the path cannot be updated mid-round.
	 * @param path - The path that the creep will follow.
	 */
	public void setPath(Path path) {
		this.path = path;
		setLocation(0);
	}
	
	private void setLocation(int newLocation) {
		xOff = 0;
		yOff = 0;
		currentIndex = newLocation;
		if (attributes.isDisoriented()) {
			previousIndex = newLocation + 1;
			nextIndex = newLocation - 1;
		} else {
			previousIndex = newLocation - 1;
			nextIndex = newLocation + 1;
		}
		currentVertex = path.getVertex(currentIndex);
		updateDirection();
	}
	
	private void updateDirection() {
		direction = path.getDirection(currentIndex, nextIndex);
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
			previousIndex = currentIndex;
			currentIndex = nextIndex;
			currentVertex = path.getVertex(currentIndex);
			if (attributes.isDisoriented()) {
				nextIndex--; //If disoriented, we move backward along the path
			} else {
				nextIndex++; //If not, we just move forward
			}
			updateDirection();
			
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
		this.nextIndex = c.nextIndex;
		//Creep can't be disoriented when they come out so we need to work around that
		if (c.attributes.isDisoriented()) {
			this.nextIndex = c.previousIndex;
			this.previousIndex = c.nextIndex;
			this.direction = c.direction.getOpposite();
		} else {
			this.nextIndex = c.nextIndex;
			this.previousIndex = c.previousIndex;
			this.direction = c.direction;
		}
		this.xOff = c.xOff;
		this.yOff = c.yOff;
		this.path = c.path;
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
