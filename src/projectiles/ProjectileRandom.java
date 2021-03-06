package projectiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import creeps.Creep;
import projectileeffects.ProjectileEffect;
import towers.Tower;
import utilities.GameConstants;

public final class ProjectileRandom extends ProjectileBasic {
	private Random rand;
	private ArrayList<Effect> effects;
	private int sum;
	
	private ProjectileRandom(Tower parent, Projectile mold) {
		super(parent, mold);
		effects = new ArrayList<Effect>();
		sum = 0;
		speed = currentSpeed = GameConstants.BASE_SPEED_PROJECTILE_RANDOM;
	}
	
	public ProjectileRandom(Tower parent) {
		super(parent);
		effects = new ArrayList<Effect>();
		sum = 0;
		speed = currentSpeed = GameConstants.BASE_SPEED_PROJECTILE_RANDOM;
	}
	
	@Override
	public ProjectileRandom clone() {
		ProjectileRandom p = new ProjectileRandom(parent, this);
		p.effects = effects;
		p.sum = sum;
		p.rand = rand;
		return p;
	}
	
	@Override
	public void detonate() {
		if (dud) {
			return;
		}
		if (doesSplash) {
			HashSet<Creep> creeps = creepManager.getOtherCreepInSplashRange(targetCreep, splashRadius, splashHitsAir);
			creeps.add(targetCreep);
			ArrayList<ProjectileEffect> e = getRandomEffects(); //We get a random effect from the list
			if (doesOnHit) {
				for (Creep c: creeps) {
					creepManager.addAllEffects(c, e);
					creepManager.onProjectileCollision(c);		
				}
			} else {
				for (Creep c: creeps) {
					creepManager.addAllEffects(c, e);		
				}
			}
		} else {
			if (doesOnHit) {
				creepManager.addAllEffects(targetCreep, getRandomEffects());
				creepManager.onProjectileCollision(targetCreep);
			} else {
				creepManager.addAllEffects(targetCreep, getRandomEffects());
			}
		}
		
	}

	public void addEffect(ArrayList<ProjectileEffect> effects, float weight) {
		Effect e = new Effect(effects, weight);
		for (int i = 0; i < weight; i++) {
			this.effects.add(e); //Can't think of a better way than adding shit to a list atm
		}
		sum += weight;
	}
	
	public void removeEffect(HashSet<ProjectileEffect> projectileEffect) {
		Iterator<Effect> iter = effects.iterator();
		while (iter.hasNext()) {
			Effect e = iter.next();
			if (e.effects.equals(projectileEffect)) {
				iter.remove();
			}
		}
	}
	
	private ArrayList<ProjectileEffect> getRandomEffects() {
		return effects.get(rand.nextInt(sum)).effects;
	}
	
	private class Effect {
		//float weight;
		ArrayList<ProjectileEffect> effects;
		
		Effect(ArrayList<ProjectileEffect> effects, float weight) {
			//this.weight = weight;
			this.effects = effects;
		}
	}
}


