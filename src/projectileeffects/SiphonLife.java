package projectileeffects;

import creeps.DamageType;
import levels.LevelManager;
import projectiles.Projectile;

public final class SiphonLife extends ProjectileEffect {
	private float goldModifier;
	
	public SiphonLife(float modifier, DamageType damageType, Projectile parent, float goldModifier) {
		super(0, modifier, 0, damageType, parent, false);
		this.goldModifier = goldModifier;
	}

	@Override
	public ProjectileEffect clone() {
		return new SiphonLife(modifier, damageType, parent, goldModifier);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

	@Override
	protected void applyEffect() {
		float maxHealth = creepManager.getMaxHealth(creep);
		creepManager.damage(creep, damageType, maxHealth * modifier, projManager.getResistPen(parent, damageType, false), 
				projManager.getResistPen(parent, damageType, true), projManager.ignoresShield(parent), 
				projManager.getShieldDrainModifier(parent), projManager.getToughPen(parent, false), projManager.getToughPen(parent, true));
		LevelManager.getInstance().addGold(maxHealth * goldModifier);
	}

	@Override
	public void onExpire() {
		return;
	}
}
