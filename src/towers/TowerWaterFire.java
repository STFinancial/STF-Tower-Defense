package towers;

import creeps.DamageType;
import creeps.SuppressionDeathrattle;
import creeps.SuppressionDisruptor;
import projectiles.ProjectileBasic;
import levels.Level;
import maps.Tile;

public class TowerWaterFire extends Tower {
	private int deathrattleSuppressionLifetime;
	private int disruptorSuppressionLifetime;
	private float disruptorSuppressionPercent;
	
	public TowerWaterFire(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
		this.deathrattleSuppressionLifetime = 43; //TODO: Move these base values to the game constants (same with all other towers) along with the quality increases?
		this.disruptorSuppressionLifetime = 60;
		this.disruptorSuppressionPercent = 0.5f;
	}

	@Override
	public int update() {
		currentAttackCoolDown--;
		if (currentAttackCoolDown < 1) {
			level.addProjectile(fireProjectile());
			attackCarryOver += 1 - currentAttackCoolDown;
			currentAttackCoolDown = attackCoolDown;
			if (attackCarryOver > 1) {
				attackCarryOver -= 1;
				currentAttackCoolDown--;
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
			disruptorSuppressionPercent = 1f;
			baseProjectile.addSpecificCreepEffect(new SuppressionDeathrattle(deathrattleSuppressionLifetime, 1, DamageType.WATER, baseProjectile));
		}
		if (progress[0][2]) {
			baseProjectile.addSpecificCreepEffect(new SuppressionDisruptor(disruptorSuppressionLifetime, disruptorSuppressionPercent, DamageType.WATER, baseProjectile, false));
		}
		
		if (progress[1][3]) {
			baseProjectile.resistPenPercent[DamageType.WATER.ordinal()] = 1;
			baseProjectile.resistPenPercent[DamageType.FIRE.ordinal()] = 1;
		}
	}


}
