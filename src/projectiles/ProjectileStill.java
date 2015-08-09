package projectiles;

import java.util.ArrayList;

import creeps.Creep;
import towers.Tower;
import levels.EffectPatch;
import levels.Level;
import projectileeffects.ProjectileEffect;

public class ProjectileStill extends ProjectileBasic {
	private int lifetime;
	private int timing;
	private float patchRadius;
	private ArrayList<ProjectileEffect> effects;
	
	protected ProjectileStill() {}
	
	public ProjectileStill(Tower parent, int lifetime, int timing, float patchRadius, ArrayList<ProjectileEffect> effects) {
		super(parent);
		this.lifetime = lifetime;
		this.timing = timing;
		this.effects = effects;
	}
	
	@Override
	public Projectile clone() {
		ProjectileStill p = new ProjectileStill();
		cloneStats(p);
		p.targetCreep = targetCreep;
		p.lifetime = lifetime;
		p.timing = timing;
		p.effects = effects;
		//this is only safe because we clone immediately before we fire
		return p;
	}

	@Override
	public void detonate(Level level) {
		if (dud) {
			return;
		}
		targetCreep.addAllEffects(creepEffects);
		for (Creep c: level.getOtherCreepInSplashRange(targetCreep, splashRadius)) {
			c.addAllEffects(splashEffects);
		}
		if (patchRadius > 0) {
			level.addEffectPatch(new EffectPatch(lifetime, timing, this.x, this.y, this.patchRadius, effects, level));
		}
	}

}
