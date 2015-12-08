package creeps;

final class CreepRadar {
	//TODO:
	private final static CreepRadar INSTANCE = new CreepRadar();
	
	private CreepRadar() {
		
	}
	
	public static CreepRadar getInstance() { return INSTANCE; }
}
