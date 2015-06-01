package towers;

import levels.Level;
import maps.Tile;
import creeps.Creep;
import creeps.DamageType;
import projectiles.Projectile;
import projectiles.ProjectileEffect;
import utilities.Circle;
import utilities.TrigHelper;

public abstract class Tower {
	//Positional Details
	protected Level level;
	public int x, y; //Top Left corner in Tile Coordinates
	public float centerX, centerY;
	public Circle targetArea;
	public int width;
	public int height;
	public int cost;

	//Targeting Details
	public TargetingType targetingType;
	public boolean targetsCreep; //False for targeting a ground spot;
	public float targetX, targetY; //For ground spot target towers, in Tile coordinates
	public Creep targetCreep;
	public float targetAngle; //For animation and to pass to projectiles when fired, Radians, 0 = right, pi / 2 = up

	//Misc.
	public Tower tetheredTo;
	public Projectile baseProjectile;

	//Attributes
	public TowerType type;
	public String name;
	public int physicalDamage;
	public int magicDamage;
	public int fireRate; //Still deciding if this will actually be the same as attack cooldown
	public float damageSplash;
	public float effectSplash;
	public float splashRadius;
	public boolean hitsAir;
	public boolean hitsGround;
	public float range;
	public float slow;
	public int attackCoolDown;
	public int currentAttackCoolDown; //Number of game ticks between tower shots, 0 for passive towers (beacons)
	public int slowDuration;

	abstract Projectile fireProjectile();

	public Tower(Level level, Tile topLeftTile, boolean targetsCreep, int width, int height) {
		this.level = level;
		this.width = width;
		this.height = height;
		x = topLeftTile.x;
		y = topLeftTile.y;
		centerX = x + width / 2f;
		centerY = y + height / 2f;
		targetArea = new Circle(centerX, centerY, range);
		this.targetsCreep = targetsCreep;
		this.targetingType = TargetingType.FIRST;
	}

	public void update() {
		currentAttackCoolDown--;
		if (targetsCreep) {
			targetCreep = level.findTargetCreep(this);
		}
		if (targetCreep != null) {
			updateAngle(targetCreep);
			if (currentAttackCoolDown < 1) {
				level.addProjectile(fireProjectile());
				currentAttackCoolDown = attackCoolDown;
			}
		}
	}

	public abstract void roundInit();

	//this should be called on any time we make changes to the tower
	public void updateProjectile() {
		//TODO make this method
		//if ()
	}

	protected void updateAngle(Creep targetCreep) {
		targetAngle = TrigHelper.angleBetween(centerX, centerY, targetCreep.hitBox.x, targetCreep.hitBox.y);
	}

	protected Projectile duplicateProjectile(Projectile p) {
		Projectile newProj = new Projectile(this);
		//TODO for now we are just going to reference the effects of the baseProjecile
		newProj.effects.addAll(p.effects);
		newProj.currentSpeed = newProj.speed = p.speed; //TODO this logic might need to change if we have projectiles that speed up
		newProj.splashRadius = p.splashRadius;
		newProj.damageSplash = p.damageSplash;
		newProj.effectSplash = p.effectSplash;
		newProj.parent = this;
		return newProj;
	}

	public String toString() {
		return "Type: " + type + " at " + x + " , " + y;
	}
}
