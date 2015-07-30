package projectiles;

import java.util.ArrayList;

import projectileeffects.ProjectileEffect;

import creeps.Creep;
import levels.Level;
import towers.Tower;
import utilities.TrigHelper;

public class ProjectileChain extends Projectile implements TargetsCreep {
	Creep targetCreep;
	Creep[] chainedCreep;
	ArrayList<ArrayList<ProjectileEffect>> chainedEffects;
	ArrayList<ArrayList<ProjectileEffect>> chainedSplashEffects;
	/**
	 * This number includes the first creep hit
	 */
	int maxChains;
	float chainRadius;
	float chainPenalty;
	
	//TODO I think the speed of this projectile should be increased or is instantaneous
	protected ProjectileChain(int maxChains, float chainRadius, float chainPenalty) {
		super();
		this.maxChains = maxChains;
		this.chainRadius = chainRadius;
		this.chainPenalty = chainPenalty;
		chainedCreep = new Creep[maxChains];
	}
	
	public ProjectileChain(Tower parent, int maxChains, float chainRadius, float chainPenalty) {
		super(parent);
		chainedCreep = new Creep[maxChains];
		chainedEffects = new ArrayList<ArrayList<ProjectileEffect>>(maxChains);
		chainedSplashEffects = new ArrayList<ArrayList<ProjectileEffect>>(maxChains);
		chainedEffects.add(creepEffects);
		chainedSplashEffects.add(splashEffects);
		float currentPenalty = chainPenalty;
		ProjectileEffect e;
		for (int i = 1; i < maxChains; i++) {
			chainedEffects.add(new ArrayList<ProjectileEffect>(creepEffects.size()));
			for (int j = 0; j < creepEffects.size(); j++) {
				e = creepEffects.get(j).clone();
				e.modifier *= currentPenalty;
				chainedEffects.get(i).add(e);
			}
			chainedSplashEffects.add(new ArrayList<ProjectileEffect>(splashEffects.size()));
			for (int j = 0; j < splashEffects.size(); j++) {
				e = splashEffects.get(j).clone();
				e.modifier *= currentPenalty;
				chainedSplashEffects.get(i).add(e);
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
	public void detonate(Level level) {
		if (dud) {
			return;
		}
		int currentChains = 0;
		targetCreep.addAllEffects(creepEffects);
		for (Creep c: level.getOtherCreepInRange(targetCreep, splashRadius)) {
			c.addAllEffects(splashEffects);
		}
		chainedCreep[currentChains] = targetCreep;
		currentChains++;
		
		while (currentChains < maxChains) {
			Creep prev = chainedCreep[currentChains - 1];
			Creep c = level.getSingleCreepInRange(prev, chainRadius);
			if (c == null) {
				return;
			}
			c.addAllEffects(chainedEffects.get(currentChains));
			chainedCreep[currentChains] = c;
			//TODO at some point we may change the splashRadius to splashRadius * currentPenalty
			for (Creep splash: level.getOtherCreepInRange(c, splashRadius)) {
				splash.addAllEffects(chainedSplashEffects.get(currentChains));
			}
			currentChains++;
		}
	}
	
	@Override
	public Projectile clone() {
		ProjectileChain p = new ProjectileChain(maxChains, chainRadius, chainPenalty);
		p.dud = false;
		p.parent = parent;
		p.level = level;
		p.creepEffects = creepEffects;
		p.splashEffects = splashEffects;
		p.hitBox = hitBox;
		p.x = parent.centerX;
		p.y = parent.centerY;
		p.splashRadius = splashRadius;
		p.size = size;
		p.targetAngle = targetAngle;
		p.multiplier = multiplier;
		p.targetCreep = targetCreep;
		p.chainedEffects = chainedEffects;
		p.chainedSplashEffects = chainedSplashEffects;
		return p;
	}

	@Override
	public void setTargetCreep(Creep c) {
		targetCreep = c;
		//TODO the trig helper might not be needed here since these detonate instantly
		targetAngle = TrigHelper.angleBetween(x, y, targetCreep.hitBox.x, targetCreep.hitBox.y);
	}

	@Override
	public Creep getTargetCreep() {
		return targetCreep;
	}

	@Override
	public void update() {
		return;
	}
}
