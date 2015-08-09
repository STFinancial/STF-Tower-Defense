package towers;

import java.util.ArrayList;

import creeps.DamageType;
import projectileeffects.Damage;
import projectileeffects.ProjectileEffect;
import projectileeffects.ArmorShred;
import projectiles.ProjectileBasic;
import projectiles.ProjectileStill;
import levels.Level;
import maps.Tile;

public class TowerFireFire extends Tower {

	public TowerFireFire(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[1][2]) {
			ArrayList<ProjectileEffect> effects = new ArrayList<ProjectileEffect>();
			effects.add(new Damage((float)damageArray[DamageType.FIRE.ordinal()] * 0.2f, DamageType.FIRE, baseProjectile));
			if (progress[1][3]) {
				effects.add(new ArmorShred(1, .2f, DamageType.FIRE, baseProjectile));
				effects.add(new ArmorShred(1, .2f, DamageType.WATER, baseProjectile));
				effects.add(new ArmorShred(1, .2f, DamageType.EARTH, baseProjectile));
				effects.add(new ArmorShred(1, .2f, DamageType.WIND, baseProjectile));
			}
			//TODO the 12 will be changed by "quality"
			baseProjectile = new ProjectileStill(this, 12, 3, splashRadius, effects);
		} else {
			baseProjectile = new ProjectileBasic(this);
		}
		
	}

	@Override
	public void update() {
		currentAttackCoolDown--;
		if (currentAttackCoolDown < 1) {
			level.addProjectile(fireProjectile());
			attackCarryOver += 1 - currentAttackCoolDown;
			currentAttackCoolDown = attackCoolDown;
			if (attackCarryOver > 1) {
				attackCarryOver -= 1;
				currentAttackCoolDown--;
			}
		}
	}

}
