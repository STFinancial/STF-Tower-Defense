package creeps;

/*
 * Elemental Affix that affects elemental damage multipliers
 */
public enum ElementType {
	EARTH, FIRE, WATER, WIND, LIGHT, DARK;

	public static ElementType fromString(String string) {
		for (ElementType type : ElementType.values()) {
			if (type.toString().equalsIgnoreCase(string)) {
				return type;
			}
		}
		return null;
	}

	public float[] baseResist() {
		switch (this) {
		case EARTH:
			return new float[] { 1, 1, .5f, 2, 1, 1 };
		case FIRE:
			return new float[] { 1, 1, 2, .5f, 1, 1 };
		case WATER:
			return new float[] { 2, .5f, 1, 1, 1, 1 };
		case WIND:
			return new float[] { .5f, 2, 1, 1, 1, 1 };
		case LIGHT:
			return new float[] { 1, 1, 1, 1, 1, .5f };
		case DARK:
			return new float[] { .5f, .5f, .5f, .5f, 2, 1 };
		default:
			return new float[] { 1, 1, 1, 1, 1, 1 };
		}
	}

}
