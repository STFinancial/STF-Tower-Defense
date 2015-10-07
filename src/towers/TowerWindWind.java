package towers;

import creeps.DamageType;
import projectileeffects.Grounding;
import projectileeffects.Hasting;
import projectileeffects.SpeedDamage;
import projectileeffects.Wealth;
import projectiles.ProjectileBasic;
import levels.Level;
import maps.Tile;

public final class TowerWindWind extends Tower {
	float hastingModifier;
	int hastingDuration;
	int maxHastingStacks;
	
	float wealthModifier;
	int wealthDuration;
	int maxWealthStacks;
	
	float groundingModifier;
	float speedDamageModifier;
	
	
	public TowerWindWind(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WIND_WIND, towerID);
		
		this.hastingModifier = 0;
		this.hastingDuration= 0;
		this.maxHastingStacks = 0;
		
		this.wealthModifier = 0;
		this.wealthDuration = 0;
		this.maxWealthStacks = 0;
		
		this.groundingModifier = 0;
		this.speedDamageModifier = 1000f;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			Hasting h = new Hasting(hastingDuration, hastingModifier, DamageType.WIND, baseProjectile);
			h.setMaxStacks(maxHastingStacks);
			baseProjectile.addSpecificCreepEffect(h);
		}
		if (progress[0][3]) {
			Wealth w = new Wealth(wealthDuration, wealthModifier, DamageType.WIND, baseProjectile, true, true);
			w.setMaxStacks(maxWealthStacks);
			baseProjectile.addSpecificCreepEffect(w);
		}
		if (progress[1][2]) {
			baseProjectile.addSpecificCreepEffect(new Grounding(groundingModifier, DamageType.PHYSICAL, baseProjectile));
		}
		if (progress[1][3]) {
			baseProjectile.addSpecificCreepEffect(new SpeedDamage(speedDamageModifier, DamageType.WIND, baseProjectile));
		}
	}

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
