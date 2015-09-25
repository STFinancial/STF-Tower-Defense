package levels;

import java.util.ArrayList;

import projectileeffects.ProjectileEffect;

import creeps.Creep;
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
		if (timing == 0) {
			lifetime = 0;
			counter = 0;
		} else {
			this.timing = timing;
		}
		this.effects = effects;
		this.area = new Circle(x, y, radius);
	}

	@Override
	public int update() {
		counter--;
		if (counter < 0) {
			return -1;
		} else if (timing != 0 && counter % timing == 0) {
			//apply the effect
			for (Creep c: level.getCreepInRange(area)) {
				c.addAllEffects(effects);
			}
			return 1;
		} else {
			//Do nothing
			return 0;
		}
	}
}
