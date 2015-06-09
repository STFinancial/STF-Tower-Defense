package towers;

import creeps.DamageType;

public class BaseAttributeList {
	public abstract class Upgrade {
		public String text;
		public abstract void upgrade(Tower t);
	}
	public TowerType type;
	public String name;
	public DamageType mainDamageType;
	public TowerType downgradeType;
	public int baseWidth;
	public int baseHeight;
	public int basePhysicalDamage;
	public int baseElementalDamage;
	public int baseFireRate;
	public int baseAttackCoolDown;
	public int baseSlowDuration;
	public int baseCost;
	public float baseDamageSplash;
	public float baseEffectSplash;
	public float baseSplashRadius;
	public float baseRange;
	public float baseSlow;
	public boolean hitsAir;
	public boolean hitsGround;
	public boolean targetsCreep;
	public Upgrade[][] upgrades;
}
