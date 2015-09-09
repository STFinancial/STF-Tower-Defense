package towers;

import creeps.ArmorShred;
import creeps.Bleed;
import creeps.DamageType;
import creeps.Detonation;
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
	
	public TowerEarthEarth(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH_EARTH, towerID);
		this.detonationModifier = 0.10f;
		this.shredModifier = 0.10f;
		this.bleedModifier = 0.5f;
		this.maxShredStacks = 5;
		this.armorShredDuration = 12;
		this.bleedDuration = 12;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this);
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
