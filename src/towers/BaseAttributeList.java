package towers;

import utilities.GameConstants;

/**
 * A class containing base stats, basic information, and {@link Upgrade} information for a Tower of {@link type}.
 * Each Tower has its own copy of this and Upgrades that modify base values will modify the values stored in the
 * copy owned by that Tower.
 * @author Timothy
 *
 */
class BaseAttributeList {
	
	/**
	 * A class containing basic information about a {@link Tower}'s upgrades, as well as three
	 * different methods that each upgrade may make use of if needed. 
	 * @see {@link Upgrade#baseUpgrade(Tower)} <br> {@link Upgrade#midSiphonUpgrade(Tower)} <br> {@link Upgrade#postSiphonUpgrade(Tower)}
	 * @author Timothy
	 */
	abstract class Upgrade {
		String name;
		String text;
		int baseCost;
		/**
		 * Applies changes to the {@link BaseAttributeList} of the {@link Tower}. Applies as soon as the {@link Upgrade} is purchased and thus effects apply before any siphoning occurs.
		 * @param t - The Tower to upgrade.
		 */
		abstract void baseUpgrade(Tower t);
		/**
		 * Applies changes directly to the stats of the {@link Tower} and thus occurs each time the Tower is refreshed.
		 * This is called "mid-siphon" because it occurs after {@link Tower#siphoningFrom} has siphoned, but before
		 * any Tower in {@link Tower#siphoningTo} has siphoned.
		 * @param t - The Tower to upgrade.
		 */
		abstract void midSiphonUpgrade(Tower t);
		/**
		 * Applies changes directly to the stats of the {@link Tower} and thus occurs each time the Tower is refreshed.
		 * This is called "post-siphon" because it occurs after all Towers in the siphon chain have siphoned.
		 * @param t - The Tower to upgrade.
		 */
		abstract void postSiphonUpgrade(Tower t);
	}
	
	@Override
	public BaseAttributeList clone() {
		/* Copy all attributes into a new instance and return it */
		BaseAttributeList b = new BaseAttributeList();
		b.type = type;
		b.name = name;
		b.downgradeType = downgradeType;
		b.baseWidth = baseWidth;
		b.baseHeight = baseHeight;
		b.baseCost = baseCost;
		b.baseAttackCooldownSiphon = baseAttackCooldownSiphon;
		b.baseAttackCooldown = baseAttackCooldown;
		b.baseSplashDamageSiphon = baseSplashDamageSiphon;
		b.baseSplashDamage = baseSplashDamage;
		b.baseSplashEffectSiphon = baseSplashEffectSiphon;
		b.baseSplashEffect = baseSplashEffect;
		b.baseSplashRadiusSiphon = baseSplashRadiusSiphon;
		b.baseSplashRadius = baseSplashRadius;
		b.baseRangeSiphon = baseRangeSiphon;
		b.baseRange = baseRange;
		b.baseSlowDurationSiphon = baseSlowDurationSiphon;
		b.baseSlowSiphon = baseSlowSiphon;
		b.baseDamageSiphon = baseDamageSiphon;
		b.baseSlowDurationArray = new int[GameConstants.NUM_DAMAGE_TYPES];
		b.baseSlowArray = new float[GameConstants.NUM_DAMAGE_TYPES];
		b.baseDamageArray = new float[GameConstants.NUM_DAMAGE_TYPES];
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			b.baseSlowDurationArray[i] = baseSlowDurationArray[i];
			b.baseSlowArray[i] = baseSlowArray[i];
			b.baseDamageArray[i] = baseDamageArray[i];
		}
		b.baseIsOnGround = baseIsOnGround;
		b.baseIsInAir = baseIsInAir;
		b.baseHitsAir = baseHitsAir;
		b.baseHitsGround = baseHitsGround;
		b.baseDoesSplash = baseDoesSplash;
		b.baseDoesSlow = baseDoesSlow;
		b.baseDoesOnHit = baseDoesOnHit;
		b.baseSplashHitsAir = baseSplashHitsAir;
		b.upgrades = upgrades;
		return b;
	}
	/** Contains the {@link Upgrade Upgrades} possible for this {@link TowerType type}. Contains {@link GameConstants#NUM_UPGRADE_PATHS} different Upgrade "paths,"
	  * each of which contains {@link GameConstants#UPGRADE_PATH_LENGTH} Upgrades. These Upgrades modify attributes of the {@link Tower}.
	  */
	Upgrade[][] upgrades;
	
	/** This is the {@link TowerType} of the {@link Tower} that this {@link BaseAttributeList} corresponds to. */
	TowerType type;
	
	/** This is the name of the {@link Tower} as it will be displayed to the user. */
	String name;
	
	/** This is the {@link TowerType}, that upon unsiphoning, the {@link Tower} will revert to. This returns null when {@link Tower#siphoningFrom} is null or {@link TowerType#isBaseType() type.isBaseType()} returns true. */
	TowerType downgradeType;
	
	/** The base width, in tiles, of the {@link Tower}. */
	int baseWidth;
	
	/** The base height, in tiles, of the {@link Tower}. */
	int baseHeight;
	
	/** The base cost of the {@link Tower} before any discounts are applied. This value is currently 0 for non base Tower, but is subject to change. */
	int baseCost;
	
	/** The base siphon coefficient for {@link Tower#attackCooldown}. */
	float baseAttackCooldownSiphon;
	
	/** The base {@link Tower#attackCooldown} of the {@link Tower}. */
	float baseAttackCooldown;
	
	/** The base siphon coefficient for the {@link Tower#splashDamage} coefficient. */
	float baseSplashDamageSiphon;
	
	/** The base {@link Tower#splashDamage} coefficient of the {@link Tower}. */
	float baseSplashDamage;
	
	/** The base siphon coefficient for the {@link Tower#splashEffect} coefficient. */
	float baseSplashEffectSiphon;
	
	/** The base {@link Tower#splashEffect} coefficient of the {@link Tower}. */
	float baseSplashEffect;
	
	/** The base siphon coefficient for the {@link Tower#splashRadius} of the {@link Tower}. */
	float baseSplashRadiusSiphon;
	
	/** The base {@link Tower#splashRadius} of the {@link Tower}. */
	float baseSplashRadius;
	
	/** The base siphon coefficient for the {@link Tower#range} of the {@link Tower}. */
	float baseRangeSiphon;
	
	/** The base {@link Tower#range} of the {@link Tower}. */
	float baseRange;
	
	/** The base siphon coefficient for each value in the {@link Tower#slowDurationArray} of the {@link Tower}. */
	float baseSlowDurationSiphon;
	
	/** The base values in the {@link Tower#slowDurationArray} of the {@link Tower}. */
	int[] baseSlowDurationArray;
	
	/** The base siphon coefficient for each value in the {@link Tower#slowArray} of the {@link Tower}. */
	float baseSlowSiphon;
	
	/** The base values in the {@link Tower#slowArray} of the {@link Tower}. */
	float[] baseSlowArray;
	
	/** The base siphon coefficient for each value in the {@link Tower#damageArray} of the {@link Tower}. */
	float baseDamageSiphon;
	
	/** The base values in the {@link Tower#damageArray} of the {@link Tower}. */
	float[] baseDamageArray;
	
	/** The default value of {@link Tower#isOnGround} in the {@link Tower}. */
	boolean baseIsOnGround;
	
	/** The default value of {@link Tower#isInAir} in the {@link Tower}. */
	boolean baseIsInAir;
	
	/** The default value of {@link Tower#hitsAir} in the {@link Tower}. */
	boolean baseHitsAir;
	
	/** The default value of {@link Tower#hitsGround} in the {@link Tower}. */
	boolean baseHitsGround;
	
	/** The default value of {@link Tower#doesSplash} in the {@link Tower}. */
	boolean baseDoesSplash;
	
	/** The default value of {@link Tower#doesSlow} in the {@link Tower}. */
	boolean baseDoesSlow;
	
	/** The default value of {@link Tower#doesOnHit} in the {@link Tower}. */
	boolean baseDoesOnHit;
	
	/** The default value of {@link Tower#splashHitsAir} in the {@link Tower}. */
	boolean baseSplashHitsAir;
}
