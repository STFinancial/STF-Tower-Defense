package towers;

import creeps.Creep;
import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.Wealth;
import projectiles.ProjectileBasic;
import levels.Level;
import maps.Tile;

public class TowerEarthFire extends Tower {
	//TODO: Currently
	int wealthDuration;
	float wealthModifier;
	float shredModifier;
	int shredDuration;
	int maxShredStacks;
	
	public TowerEarthFire(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH_FIRE, towerID);
		this.wealthDuration = 0;
		this.wealthModifier = 0;
		this.shredModifier = 0;
		this.shredDuration = 0;
		this.maxShredStacks = 0;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			Wealth w = new Wealth(wealthDuration, wealthModifier, DamageType.EARTH, baseProjectile, false, false);
			w.setMaxStacks(1);
			baseProjectile.addSpecificCreepEffect(w);
		}
		if (progress[1][2]) {
			baseProjectile.setResistPenPercent(DamageType.PHYSICAL, 1);
		}
		if (progress[1][3]) {
			//TODO: We really should have a better way of getting the damage of a certain type
			ArmorShred a = new ArmorShred(shredDuration, shredModifier * damageArray[DamageType.PHYSICAL.ordinal()], DamageType.PHYSICAL, baseProjectile, true);
			a.setMaxStacks(maxShredStacks);
			baseProjectile.addSpecificCreepEffect(a);
		}
		
	}

	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = guider.findTargetCreep(this, hitsAir);
			if (targetCreep != null) {
				//TODO is there a better way than casting, perhaps changing the method signature of the fire projectile
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
		}
		return 0;
	}

}
