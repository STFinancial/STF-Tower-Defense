package towers;

import creeps.Creep;
import creeps.DamageType;
import projectileeffects.SuppressionDeathrattle;
import projectileeffects.SuppressionDisruptor;
import projectiles.ProjectileBasic;
import levels.Level;
import maps.Tile;

public class TowerWaterFire extends Tower {
	int deathrattleSuppressionLifetime;
	
	
	int disruptorSuppressionLifetime;
	float disruptorSuppressionPercent;
	
	public TowerWaterFire(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
		this.disruptorSuppressionLifetime = 0;
		this.disruptorSuppressionPercent = 0;
		
		this.deathrattleSuppressionLifetime = 0;
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
			baseProjectile.addSpecificCreepEffect(new SuppressionDeathrattle(deathrattleSuppressionLifetime, 1, DamageType.WATER, baseProjectile));
		}
		if (progress[0][2]) {
			baseProjectile.addSpecificCreepEffect(new SuppressionDisruptor(disruptorSuppressionLifetime, disruptorSuppressionPercent, DamageType.WATER, baseProjectile, false));
		}
		
		if (progress[1][3]) {
			baseProjectile.setResistPenPercent(DamageType.WATER, 1);
			baseProjectile.setResistPenPercent(DamageType.FIRE, 1);
		}
	}
}
