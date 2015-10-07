package towers;

import java.util.ArrayList;

import creeps.Creep;
import creeps.DamageType;

import projectileeffects.MaxHealthDamage;
import projectileeffects.Nullify;
import projectileeffects.ProjectileEffect;
import projectiles.ProjectileBasic;
import projectiles.ProjectileEffectPatch;
import projectiles.ProjectilePassThroughTarget;
import utilities.GameConstants;
import levels.Level;
import maps.Tile;

public final class TowerFireWater extends Tower {
	float passThroughRadiusModifier;
	float passThroughModifier;
	
	int patchLifetime;
	int patchTiming;
	float patchMaxHealthModifier;
	
	boolean doesSplash;
	
	public TowerFireWater(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.FIRE_WATER, towerID);
		
		this.passThroughRadiusModifier = 0;
		this.passThroughModifier = 0;
		
		this.patchLifetime = 0;
		this.patchTiming = 0;
		this.patchMaxHealthModifier = 0;
		
		this.hitsAir = false;
		this.doesSplash = false;
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			baseProjectile = new ProjectilePassThroughTarget(this, passThroughRadiusModifier * splashRadius, passThroughModifier, doesSplash, 1);
		} else if (progress[1][2]) {
			ArrayList<ProjectileEffect> effects = new ArrayList<ProjectileEffect>();
			effects.add(new MaxHealthDamage(patchMaxHealthModifier * damageArray[DamageType.WATER.ordinal()], DamageType.WATER, baseProjectile));
			effects.add(new MaxHealthDamage(patchMaxHealthModifier * damageArray[DamageType.FIRE.ordinal()], DamageType.FIRE, baseProjectile));
			if (progress[1][3]) {
				effects.add(new Nullify(DamageType.WATER, baseProjectile));
			}
			baseProjectile = new ProjectileEffectPatch(this, patchLifetime, patchTiming, splashRadius, effects);
		} else {
			baseProjectile = new ProjectileBasic(this);
		}
		if (progress[0][2]) {
			baseProjectile.setIgnoresShield(true);
			for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
				baseProjectile.setResistPenPercent(DamageType.values()[i], 1);
			}
			baseProjectile.setToughPenPercent(1);
		}
	}

	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = guider.findTargetCreep(this, hitsAir);
			if (targetCreep != null) {
				((ProjectileBasic) baseProjectile).setTargetCreep(targetCreep);
				level.addProjectile(fireProjectile());
				attackCarryOver += 1 - currentAttackCooldown;
				currentAttackCooldown = attackCooldown;
				if (attackCarryOver > 1) {
					attackCarryOver -= 1;
					currentAttackCooldown--;
				}
			}
			return 1;
		}
		return 0;
	}
}
