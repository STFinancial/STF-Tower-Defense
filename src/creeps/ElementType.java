package creeps;
/*
 * Elemental Affix that affects elemental damage multipliers
 */
public enum ElementType {
	EARTH, FIRE, WATER, WIND;

	public static ElementType fromString(String string) {
		for(ElementType type : ElementType.values()){
			if(type.toString().equalsIgnoreCase(string)){
				return type;
			}
		}
		return null;
	}
}
