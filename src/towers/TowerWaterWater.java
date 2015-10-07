package towers;

import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.Bleed;
import projectileeffects.Consume;
import projectileeffects.Snare;
import projectiles.ProjectileAOE;
import levels.Level;
import maps.Tile;

public final class TowerWaterWater extends Tower {
	int snareDuration;
	
	int shredDuration;
	float shredModifier;
	int maxShredStacks;
	
	int bleedDuration;
	float bleedModifier;
	int bleedTiming;
	int maxBleedStacks;
	
	float consumeModifier;
	
	boolean doesSplash;
	boolean doesOnHit;
	
	public TowerWaterWater(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WATER_WATER, towerID);
		this.snareDuration = 0;
		
		this.shredDuration = 0;
		this.shredModifier = 0;
		this.maxShredStacks = 0;
		
		this.bleedDuration = 0;
		this.bleedModifier = 0;
		this.maxBleedStacks = 0;
		this.bleedTiming = 3;
		
		this.consumeModifier = 0;
		
		this.doesSplash = false;
		this.doesOnHit = false;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this, doesSplash, doesOnHit);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			baseProjectile.addSpecificCreepEffect(new Snare(snareDuration, DamageType.WATER, baseProjectile));
		}
		if (progress[1][2]) {
			ArmorShred a = new ArmorShred(shredDuration, shredModifier, DamageType.WATER, baseProjectile, false);
			a.setMaxStacks(maxShredStacks);
			baseProjectile.addSpecificCreepEffect(a);
			Bleed b = new Bleed(bleedDuration, damageArray[DamageType.WATER.ordinal()] * bleedModifier, bleedTiming, DamageType.WATER, baseProjectile);
			b.setMaxStacks(maxBleedStacks);
			baseProjectile.addSpecificCreepEffect(b);
		}
		if (progress[1][3]) {
			baseProjectile.addSpecificCreepEffect(new Consume(damageArray[DamageType.WATER.ordinal()] * consumeModifier, DamageType.WATER, baseProjectile));
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
