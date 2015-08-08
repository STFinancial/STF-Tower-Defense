package levels;

import projectileeffects.ProjectileEffect;
import utilities.Circle;


public class EffectPatch implements Updatable {
	private int lifetime;
	private int timing;
	private Circle area;
	private ProjectileEffect effect;
	
	public EffectPatch(int lifetime, int timing, float x, float y, float radius, ProjectileEffect effect) {
		this.lifetime = lifetime;
		this.timing = timing;
		this.effect = effect;
		this.area = new Circle(x, y, radius);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
