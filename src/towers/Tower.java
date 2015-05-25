package towers;

import levels.Level;
import maps.Tile;
import creeps.Creep;
import creeps.ElementType;
import projectiles.Projectile;
import utilities.Circle;
import utilities.TrigHelper;

public abstract class Tower {
	public int attackCoolDown, currentAttackCoolDown; //Number of game ticks between tower shots, 0 for passive towers (beacons)

	public int x, y; //Top Left corner in Tile Coordinates
	public float centerX, centerY, range;
	public Circle targetArea;
	public int width;
	public int height;
	public int cost;
	
	public TargetingType targetingType;
	public boolean targetsCreep; //False for targeting a ground spot;
	public float targetX, targetY; //For ground spot target towers, in Tile coordinates
	public Creep targetCreep;
	public float targetAngle; //For animation and to pass to projectiles when fired, Radians, 0 = right, pi / 2 = up

	private Level level;
	
	public Tower tetheredTo;

	public TowerType type;
	
	abstract Projectile fireProjectile();
	
	public Tower(Level level, Tile topLeftTile, int width, int height, int attackCoolDown, boolean targetsCreep, float range){
		this.level = level;
		x = topLeftTile.x;
		y = topLeftTile.y;
		System.out.println("Tower built at " + x + " , " + y + " (TOP LEFT TILE)");
		centerX = x + width / 2f;
		centerY = y + height / 2f;
		this.width = width;
		this.height = height;
		this.attackCoolDown = attackCoolDown;
		this.targetsCreep = targetsCreep;
		this.range = range;
		targetArea = new Circle(centerX, centerY, range);
		this.targetingType = TargetingType.FIRST;
	}

	
	public void update(){
		currentAttackCoolDown--;
		if(targetsCreep){
			targetCreep = level.findTargetCreep(this);
		}
		if(targetCreep != null){
			updateAngle(targetCreep);
			if(currentAttackCoolDown < 1){
				level.addProjectile(fireProjectile());
				currentAttackCoolDown = attackCoolDown;
			}
		}
	}
	
	public void roundInit() {
		// TODO generate the projectile effects
		
	}

	private void updateAngle(Creep targetCreep) {
		targetAngle = TrigHelper.angleBetween(centerX, centerY, targetCreep.hitBox.x, targetCreep.hitBox.y);
	}

	
}
