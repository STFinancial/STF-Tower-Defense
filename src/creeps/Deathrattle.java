package creeps;

import java.util.ArrayList;
import java.util.List;

import levels.Updatable;

import projectileeffects.ProjectileEffect;

final class Deathrattle extends Attribute implements Updatable{
	//TODO: Needs some implementation and fleshing out
	private int deathrattleSuppressionTimer;
	private List<ProjectileEffect> effects;
	private List<Creep> children;
	
	Deathrattle(CreepAttributes parent, List<ProjectileEffect> effects, List<Creep> children) {
		this.parent = parent;
		//TODO: Should I do something to handle null values passed in if user chooses not to build deathrattle?
		if (effects == null) {
			effects = new ArrayList<ProjectileEffect>();
		}
		this.effects = effects;
		if (children == null) {
			children = new ArrayList<Creep>();
		}
		this.children = children;
		this.deathrattleSuppressionTimer = 0;
	}
	
	boolean hasDeathrattleEffect() { return effects != null || effects.size() != 0; }
	boolean hasDeathrattleChildren() { return children != null || children.size() != 0; }
	
	void addDeathrattleEffect(ProjectileEffect effect, int duration) {
		//TODO: Something with the duration, maybe a pair class for an effect and its duration
		if (duration < 0) {
			effects.add(effect);
		} else {
			
		}
	}
	
	void suppressDeathrattle(float modifier, int lifetime) {
		//TODO: Something with the modifier?
		if (lifetime > deathrattleSuppressionTimer) {
			deathrattleSuppressionTimer = lifetime;
		}
	}

	@Override
	public int update() {
		return (--deathrattleSuppressionTimer < 0 ? -1 : 1);
	}

	@Override
	Attribute clone(CreepAttributes parent) {
		ArrayList<Creep> newChildren = new ArrayList<Creep>(children.size());
		ArrayList<ProjectileEffect> newEffects = new ArrayList<ProjectileEffect>(effects.size());
		//TODO: Need to be very careful to avoid circular dependencies here and cloning in circles. How can we avoid this other than avoiding user error?
		for (Creep c: children) {
			newChildren.add(c.clone());
		}
		for (ProjectileEffect e: effects) {
			newEffects.add(e.clone()); //TODO: Not actually sure if I should be cloning here. I think I just want to reference the effects but I'm not sure
		}
		return new Deathrattle(parent, newEffects, newChildren);
	}
}
