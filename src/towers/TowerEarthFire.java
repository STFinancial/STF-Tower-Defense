package towers;

import creeps.Creep;
import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.Wealth;
import projectiles.ProjectileBasic;
import levels.Level;
import maps.Tile;

public class TowerEarthFire extends Tower {
	int wealthDuration;
	float wealthModifier;
	private float shredModifier;
	private int shredDuration;
	
	public TowerEarthFire(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH_FIRE, towerID);
		wealthDuration = 30;
		wealthModifier = 1.15f;
		shredModifier = 0.15f;
		shredDuration = 10;
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
			ArmorShred a = new ArmorShred(shredDuration, shredModifier, DamageType.PHYSICAL, baseProjectile, false);
			a.setMaxStacks(1);
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
