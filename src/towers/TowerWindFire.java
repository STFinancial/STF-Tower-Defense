package towers;

import projectileeffects.Snare;
import projectiles.ProjectileChain;
import creeps.Creep;
import creeps.DamageType;
import levels.Level;
import maps.Tile;

public class TowerWindFire extends Tower {
	int maxChains;
	float chainPenalty;
	
	public TowerWindFire(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
		maxChains = 3;
		chainPenalty = 0.75f;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileChain(this, maxChains, (float) Math.sqrt(range), chainPenalty);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			((ProjectileChain) baseProjectile).noDuplicates = false;
		}
		if (progress[1][2]) {
			baseProjectile.shieldDrainModifier = 2f;
		}
		if (progress[1][3]) {
			baseProjectile.addSpecificCreepEffect(new Snare(4, DamageType.WIND, baseProjectile));
		}
	}

	@Override
	public void update() {
		currentAttackCoolDown--;
		if (currentAttackCoolDown < 1) {
			Creep targetCreep = level.findTargetCreep(this);
			if (targetCreep != null) {
				//TODO is there a better way than casting, perhaps changing the method signature of the fire projectile
				((ProjectileChain) baseProjectile).setTargetCreep(targetCreep);
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
