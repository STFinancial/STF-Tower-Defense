package players;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import utilities.GameConstants;

final class AdventureProgress {
	LevelProgress[][] adventureProgress;
	
	AdventureProgress() {
		adventureProgress = new LevelProgress[GameConstants.NUM_WORLDS][GameConstants.LARGEST_WORLD];
		for (int worldNum = 0; worldNum < GameConstants.NUM_WORLDS; worldNum++) {
			for (int levelNum = 0; levelNum < GameConstants.LEVELS_IN_WORLD[worldNum]; levelNum++) {
				adventureProgress[worldNum][levelNum] = new LevelProgress();
			}
		}
		
		LevelProgress prog;
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(GameConstants.BASE_FILE_PATH + GameConstants.ADVENTURE_PROGRESS_FILE_EXT);
			br = new BufferedReader(fr);
			for (int worldNum = 0; worldNum < GameConstants.NUM_WORLDS; worldNum++) {
				for (int levelNum = 0; levelNum < GameConstants.LEVELS_IN_WORLD[worldNum]; levelNum++) {
					prog = adventureProgress[worldNum][levelNum];
					prog.completed = Boolean.valueOf(br.readLine());
					prog.bestScore = Integer.valueOf(br.readLine());
					prog.bestStars = Integer.valueOf(br.readLine());
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find adventure progress. Prompt to reset.");
		} catch (IOException e) {
			System.out.println("Adventure mode data corrupted.");
			try {
				br.close();
			} catch (IOException e1) {
				System.out.println("Could not close the adventure progress file");
			}
		}
	}
	
	void writeProgress() {
		LevelProgress prog;
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(GameConstants.BASE_FILE_PATH + GameConstants.ADVENTURE_PROGRESS_FILE_EXT);
			for (int worldNum = 0; worldNum < GameConstants.NUM_WORLDS; worldNum++) {
				for (int levelNum = 0; levelNum < GameConstants.LEVELS_IN_WORLD[worldNum]; levelNum++) {
					prog = adventureProgress[worldNum][levelNum];
					pw.println(prog.completed);
					pw.println(prog.bestScore);
					pw.println(prog.bestStars);
				}
			}
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find/create progress file.");
		}
		
	}
	
	//TODO: Numbering for the worlds starts as 0, but this reveals implementation details. Should we start numbering at 1?
	
	/**
	 * Returns the best score for the provided level and world.
	 * @param world - The world number of the desired level. Numbering starts at 0.
	 * @param level - The level number of the desired level. Numbering starts at 0.
	 * @return The best score for that level.
	 */
	int getBestScore(int world, int level) {
		return adventureProgress[world][level].bestScore;
	}
	
	/**
	 * Returns the best stars for the provided level and world.
	 * @param world - The world number of the desired level. Numbering starts at 0.
	 * @param level - The level number of the desired level. Numbering starts at 0.
	 * @return The best stars for that level.
	 */
	int getBestStars(int world, int level) {
		return adventureProgress[world][level].bestStars;
	}
	
	/**
	 * @param world - The world number of the desired level. Numbering starts at 0.
	 * @param level - The level number of the desired level. Numbering starts at 0.
	 * @return Returns true if the level has been completed successfully.
	 */
	boolean isLevelCompleted(int world, int level) {
		return adventureProgress[world][level].completed;
	}
	
	/**
	 * Convenience method to quickly set both the best score and stars for a given level. This avoids duplicating writes to the data files.
	 * @param world - The world number of the level. Numbering starts at 0.
	 * @param level - The level number of the level. Numbering starts at 0.
	 * @param newBestScore - The value of the new best score.
	 * @param newBestStars - The number of stars of the new best score.
	 */
	void setBestScoreAndStars(int world, int level, int newBestScore, int newBestStars) {
		if (newBestScore > adventureProgress[world][level].bestScore) {
			adventureProgress[world][level].bestScore = newBestScore;
			adventureProgress[world][level].bestStars = newBestStars;
			writeProgress();
		}
		return;
	}
	
	void setLevelCompletion(int world, int level) {
		if (!adventureProgress[world][level].completed) {
			adventureProgress[world][level].completed = true;
			writeProgress();
		}
	}
	
	private final class LevelProgress {
		boolean completed;
		int bestScore;
		int bestStars;
		
		LevelProgress() { }
	}
}
