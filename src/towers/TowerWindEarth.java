package towers;

import projectileeffects.Disorient;
import projectileeffects.Knockup;
import projectileeffects.Slow;
import projectiles.ProjectileBasic;
import projectiles.ProjectilePassThroughTarget;
import creeps.Creep;
import creeps.DamageType;
import levels.Level;
import maps.Tile;

public final class TowerWindEarth extends Tower {
	int knockupDuration;
	int disorientDuration;
	float unslowAmount;
	int unslowDuration;
	
	float passThroughRadiusModifier;
	float passThroughModifier;
	
	boolean doesSplash;
	
	public TowerWindEarth(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WIND_EARTH, towerID);
		this.disorientDuration = 0;
		
		this.knockupDuration = 0;
		
		this.unslowAmount = 0;
		this.unslowDuration = 0;
		
		this.passThroughModifier = 0;
		this.passThroughRadiusModifier = 0;
		
		this.doesSplash = false;
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			baseProjectile = new ProjectilePassThroughTarget(this, passThroughRadiusModifier * range, passThroughModifier, doesSplash, 1);
		} else {
			baseProjectile = new ProjectileBasic(this);
		}
		if (progress[0][2]) {
			baseProjectile.addSpecificCreepEffect(new Knockup(knockupDuration, DamageType.WIND, baseProjectile));
		}
		if (progress[1][2]) {
			baseProjectile.addSpecificCreepEffect(new Disorient(disorientDuration, DamageType.EARTH, baseProjectile));
		}
		if (progress[1][3]) {
			baseProjectile.addSpecificCreepEffect(new Slow(unslowDuration, unslowAmount, DamageType.EARTH, baseProjectile));
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
