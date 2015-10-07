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
import levels.Level;
import maps.Tile;

public final class TowerFireEarth extends Tower {
	float poisonModifier;
	int poisonDuration;
	int poisonTiming;
	int maxPoisonStacks;
	float percentMaxHealthModifier;
	float DOHModifier;
	int DOHLifetime;
	int maxDOHStacks;
	float armorShredModifier;
	int armorShredDuration;
	int maxArmorShredStacks;
	float toughnessShredModifier;
	int toughnessShredDuration;
	int maxToughnessShredStacks;
	boolean doesSplash;
	boolean doesOnHit;

	public TowerFireEarth(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.FIRE_EARTH, towerID);
		this.poisonModifier = 0;
		this.poisonDuration = 0;
		this.poisonTiming = 3;
		this.maxPoisonStacks = 0;
		this.armorShredModifier = 0;
		this.armorShredDuration = 0;
		this.maxArmorShredStacks = 0;
		this.toughnessShredModifier = 0;
		this.toughnessShredDuration = 0;
		this.maxToughnessShredStacks = 0;
		this.DOHModifier = 0;
		this.DOHLifetime = 0;
		this.maxDOHStacks = 0;
		this.percentMaxHealthModifier = 0;
		this.doesSplash = false;
		this.doesOnHit = false;
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][1]) {
			baseProjectile = new ProjectileAOE(this, doesSplash, doesOnHit);
		} else {
			baseProjectile = new ProjectileBasic(this);
		}
		if (progress[0][3]) {
			Bleed b = new Bleed(poisonDuration, poisonModifier, poisonTiming, DamageType.EARTH, baseProjectile);
			b.setMaxStacks(maxPoisonStacks);
			baseProjectile.addSpecificCreepEffect(b);
		}
		if (progress[1][0]) {
			ArmorShred a = new ArmorShred(armorShredDuration, armorShredModifier, DamageType.PHYSICAL, baseProjectile, true);
			a.setMaxStacks(maxArmorShredStacks);
			baseProjectile.addSpecificCreepEffect(a);
		}
		if (progress[1][1]) {
			ToughnessShred t = new ToughnessShred(toughnessShredDuration, toughnessShredModifier, DamageType.FIRE, baseProjectile, true);
			t.setMaxStacks(maxToughnessShredStacks);
			baseProjectile.addSpecificCreepEffect(t);
		}
		if (progress[1][2]) {
			DamageOnHit d = new DamageOnHit(DOHLifetime, DOHModifier * damageArray[DamageType.FIRE.ordinal()], DamageType.FIRE, baseProjectile);
			d.setMaxStacks(maxDOHStacks);
			baseProjectile.addSpecificCreepEffect(d);
		}
		if (progress[1][3]) {
			baseProjectile.addSpecificCreepEffect(new MaxHealthDamage(percentMaxHealthModifier, DamageType.FIRE, baseProjectile));
		}
	}

	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
			if (progress[0][1]) {
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
					return 1;
				}
			} else {
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
		}
		return 0;
	}

}
