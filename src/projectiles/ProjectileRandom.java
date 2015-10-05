package projectiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import creeps.Creep;
import projectileeffects.ProjectileEffect;
import towers.Tower;

public final class ProjectileRandom extends ProjectileBasic {
	private Random rand;
	private ArrayList<Effect> effects;
	private int sum;
	
	private ProjectileRandom(Tower parent, Projectile mold) {
		super(parent, mold);
		effects = new ArrayList<Effect>();
		sum = 0;
	}
	
	public ProjectileRandom(Tower parent) {
		super(parent);
		effects = new ArrayList<Effect>();
		sum = 0;
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
		HashSet<Creep> creeps = guider.getOtherCreepInSplashRange(targetCreep, splashRadius, parent.hitsAir || parent.splashHitsAir);
		creeps.add(targetCreep);
		ProjectileEffect e = getRandomEffect(); //We get a random effect from the list
		for (Creep c: creeps) {
			c.addEffect(e);
			c.onProjectileCollision();
		}
	}

	public void addEffect(ProjectileEffect effect, float weight) {
		Effect e = new Effect(effect, weight);
		for (int i = 0; i < weight; i++) {
			effects.add(e); //Can't think of a better way than adding shit to a list atm
		}
		sum += weight;
	}
	
	public void removeEffect(ProjectileEffect projectileEffect) {
		Iterator<Effect> iter = effects.iterator();
		while (iter.hasNext()) {
			Effect e = iter.next();
			if (e.effect.equals(projectileEffect)) {
				iter.remove();
			}
		}
	}
	
	private ProjectileEffect getRandomEffect() {
		return effects.get(rand.nextInt(sum)).effect;
	}
	
	private class Effect {
		//float weight;
		ProjectileEffect effect;
		
		Effect(ProjectileEffect effect, float weight) {
			//this.weight = weight;
			this.effect = effect;
		}
	}
}


