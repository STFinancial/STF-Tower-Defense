package towers;

import creeps.Creep;
import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.Wealth;
import projectiles.ProjectileBasic;
import levels.Tile;

public final class TowerEarthFire extends Tower {
	int wealthDuration;
	float wealthModifier;
	private float qWealthDuration;
	private float qWealthModifier;
	
	float shredModifier;
	int shredDuration;
	int maxShredStacks;
	private float qShredModifier;
	private float qShredDuration;
	private float qShredStacks;
	
	TowerEarthFire(Tile topLeftTile, int towerID) {
		super(topLeftTile, TowerType.EARTH_FIRE, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
		boolean[][] progress = getUpgradeTracks()[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			Wealth w = new Wealth(wealthDuration, wealthModifier, DamageType.EARTH, baseProjectile, false, false);
			w.setMaxStacks(1);
			projManager.addProjectileEffect(false, baseProjectile, w);
		}
		if (progress[1][2]) {
			//TODO: I don't exactly like it being called this way.
			projManager.setResistPenPercent(baseProjectile, DamageType.PHYSICAL, 1);
		}
		if (progress[1][3]) {
			//TODO: We really should have a better way of getting the damage of a certain type
			ArmorShred a = new ArmorShred(shredDuration, shredModifier * damageArray[DamageType.PHYSICAL.ordinal()], DamageType.PHYSICAL, baseProjectile, true);
			a.setMaxStacks(maxShredStacks);
			projManager.addProjectileEffect(false, baseProjectile, a);
		}
	}

	@Override
	protected int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = projManager.findTargetCreep(this, hitsAir);
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
		return 0;
	}

	@Override
	protected void adjustClassSpecificBaseStats() {
		this.wealthDuration = 0;
		this.wealthModifier = 0;
		this.shredModifier = 0;
		this.shredDuration = 0;
		this.maxShredStacks = 0;
		
		//TODO: Set appropriate values for all of the towers, should we do this in TowerType?
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.02f;
		this.qRange = 0f;
		
		this.qWealthDuration = 1;
		this.qWealthModifier = 0.01f;
		
		this.qShredModifier = 0.0008f;
		this.qShredDuration = 0.5f;
		this.qShredStacks = 0.5f;
	}

	@Override
	protected void adjustClassSpecificQuality() {
		shredModifier += qShredModifier * qLevel;
		shredDuration += (int) (qShredDuration * qLevel);
		maxShredStacks += (int) (qShredStacks * qLevel);
		
		wealthModifier += qWealthModifier * qLevel;
		wealthDuration += (int) (qWealthDuration * qLevel);
	}

}
