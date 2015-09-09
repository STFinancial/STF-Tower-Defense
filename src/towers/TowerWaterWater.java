package towers;

import creeps.ArmorShred;
import creeps.Bleed;
import creeps.Consume;
import creeps.DamageType;
import creeps.Snare;
import projectiles.ProjectileAOE;
import levels.Level;
import maps.Tile;

public class TowerWaterWater extends Tower {
	private int snareDuration;
	private int shredDuration;
	private float shredModifier; //TODO We guarantee double by the description, we should change that then
	private int bleedDuration;
	private float bleedModifier;
	private int maxBleedStacks;
	
	public TowerWaterWater(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WATER_WATER, towerID);
		this.snareDuration = 7;
		this.shredDuration = 7;
		this.shredModifier = 0.5f;
		this.bleedDuration = 12;
		this.bleedModifier = 1;
		this.maxBleedStacks = 5;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			baseProjectile.addSpecificCreepEffect(new Snare(snareDuration, DamageType.WATER, baseProjectile));
		}
		if (progress[1][2]) {
			ArmorShred a = new ArmorShred(shredDuration, shredModifier, DamageType.WATER, baseProjectile, false);
			a.setMaxStacks(1);
			baseProjectile.addSpecificCreepEffect(a);
			Bleed b = new Bleed(bleedDuration, damageArray[DamageType.WATER.ordinal()] * bleedModifier, 3, DamageType.WATER, baseProjectile);
			b.setMaxStacks(maxBleedStacks);
			baseProjectile.addSpecificCreepEffect(b);
		}
		if (progress[1][3]) {
			for (DamageType d: DamageType.values()) {
				baseProjectile.addSpecificCreepEffect(new Consume(damageArray[DamageType.WATER.ordinal()] / (damageArray[DamageType.WATER.ordinal()] + 300), d, baseProjectile));
			}
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
