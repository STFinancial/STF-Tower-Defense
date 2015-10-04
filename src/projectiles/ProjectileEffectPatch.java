package projectiles;

import java.util.ArrayList;

import projectileeffects.ProjectileEffect;

import creeps.Creep;
import towers.Tower;
import levels.EffectPatch;

public final class ProjectileEffectPatch extends ProjectileBasic {
	private int lifetime;
	private int timing;
	private float patchRadius;
	private ArrayList<ProjectileEffect> effects;
	
	private ProjectileEffectPatch(Tower parent, Projectile mold, int lifetime, int timing, float patchRadius, ArrayList<ProjectileEffect> effects) {
		super(parent, mold);
		this.lifetime = lifetime;
		this.timing = timing;
		this.patchRadius = patchRadius;
		this.effects = effects;
	}
	
	public ProjectileEffectPatch(Tower parent, int lifetime, int timing, float patchRadius, ArrayList<ProjectileEffect> effects) {
		super(parent);
		this.lifetime = lifetime;
		this.timing = timing;
		this.patchRadius = patchRadius;
		this.effects = effects;
	}
	
	@Override
	public Projectile clone() {
		ProjectileEffectPatch p = new ProjectileEffectPatch(parent, this, lifetime, timing, patchRadius, effects);
		p.targetCreep = targetCreep;
		return p;
	}

	@Override
	public void detonate() {
		if (dud) {
			return;
		}
		targetCreep.addAllEffects(creepEffects);
		for (Creep c: guider.getOtherCreepInSplashRange(targetCreep, splashRadius, parent.hitsAir)) {
			c.addAllEffects(splashEffects);
		}
		if (patchRadius > 0) {
			level.addEffectPatch(new EffectPatch(lifetime, timing, this.x, this.y, this.patchRadius, effects));
		}
	}

}
