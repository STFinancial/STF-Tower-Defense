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
	private float qDetonation;
	
	float shredModifier;
	int maxShredStacks;
	int armorShredDuration;
	private float qShredModifier;
	private float qShredDuration;
	private float qShredStacks;
	
	float bleedModifier;
	int bleedTiming;
	int bleedDuration;
	int maxBleedStacks;
	private float qBleedModifier;
	private float qBleedDuration;
	private float qBleedStacks;
	
	boolean doesOnHit;
	boolean doesSplash;
	
	public TowerEarthEarth(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH_EARTH, towerID);
		adjustClassSpecificBaseStats();
		//TODO: Do I need to call updateTowerChain()
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileAOE(this, doesSplash, doesOnHit);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			baseProjectile.addSpecificCreepEffect(new Detonation(damageArray[DamageType.PHYSICAL.ordinal()] * detonationModifier, DamageType.PHYSICAL, baseProjectile));
		}
		if (progress[1][2]) {
			Bleed b = new Bleed(bleedDuration, (float) damageArray[DamageType.PHYSICAL.ordinal()] * bleedModifier, bleedTiming, DamageType.PHYSICAL, baseProjectile);
			b.setMaxStacks(maxBleedStacks);
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
			if (guider.isCreepInRange(targetZone, hitsAir)) {
				level.addProjectile(fireProjectile());
				attackCarryOver += 1 - currentAttackCooldown;
				currentAttackCooldown = attackCooldown;
				if (attackCarryOver > 1) {
					attackCarryOver -= 1;
					currentAttackCooldown--;
				}
				return 1;
			}
		}
		return 0;
	}

	@Override
	protected void adjustClassSpecificBaseStats() {
		this.detonationModifier = 0;
		this.shredModifier = 0;
		this.bleedModifier = 0;
		this.bleedTiming = 0;
		this.maxShredStacks = 0;
		this.armorShredDuration = 0;
		this.bleedDuration = 0;
		this.doesSplash = false;
		this.doesOnHit = false;
		
		//TODO: Set appropriate values for all of the towers, should we do this in TowerType?
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.02f;
		this.qRange = 0f;
		
		this.qDetonation = 0.01f;
		this.qShredModifier = 0.003f;
		this.qShredDuration = 0.5f;
		this.qShredStacks = 0.25f;
		
		this.qBleedModifier = 0.01f;
		this.qBleedDuration = 0.5f;
		this.qBleedStacks = 0.10f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		detonationModifier += qDetonation * qLevel;
		
		shredModifier += qShredModifier * qLevel;
		armorShredDuration += (int) (qShredDuration * qLevel);
		maxShredStacks += (int) (qShredStacks * qLevel);
		
		bleedModifier += qBleedModifier * qLevel;
		bleedDuration += (int) (qBleedDuration * qLevel);
		maxBleedStacks += (int) (qBleedStacks * qLevel);
	}

}
