package maps;

import java.util.HashSet;

import creeps.Creep;

/*
 * Collection of Creep and their relative spawn times
 * Methods to grab next creep(s), and get time for creep after
 */
public class Wave {

	Creep[] creeps; // List of creeps that we will spawn
	int[] timings; // time to wait before spawning creep, typically timings[0] is zero as we spawn the first creep(s) at the round start
	int size;
	int counter = 0;

	public HashSet<Creep> getNextCreeps() {
		HashSet<Creep> toReturn = new HashSet<Creep>(); // What do i call this in standard conventions?

		do {
			toReturn.add(creeps[counter]);
			counter++;
		} while (counter < size && timings[counter] == 0);

		return toReturn;
	}

	public int getDelayForNextCreep() {
		if (counter >= size) {
			return -1;
		}
		return timings[counter];
	}
}
