package creeps;

import towers.TowerType;

/*
 * Elemental Affix that affects elemental damage multipliers
 */
public enum DamageType {
	PHYSICAL, EARTH, FIRE, WATER, WIND, LIGHT, DARK;

	public static DamageType fromString(String string) {
		for (DamageType type : DamageType.values()) {
			if (type.toString().equalsIgnoreCase(string)) {
				return type;
			}
		}
		return null;
	}

	public float[] baseResist() {
		switch (this) {
		case EARTH:
			return new float[] {0.2f, 0, 0, .5f, -1, 0, 0};
		case FIRE:
			return new float[] {0.2f, 0, 0, -1, .5f, 0, 0};
		case WATER:
			return new float[] {0.2f, .5f, -1, 0, 0, 0, 0};
		case WIND:
			return new float[] {0.2f, -1, .5f, 0, 0, 0, 0};
		case LIGHT:
			return new float[] {0.2f, 0, 0, 0, 0, 0, .5f};
		case DARK:
			return new float[] {0.2f, .5f, .5f, .5f, .5f, -1, 0};
		default:
			return new float[] {0.2f, 0, 0, 0, 0, 0, 0};
		}
	}
	
	public static DamageType getDamageTypeFromTower(TowerType t) {
		switch (t) {
		case EARTH:
			return EARTH;
		case WIND:
			return WIND;
		case FIRE:
			return FIRE;
		case WATER:
			return WATER;
		default:
			return null;
		}
	}
	
	public boolean isBaseElemental() {
		switch (this) {
		case EARTH:
			return true;
		case FIRE:
			return true;
		case WATER:
			return true;
		case WIND:
			return true;
		default:
			return false;
		}
	}

}
