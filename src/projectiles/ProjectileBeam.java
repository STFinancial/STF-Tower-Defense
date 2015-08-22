package projectiles;

import towers.Tower;

public class ProjectileBeam extends ProjectileBasic {
	//TODO will have to add stuff so this animates better later
	
	public ProjectileBeam(Tower parent) {
		super(parent);
		this.speed = 0;
		this.currentSpeed = 0;
	}
	
	@Override
	public boolean isDone() {
		return true;
	}
}
