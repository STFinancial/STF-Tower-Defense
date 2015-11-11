package projectileeffects;

import java.util.HashSet;

import creeps.Creep;
import creeps.DamageType;

import projectiles.Projectile;
import projectiles.ProjectileManager;

public class Detonation extends ProjectileEffect {
	public Detonation(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent); //TODO: Do we want to set a range here so that we can affect it with quality?
	}

	@Override
	protected void applyEffect() {
		//TODO: Optimization - For this and many methods we call the getters on the parents many times rather than once when the effects are created.
		HashSet<Creep> inRange = ProjectileManager.getInstance().getCreepAdjacentToEarth(parent.hitsAir()); // Maybe there should be a separate variable for that, who knows.
		for (Creep c: inRange) {
			c.damage(damageType, modifier, parent.getResistPen(damageType, false), parent.getResistPen(damageType, true), parent.ignoresShield(), parent.getShieldDrainModifier(), parent.getToughPen(false), parent.getToughPen(true));
		}
	}

	@Override
	public void onExpire() {
		return;
	}

	@Override
	public ProjectileEffect clone() {
		return new Detonation(modifier, damageType, parent);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

}
