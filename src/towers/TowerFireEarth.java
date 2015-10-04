package towers;

import creeps.DamageType;
import projectileeffects.ArmorShred;
import projectileeffects.DamageOnHit;
import projectileeffects.MaxHealthDamage;
import projectileeffects.ToughnessShred;
import projectiles.ProjectileAOE;
import projectiles.ProjectileBasic;
import levels.Level;
import maps.Tile;

public class TowerFireEarth extends Tower {
	private float percentMaxHealthModifier;
	private float DOHModifier;
	private int DOHLifetime;
	private int flatArmorShred;
	private int armorShredDuration;
	private int flatToughnessShred;
	private int toughnessShredDuration;

	public TowerFireEarth(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
		percentMaxHealthModifier = 0.02f;
		flatArmorShred = 1;
		flatToughnessShred = 1;
		DOHModifier = 0.10f;
		DOHLifetime = 20;
	}

	@Override
	protected void adjustProjectileStats() {
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][1]) {
			baseProjectile = new ProjectileAOE(this);
		} else {
			baseProjectile = new ProjectileBasic(this);
		}
		if (progress[1][0]) {
			baseProjectile.addSpecificCreepEffect(new ArmorShred(armorShredDuration, flatArmorShred, DamageType.PHYSICAL, baseProjectile, true));
		}
		if (progress[1][1]) {
			baseProjectile.addSpecificCreepEffect(new ToughnessShred(toughnessShredDuration, flatToughnessShred, DamageType.FIRE, baseProjectile, true));
		}
		if (progress[1][2]) {
			baseProjectile.addSpecificCreepEffect(new DamageOnHit(DOHLifetime, DOHModifier, DamageType.FIRE, baseProjectile));
		}
		if (progress[1][3]) {
			baseProjectile.addSpecificCreepEffect(new MaxHealthDamage(percentMaxHealthModifier, DamageType.FIRE, baseProjectile));
		}
	}

	//TODO: This isn't right, we haven't targeted the right creep
	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			level.addProjectile(fireProjectile());
			attackCarryOver += 1 - currentAttackCooldown;
			currentAttackCooldown = attackCooldown;
			if (attackCarryOver > 1) {
				attackCarryOver -= 1;
				currentAttackCooldown--;
			}
			return 1;
		}
		return 0;
	}

}
