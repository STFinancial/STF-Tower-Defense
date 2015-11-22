package projectileeffects;

import java.util.HashSet;

import creeps.Creep;
import creeps.DamageType;
import levels.LevelManager;
import projectiles.Projectile;
import projectiles.ProjectileManager;

public class Detonation extends ProjectileEffect {
	public Detonation(float modifier, DamageType damageType, Projectile parent) {
		super(0, modifier, 0, damageType, parent, false); //TODO: Do we want to set a range here so that we can affect it with quality?
	}

	@Override
	protected void applyEffect() {
		//TODO: Optimization - For this and many methods we call the getters on the parents many times rather than once when the effects are created.
		HashSet<Creep> inRange = LevelManager.getInstance().getCreepAdjacentToEarth(projManager.hitsAir(parent)); // Maybe there should be a separate variable for that, who knows.
		float percentResistPen = projManager.getResistPen(parent, damageType, false);
		float flatResistPen = projManager.getResistPen(parent, damageType, true);
		boolean ignoresShield = projManager.ignoresShield(parent);
		float shieldDrainModifier = projManager.getShieldDrainModifier(parent);
		float percentToughPen = projManager.getToughPen(parent, false);
		float flatToughPen = projManager.getToughPen(parent, true);
		for (Creep c: inRange) {
			creepManager.damage(c, damageType, modifier, percentResistPen, flatResistPen, ignoresShield, shieldDrainModifier, percentToughPen, flatToughPen);
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
