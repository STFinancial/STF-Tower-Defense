package creeps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import levels.Updatable;

import projectileeffects.ProjectileEffect;
import utilities.Circle;

final class Deathrattle extends Attribute implements Updatable {
	private int deathrattleSuppressionTimer;
	private List<DeathrattleEffect> effects;
	private List<Creep> children;
	
	Deathrattle(CreepAttributes parent, List<DeathrattleEffect> effects, List<Creep> children) {
		this.parent = parent;
		if (effects == null) {
			effects = new ArrayList<DeathrattleEffect>();
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
	
	void addDeathrattleEffect(ProjectileEffect effect, Circle area) {
		effects.add(new DeathrattleEffect(effect, area, -1));
	}
	
	void addDeathrattleEffect(ProjectileEffect effect, Circle area, int duration) {
		effects.add(new DeathrattleEffect(effect, area, duration));
	}
	
	void onDeath() {
		//TODO:
		
	}
	
	void suppressDeathrattle(float modifier, int lifetime) {
		//TODO: Something with the modifier?
		if (lifetime > deathrattleSuppressionTimer) {
			deathrattleSuppressionTimer = lifetime;
		}
	}

	@Override
	public int update() {
		Iterator<DeathrattleEffect> i = effects.iterator();
		DeathrattleEffect d;
		while (i.hasNext()) {
			d = i.next();
			if (d.update() == -1) {
				i.remove();
			}
		}
		return (--deathrattleSuppressionTimer < 0 ? -1 : 1);
	}

	@Override
	Attribute clone(CreepAttributes parent) {
		//TODO: Should consider cloning deathrattle creeps that does a clone of the entire deathrattle tree
		return new Deathrattle(parent, null, null); //This is to avoid circular dependencies
	}
}
