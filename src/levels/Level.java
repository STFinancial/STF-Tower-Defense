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
import creeps.CreepType;
import creeps.ElementType;
import maps.Tile;
import players.Player;
import projectiles.Projectile;
import towers.BasicWindTower;
import towers.BasicEarthTower;
import towers.BasicFireTower;
import towers.BasicWaterTower;
import towers.Tower;
import towers.TowerType;
import utilities.Circle;
import utilities.CreepWaveGenerator;
import utilities.MapGenerator;
import utilities.PathFinder;

/*
 * Executes main game logic loop
 */
public class Level {

	public final Map map;
	private final Player player;
	private final ArrayList<Wave> creepWaves;

	public int round = 0; //Each round represents a specific creepwave (Or waves for multiple entrance)
	public int tick = 0; //Specific game logic step, smallest possible difference in game states time wise

	int gold, health;
	int nextSpawnTick = -1;
	Wave currentWave;
	boolean roundInProgress = false;
	
	//Currently loaded/active units
	public ArrayList<Tower> towers = new ArrayList<Tower>();
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public ArrayList<Creep> creeps = new ArrayList<Creep>();

	public Path groundPath, airPath, proposedGroundPath;

	//Temp Variables for readability
	Creep c;
	int i;

	public Level(){
		this(new Player(), (new MapGenerator()).generateMap(), (new CreepWaveGenerator()).generateCreepWaves());
	}
	
	public Level(Player player, Map map, ArrayList<Wave> creepWaves) {
		this.player = player;
		this.map = map;
		this.creepWaves = creepWaves;
		updatePath();
	}

	//Can be called from App
	public void startRound() {
		roundInProgress = true;
		currentWave = creepWaves.get(round);
		round++;
		tick = 0;
		nextSpawnTick = currentWave.getDelayForNextCreep();
		for (Tower t: towers) {
			t.roundInit();
		}
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

		for (i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			p.update();
			if (p.isDone()) {
				detonateProjectile(p);
				projectiles.remove(i);
				i--;
			}
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
			t.update();
		}

		//Check if round has finished from all creep being dead

		tick++;
	}

	private void detonateProjectile(Projectile p) {
		//Check for aoe and complicated shit
		if (p.targetsCreep) {
			//Targeted a specific minion
			p.applyEffect(p.targetCreep);
			p.applySplashEffects(getCreepInRange(p, p.splashRadius));
			p.parent.attackCoolDown += p.targetCreep.disruptorAmount;
		} else {
			//Targeted an area
			
		}
		
	}

	private void spawnCreeps(HashSet<Creep> creepsToSpawn) {
		for (Creep c : creepsToSpawn) {

			if (c.is(CreepType.FLYING)) {
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
		ArrayList<Creep> deathRattleChildren;
		deathRattleChildren = c.death();
		if (deathRattleChildren != null) {
			for (Creep drc : deathRattleChildren) {
				drc.setLocation(c);
				creeps.add(drc);
			}
		}
		//Creep slain event
		//Add gold
	}

	//Can be called from App
	public Tower buildTower(TowerType type, int x, int y) {
		//TODO gold cost and stats and plenty of other shit
		Tower t;
		Tile tile = map.getTile(y,x);
		switch(type){ //TODO move this to a factory pattern? probably if we go with the TowerType enum
		case WIND:
			t = new BasicWindTower(this, tile);
			break;
		case WATER:
			t = new BasicWaterTower(this, tile);
			break;
		case FIRE:
			t = new BasicFireTower(this, tile);
			break;
		default:
			t = new BasicEarthTower(this, tile);
			break;
		}
		towers.add(t);
		for(int i = 0; i < t.width; i++){
			for(int j = 0; j < t.height; j++){
				map.getTile(t.y + j, t.x + i).addTower(t);
			}
		}
		updatePath();
		return t; //TODO too lazy to implement event system so i can grab this relevant information, so returning for now
	}
	
	public void tetherTower(Tower tetherFrom, Tower tetherTo) {
		//TODO Implement tower tether
		//Need to destroy old tower then create new hybrid one?
	}

	//Can be called from App
	public void upgradeTower(Tower t, int index) {
		//TODO
	}

	//Can be called from App
	public void razeTower(Tower t) {
		//TODO
	}

	public void updatePath() {
		VertexGraph vg = new VertexGraph();
		vg = PathFinder.mapToVertexGraph(vg, map);
		groundPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, true);
		airPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, false);
	}
	
	public void proposePath(int x, int y, int width, int height){
		//Cludgey as fuck, fix for me tim TODO
		
		boolean old[][] = new boolean[height][width];
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				old[j][i] = map.getTile(y + j, x + i).groundTraversable;
				map.getTile(y + j, x + i).groundTraversable = false;
			}
		}
		
		VertexGraph vg = new VertexGraph();
		vg = PathFinder.mapToVertexGraph(vg, map);
		proposedGroundPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, true);
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map.getTile(y + j, x + i).groundTraversable = old[j][i];
			}
		}
	}

	public boolean stillSpawning() {
		if (currentWave == null) {
			return true; //Hack for quick GUI fix, fix later TODO
		}
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

	public Creep findTargetCreep(Tower tower) {
		Creep toTarget = null;
		ArrayList<Creep> inRange = new ArrayList<Creep>();
		for (Creep c : creeps) {
			if (c.hitBox.intersects(tower.targetArea)) {
				inRange.add(c);
			}
		}
		switch (tower.targetingType) {
		case AREA:
			break;
		case FIRST:
			int max = -1;
			for (Creep c : inRange) {
				if (c.pathIndex > max) {
					toTarget = c;
					max = c.pathIndex;
				}
			}
			break;
		case HIGHEST_HEALTH:
			break;
		case LAST:
			break;
		default:
			break;

		}
		return toTarget;
	}
	
	public HashSet<Creep> getCreepInRange(Projectile p, float range) {
		HashSet<Creep> inRange = new HashSet<Creep>();
		Circle splash = new Circle(p.x, p.y, range);
		for (Creep c : creeps) {
			if (c.hitBox.intersects(splash)) {
				inRange.add(c);
			}
		}
		return inRange;
	}

	public void addProjectile(Projectile p) {
		projectiles.add(p);
		//add game event for new fire!
	}

	public boolean canBuild(int x, int y, int width, int height) {
		if (x < 0 || y < 0 || x + width >= map.width || y + height >= map.height) {
			proposedGroundPath = null;
			return false;
		}
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				if (!map.getTile(j, i).buildable) {
					proposedGroundPath = null;
					return false;
				}
			}
		}
		proposePath(x, y, width, height);
		return proposedGroundPath != null;
	}

}
