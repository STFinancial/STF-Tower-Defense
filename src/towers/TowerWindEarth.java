package towers;

import projectileeffects.Disorient;
import projectileeffects.Knockup;
import projectileeffects.Slow;
import projectiles.ProjectileBasic;
import projectiles.ProjectileBeam;
import creeps.Creep;
import creeps.DamageType;
import levels.Level;
import maps.Tile;

public class TowerWindEarth extends Tower {
	private int knockupDuration;
	private int disorientDuration;
	private float unslowAmount;
	private int unslowDuration;
	
	public TowerWindEarth(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
		this.knockupDuration = 24;
		this.disorientDuration = 38;
		this.unslowAmount = -1f;
		this.unslowDuration = 38;
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			baseProjectile = new ProjectileBeam(this);
		} else {
			baseProjectile = new ProjectileBasic(this);
		}
		if (progress[0][2]) {
			baseProjectile.addSpecificCreepEffect(new Knockup(knockupDuration, DamageType.WIND, baseProjectile));
		}
		if (progress[1][2]) {
			baseProjectile.addSpecificCreepEffect(new Disorient(disorientDuration, DamageType.EARTH, baseProjectile));
		}
		if (progress[1][3]) {
			baseProjectile.addSpecificCreepEffect(new Slow(unslowDuration, unslowAmount, DamageType.EARTH, baseProjectile));
		}
	}

	@Override
	public int update() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			Creep targetCreep = level.findTargetCreep(this);
			if (targetCreep != null) {
				((ProjectileBeam) baseProjectile).setTargetCreep(targetCreep);
				level.addProjectile(fireProjectile());
			}
			return 1;
		} else {
			currentAttackCoolDown--;
			if (currentAttackCoolDown < 1) {
				Creep targetCreep = level.findTargetCreep(this);
				if (targetCreep != null) {
					//TODO is there a better way than casting, perhaps changing the method signature of the fire projectile
					((ProjectileBasic) baseProjectile).setTargetCreep(targetCreep);
					level.addProjectile(fireProjectile());
					attackCarryOver += 1 - currentAttackCoolDown;
					currentAttackCoolDown = attackCoolDown;
					if (attackCarryOver > 1) {
						attackCarryOver -= 1;
						currentAttackCoolDown--;
					}
				}
				return 1;
			}
			return 0;
		}
	}

}
