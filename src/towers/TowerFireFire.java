package towers;

import java.util.ArrayList;

import creeps.Creep;
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
	
	int bleedDuration;
	float bleedModifier;
	int bleedTiming;
	int maxBleedStacks;
	
	int effectPatchDuration;
	int effectPatchTiming;
	float effectPatchDamageModifier;
	float effectPatchShredModifier;
	int effectPatchShredDuration;
	int maxShredStacks;
	
	
	public TowerFireFire(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.FIRE_FIRE, towerID);
		this.bleedModifier = 0;
		this.bleedDuration = 0;
		this.bleedTiming = 3;
		this.maxBleedStacks = 0;
		
		this.effectPatchDuration = 0;
		this.effectPatchTiming = 3;
		this.effectPatchDamageModifier = 0;
		this.effectPatchShredModifier = 0;
		this.effectPatchShredDuration = 0;
		this.maxShredStacks = 0;
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[1][2]) {
			ArrayList<ProjectileEffect> effects = new ArrayList<ProjectileEffect>();
			effects.add(new Damage(damageArray[DamageType.FIRE.ordinal()] * 0.2f, DamageType.FIRE, baseProjectile));
			if (progress[1][3]) {
				ArmorShred a = new ArmorShred(effectPatchShredDuration, effectPatchShredModifier * damageArray[DamageType.FIRE.ordinal()], DamageType.FIRE, baseProjectile, false);
				a.setMaxStacks(maxShredStacks);
				effects.add(a);
				a = new ArmorShred(effectPatchShredDuration, effectPatchShredModifier * damageArray[DamageType.FIRE.ordinal()], DamageType.WATER, baseProjectile, false);
				a.setMaxStacks(maxShredStacks);
				effects.add(a);
				a = new ArmorShred(effectPatchShredDuration, effectPatchShredModifier * damageArray[DamageType.FIRE.ordinal()], DamageType.EARTH, baseProjectile, false);
				a.setMaxStacks(maxShredStacks);
				effects.add(a);
				a = new ArmorShred(effectPatchShredDuration, effectPatchShredModifier * damageArray[DamageType.FIRE.ordinal()], DamageType.WIND, baseProjectile, false);
				a.setMaxStacks(maxShredStacks);
				effects.add(a);
			}
			baseProjectile = new ProjectileEffectPatch(this, effectPatchDuration, effectPatchTiming, splashRadius, effects);
		} else {
			baseProjectile = new ProjectileBasic(this);
			if (progress[0][3]) {
				baseProjectile.addSpecificCreepEffect(new Bleed(bleedDuration, damageArray[DamageType.FIRE.ordinal()] * bleedModifier, bleedTiming, DamageType.FIRE, baseProjectile));
				baseProjectile.addSpecificCreepEffect(new Bleed(bleedDuration, damageArray[DamageType.FIRE.ordinal()] * bleedModifier, bleedTiming, DamageType.WATER, baseProjectile));
				baseProjectile.addSpecificCreepEffect(new Bleed(bleedDuration, damageArray[DamageType.FIRE.ordinal()] * bleedModifier, bleedTiming, DamageType.EARTH, baseProjectile));
				baseProjectile.addSpecificCreepEffect(new Bleed(bleedDuration, damageArray[DamageType.FIRE.ordinal()] * bleedModifier, bleedTiming, DamageType.WIND, baseProjectile));
			}
		}
	}

	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = guider.findTargetCreep(this, hitsAir);
			if (targetCreep != null) {
				((ProjectileBasic) baseProjectile).setTargetCreep(targetCreep);
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
