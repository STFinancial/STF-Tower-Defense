package creeps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import projectileeffects.ProjectileEffect;
import utilities.Circle;

final class Deathrattle extends Attribute {
	private int deathrattleSuppressionTimer;
	private List<DeathrattleEffect> effects;
	private List<Creep> children;
	
	Deathrattle(CreepAttributes parent, List<DeathrattleEffect> effects, List<Creep> children) {
		this.parent = parent;
		if (effects == null) {
			effects = new LinkedList<DeathrattleEffect>();
		}
		this.effects = effects;
		if (children == null) {
			children = new LinkedList<Creep>();
		}
		this.children = children;
		this.deathrattleSuppressionTimer = -1;
	}
	
	
	boolean hasDeathrattleEffect() { return effects != null && effects.size() != 0; }
	boolean hasDeathrattleChildren() { return children != null && children.size() != 0; }
	
	void addDeathrattleChild(Creep child) {
		children.add(child);
	}
	
	void addDeathrattleEffect(ProjectileEffect effect, Circle area, boolean hitsAir) {
		effects.add(new DeathrattleEffect(effect, area, -1, hitsAir));
	}
	
	void addDeathrattleEffect(ProjectileEffect effect, Circle area, int duration, boolean hitsAir) {
		effects.add(new DeathrattleEffect(effect, area, duration, hitsAir));
	}
	
	List<Creep> onDeath() {
		if (deathrattleSuppressionTimer < 0) {
			for (DeathrattleEffect e: effects) { //TODO: Do I want to use the defaultDuration?
				e.applyEffect(1); //TODO: Could be 1 - suppression modifier or something
			}
			return children;
		}
		return new ArrayList<Creep>(1);
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
