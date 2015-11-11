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
	private Level level;
	private Map map;
	private ProjectileManager projManager;
	private TowerManager towerManager;
	private CreepManager creepManager;
	private CreepBuilder creepBuilder;
	private CreepWaveGenerator waveGenerator;
	
	public Game() {
		this(new Player(), (new MapGenerator()).generateMap());
	}
	
	public Game(Player player, Map map) {
		this.player = player;
		this.map = map;
		level = new Level(this, player, map);
		this.projManager = ProjectileManager.getInstance();
		this.towerManager = TowerManager.getInstance();
		this.creepManager = CreepManager.getInstance();
		this.creepBuilder = CreepBuilder.getInstance();
		this.creepBuilder.setInstanceLevel(level);
		this.waveGenerator = CreepWaveGenerator.getInstance();
		this.waveGenerator.setLevel(level);
	}
	
	public Player getPlayer() { return player; }
	
}
