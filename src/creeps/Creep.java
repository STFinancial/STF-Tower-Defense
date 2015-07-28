package creeps;

import java.util.ArrayList;
import java.util.HashSet;

import levels.Path;
import maps.DirectionType;
import maps.Vertex;
import projectileeffects.ProjectileEffect;
import utilities.Circle;

public class Creep {
	//Primary Stats
	public int health;
	public int toughness; //flat reduction for all types
	public float speed; //In Tiles per Tick (Imagining .030 - .050 being a normal speed)
	public int healthCost; //Damage Player takes on escape
	public int goldValue; //Money player takes on kill
	public DamageType elementType; //FIRE AIR etc creep type 

	//Current Stats
	public int currentToughness;
	public float currentHealth;
	public float currentSpeed;
	public ArrayList<CreepEffect> effects = new ArrayList<CreepEffect>();

	//Secondary Stats
	public float[] resist; //percentage of damage taken from each element
	public float slowResist;
	public boolean snareImmune;
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
	float baseShield;
	public float currentShield;
	float shieldCap;
	public int disruptorAmount;

	public Creep(int health, float speed, int toughness, int healthCost, int goldValue, DamageType elementType) {
		this.health = health;
		this.speed = speed;
		this.healthCost = healthCost;
		this.goldValue = goldValue;
		this.elementType = elementType;
		this.toughness = toughness;
		currentHealth = health;
		currentSpeed = speed;

		resist = elementType.baseResist();
		hitBox = new Circle(1, 1, size);
	}
	//TODO possibly remove this and go back to just recreating each projectile effect
	public class CreepEffect {
		CreepEffect(ProjectileEffect p) {
			projectileEffect = p;
			duration = p.lifetime;
			timing = p.timing;
			counter = 0;
		}

		public ProjectileEffect projectileEffect;
		int duration;
		int timing;
		public int counter;
	}

	public void addEffect(ProjectileEffect effect) {
		effects.add(new CreepEffect(effect));
	}

	public void addAllEffects(ArrayList<ProjectileEffect> effects) {
		for (ProjectileEffect p : effects) {
			this.effects.add(new CreepEffect(p));
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
		return currentHealth < 1;
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

	public void update() {
		updateMovement();
		updateHitBox();
		updateEffects();
	}

	private void updateEffects() {
		currentSpeed = speed;
		for (int i = 0; i < effects.size(); i++) {
			CreepEffect e = effects.get(i);
			if (e.timing != 0 && e.counter % e.timing == 0) {
				e.projectileEffect.applyEffect(this, null);
			} else if (e.timing == 0 && e.counter == 0) {
				e.projectileEffect.applyEffect(this, null);
			}
			if (e.counter == e.duration) {
				e.projectileEffect.onExpire(this);
				effects.remove(i--);
				continue;
			}
			e.counter++;
		}
	}

	private void updateMovement() {
		xOff += direction.x * currentSpeed;
		yOff += direction.y * currentSpeed;
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
		Creep clone = new Creep(health, speed, toughness, healthCost, goldValue, elementType);

		//Secondary Stats
		clone.resist = this.resist.clone(); //percentage of damage taken from each element
		clone.slowResist = this.slowResist;
		clone.snareImmune = this.snareImmune;
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
		clone.baseShield = this.baseShield;
		clone.currentShield = this.currentShield;
		clone.shieldCap = this.shieldCap;
		clone.disruptorAmount = this.disruptorAmount;

		return clone;
	}

	public String toString() {
		String string = "hp = " + health + ", toughness = " + toughness + " , speed = " + speed;
		string += "\nelement = " + elementType + ", Modifiers: ";
		for (CreepType type : creepTypes) {
			string += " " + type;
			if (type == CreepType.DEATH_RATTLE) {
				string += "\n  " + children.size() + " Children: " + children.get(0).toString();
			}
		}
		return string;
	}
}
