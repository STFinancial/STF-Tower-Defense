package projectiles;

import levels.Level;
import towers.Tower;
import utilities.Circle;
import utilities.TrigHelper;

//Stops at the first target it hits on the way to its destination
public class ProjectileBeam extends Projectile implements TargetsArea {
	//TODO will have to add stuff so this animates better later
	//TODO This isn't working correctly, needs to stop at the first thing it hits
	private Circle targetArea;
	
	public ProjectileBeam(Tower parent, float targetAreaRadius) {
		super(parent);
		this.targetArea = new Circle(parent.x, parent.y, targetAreaRadius);
		this.speed = 0;
		this.currentSpeed = 0;
		this.targetAngle = 0;
	}
	
	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public boolean setTargetArea(float x, float y) {
		targetArea.x = x;
		targetArea.y = y;
		targetAngle = TrigHelper.angleBetween(this.x, this.y, x, y);
		return true;
	}

	@Override
	public Projectile clone() {
		ProjectileBeam p = new ProjectileBeam(parent, targetArea.radius);
		cloneStats(p);
		p.targetArea = targetArea.clone();
		return p;
	}

	@Override
	public int update() {
		guider.getFirstCreepRadially(x, y, targetAngle);
	}

	@Override
	public void detonate(Level level) {
		// TODO Auto-generated method stub
		
	}
}
