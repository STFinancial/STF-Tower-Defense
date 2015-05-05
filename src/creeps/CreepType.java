package creeps;
/*
 * Attribute that dramatically affects how the creep interacts with the level or their base stats
 * Used to let the user know information about the creep face, also different/modified animations
 */
public enum CreepType {
	FLYING, 		//Creep that flys, avoids ground terrain
	DEATH_RATTLE,	//Creep that spawns additional creep(s) when dead
	DISRUPTOR,		//Creep that slows towers when hit
	GIANT,			//Slower, Higher health creep
	QUICK,			//Faster, Smaller creep, usually swarmed
	SHIELDED,		//Contains a shield which caps damage taken per shot, requiring multiple hits
	REGENERATING, 	//Regenerates life quickly
	JUGGERNAUT;		//Massive CC Reduction
	
	
	public static CreepType fromString(String string) {
		for(CreepType type : CreepType.values()){
			if(type.toString().equalsIgnoreCase(string)){
				return type;
			}
		}
		System.out.println(string + " was invalid creep Type");
		return null;
	}
}
