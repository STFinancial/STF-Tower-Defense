package game;

/*
 * Different events we need to log for animation
 */
public enum GameEventType {
	CREEP_SPAWNED, 
	CREEP_KILLED, 
	CREEP_ESCAPED, 
	HEALTH_ZERO, 
	PROJECTILE_DETONATED, 
	PROJECTILE_EXPIRED, 
	PROJECTILE_FIRED, 
	ROUND_FINISHED,
	TOWER_CREATED,
	TOWER_DESTROYED,
	NULL;
}
