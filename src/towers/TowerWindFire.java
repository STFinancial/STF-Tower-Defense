package towers;

import creeps.Snare;
import projectiles.ProjectileChain;
import creeps.Creep;
import creeps.DamageType;
import levels.Level;
import maps.Tile;

public class TowerWindFire extends Tower {
	int maxChains;
	float chainPenalty;
	private int snareDuration = 4;
	
	public TowerWindFire(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WIND_FIRE, towerID);
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
			baseProjectile.addSpecificCreepEffect(new Snare(snareDuration, DamageType.WIND, baseProjectile));
		}
	}

	@Override
	public int update() {
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
			return 1;
		}
		return 0;
	}

}
