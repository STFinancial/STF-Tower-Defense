package players;


import utilities.GameConstants;

/*
 * Class containing current world map progress and relevant statistics
 * Methods to add newly completed levels/games
 */
public class PlayerProgress {
	private AdventureProgress[][] progress; //world by level by stars
	
	public PlayerProgress() {
		progress = new AdventureProgress[GameConstants.NUM_WORLDS][GameConstants.LARGEST_WORLD];
		
		for (int world = 0; world < GameConstants.NUM_WORLDS; world++) {
			for (int level = 0; level < GameConstants.LARGEST_WORLD; level++) {
				progress[world][level] = new AdventureProgress();
			}
		}
	}
	
	public boolean updateStars(int world, int level, int numStars) {
		return progress[world][level].updateStars(numStars);
	}
	
	public boolean updateScore(int world, int level, int score) {
		return progress[world][level].updateScore(score);
	}
}