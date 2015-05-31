package creeps;

import java.util.ArrayList;
import java.util.HashSet;

import levels.Path;
import maps.DirectionType;
import maps.Vertex;
import projectiles.DamageEffect;
import projectiles.DamageType;
import projectiles.ProjectileEffect;
import utilities.Circle;

public class Creep {
	//Primary Stats
	public float health;
	public int toughness; //flat reduction for all types
	public float armor;
	public float speed; //In Tiles per Tick (Imagining .030 - .050 being a normal speed)
	public int healthCost; //Damage Player takes on escape
	public int goldValue; //Money player takes on kill

	//Secondary Stats
	public float[] resist; //percentage of damage taken from each element
	public float slowResist;
	public boolean snareImmune;
	public ElementType elementType;
	public HashSet<CreepType> creepTypes = new HashSet<CreepType>();

	//Current Stats
	public int currentArmor;
	public int currentHealth;
	public float currentSpeed;
	public ArrayList<CreepEffect> effects = new ArrayList<CreepEffect>();

	//Movement
	public Vertex currentVertex;
	public Vertex nextVertex;
	public DirectionType direction;
	public float xOff, yOff;
	public Path path;
	public int pathIndex;
	public float size = .2f; //Radius
	public Circle hitBox;

	//Fancy Effects
	public ArrayList<Creep> children;
	float baseShield;
	float currentShield;
	float shieldCap;
	public int disruptorAmount;
	
	public Creep(int health, float speed, float armor, int toughness, int healthCost, int goldValue, ElementType elementType) {
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
	
	public class CreepEffect {
		CreepEffect(ProjectileEffect p, int d) {
			projectileEffect = p;
			duration = d;
			counter = 0;
		}
		public ProjectileEffect projectileEffect;
		int duration;
		public int counter;
	}
	
	public void addEffect(ProjectileEffect effect) {
		effects.add(new CreepEffect(effect, effect.lifetime));
	}
	
	public void addAllEffects(ArrayList<ProjectileEffect> effects) {
		for (ProjectileEffect p: effects) {
			this.effects.add(new CreepEffect(p, p.lifetime));
		}
	}

	public void addAffix(CreepType type) {
		creepTypes.add(type);
		if(type == CreepType.GIANT){
			size = .4f;
			hitBox.radius = size;
		}
		if(type == CreepType.QUICK){
			size = .1f;
			hitBox.radius = size;
		}
	}

	public void damage(DamageEffect damager) {
		float baseDamage = damager.modifier;
		float damageToDo = baseDamage;
		if (damager.damageType == DamageType.PHYSICAL) {
			if (!damager.ignoresArmor()) {
				damageToDo *= armor;
			}
		} else {
			damageToDo = baseDamage * resist[damager.elementType.ordinal()];
		}
		damageToDo -= toughness;
		
		if (damageToDo < 0) {
			damageToDo = 0;
		}
		if (currentShield < damageToDo) {
			float damageLeft = currentShield - damageToDo;
			currentShield = 0;
			currentHealth -= damageLeft;
		} else {
			currentShield -= damageToDo;
		}
	}

	public ArrayList<Creep> death() {
		//TODO
		return children;
	}

	public boolean isDead() {
		return currentHealth < 1;
	}

	public boolean is(CreepType type){
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
		for (CreepEffect e: effects) {
			e.counter++; //TODO should we do this before or after?
			if (e.duration == 0) {
				e.projectileEffect.onExpire(this);
				effects.remove(e);
			} else {
				e.projectileEffect.applyEffect(this, e);
				e.duration--;
			}
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
	
	public void updateHitBox(){
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
}
