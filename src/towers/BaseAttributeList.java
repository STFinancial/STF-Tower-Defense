package towers;


import projectileeffects.ProjectileEffect;
import creeps.DamageType;

public class BaseAttributeList {
	public abstract class Upgrade {
		public String name;
		public String text;
		public boolean isBase;
		public int baseCost;
		public abstract void upgrade(Tower t);
	}
	@Override
	public BaseAttributeList clone() {
		BaseAttributeList newList = new BaseAttributeList();
		newList.type = type;
		newList.name = name;
		newList.mainDamageType = mainDamageType;
		newList.downgradeType = downgradeType;
		newList.baseWidth = baseWidth;;
		newList.baseHeight =  baseHeight;
		newList.baseSlowDuration = baseSlowDuration;
		newList.baseCost = baseCost;
		newList.baseAttackCoolDown = baseAttackCoolDown;
		newList.baseDamageSplash = baseDamageSplash;
		newList.baseEffectSplash = baseEffectSplash;
		newList.baseSplashRadius = baseSplashRadius;
		newList.baseRange = baseRange;
		newList.baseSlow = baseSlow;
		newList.baseDamageArray = baseDamageArray;
		newList.hitsAir = hitsAir;
		newList.hitsGround = hitsGround;
		newList.additionalEffect = additionalEffect;
		newList.upgrades = upgrades;
		return newList;
	}
	public TowerType type;
	public String name;
	public DamageType mainDamageType;
	public TowerType downgradeType;
	public int baseWidth;
	public int baseHeight;
	public int baseSlowDuration;
	public int baseCost;
	public float baseAttackCoolDown;
	public float baseDamageSplash;
	public float baseEffectSplash;
	public float baseSplashRadius;
	public float baseRange;
	public float baseSlow;
	public float[] baseDamageArray;
	public boolean hitsAir;
	public boolean hitsGround;
	public ProjectileEffect additionalEffect;
	public Upgrade[][] upgrades;
}
