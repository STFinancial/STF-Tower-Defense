package levels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import maps.Map;
import maps.Vertex;
import maps.VertexGraph;
import maps.Wave;
import creeps.Creep;
import creeps.ElementType;
import maps.Tile;
import players.Player;
import projectiles.Projectile;
import towers.Tower;
import utilities.PathFinder;

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
	public ArrayList<Creep> creeps = new ArrayList<Creep>();

	public Path groundPath;
	Path airPath;

	//Temp Variables for readability
	Creep c;
	int i;

	public Level(Player player, Map map, ArrayList<Wave> creepWaves) {
		this.player = player;
		this.map = map;
		this.creepWaves = creepWaves;
	}

	//Can be called from App
	public void startRound() {
		roundInProgress = true;
		currentWave = creepWaves.get(round);
		round++;
		tick = 0;
		nextSpawnTick = currentWave.getDelayForNextCreep();
	}

	public void gameTick() {
		//Check for new spawns from creep wave;
		if (tick == nextSpawnTick) {
			spawnCreeps(currentWave.getNextCreeps());
			nextSpawnTick = tick + currentWave.getDelayForNextCreep();
		}

		for (i = 0; i < creeps.size(); i++) {
			c = creeps.get(i);
			c.update();
			if (c.currentVertex.equals(groundPath.getFinish())) {
				escapeCreep(c);
				creeps.remove(i);
				i--;
			}
		}

		for (Projectile p : projectiles) {
			//Update movement, execute impact logic if needed
		}

		for (i = 0; i < creeps.size(); i++) {
			c = creeps.get(i);
			if (c.isDead()) {
				killCreep(c);
				creeps.remove(c);
				i--;
			}
		}

		for (Tower t : towers) {
			//Update target, if ready fire new projectile
		}

		//Check if round has finished from all creep being dead

		tick++;
	}

	private void spawnCreeps(HashSet<Creep> creepsToSpawn) {
		for (Creep c : creepsToSpawn) {

			if (c.isFlying()) {
				c.setPath(airPath);
			} else {
				c.setPath(groundPath);
			}
			creeps.add(c);
			//Game Spawn Event
		}
	}

	private void escapeCreep(Creep c) {
		//TODO
		//Game Escape Event

		//Take away health or gold or whatever

		//Check if we lose?

	}

	private void killCreep(Creep c) {
		// TODO Auto-generated method stub
		c.death();
		//Creep slain event
		//Add gold
	}

	//Can be called from App
	public void buildTower(ElementType type, Tile location) {
		//@TODO
	}

	//Can be called from App
	public void upgradeTower(Tower t, int index) {
		//@TODO
	}

	//Can be called from App
	public void razeTower(Tower t) {
		//@TODO
	}

	public void updatePath() {
		VertexGraph vg = new VertexGraph();
		vg = PathFinder.mapToVertexGraph(vg, map);
		groundPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, true);
		airPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, false);
	}

	public boolean stillSpawning() {
		return currentWave.stillSpawning();
	}

	public boolean creepLeft() {
		return !creeps.isEmpty();
	}

	public boolean isRoundOver() {
		return !stillSpawning() && !creepLeft();
	}

	public boolean isOver() {
		return isRoundOver() && round == creepWaves.size();
	}

}
