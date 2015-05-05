package maps;

import java.util.ArrayList;
import java.util.HashSet;

import creeps.Creep;

/*
 * Collection of Creep and their relative spawn times
 * Methods to grab next creep(s), and get time for creep after
 */
public class Wave {

	public ArrayList<Creep> creeps; // List of creeps that we will spawn
	public ArrayList<Integer> timings; // time to wait before spawning creep, typically timings[0] is zero as we spawn the first creep(s) at the round start
	public int size;
	public int counter;

	public Wave() {
		size = 0;
		counter = 0;
		creeps = new ArrayList<Creep>();
		timings = new ArrayList<Integer>();
	}

	public void addCreep(Creep c, int timing) {
		creeps.add(c);
		timings.add(timing);
		size++;
	}

	public HashSet<Creep> getNextCreeps() {
		HashSet<Creep> toReturn = new HashSet<Creep>(); // What do i call this in standard conventions?

		do {
			toReturn.add(creeps.get(counter));
			counter++;
		} while (counter < size && timings.get(counter) == 0);

		return toReturn;
	}

	public int getDelayForNextCreep() {
		if (counter >= size) {
			return -1;
		}
		return timings.get(counter);
	}

	public boolean stillSpawning() {
		return (counter < size);
	}
}
