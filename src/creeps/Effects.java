package creeps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import projectileeffects.Bleed;
import projectileeffects.ProjectileEffect;

final class Effects extends Attribute {
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
			effects.add(effect.clone()); //TODO: Why don't I just clone when I create the projectile?
		}
	}

	void addAllEffects(ArrayList<ProjectileEffect> effects) {
		for (ProjectileEffect e: effects) {
			addEffect(e);
		}
	}
	
	void consumeBleeds(float modifier) {
		ArrayList<ProjectileEffect> s = new ArrayList<ProjectileEffect>();
		for (Iterator<ProjectileEffect> iterator = effects.iterator(); iterator.hasNext();) {
			ProjectileEffect e = iterator.next();
			if (e instanceof Bleed) {
				s.add(((Bleed) e).convertToDamage(modifier));
				iterator.remove();
			}
		}
		addAllEffects(s);
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

	@Override
	Attribute clone(CreepAttributes parent) {
		//TODO: I don't think we want anything other than a return statement but we shall see
		return new Effects(parent);
	}
}
