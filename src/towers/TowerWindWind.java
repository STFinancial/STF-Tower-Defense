package towers;

import creeps.DamageType;
import projectileeffects.Grounding;
import projectileeffects.Hasting;
import projectileeffects.SpeedDamage;
import projectileeffects.Wealth;
import projectiles.ProjectileBasic;
import levels.Level;
import maps.Tile;

public class TowerWindWind extends Tower {
	private float hastingModifier;
	private int hastingLifetime;
	private int maxHastingStacks;
	private float wealthModifier;
	private int wealthLifetime;
	private int maxWealthStacks;
	private float groundingModifier;
	private float speedDamageModifier;
	
	
	public TowerWindWind(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
		this.hastingModifier = 1f;
		this.hastingLifetime = 20;
		this.maxHastingStacks = 1;
		this.wealthModifier = 6f;
		this.wealthLifetime = 20;
		this.maxWealthStacks = 2;
		this.groundingModifier = 70;
		this.speedDamageModifier = 1000f;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileBasic(this);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			Hasting h = new Hasting(hastingLifetime, hastingModifier, DamageType.WIND, baseProjectile);
			h.setMaxStacks(maxHastingStacks); //TODO: Do this for all stackables in towers
			baseProjectile.addSpecificCreepEffect(h);
		}
		if (progress[0][3]) {
			Wealth w = new Wealth(wealthLifetime, wealthModifier, DamageType.WIND, baseProjectile, true, true);
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
		currentAttackCoolDown--;
		if (currentAttackCoolDown < 1) {
			level.addProjectile(fireProjectile());
			attackCarryOver += 1 - currentAttackCoolDown;
			currentAttackCoolDown = attackCoolDown;
			if (attackCarryOver > 1) {
				attackCarryOver -= 1;
				currentAttackCoolDown--;
			}
			return 1;
		}
		return 0;
	}

}
