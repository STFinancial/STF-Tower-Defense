package levels;

import creeps.CreepBuilder;
import creeps.CreepManager;
import maps.Map;
import players.Player;
import projectiles.ProjectileManager;
import towers.TowerManager;
import utilities.CreepWaveGenerator;
import utilities.MapGenerator;

public class Game {
	private Player player;
	private Level level; //TODO: Could level be a singleton too? LevelManager maybe?
	private Map map;
	private ProjectileManager projManager;
	private TowerManager towerManager;
	private CreepManager creepManager;
	private CreepBuilder creepBuilder;
	private CreepWaveGenerator waveGenerator;
	
	private int round;
	private int gameTick;
	private boolean roundInProgress;
	
	public Game() {
		this(new Player(), (new MapGenerator()).generateMap());
	}
	
	public Game(Player player, Map map) {
		this.player = player;
		this.map = map;
		this.level = new Level(this, player, map);
		this.projManager = ProjectileManager.getInstance();
		this.towerManager = TowerManager.getInstance();
		this.creepManager = CreepManager.getInstance();
		this.creepBuilder = CreepBuilder.getInstance();
		this.creepBuilder.setLevel(level);
		this.waveGenerator = CreepWaveGenerator.getInstance();
		this.waveGenerator.setLevel(level);
		this.round = 0;
		this.gameTick = 0;
		this.roundInProgress = false;
	}
	
	public Player getPlayer() { return player; }
	
	public void startRound() {
		if (!roundInProgress) {
			roundInProgress = true;
			level.startRound(round);
			towerManager.startRound(round);
			creepManager.startRound(round);
			projManager.startRound(round);
			round++;
			gameTick = 0;
		}
	}
	
	public void gameTick() {
		//Check for creeps from wave
		creepManager.updateCreepSpawn(gameTick);
		//Update creep movement
		creepManager.updateCreepMovement();
		//Update adjacent to earth
		level.updateCreepAdjacentToEarth();
		//Update towers
		towerManager.updateTowers();
		//Update projectiles
		projManager.updateProjectiles();
		//Check if creeps are dead
		creepManager.updateDeadCreep();
		//Update effect patches
		level.updateEffectPatches();
		//Update game tick
		gameTick++;
	}
	
	//TODO: Should this be a game object instead of an object?
	public void newEvent(GameEventType type, Object o) {
		
	}
}
