package towers;

import java.util.ArrayList;

import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.Bleed;
import projectileeffects.Damage;
import projectileeffects.ProjectileEffect;
import projectiles.ProjectileBasic;
import projectiles.ProjectileEffectPatch;
import levels.Level;
import maps.Tile;

public class TowerFireFire extends Tower {
	private int shredDuration;
	private int bleedDuration;
	private float shredModifier;
	private float bleedModifier;
	private int effectPatchDuration;
	
	public TowerFireFire(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.FIRE_FIRE, towerID);
		this.shredDuration = 12;
		this.bleedDuration = 12;
		this.shredModifier = 0.2f;
		this.bleedModifier = 3;
		this.effectPatchDuration = 12;
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[1][2]) {
			ArrayList<ProjectileEffect> effects = new ArrayList<ProjectileEffect>();
			effects.add(new Damage((float)damageArray[DamageType.FIRE.ordinal()] * 0.2f, DamageType.FIRE, baseProjectile));
			if (progress[1][3]) {
				ArmorShred a = new ArmorShred(shredDuration, shredModifier, DamageType.FIRE, baseProjectile, false);
				a.setMaxStacks(1);
				effects.add(a);
				a = new ArmorShred(shredDuration, shredModifier, DamageType.WATER, baseProjectile, false);
				a.setMaxStacks(1);
				effects.add(a);
				a = new ArmorShred(shredDuration, shredModifier, DamageType.EARTH, baseProjectile, false);
				a.setMaxStacks(1);
				effects.add(a);
				a = new ArmorShred(shredDuration, shredModifier, DamageType.WIND, baseProjectile, false);
				a.setMaxStacks(1);
				effects.add(a);
			}
			//TODO the 12 will be changed by "quality"
			baseProjectile = new ProjectileEffectPatch(this, effectPatchDuration, 3, splashRadius, effects);
		} else {
			baseProjectile = new ProjectileBasic(this);
			if (progress[0][3]) {
				baseProjectile.addSpecificCreepEffect(new Bleed(bleedDuration, damageArray[DamageType.FIRE.ordinal()] * bleedModifier, 3, DamageType.FIRE, baseProjectile));
				baseProjectile.addSpecificCreepEffect(new Bleed(bleedDuration, damageArray[DamageType.FIRE.ordinal()] * bleedModifier, 3, DamageType.WATER, baseProjectile));
				baseProjectile.addSpecificCreepEffect(new Bleed(bleedDuration, damageArray[DamageType.FIRE.ordinal()] * bleedModifier, 3, DamageType.EARTH, baseProjectile));
				baseProjectile.addSpecificCreepEffect(new Bleed(bleedDuration, damageArray[DamageType.FIRE.ordinal()] * bleedModifier, 3, DamageType.WIND, baseProjectile));
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
