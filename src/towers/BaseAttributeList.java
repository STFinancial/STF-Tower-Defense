package towers;

import utilities.GameConstants;

class BaseAttributeList {
	//TODO: I'm not sure these need to be public, we will see, it might need to be for artwork and such
	public abstract class Upgrade {
		public String name;
		public String text;
		public int baseCost;
		public abstract void baseUpgrade(Tower t);
		public abstract void midSiphonUpgrade(Tower t);
		public abstract void postSiphonUpgrade(Tower t);
	}
	
	@Override
	public BaseAttributeList clone() {
		BaseAttributeList b = new BaseAttributeList();
		b.type = type;
		b.name = name;
		b.downgradeType = downgradeType;
		b.baseWidth = baseWidth;
		b.baseHeight = baseHeight;
		b.baseCost = baseCost;
		b.attackCooldownSiphon = attackCooldownSiphon;
		b.baseAttackCooldown = baseAttackCooldown;
		b.damageSplashSiphon = damageSplashSiphon;
		b.baseDamageSplash = baseDamageSplash;
		b.effectSplashSiphon = effectSplashSiphon;
		b.baseEffectSplash = baseEffectSplash;
		b.radiusSplashSiphon = radiusSplashSiphon;
		b.baseSplashRadius = baseSplashRadius;
		b.rangeSiphon = rangeSiphon;
		b.baseRange = baseRange;
		b.slowDurationSiphon = slowDurationSiphon;
		b.slowSiphon = slowSiphon;
		b.damageSiphon = damageSiphon;
		b.baseSlowDurationArray = new int[GameConstants.NUM_DAMAGE_TYPES];
		b.baseSlowArray = new float[GameConstants.NUM_DAMAGE_TYPES];
		b.baseDamageArray = new float[GameConstants.NUM_DAMAGE_TYPES];
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			b.baseSlowDurationArray[i] = baseSlowDurationArray[i];
			b.baseSlowArray[i] = baseSlowArray[i];
			b.baseDamageArray[i] = baseDamageArray[i];
		}
		b.isOnGround = isOnGround;
		b.isInAir = isInAir;
		b.hitsAir = hitsAir;
		b.hitsGround = hitsGround;
		b.doesSplash = doesSplash;
		b.doesSlow = doesSlow;
		b.doesOnHit = doesOnHit;
		b.splashHitsAir = splashHitsAir;
		b.upgrades = upgrades;
		return b;
	}
	public TowerType type;
	public String name;
	public TowerType downgradeType;
	public int baseWidth;
	public int baseHeight;
	public int baseCost;
	public float attackCooldownSiphon;
	public float baseAttackCooldown;
	public float damageSplashSiphon;
	public float baseDamageSplash;
	public float effectSplashSiphon;
	public float baseEffectSplash;
	public float radiusSplashSiphon;
	public float baseSplashRadius;
	public float rangeSiphon;
	public float baseRange;
	public float slowDurationSiphon;
	public int[] baseSlowDurationArray;
	public float slowSiphon;
	public float[] baseSlowArray;
	public float damageSiphon;
	public float[] baseDamageArray;
	public boolean isOnGround;
	public boolean isInAir;
	public boolean hitsAir;
	public boolean hitsGround;
	public boolean doesSplash;
	public boolean doesSlow;
	public boolean doesOnHit;
	public boolean splashHitsAir;
	public Upgrade[][] upgrades;
}
