package game;

import java.util.LinkedList;

import creeps.CreepBuilder;
import creeps.CreepManager;
import levels.Level;
import levels.LevelManager;
import levels.Map;
import levels.MapGenerator;
import players.Player;
import projectiles.ProjectileManager;
import towers.TowerManager;
import utilities.CreepWaveGenerator;

public class Game {
	//TODO: Should I have player and map in here or let level take care of that? Maybe just player?
	private Player player;
	private Map map;
	private LevelManager levelManager;
	private ProjectileManager projManager;
	private TowerManager towerManager;
	private CreepManager creepManager;
	private CreepBuilder creepBuilder;
	private CreepWaveGenerator waveGenerator;
	
	private LinkedList<GameEvent> gameEvents;
	
	private int round;
	private int gameTick;
	private boolean roundInProgress;
	
	public Game() {
		this(new Player(), (new MapGenerator()).generateMap());
	}
	
	public Game(Player player, Map map) {
		this.player = player;
		this.map = map;
		this.levelManager = LevelManager.getInstance();
		this.levelManager.initialize(this, player, map);
		this.projManager = ProjectileManager.getInstance();
		this.projManager.initialize(this);
		this.towerManager = TowerManager.getInstance();
		this.towerManager.initialize(this);
		this.creepManager = CreepManager.getInstance();
		this.creepManager.initialize(this);
		this.creepBuilder = CreepBuilder.getInstance();
		this.creepBuilder.initialize(this);
		this.waveGenerator = CreepWaveGenerator.getInstance();
		this.waveGenerator.initialize(this);
		this.creepManager.setCreepWaves(waveGenerator.generateCreepWaves());
		this.gameEvents = new LinkedList<GameEvent>();
		this.round = 0;
		this.gameTick = 0;
		this.roundInProgress = false;
	}
	
	public Player getPlayer() { return player; }
	
	public void startRound() {
		if (!roundInProgress) {
			roundInProgress = true;
			levelManager.startRound(round);
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
		//Check if round is over?
		//Update adjacent to earth
		levelManager.updateCreepAdjacentToEarth();
		//Update towers
		towerManager.updateTowers();
		//Update projectiles
		projManager.updateProjectiles();
		//Update effect patches
		levelManager.updateEffectPatches();
		//Check if creeps are dead
		creepManager.updateDeadCreep();
		//Update game tick
		gameTick++;
	}
	
	public boolean isRoundInProgress() {
		return roundInProgress;
	}
	
	//TODO: Should this be a game object instead of an object?
	public void newEvent(GameEventType type, Object o) {
		gameEvents.add(new GameEvent(type, o, round, gameTick));
	}
	
	//TODO: Who or what is processing these events and making the necessary changes?
	public GameEvent getEvent() {
		if (gameEvents.size() > 0) {
			return gameEvents.remove(0);
		}
		return new GameEvent(GameEventType.NULL, null, round, gameTick);
	}
	
	@Override
	public String toString() {
		String s = new String();
		s = s.concat("Round: " + round + "\n");
		s = s.concat("Game Tick: " + gameTick + "\n");
		//s = s.concat(creepManager.toString());
		return s;
	}
}
