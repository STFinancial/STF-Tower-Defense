package towers;

import creeps.Creep;
import creeps.DamageType;
import projectileeffects.SuppressionDeathrattle;
import projectileeffects.SuppressionDisruptor;
import projectiles.ProjectileBasic;
import levels.Level;
import maps.Tile;

public final class TowerWaterFire extends Tower {
	int deathrattleSuppressionDuration;
	private float qDeathrattle;
	
	int disruptorSuppressionDuration;
	float disruptorSuppressionPercent;
	private float qDisruptorDuration;
	//TODO: qDisruptorPercent do I want this to be able to grant attack cooldown?
	
	public TowerWaterFire(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WATER_FIRE, towerID);
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

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			baseProjectile.addSpecificCreepEffect(new SuppressionDeathrattle(deathrattleSuppressionDuration, 1, DamageType.WATER, baseProjectile));
		}
		if (progress[0][2]) {
			baseProjectile.addSpecificCreepEffect(new SuppressionDisruptor(disruptorSuppressionDuration, disruptorSuppressionPercent, DamageType.WATER, baseProjectile, false));
		}
		
		if (progress[1][3]) {
			baseProjectile.setResistPenPercent(DamageType.WATER, 1);
			baseProjectile.setResistPenPercent(DamageType.FIRE, 1);
		}
	}

	@Override
	protected void adjustClassSpecificBaseStats() {
		this.disruptorSuppressionDuration = 0;
		this.disruptorSuppressionPercent = 0;
		
		this.deathrattleSuppressionDuration = 0;
		
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.02f;
		this.qRange = 0f;
		
		this.qDeathrattle = 2;
		this.qDisruptorDuration = 2.3f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		deathrattleSuppressionDuration += (int) (qDeathrattle * qLevel);
		disruptorSuppressionDuration += (int) (qDisruptorDuration * qLevel);
	}
}
