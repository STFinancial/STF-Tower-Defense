package towers;

import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.Bleed;
import projectileeffects.Detonation;
import projectiles.ProjectileAOE;
import levels.Level;
import maps.Tile;

public class TowerEarthEarth extends Tower {
	private float detonationModifier;
	private float shredModifier;
	private float bleedModifier;
	private int maxShredStacks;
	private int armorShredDuration;
	private int bleedDuration; //TODO These numbers maybe will be longer, realistically the bleed lasts only the duration of a tower cooldown
	private boolean doesOnHit;
	private boolean doesSplash;
	
	public TowerEarthEarth(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH_EARTH, towerID);
		this.detonationModifier = 0.10f;
		this.shredModifier = 0.10f;
		this.bleedModifier = 0.5f;
		this.maxShredStacks = 5;
		this.armorShredDuration = 18;
		this.bleedDuration = 18;
		this.doesSplash = false;
		this.doesOnHit = false;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this, doesSplash, doesOnHit);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			
		}
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
