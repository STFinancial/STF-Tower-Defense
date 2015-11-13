package game;

/*
 * Detailed information when certain things (See GameEventType) happen in game, for both logging and animation
 */
public class GameEvent {

	public final Object object;
	public final int round, tick;
	public final GameEventType type;

	public GameEvent(GameEventType type, Object object, int round, int tick) {
		//System.out.println(round + " , " + tick + " New event " + type);
		this.type = type;
		this.object = object;
		this.round = round;
		this.tick = tick;
	}
}
