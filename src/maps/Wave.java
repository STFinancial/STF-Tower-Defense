package maps;

import creeps.Creep;

/*
 * Collection of Creep and their relative spawn times
 * Methods to grab next creep(s), and get time for creep after
 */
public class Wave {
	
	Creep[] creeps;
	int[] timings;
	int size;
	int counter = 0;
	
	public Creep[] getNextCreeps(){
		//TODO
		//Check how many creep come in this game tick (in case timings are set to zero and multiple spawn)
		//Increment counter accordingly
		//Return creep(s)
		return null;
	}
	
	public int getTicksForNextCreep(){
		if (size > counter) {
			return -1;
		}
		return timings[counter];
	}
}
