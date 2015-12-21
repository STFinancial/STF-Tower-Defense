package creeps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import levels.Path;

/*
 * Collection of Creep and their absolute spawn times
 * Methods to grab next creep(s), and get time for creep after
 */
public final class Wave {
	private ArrayList<Spawn> spawns;
	private int size;
	private int spawnCounter;
	private int nextSpawnTick;

	public Wave() {
		size = 0;
		spawnCounter = 0;
		spawns = new ArrayList<Spawn>();
	}

	public void addCreep(Creep c, int timing) {
		spawns.add(new Spawn(c, timing));
		Collections.sort(spawns); //Could be unnecessary if this is called right, but to be safe
		nextSpawnTick = spawns.get(0).timing;
		size++;
	}

	public List<Creep> getNextCreeps(int tick) {
		List<Creep> toReturn = Collections.emptyList(); // What do i call this in standard conventions?
		if (tick == nextSpawnTick) {
			toReturn = new ArrayList<Creep>();
			do {
				toReturn.add(spawns.get(spawnCounter++).creep);
			} while (spawnCounter < size && (nextSpawnTick = spawns.get(spawnCounter).timing) == tick);
		}
		return toReturn;
	}
	
	public boolean stillSpawning() {
		return (spawnCounter < size);
	}
	
	private class Spawn implements Comparable<Spawn> {
		private Creep creep;
		private int timing;
		
		Spawn (Creep creep, int timing) {
			this.creep = creep;
			this.timing = timing;
		}

		@Override
		public int compareTo(Spawn s) {
			if (timing < s.timing) {
				return -1;
			} else if (timing > s.timing) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
