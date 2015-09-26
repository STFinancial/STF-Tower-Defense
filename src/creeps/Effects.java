package creeps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import projectileeffects.ProjectileEffect;
import levels.Updatable;

final class Effects extends Attribute implements Updatable {
	private HashSet<ProjectileEffect> effects;
	
	Effects(CreepAttributes parent) { 
		this.parent = parent;
		effects = new HashSet<ProjectileEffect>(); 
	}
	
	void addEffect(ProjectileEffect effect) {
		if (effects.contains(effect)) {
			getEquivalent(effect).onApply();
		} else {
			effect.onApply();
			effects.add(effect.clone());
		}
	}

	void addAllEffects(ArrayList<ProjectileEffect> effects) {
		for (ProjectileEffect e: effects) {
			addEffect(e);
		}
	}
	
	private ProjectileEffect getEquivalent(ProjectileEffect pe) {
		for (ProjectileEffect e: effects) {
			if (e.equals(pe)) {
				return e;
			}
		}
		return null;
	}

	@Override
	public int update() {
		Iterator<ProjectileEffect> i = effects.iterator();
		while (i.hasNext()) {
			ProjectileEffect e = i.next();
			int updateVal = e.update();
			switch (updateVal) {
			case 1:
				break;
			case 0:
				break;
			case -1:
				i.remove();
				break;
			default:
				System.out.println("Shouldn't be reached, Effects.update()");
				break;
			}
		}
		return 0;
	}
}
