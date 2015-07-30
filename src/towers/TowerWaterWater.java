package towers;

import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.Bleed;
import projectileeffects.Consume;
import projectileeffects.Snare;
import projectiles.ProjectileAOE;
import levels.Level;
import maps.Tile;

public class TowerWaterWater extends Tower {

	public TowerWaterWater(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			baseProjectile.addSpecificCreepEffect(new Snare(7, DamageType.WATER, baseProjectile));
		}
		if (progress[1][2]) {
			baseProjectile.addSpecificCreepEffect(new ArmorShred(7, .5f, DamageType.WATER, baseProjectile));
			baseProjectile.addSpecificCreepEffect(new Bleed(12, damageArray[DamageType.WATER.ordinal()] * (damageArray[DamageType.WATER.ordinal()] / (damageArray[DamageType.WATER.ordinal()] + 800)), 3, DamageType.WATER, baseProjectile));
		}
		if (progress[1][3]) {
			for (DamageType d: DamageType.values()) {
				baseProjectile.addSpecificCreepEffect(new Consume(damageArray[DamageType.WATER.ordinal()] / (damageArray[DamageType.WATER.ordinal()] + 300), d, baseProjectile));
			}
		}
	}

	@Override
	public void update() {
		currentAttackCoolDown--;
		if (currentAttackCoolDown < 1) {
			level.addProjectile(fireProjectile());
			attackCarryOver += 1 - currentAttackCoolDown;
			currentAttackCoolDown = attackCoolDown;
			if (attackCarryOver > 1) {
				attackCarryOver -= 1;
				currentAttackCoolDown--;
			}
		}
	}

}
