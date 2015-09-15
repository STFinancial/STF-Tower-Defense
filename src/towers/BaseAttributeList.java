package towers;

public class BaseAttributeList {
	public abstract class Upgrade {
		public String name;
		public String text;
		public boolean isBase; //TODO: Remove this and just modify the base stats directly if I'm cloning
		public int baseCost;
		public abstract void upgrade(Tower t);
	}
	public TowerType type;
	public String name;
	public TowerType downgradeType;
	public int baseWidth;
	public int baseHeight;
	public int[] baseSlowDurationArray;
	public int baseCost;
	public float baseAttackCoolDown;
	public float baseDamageSplash;
	public float baseEffectSplash;
	public float baseSplashRadius;
	public float baseRange;
	public float[] baseSlowArray;
	public float[] baseDamageArray;
	public boolean hitsAir;
	public boolean hitsGround;
	public Upgrade[][] upgrades;
}
