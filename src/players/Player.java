package players;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import utilities.GameConstants;

/*
 * Class that contains all player information and is used to save/load player data
 */
public class Player {
	/* Misc Attributes */
	String userName;
	String profileCommentBox;
	String inGameName;
	
	/* Game Data */
	AdventureProgress adventureProgress;
	MacroTalentTree macroTalentTree;
	
	public Player() {
		loadPlayerData();
		adventureProgress = new AdventureProgress();
		macroTalentTree = new MacroTalentTree();
	}
	
	void resetPlayerData() {
		
	}
	
	void loadPlayerData() {
		try {
			FileReader fr = new FileReader(GameConstants.PLAYER_DATA_FILE_PATH + "player_data.dat");
			BufferedReader br = new BufferedReader(fr);
			
			userName = br.readLine();
			profileCommentBox = br.readLine();
			inGameName = br.readLine();
			
			br.close();
		} catch (FileNotFoundException e) {
			/* Data does not exist. Create a new one. */
			resetPlayerData();
		} catch (IOException e) {
			/* Can't read the data. Inform the user and prompt to retry */
		}
		
	}
}
