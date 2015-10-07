package towers;

import java.util.ArrayList;

import creeps.DamageType;
import levels.Level;
import maps.Tile;
import projectileeffects.Damage;
import projectileeffects.Explosion;
import projectileeffects.ProjectileEffect;
import projectileeffects.SiphonLife;
import projectiles.ProjectileAOE;
import utilities.GameConstants;

public final class TowerWindWater extends Tower {
	float maxHealthModifier;
	float goldModifier;
	
	float damageOnDeathModifier;
	float explosionRadiusModifier;
	
	boolean doesSplash;
	boolean doesOnHit;

	public TowerWindWater(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WIND_WATER, towerID);
		
		this.maxHealthModifier = 0.01f;
		this.goldModifier = 0.00005f;
		
		this.damageOnDeathModifier = 0;
		this.explosionRadiusModifier = 0;
		
		this.doesOnHit = false;
		this.doesSplash = false;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this, doesSplash, doesOnHit);
		baseProjectile.clearAllBasicEffects();
		baseProjectile.addSpecificCreepEffect(new SiphonLife(maxHealthModifier, DamageType.WATER, baseProjectile, goldModifier));
	
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			
		}
		if (progress[0][3]) {
			
		}
		if (progress[1][2]) {
			ArrayList<ProjectileEffect> dod = new ArrayList<ProjectileEffect>(GameConstants.NUM_DAMAGE_TYPES);
			for (DamageType type: DamageType.values()) {
				Damage d = new Damage(damageOnDeathModifier * damageArray[type.ordinal()], type, baseProjectile);
				dod.add(d);
			}
			baseProjectile.addSpecificCreepEffect(new Explosion(DamageType.PHYSICAL, baseProjectile, explosionRadiusModifier * range, dod));
		}
		if (progress[1][3]) {
			
		}
	}

	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			if (guider.isCreepInRange(targetZone, hitsAir)) {
				level.addProjectile(fireProjectile());
				attackCarryOver += 1 - currentAttackCooldown;
				currentAttackCooldown = attackCooldown;
				if (attackCarryOver > 1) {
					attackCarryOver -= 1;
					currentAttackCooldown--;
				}
				return 1;
			}
		}
		return 0;
	}
}
