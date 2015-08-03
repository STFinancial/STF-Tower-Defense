package towers;

import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.Wealth;
import projectiles.ProjectileBasic;
import levels.Level;
import maps.Tile;

public class TowerEarthFire extends Tower {
	int wealthDuration;
	float wealthModifier;
	
	public TowerEarthFire(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH_FIRE, towerID);
		wealthDuration = 30;
		wealthModifier = 1.15f;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			baseProjectile.addSpecificCreepEffect(new Wealth(wealthDuration, wealthModifier, DamageType.EARTH, baseProjectile));
		}
		if (progress[1][2]) {
			baseProjectile.armorPenPercent = 1;
		}
		if (progress[1][3]) {
			ArmorShred a = new ArmorShred(10, .15f, DamageType.PHYSICAL, baseProjectile);
			a.refreshable = false;
			baseProjectile.addSpecificCreepEffect(a);
		}
		
	}

	@Override
	public void update() {
		
	}

}
