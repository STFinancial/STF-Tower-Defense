package projectiles;

import java.util.ArrayList;

import projectileeffects.ProjectileEffect;

import creeps.Creep;
import towers.Tower;

public final class ProjectileChain extends Projectile implements TargetsCreep {
	private Creep targetCreep;
	private ArrayList<Creep> chainedCreep;
	private ArrayList<ArrayList<ProjectileEffect>> chainedEffects;
	private ArrayList<ArrayList<ProjectileEffect>> chainedSplashEffects;
	/**
	 * This number includes the first creep hit
	 */
	private int maxChains;
	private float chainRadius;
	private float chainPenalty;
	private boolean noDuplicates;
	
	private ProjectileChain(Tower parent, Projectile mold, int maxChains, float chainRadius, float chainPenalty, boolean noDuplicates) {
		super(parent, mold);
		this.maxChains = maxChains;
		this.chainRadius = chainRadius;
		this.chainPenalty = chainPenalty;
		this.noDuplicates = noDuplicates;
		this.chainedCreep = new ArrayList<Creep>(maxChains);
	}
	
	//TODO I think the speed of this projectile should be increased or is instantaneous
	public ProjectileChain(Tower parent, int maxChains, float chainRadius, float chainPenalty, boolean noDuplicates) {
		super(parent);
		this.maxChains = maxChains;
		this.chainRadius = chainRadius;
		this.chainPenalty = chainPenalty;
		this.noDuplicates = noDuplicates;
		this.chainedCreep = new ArrayList<Creep>(maxChains);
		this.chainedEffects = new ArrayList<ArrayList<ProjectileEffect>>(maxChains);
		this.chainedSplashEffects = new ArrayList<ArrayList<ProjectileEffect>>(maxChains);
		this.chainedEffects.add(creepEffects);
		this.chainedSplashEffects.add(splashEffects);
		float currentPenalty = chainPenalty;
		for (int i = 1; i < maxChains; i++) {
			chainedEffects.add(new ArrayList<ProjectileEffect>(creepEffects.size()));
			for (int j = 0; j < creepEffects.size(); j++) {
				chainedEffects.get(i).add(creepEffects.get(j).clone().applyPenalty(currentPenalty));
			}
			chainedSplashEffects.add(new ArrayList<ProjectileEffect>(splashEffects.size()));
			for (int j = 0; j < splashEffects.size(); j++) {
				chainedSplashEffects.get(i).add(splashEffects.get(j).clone().applyPenalty(currentPenalty));
			}
			currentPenalty *= chainPenalty;
		}
		this.maxChains = maxChains;
		this.chainRadius = chainRadius;
		this.chainPenalty = chainPenalty;
	}
	
	@Override
	public boolean isDone() {
		return true;
	}
	
	@Override
	public void detonate() {
		if (dud) {
			return;
		}
		int currentChains = 0;
		targetCreep.addAllEffects(creepEffects);
		for (Creep c: guider.getOtherCreepInSplashRange(targetCreep, splashRadius, parent.hitsAir)) {
			c.addAllEffects(splashEffects);
		}
		chainedCreep.add(targetCreep);
		currentChains++;
		
		while (currentChains < maxChains) {
			Creep prevCreep = chainedCreep.get(currentChains - 1);
			Creep newCreep;
			if (noDuplicates) {
				newCreep = guider.getSingleCreepInRange(prevCreep, chainRadius, chainedCreep, parent.hitsAir);
			} else {
				newCreep = guider.getSingleCreepInRange(prevCreep, chainRadius, null, parent.hitsAir);
			}
			
			if (newCreep == null) {
				return;
			}
			newCreep.addAllEffects(chainedEffects.get(currentChains));
			chainedCreep.add(newCreep);
			//TODO at some point we may change the splashRadius to splashRadius * currentPenalty
			for (Creep splash: guider.getOtherCreepInSplashRange(newCreep, splashRadius, parent.hitsAir || parent.splashHitsAir)) {
				splash.addAllEffects(chainedSplashEffects.get(currentChains));
			}
			currentChains++;
		}
	}
	
	@Override
	public Projectile clone() {
		ProjectileChain p = new ProjectileChain(parent, this, maxChains, chainRadius, chainPenalty, noDuplicates);
		p.targetCreep = targetCreep;
		p.chainedEffects = chainedEffects;
		p.chainedSplashEffects = chainedSplashEffects;
		return p;
	}

	@Override
	public void setTargetCreep(Creep c) {
		targetCreep = c;
		updateAngle();
	}

	@Override
	public Creep getTargetCreep() {
		return targetCreep;
	}

	@Override
	public int update() {
		return 0;
	}
}
