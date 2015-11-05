package creeps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import projectileeffects.ProjectileEffect;

import levels.Level;
import levels.Path;
import levels.Updatable;
import maps.DirectionType;
import maps.Vertex;
import towers.Tower;
import utilities.Circle;

public class Creep implements Updatable {
	public int creepID;
	public DamageType elementType; //FIRE AIR etc creep type //TODO: Move this to attributes?

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
	public Level level;
	public Circle hitBox;

	Creep(Level level, int id) {
		this.level = level;
		this.xOff = 0;
		this.yOff = 0;
		this.creepID = id;
		this.hitBox = new Circle(0,0,0);
	}
	
	void setAttributes(CreepAttributes attributes) { this.attributes = attributes; hitBox.radius = attributes.getCurrentSize(); setLocation(0); }
	
	public float onProjectileCollision() {
		attributes.onProjectileCollision();
		return attributes.getDisruption();
	}
	
	public List<Creep> onDeath() {
		level.addGold(attributes.getCurrentGoldValue());
		return attributes.deathrattle();
	}
	
	//Public interface methods that simply delegate to the attributes layer.
	public void addAllEffects(ArrayList<ProjectileEffect> effects) { attributes.addAllEffects(effects); }
	public void addDeathrattleEffect(ProjectileEffect effect, Circle area) { attributes.addDeathrattleEffect(effect, area); }
	public void addDeathrattleEffect(ProjectileEffect effect, Circle area, int duration) { attributes.addDeathrattleEffect(effect, area, duration); }
	public void addEffect(ProjectileEffect effect) { attributes.addEffect(effect); }
	public void consumeBleeds(float amount) { attributes.consumeBleeds(amount); }
	public void damage(DamageType type, float amount, float penPercent, float penFlat, boolean ignoresShield, float shieldDrainModifier, float toughPenPercent, float toughPenFlat) { attributes.damage(type, amount, penPercent, penFlat, ignoresShield, shieldDrainModifier, toughPenPercent, toughPenFlat); }
	public void increaseDamageOnHit(DamageType type, float amount) { attributes.increaseDamageOnHit(type, amount); }
	public void increaseDamageResist(DamageType type, float amount, boolean isFlat) { attributes.increaseDamageResist(type, amount, isFlat); }
	public void increaseGoldOnHit(float amount) { attributes.increaseGoldOnHit(amount); }
	public void increaseGoldValue(float amount, boolean isFlat) { attributes.increaseGoldValue(amount, isFlat); }
	public void increaseHasting(DamageType type, float amount) { attributes.increaseCDOnHit(type, amount); }
	public void increaseToughness(float amount, boolean isFlat) { attributes.increaseToughness(amount, isFlat);	}
	public void knockup(int duration) { attributes.knockup(duration); }
	public void nullify() { attributes.nullify(); } //TODO: Maybe want modifier in the future, or lifetime
	public void reduceDamageOnHit(DamageType type, float amount) { attributes.reduceDamageOnHit(type, amount); }
	public void reduceDamageResist(DamageType type, float amount, boolean isFlat) { attributes.reduceDamageResist(type, amount, isFlat); }
	public void reduceGoldOnHit(float amount) { attributes.reduceGoldOnHit(amount); }
	public void reduceGoldValue(float amount, boolean isFlat) { attributes.reduceGoldValue(amount, isFlat); }
	public void reduceHasting(DamageType type, float amount) { attributes.reduceCDOnHit(type, amount); }
	public void reduceMaxSpeed(DamageType type, float amount, boolean isFlat) { attributes.reduceMaxSpeed(type, amount, isFlat); }
	public void reduceToughness(float amount, boolean isFlat) { attributes.reduceToughness(amount, isFlat);	}
	public void slow(DamageType type, float amount) { attributes.slow(type, amount); }
	public void snare(int duration) { attributes.snare(duration); } //TODO: May want to attach type if some creeps are immune to this
	public void suppressDeathrattle(DamageType type, float modifier, int lifetime) { attributes.suppressDeathrattle(modifier, lifetime); }
	public void suppressDisruption(DamageType type, float amount, boolean isFlat) { attributes.suppressDisruption(amount, isFlat); }
	public void unslow(DamageType type, float amount) { attributes.unslow(type, amount); }
	public void unsuppressDisruption(DamageType type, float amount, boolean isFlat) { attributes.unsuppressDisruption(amount, isFlat); }

	//Public getter methods
	public float getCurrentSize() { return attributes.getCurrentSize(); }
	public float getCurrentSpeed() { return attributes.getCurrentSpeed(); }
	public float getCurrentHealthCost() { return attributes.getCurrentHealthCost(); }
	public float getMaxHealth() { return attributes.getMaxHealth(); }
	public boolean isFlying() { return attributes.isFlying(); }
	
	public void disorient(int lifetime) { 
		if (attributes.disorient(lifetime)) {
			int temp = previousIndex;
			previousIndex = nextIndex;
			nextIndex = temp;
			updateDirection();
		}
	}
	
	public void ground() {
		if (attributes.ground()) {
			Path newPath = level.getGroundPath();
			setLocation(level.getVertexBelow(currentVertex));
			this.path = newPath;
		}
	}
	
	public void addAffix(CreepType type) {
		//TODO: Implement these
		creepTypes.add(type);
		if (type == CreepType.GIANT) {
			//size = .7f;
			//hitBox.radius = size;
		}
		if (type == CreepType.QUICK) {
			//size = .2f;
			//hitBox.radius = size;
		}
	}

	

	public boolean isDead() {
		return attributes.isDead();
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
		updateHitBox();
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

	//Clones the attributes back to their default states. Does not clone effects on the creep.
	public Creep clone() {
		CreepBuilder.getInstance().begin();
		Creep c = CreepBuilder.getInstance().build();
		c.setAttributes(attributes.clone(c));
		return c;
	}

	public String toString() {
		String string = "HP = " + attributes.getCurrentHealth() + ", Toughness = " + attributes.getCurrentToughness() + " , Speed = " + attributes.getCurrentSpeed();
//		string += "\nelement = " + elementType + ", Modifiers: ";
//		for (CreepType type : creepTypes) {
//			string += " " + type;
//			if (type == CreepType.DEATH_RATTLE) {
//				string += "\n  " + children.size() + " Children: " + children.get(0).toString();
//			}
//		}
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
