package towers;

import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.Bleed;
import projectileeffects.Detonation;
import projectiles.ProjectileAOE;
import levels.Level;
import maps.Tile;

public final class TowerEarthEarth extends Tower {
	float detonationModifier;
	float shredModifier;
	float bleedModifier;
	int maxShredStacks;
	int armorShredDuration;
	int bleedDuration; //TODO These numbers maybe will be longer, realistically the bleed lasts only the duration of a tower cooldown
	boolean doesOnHit;
	boolean doesSplash;
	
	public TowerEarthEarth(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH_EARTH, towerID);
		this.detonationModifier = 0;
		this.shredModifier = 0;
		this.bleedModifier = 0;
		this.maxShredStacks = 0;
		this.armorShredDuration = 0;
		this.bleedDuration = 0;
		this.doesSplash = false;
		this.doesOnHit = false;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this, doesSplash, doesOnHit);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			baseProjectile.addSpecificCreepEffect(new Detonation(damageArray[DamageType.PHYSICAL.ordinal()] * detonationModifier, DamageType.PHYSICAL, baseProjectile));
		}
		if (progress[1][2]) {
			Bleed b = new Bleed(bleedDuration, (float) damageArray[DamageType.PHYSICAL.ordinal()] * bleedModifier, 3, DamageType.PHYSICAL, baseProjectile);
			baseProjectile.addSpecificCreepEffect(b);
		}
		if (progress[1][3]) {
			ArmorShred a = new ArmorShred(armorShredDuration, damageArray[DamageType.PHYSICAL.ordinal()] * shredModifier, DamageType.PHYSICAL, baseProjectile, true);
			a.setMaxStacks(maxShredStacks);
			baseProjectile.addSpecificCreepEffect(a);
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
