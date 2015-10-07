package towers;

import projectileeffects.Snare;
import projectiles.ProjectileChain;
import creeps.Creep;
import creeps.DamageType;
import levels.Level;
import maps.Tile;

public final class TowerWindFire extends Tower {
	int maxChains;
	
	float chainPenalty;
	int snareDuration;
	
	boolean noDuplicates;
	
	float shieldDrainModifier;
	
	public TowerWindFire(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WIND_FIRE, towerID);
		this.maxChains = 3;
		this.chainPenalty = 0.75f;
		
		this.snareDuration = 0;
		
		this.noDuplicates = true;
		this.shieldDrainModifier = 1;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileChain(this, maxChains, (float) Math.sqrt(range), chainPenalty, noDuplicates);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		baseProjectile.setShieldDrainModifier(shieldDrainModifier);
		if (progress[1][3]) {
			baseProjectile.addSpecificCreepEffect(new Snare(snareDuration, DamageType.WIND, baseProjectile));
		}
	}
	
	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = guider.findTargetCreep(this, hitsAir);
			if (targetCreep != null) {
				((ProjectileChain) baseProjectile).setTargetCreep(targetCreep);
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
}
