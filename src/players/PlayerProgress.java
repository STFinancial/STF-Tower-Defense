package players;


import utilities.Constants;

/*
 * Class containing current world map progress and relevant statistics
 * Methods to add newly completed levels/games
 */

public class PlayerProgress {
	private SingleMapProgress[][] progress; //world by level by stars
	
	public PlayerProgress() {
		progress = new SingleMapProgress[Constants.NUM_WORLDS][Constants.LARGEST_WORLD];
		
		for (int world = 0; world < Constants.NUM_WORLDS; world++) {
			for (int level = 0; level < Constants.LARGEST_WORLD; level++) {
				progress[world][level] = new SingleMapProgress();
			}
		}
	}
	
	public void updateStars(int world, int level, int numStars) {
		progress[world][level].updateStars(numStars);
	}
	
	public void updateScore(int world, int level, int score) {
		//TODO
	}
}