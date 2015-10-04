package towers;

import java.util.ArrayList;

import creeps.DamageType;

import projectileeffects.MaxHealthDamage;
import projectileeffects.Nullify;
import projectileeffects.ProjectileEffect;
import projectiles.ProjectileBasic;
import projectiles.ProjectileEffectPatch;
import projectiles.ProjectilePassThroughTarget;
import levels.Level;
import maps.Tile;

public class TowerFireWater extends Tower {
	private int patchLifetime;
	private int patchTiming;
	private float patchMaxHealth;
	
	public TowerFireWater(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
		this.patchLifetime = 30;
		this.patchTiming = 5;
		this.patchMaxHealth = 0.00005f;
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			baseProjectile = new ProjectilePassThroughTarget(this);
		} else if (progress[1][2]) {
			ArrayList<ProjectileEffect> effects = new ArrayList<ProjectileEffect>();
			effects.add(new MaxHealthDamage(patchMaxHealth * damageArray[DamageType.WATER.ordinal()], DamageType.WATER, baseProjectile));
			effects.add(new MaxHealthDamage(patchMaxHealth * damageArray[DamageType.FIRE.ordinal()], DamageType.FIRE, baseProjectile));
			if (progress[1][3]) {
				effects.add(new Nullify(DamageType.WATER, baseProjectile));
			}
			baseProjectile = new ProjectileEffectPatch(this, patchLifetime, patchTiming, splashRadius, effects);
		} else {
			baseProjectile = new ProjectileBasic(this);
		}
		if (progress[0][2]) {
			baseProjectile.ignoresShield = true;
			for (int i = 0; i < baseProjectile.resistPenPercent.length; i++) {
				baseProjectile.resistPenPercent[i] = 1;
			}
			baseProjectile.toughPenPercent = 1;
		}
		
	}

	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			level.addProjectile(fireProjectile()); //TODO use the projectile guider
			attackCarryOver += 1 - currentAttackCooldown;
			currentAttackCooldown = attackCooldown;
			if (attackCarryOver > 1) {
				attackCarryOver -= 1;
				currentAttackCooldown--;
			}
			return 1;
		}
		return 0;
	}

}
