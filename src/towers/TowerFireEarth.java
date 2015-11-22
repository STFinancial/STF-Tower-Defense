package towers;

import creeps.Creep;
import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.Bleed;
import projectileeffects.DamageOnHit;
import projectileeffects.MaxHealthDamage;
import projectileeffects.ToughnessShred;
import projectiles.ProjectileAOE;
import projectiles.ProjectileBasic;
import levels.Tile;

public final class TowerFireEarth extends Tower {
	float poisonModifier;
	int poisonDuration;
	int poisonTiming;
	int maxPoisonStacks;
	private float qPoisonModifier;
	private float qPoisonDuration;
	private float qPoisonStacks;
	
	float percentMaxHealthModifier;
	private float qMaxHealth;
	
	float DOHModifier;
	int DOHDuration;
	int maxDOHStacks;
	private float qDOHModifier;
	private float qDOHDuration;
	private float qDOHStacks;
	
	float armorShredModifier;
	int armorShredDuration;
	int maxArmorShredStacks;
	private float qArmorModifier;
	private float qArmorDuration;
	private float qArmorStacks;
	
	float toughnessShredModifier;
	int toughnessShredDuration;
	int maxToughnessShredStacks;
	private float qToughModifier;
	private float qToughDuration;
	private float qToughStacks;

	TowerFireEarth(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.FIRE_EARTH, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = getUpgradeTracks()[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][1]) {
			baseProjectile = new ProjectileAOE(this);
		} else {
			baseProjectile = new ProjectileBasic(this);
		}
		if (progress[0][3]) {
			Bleed b = new Bleed(poisonDuration, poisonModifier, poisonTiming, DamageType.EARTH, baseProjectile);
			b.setMaxStacks(maxPoisonStacks);
			projManager.addProjectileEffect(false, baseProjectile, b);
		}
		if (progress[1][0]) {
			ArmorShred a = new ArmorShred(armorShredDuration, armorShredModifier, DamageType.PHYSICAL, baseProjectile, true);
			a.setMaxStacks(maxArmorShredStacks);
			projManager.addProjectileEffect(false, baseProjectile, a);
		}
		if (progress[1][1]) {
			ToughnessShred t = new ToughnessShred(toughnessShredDuration, toughnessShredModifier, DamageType.FIRE, baseProjectile, true);
			t.setMaxStacks(maxToughnessShredStacks);
			projManager.addProjectileEffect(false, baseProjectile, t);
		}
		if (progress[1][2]) {
			DamageOnHit d = new DamageOnHit(DOHDuration, DOHModifier * damageArray[DamageType.FIRE.ordinal()], DamageType.FIRE, baseProjectile);
			d.setMaxStacks(maxDOHStacks);
			projManager.addProjectileEffect(false, baseProjectile, d);
		}
		if (progress[1][3]) {
			projManager.addProjectileEffect(false, baseProjectile, new MaxHealthDamage(percentMaxHealthModifier, DamageType.FIRE, baseProjectile));
		}
	}

	@Override
	protected int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			boolean[][] progress = getUpgradeTracks()[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
			if (progress[0][1]) {
				if (creepManager.isCreepInRange(targetZone, hitsAir)) {
					projManager.addProjectile(fireProjectile());
					attackCarryOver += 1 - currentAttackCooldown;
					currentAttackCooldown = attackCooldown;
					if (attackCarryOver > 1) {
						attackCarryOver -= 1;
						currentAttackCooldown--;
					}
					return 1;
				}
			} else {
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
					return 1;
				}
				
			}
		}
		return 0;
	}

	@Override
	protected void adjustClassSpecificBaseStats() {
		this.poisonModifier = 0;
		this.poisonDuration = 0;
		this.poisonTiming = 0;
		this.maxPoisonStacks = 0;
		this.armorShredModifier = 0;
		this.armorShredDuration = 0;
		this.maxArmorShredStacks = 0;
		this.toughnessShredModifier = 0;
		this.toughnessShredDuration = 0;
		this.maxToughnessShredStacks = 0;
		this.DOHModifier = 0;
		this.DOHDuration = 0;
		this.maxDOHStacks = 0;
		this.percentMaxHealthModifier = 0;
		
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.02f;
		this.qRange = 0f;	
		
		this.qPoisonModifier = 0.005f;
		this.qPoisonDuration = 0.6f;
		this.qPoisonStacks = 0.1f;
		
		this.qMaxHealth = 0.001f;
		
		this.qDOHModifier = 0.004f;
		this.qDOHDuration = 1;
		this.qDOHStacks = 0.05f;
		
		this.qArmorModifier = 0.07f;
		this.qArmorDuration = 1;
		this.qArmorStacks = 1;
		
		this.qToughModifier = 0.07f;
		this.qArmorDuration = 1;
		this.qArmorStacks = 1;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		poisonModifier += qPoisonModifier * qLevel;
		poisonDuration += (int) (qPoisonDuration * qLevel);
		maxPoisonStacks += (int) (qPoisonStacks * qLevel);
		
		percentMaxHealthModifier += qMaxHealth * qLevel;
		
		DOHModifier += qDOHModifier * qLevel;
		DOHDuration += (int) (qDOHDuration * qLevel);
		maxDOHStacks += (int) (qDOHStacks * qLevel);
		
		armorShredModifier += qArmorModifier * qLevel;
		armorShredDuration += (int) (qArmorDuration * qLevel);
		maxArmorShredStacks += (int) (qArmorStacks * qLevel);
		
		toughnessShredModifier += qToughModifier * qLevel;
		toughnessShredDuration += (int) (qToughDuration * qLevel);
		maxToughnessShredStacks += (int) (qToughStacks * qLevel);
	}

}
