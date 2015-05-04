package levels;

import java.util.ArrayList;

import maps.Map;
import maps.Wave;
import creeps.Creep;
import creeps.ElementType;
import maps.Tile;
import players.Player;
import towers.Projectile;
import towers.Tower;

/*
 * Executes main game logic loop
 */
public class Level {

	private final Map map;
	private final Player player;
	private final ArrayList<Wave> creepWaves;

	public int round = 0; //Each round represents a specific creepwave (Or waves for multiple entrance)
	public int tick = 0; //Specific game logic step, smallest possible difference in game states time wise
	
	int gold, health;
	int nextSpawnTick = -1;
	Wave currentWave;
	boolean roundInProgress = false;
	
	//Currently loaded/active units
	ArrayList<Tower> towers = new ArrayList<Tower>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Creep> creeps = new ArrayList<Creep>();
	
	
	public Level(Player player, Map map, ArrayList<Wave> creepWaves){
		this.player = player;
		this.map = map;
		this.creepWaves = creepWaves;
	}
	
	public void startRound(){
		roundInProgress = true;
		currentWave = creepWaves.get(round);
		round++;
		tick = 0;
		nextSpawnTick = 1;
	}
	
	public void gameTick(){
		tick++;
		
		//Check for new spawns from creep wave;
		if(tick == nextSpawnTick){
			spawnCreeps(currentWave.getNextCreeps());
			nextSpawnTick = tick + currentWave.getTicksForNextCreep();
		}
		
		
		for(Creep c: creeps){
			//Update movement, direction faceing
		}
		
		for(Projectile p: projectiles){
			//Update movement, execute impact logic if needed
		}

		for(Tower t: towers){
			//Update target, if ready fire new projectile
		}
		
		//Check if round has finished from all creep being dead
	}
	
	private void spawnCreeps(Creep[] nextCreeps) {
		// TODO Auto-generated method stub
		
	}

	public void buildTower(ElementType type, Tile location){
		//@TODO
	}
	
	public void upgradeTower(Tower t, int index){
		//@TODO
	}
	
	public void razeTower(Tower t){
		//@TODO
	}
	
}
