package levels;

import java.util.ArrayList;

import creeps.Creep;
import projectileeffects.ProjectileEffect;
import utilities.Circle;


public class EffectPatch implements Updatable {
	private int lifetime;
	private int timing;
	private int counter = 0;
	private Circle area;
	private ArrayList<ProjectileEffect> effects;
	private Level level;
	
	public EffectPatch(int lifetime, int timing, float x, float y, float radius, ArrayList<ProjectileEffect> effects, Level level) {
		this.lifetime = lifetime;
		this.timing = timing;
		this.effects = effects;
		this.area = new Circle(x, y, radius);
	}

	@Override
	public void update() {
		if (counter % timing == 0) {
			for (Creep c: level.getCreepInRange(area)) {
				c.addAllEffects(effects);
			}
		}
	}
	
	public boolean isDone() {
		return counter >= lifetime;
	}
}
