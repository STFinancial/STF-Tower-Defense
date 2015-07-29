package towers;

import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.Bleed;
import projectileeffects.Detonation;
import projectiles.ProjectileAOE;
import levels.Level;
import maps.Tile;

public class TowerEarthEarth extends Tower {
	
	
	public TowerEarthEarth(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			
		}
		if (progress[0][3]) {
			baseProjectile.addSpecificCreepEffect(new Detonation(damageArray[DamageType.PHYSICAL.ordinal()] * 0.1f, DamageType.PHYSICAL, baseProjectile));
		}
		if (progress[1][2]) {
			baseProjectile.addSpecificCreepEffect(new Bleed(12, (float) damageArray[DamageType.PHYSICAL.ordinal()] * (damageArray[DamageType.PHYSICAL.ordinal()] / (damageArray[DamageType.PHYSICAL.ordinal()] + 700)), 3, DamageType.PHYSICAL, baseProjectile));
		}
		if (progress[1][3]) {
			baseProjectile.addSpecificCreepEffect(new ArmorShred(12, (float) damageArray[DamageType.PHYSICAL.ordinal()] / (damageArray[DamageType.PHYSICAL.ordinal()] + 700), DamageType.PHYSICAL, baseProjectile));
		}
	}

	@Override
	public void update() {
		currentAttackCoolDown--;
		if (currentAttackCoolDown < 1) {
			targetCreep = level.findTargetCreep(this);
			if (targetCreep != null) {
				updateAngle(targetCreep);
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

}
