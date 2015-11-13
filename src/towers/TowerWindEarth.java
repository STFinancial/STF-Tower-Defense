package towers;

import projectileeffects.Disorient;
import projectileeffects.Knockup;
import projectileeffects.Slow;
import projectiles.ProjectileBasic;
import projectiles.ProjectilePassThroughTarget;
import creeps.Creep;
import creeps.DamageType;
import levels.Level;
import levels.Tile;

public final class TowerWindEarth extends Tower {
	int knockupDuration;
	int disorientDuration;
	float unslowModifier;
	int unslowDuration;
	private float qKnockup;
	private float qDisorient;
	private float qUnslowModifier;
	private float qUnslowDuration;
	
	float passThroughRadiusModifier;
	float passThroughModifier;
	private float qPassModifier;
	private float qPassRadius;
	
	
	public TowerWindEarth(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WIND_EARTH, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = getUpgradeTracks()[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			baseProjectile = new ProjectilePassThroughTarget(this, passThroughRadiusModifier * range, passThroughModifier, 1);
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
			baseProjectile.addSpecificCreepEffect(new Slow(unslowDuration, unslowModifier, DamageType.EARTH, baseProjectile));
		}
	}

	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = projManager.findTargetCreep(this, hitsAir);
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

	@Override
	protected void adjustClassSpecificBaseStats() {
		this.disorientDuration = 0;
		this.knockupDuration = 0;
		this.unslowModifier = 0;
		this.unslowDuration = 0;
		this.passThroughModifier = 0;
		this.passThroughRadiusModifier = 0;
		
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.02f;
		this.qRange = 0f;
		
		this.qKnockup = 1;
		this.qPassModifier = 0.01f;
		this.qPassRadius = 0.01f;
		this.qDisorient = 1.2f;
		this.qUnslowDuration = 1.2f;
		this.qUnslowModifier = -0.03f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		knockupDuration += (int) (qKnockup * qLevel);
		disorientDuration += (int) (qDisorient * qLevel);
		unslowModifier += qUnslowModifier * qLevel;
		unslowDuration += (int) (qUnslowDuration * qLevel);
		passThroughModifier += qPassModifier * qLevel;
		passThroughRadiusModifier += qPassRadius * qLevel;
	}

}
