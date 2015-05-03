package players;

public class SingleMapProgress {
	//probably refactor this for a better name later. This is a class that can keep track of stars on a map, high score, etc.
	private int bestStars;
	private int bestScore;
	
	public SingleMapProgress() {
		bestStars = 0;
	}
	
	public int getBestStars() {
		return bestStars;
	}
	
	public int getBestScore() {
		return bestScore;
	}
	
	/**
	 * A function that will be called after each attempt at a level, regardless of whether it beat the previous star record.
	 * @param numStars - the number of stars gotten on previous level attempt
	 * @return Returns true if we have a new high star score, false otherwise.
	 */
	public boolean updateStars(int numStars) {
		if (numStars > bestStars) {
			bestStars = numStars;
			return true;
		}
		return false;
	}
	
	/**
	 * A function that will be called after each attempt at a level, regardless of whether it beat the previous high score.
	 * @param score - the score gotten on the previous attempt
	 * @return Returns true if we have a new high score, false otherwise.
	 */
	public boolean updateScore(int score) {
		if (score > bestScore) {
			bestScore = score;
			return true;
		}
		return false;
	}
}
