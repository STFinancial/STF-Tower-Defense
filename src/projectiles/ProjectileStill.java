package projectiles;

import creeps.Creep;
import towers.Tower;
import levels.EffectPatch;
import levels.Level;
import projectileeffects.ProjectileEffect;

public class ProjectileStill extends ProjectileBasic {
	private int lifetime;
	private int timing;
	private float patchRadius;
	private ProjectileEffect effect;
	
	protected ProjectileStill() {}
	
	public ProjectileStill(Tower parent, int lifetime, int timing, float patchRadius, ProjectileEffect effect) {
		super(parent);
		this.lifetime = lifetime;
		this.timing = timing;
		this.effect = effect;
	}
	
	@Override
	public Projectile clone() {
		ProjectileStill p = new ProjectileStill();
		cloneStats(p);
		p.targetCreep = targetCreep;
		p.lifetime = lifetime;
		p.timing = timing;
		p.effect = effect;
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
			level.addEffectPatch(new EffectPatch(lifetime, timing, this.x, this.y, this.patchRadius, effect));
		}
	}

}
