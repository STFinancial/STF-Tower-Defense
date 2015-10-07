package towers;

import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.PermaSlow;
import projectiles.ProjectileAOE;
import levels.Level;
import maps.Tile;

public final class TowerWaterEarth extends Tower {
	float shredModifier;
	int shredDuration;
	int maxShredStacks;
	
	float permaSlowModifier;
	
	boolean doesSplash;
	boolean doesOnHit;
	
	public TowerWaterEarth(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WATER_EARTH, towerID);
		this.maxShredStacks = 0;
		this.shredModifier = 0;
		this.shredDuration = 0;
		
		this.permaSlowModifier = 0.005f;
		
		this.doesSplash = false;
		this.doesOnHit = false;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this, doesSplash, doesOnHit);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			ArmorShred a = new ArmorShred(shredDuration, shredModifier, DamageType.PHYSICAL, baseProjectile, false);
			a.setMaxStacks(maxShredStacks);
			baseProjectile.addSpecificCreepEffect(a);
		}
		if (progress[1][3]) {
			baseProjectile.addSpecificCreepEffect(new PermaSlow(permaSlowModifier, DamageType.EARTH, baseProjectile, true));
		}
	}

	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			level.addProjectile(fireProjectile());
			attackCarryOver += 1 - currentAttackCooldown;
			currentAttackCooldown = attackCooldown;
			if (attackCarryOver > 1) {
				attackCarryOver -= 1;
				currentAttackCooldown--;
			}
			return 1;
		}
		return 0;
	}

}
