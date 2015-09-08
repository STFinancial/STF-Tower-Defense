package levels;

import java.util.ArrayList;

import creeps.Creep;
import creeps.ProjectileEffect;
import utilities.Circle;


public class EffectPatch implements Updatable {
	private int lifetime;
	private int timing;
	private int counter;
	private Circle area;
	private ArrayList<ProjectileEffect> effects;
	private Level level;
	
	public EffectPatch(int lifetime, int timing, float x, float y, float radius, ArrayList<ProjectileEffect> effects, Level level) {
		this.lifetime = lifetime;
		this.counter = lifetime;
		this.timing = timing;
		this.effects = effects;
		this.area = new Circle(x, y, radius);
	}

	@Override
	public int update() {
		counter--;
		//TODO: Optimization - Can avoid this check by ensuring timing is not zero earlier
		//To protect against modding by zero
		if (timing == 0 || counter % timing == 0) {
			for (Creep c: level.getCreepInRange(area)) {
				for (ProjectileEffect e: effects) {
					c.addEffect(e.clone());
				}
			}
		}
		return 0;
	}
	
	public boolean isDone() {
		return counter >= lifetime;
	}
}
