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
import levels.Tile;

public final class TowerFireFire extends Tower {
	
	float bleedModifier;
	int bleedDuration;
	int bleedTiming; //TODO: Quality modifier?
	int maxBleedStacks;
	private float qBleedModifier;
	private float qBleedDuration;
	private float qBleedStacks;
	
	int effectPatchDuration;
	int effectPatchTiming; //TODO: Quality Modifier?
	private float qPatchDuration;
	
	
	float effectPatchDamageModifier;
	private float qPatchDamageModifier;
	
	float effectPatchShredModifier;
	int effectPatchShredDuration;
	int maxShredStacks;
	private float qPatchShredModifier;
	private float qPatchShredDuration;
	private float qPatchShredStacks;
	
	TowerFireFire(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.FIRE_FIRE, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = getUpgradeTracks()[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
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
				projManager.addProjectileEffect(false, baseProjectile, new Bleed(bleedDuration, damageArray[DamageType.FIRE.ordinal()] * bleedModifier, bleedTiming, DamageType.FIRE, baseProjectile));
				projManager.addProjectileEffect(false, baseProjectile, new Bleed(bleedDuration, damageArray[DamageType.FIRE.ordinal()] * bleedModifier, bleedTiming, DamageType.WATER, baseProjectile));
				projManager.addProjectileEffect(false, baseProjectile, new Bleed(bleedDuration, damageArray[DamageType.FIRE.ordinal()] * bleedModifier, bleedTiming, DamageType.EARTH, baseProjectile));
				projManager.addProjectileEffect(false, baseProjectile, new Bleed(bleedDuration, damageArray[DamageType.FIRE.ordinal()] * bleedModifier, bleedTiming, DamageType.WIND, baseProjectile));
			}
		}
	}

	@Override
	protected int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = creepManager.findTargetCreep(targetingMode, targetZone, hitsAir);
			if (targetCreep != null) {
				((ProjectileBasic) baseProjectile).setTargetCreep(targetCreep);
				projManager.addProjectile(fireProjectile());
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

	@Override
	protected void adjustClassSpecificBaseStats() {
		this.bleedModifier = 0;
		this.bleedDuration = 0;
		this.bleedTiming = 0;
		this.maxBleedStacks = 0;
		
		this.effectPatchDuration = 0;
		this.effectPatchTiming = 0;
		this.effectPatchDamageModifier = 0;
		this.effectPatchShredModifier = 0;
		this.effectPatchShredDuration = 0;
		this.maxShredStacks = 0;
		
		this.qBleedModifier = 0.05f;
		this.qBleedDuration = 0.06f;
		this.qBleedStacks = 0.13f;
		
		this.qPatchDuration = 0.6f;
		this.qPatchDamageModifier = 0.01f;
		this.qPatchShredModifier = 0.00003f;
		this.qPatchShredDuration = 0.8f;
		this.qPatchShredStacks = 0.25f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		bleedModifier += qBleedModifier * qLevel;
		bleedDuration += (int) (qBleedDuration * qLevel);
		maxBleedStacks += (int) (qBleedStacks * qLevel);
		
		effectPatchDuration += (int) (qPatchDuration * qLevel);
		effectPatchDamageModifier += qPatchDamageModifier * qLevel;
		effectPatchShredModifier += qPatchShredModifier * qLevel;
		effectPatchShredDuration += (int) (qPatchShredDuration * qLevel);
		maxShredStacks += (int) (qPatchShredStacks * qLevel);	
	}
}
