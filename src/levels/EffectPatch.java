package levels;

import java.util.ArrayList;

import projectileeffects.ProjectileEffect;
import creeps.Creep;
import creeps.CreepManager;
import utilities.Circle;

public class EffectPatch {
	//private int lifetime;
	private int timing;
	private int counter;
	private Circle area;
	private ArrayList<ProjectileEffect> effects;
	private CreepManager creepManager = CreepManager.getInstance();
	//private Level level;
	
	public EffectPatch(int lifetime, int timing, float x, float y, float radius, ArrayList<ProjectileEffect> effects) {
		//this.lifetime = lifetime;
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
	
	public boolean isDone() {
		return counter < 0;
	}

	public void update() {
		counter--;
		if (counter < 0) {
			return;
		} else if (timing != 0 && counter % timing == 0) {
			//apply the effect
			for (Creep c: creepManager.getCreepInRange(area, false)) {
				creepManager.addAllEffects(c, effects);
			}
			return;
		}
	}
}
