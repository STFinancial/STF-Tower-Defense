package towers;

import creeps.ArmorShred;
import creeps.Bleed;
import creeps.DamageType;
import creeps.Detonation;
import projectiles.ProjectileAOE;
import levels.Level;
import maps.Tile;

public class TowerEarthEarth extends Tower {
	private float shredModifier = .05f;
	
	public TowerEarthEarth(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH_EARTH, towerID);
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
			baseProjectile.addSpecificCreepEffect(new ArmorShred(12, (float) damageArray[DamageType.PHYSICAL.ordinal()] * shredModifier / (damageArray[DamageType.PHYSICAL.ordinal()] + 700), DamageType.PHYSICAL, baseProjectile, true, 5));
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
