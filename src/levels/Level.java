package levels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
	
	//Can be called from App
	public void startRound(){
		roundInProgress = true;
		currentWave = creepWaves.get(round);
		round++;
		tick = 0;
		nextSpawnTick = currentWave.getDelayForNextCreep();
	}
	
	public void gameTick(){
		//Check for new spawns from creep wave;
		if(tick == nextSpawnTick){
			spawnCreeps(currentWave.getNextCreeps());
			nextSpawnTick = tick + currentWave.getDelayForNextCreep();
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
		
		
		tick++;
	}
	
	private void spawnCreeps(HashSet<Creep> creepsToSpawn) {
		// TODO Auto-generated method stub
		//Grab all the creeps
		//Set their location to the appropriate starting spot
		//Give them path info?
		//Turn them facing the correct direction
		//Let them know they are now alive
		//Add them to creeps collection
		//add GameEvent
	}

	//Can be called from App
	public void buildTower(ElementType type, Tile location){
		//@TODO
	}
	
	//Can be called from App
	public void upgradeTower(Tower t, int index){
		//@TODO
	}
	
	//Can be called from App
	public void razeTower(Tower t){
		//@TODO
	}
	
}
