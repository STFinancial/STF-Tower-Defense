package game;

/*
 * Detailed information when certain things (See GameEventType) happen in game, for both logging and animation
 */
public class GameEvent {
	private final GameEventType type;
	private final Object object;
	private final int round;
	private final int gameTick;

	//TODO: Should this constructor be public?
	public GameEvent(GameEventType type, Object object, int round, int tick) {
		//System.out.println(round + " , " + tick + " New event " + type);
		this.type = type;
		this.object = object;
		this.round = round;
		this.gameTick = tick;
	}
	
	public GameEventType getType() {
		return type;
	}
	
	public Object getObject() {
		return object;
	}
	
	public int getRound() {
		return round;
	}
	
	public int getGameTick() {
		return gameTick;
	}
}
