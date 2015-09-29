package creeps;

import java.util.ArrayList;
import java.util.List;

import levels.Updatable;

import projectileeffects.ProjectileEffect;

final class Deathrattle extends Attribute implements Updatable{
	//TODO: Needs some implementation and fleshing out
	private int deathrattleSuppressionTimer;
	private ProjectileEffect effect;
	private List<Creep> children;
	
	Deathrattle(CreepAttributes parent, ProjectileEffect effect, List<Creep> children) {
		this.parent = parent;
		//TODO: Should I do something to handle null values passed in if user chooses not to build deathrattle?
		this.effect = effect;
		if (children == null) {
			children = new ArrayList<Creep>();
		}
		this.children = children;
		this.deathrattleSuppressionTimer = 0;
	}
	
	boolean hasDeathrattleEffect() { return effect != null; }
	boolean hasDeathrattleChildren() { return children != null; }
	
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
		//TODO: Need to be very careful to avoid circular dependencies here and cloning in circles. How can we avoid this other than avoiding user error?
		for (Creep c: children) {
			newChildren.add(c.clone());
		}
		return new Deathrattle(parent, effect.clone(), newChildren);
	}
}
