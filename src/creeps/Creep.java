package creeps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import game.GameObject;
import projectileeffects.ProjectileEffect;
import levels.DirectionType;
import levels.LevelManager;
import levels.Path;
import levels.Vertex;
import towers.Tower;
import utilities.Circle;

public class Creep extends GameObject {
	private int creepID;
	protected DamageType elementType; //FIRE AIR etc creep type //TODO: Move this to attributes?

	//Current Stats
	protected CreepAttributes attributes;

	//Secondary Stats
	protected HashSet<CreepType> creepTypes = new HashSet<CreepType>();

	//Movement
	protected LevelManager levelManager;
	protected Vertex currentVertex;
	protected DirectionType direction;
	protected int previousIndex;
	protected int currentIndex;
	protected int nextIndex;
	protected float xOff, yOff;
	protected Path path;
	protected Circle hitBox;

	protected Creep(int id) {
		this.levelManager = LevelManager.getInstance();
		this.xOff = 0;
		this.yOff = 0;
		this.creepID = id;
		this.hitBox = new Circle(0,0,0);
	}
	
	protected void setAttributes(CreepAttributes attributes) { 
		this.attributes = attributes;
		//At some point in the future we might want to set the attributes: attributes.setParentCreep(this);
		hitBox.radius = attributes.getCurrentSize();
		setLocation(0); 
	}
	
	protected float onProjectileCollision() {
		attributes.onProjectileCollision();
		return attributes.getDisruption();
	}
	
	protected List<Creep> onDeath() {
		levelManager.addGold(attributes.getCurrentGoldValue());
		return attributes.deathrattle();
	}
	
	//Public interface methods that simply delegate to the attributes layer.
	protected void addAllEffects(ArrayList<ProjectileEffect> effects) { attributes.addAllEffects(effects); }
	protected void addDeathrattleEffect(ProjectileEffect effect, Circle area) { attributes.addDeathrattleEffect(effect, area); }
	protected void addDeathrattleEffect(ProjectileEffect effect, Circle area, int duration) { attributes.addDeathrattleEffect(effect, area, duration); }
	protected void addEffect(ProjectileEffect effect) { attributes.addEffect(effect); }
	protected void consumeBleeds(float amount) { attributes.consumeBleeds(amount); }
	protected void damage(DamageType type, float amount, float penPercent, float penFlat, boolean ignoresShield, float shieldDrainModifier, float toughPenPercent, float toughPenFlat) { attributes.damage(type, amount, penPercent, penFlat, ignoresShield, shieldDrainModifier, toughPenPercent, toughPenFlat); }
	protected void increaseDamageOnHit(DamageType type, float amount) { attributes.increaseDamageOnHit(type, amount); }
	protected void increaseDamageResist(DamageType type, float amount, boolean isFlat) { attributes.increaseDamageResist(type, amount, isFlat); }
	protected void increaseGoldOnHit(float amount) { attributes.increaseGoldOnHit(amount); }
	protected void increaseGoldValue(float amount, boolean isFlat) { attributes.increaseGoldValue(amount, isFlat); }
	protected void increaseHasting(DamageType type, float amount) { attributes.increaseCDOnHit(type, amount); }
	protected void increaseToughness(float amount, boolean isFlat) { attributes.increaseToughness(amount, isFlat);	}
	protected void knockup(int duration) { attributes.knockup(duration); }
	protected void nullify() { attributes.nullify(); } //TODO: Maybe want modifier in the future, or lifetime
	protected void reduceDamageOnHit(DamageType type, float amount) { attributes.reduceDamageOnHit(type, amount); }
	protected void reduceDamageResist(DamageType type, float amount, boolean isFlat) { attributes.reduceDamageResist(type, amount, isFlat); }
	protected void reduceGoldOnHit(float amount) { attributes.reduceGoldOnHit(amount); }
	protected void reduceGoldValue(float amount, boolean isFlat) { attributes.reduceGoldValue(amount, isFlat); }
	protected void reduceHasting(DamageType type, float amount) { attributes.reduceCDOnHit(type, amount); }
	protected void reduceMaxSpeed(DamageType type, float amount, boolean isFlat) { attributes.reduceMaxSpeed(type, amount, isFlat); }
	protected void reduceToughness(float amount, boolean isFlat) { attributes.reduceToughness(amount, isFlat);	}
	protected void slow(DamageType type, float amount) { attributes.slow(type, amount); }
	protected void snare(int duration) { attributes.snare(duration); } //TODO: May want to attach type if some creeps are immune to this
	protected void suppressDeathrattle(DamageType type, float modifier, int lifetime) { attributes.suppressDeathrattle(modifier, lifetime); }
	protected void suppressDisruption(DamageType type, float amount, boolean isFlat) { attributes.suppressDisruption(amount, isFlat); }
	protected void unslow(DamageType type, float amount) { attributes.unslow(type, amount); }
	protected void unsuppressDisruption(DamageType type, float amount, boolean isFlat) { attributes.unsuppressDisruption(amount, isFlat); }

	//Public getter methods
	protected float getCurrentSize() { return attributes.getCurrentSize(); }
	protected float getCurrentSpeed() { return attributes.getCurrentSpeed(); }
	protected float getCurrentHealthCost() { return attributes.getCurrentHealthCost(); }
	protected float getMaxHealth() { return attributes.getMaxHealth(); }
	protected float getX() { return hitBox.x; }
	protected float getY() { return hitBox.y; }
	protected boolean intersects(Circle area) { return hitBox.intersects(area); }
	protected boolean isFlying() { return attributes.isFlying(); }
	
	protected void disorient(int lifetime) { 
		if (attributes.disorient(lifetime)) {
			int temp = previousIndex;
			previousIndex = nextIndex;
			nextIndex = temp;
			updateDirection();
		}
	}
	
	protected void ground() {
		if (attributes.ground()) {
			Path newPath = levelManager.getPath(false);
			setLocation(levelManager.getVertexBelow(currentVertex));
			this.path = newPath;
		}
	}
	
	protected void addAffix(CreepType type) {
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

	

	protected boolean isDead() {
		return attributes.isDead();
	}

	protected boolean is(CreepType type) {
		return creepTypes.contains(type);
	}

	/**
	 * Sets the creep's location at the beginning of the path. This assumes that the path cannot be updated mid-round.
	 * @param path - The path that the creep will follow.
	 */
	protected void setPath(Path path) {
		this.path = path;
		setLocation(0);
	}
	
	protected void setPath(Path path, int currentIndex) {
		this.path = path;
		setLocation(currentIndex);
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

	protected int update() {
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
	
	protected void updateHitBox() {
		hitBox.x = currentVertex.getX() + xOff + 1;
		hitBox.y = currentVertex.getY() + yOff + 1;
	}

	
	
	
//	protected void setLocation(Creep c) {
//		//Update freshly spawned deathrattle creep with parent's path
//		this.currentVertex = c.currentVertex;
//		this.nextIndex = c.nextIndex;
//		//Creep can't be disoriented when they come out so we need to work around that
//		if (c.attributes.isDisoriented()) {
//			this.nextIndex = c.previousIndex;
//			this.previousIndex = c.nextIndex;
//			this.direction = c.direction.getOpposite();
//		} else {
//			this.nextIndex = c.nextIndex;
//			this.previousIndex = c.previousIndex;
//			this.direction = c.direction;
//		}
//		this.xOff = c.xOff;
//		this.yOff = c.yOff;
//		this.path = c.path;
//	}

	//Clones the attributes back to their default states. Does not clone effects on the creep.
	public Creep clone() {
		CreepBuilder.getInstance().begin();
		Creep c = CreepBuilder.getInstance().build();
		c.setAttributes(attributes.clone());
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
