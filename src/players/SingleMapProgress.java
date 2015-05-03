package players;

public class SingleMapProgress {
	//probably refactor this for a better name later. This is a class that can keep track of stars on a map, high score, etc.
	private int bestStars;
	
	public SingleMapProgress() {
		bestStars = 0;
	}
	
	public int getBestStars() {
		return bestStars;
	}
	
	//TODO: This should return something if it is a new high score.
	public void updateStars(int numStars) {
		if (numStars > bestStars) {
			bestStars = numStars;
		}
	}
}
