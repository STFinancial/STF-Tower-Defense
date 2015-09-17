package towers;

import creeps.ArmorShred;
import creeps.DamageType;
import creeps.PermaSlow;
import projectiles.ProjectileAOE;
import levels.Level;
import maps.Tile;

public class TowerWaterEarth extends Tower {
	private float shredModifier;
	private int shredLifetime;
	private float permaSlowModifier;
	
	public TowerWaterEarth(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
		this.shredModifier = 0.50f;
		this.shredLifetime = 24;
		this.permaSlowModifier = 0.005f;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			ArmorShred a = new ArmorShred(shredLifetime, shredModifier, DamageType.PHYSICAL, baseProjectile, false);
			a.setMaxStacks(1);
			baseProjectile.addSpecificCreepEffect(a);
		}
		if (progress[1][3]) {
			baseProjectile.addSpecificCreepEffect(new PermaSlow(permaSlowModifier, DamageType.EARTH, baseProjectile, true));
		}
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

}
