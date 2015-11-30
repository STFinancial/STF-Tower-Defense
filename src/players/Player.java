package players;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import utilities.GameConstants;

/*
 * Class that contains all player information and is used to save/load player data
 */
public class Player {
	private static final Player INSTANCE = new Player();
	
	/* Misc Attributes */
	String userName;
	String profileCommentBox;
	String inGameName;
	
	/* Game Data */
	AdventureProgress adventureProgress;
	MacroTalentTree macroTalentTree;
	
	
	public static Player getInstance() { return INSTANCE; }
	
	private Player() {
		loadPlayerData();
		adventureProgress = new AdventureProgress();
		macroTalentTree = new MacroTalentTree();
	}
	
	
	void setUserName(String newUserName) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(GameConstants.BASE_FILE_PATH + GameConstants.PLAYER_DATA_FILE_EXT);
			pw.println(newUserName);
			this.userName = newUserName; /* Set this class's username immediately after we change the file */
			pw.println(profileCommentBox);
			pw.println(inGameName);
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			/* We should do nothing and prompt a reset */
			System.out.println("Could not find player data file. Resetting player data.");
			resetPlayerData();
		}
	}
	
	void setProfileCommentBox(String newProfileCommentBox) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(GameConstants.BASE_FILE_PATH + GameConstants.PLAYER_DATA_FILE_EXT);
			pw.println(userName);
			pw.println(newProfileCommentBox);
			this.profileCommentBox = newProfileCommentBox; /* Set this class's comment box immediately after we change the file */
			pw.println(inGameName);
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			/* We should do nothing and prompt a reset */
			System.out.println("Could not find player data file. Resetting player data.");
			resetPlayerData();
		}
	}
	
	void setInGameName(String newInGameName) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(GameConstants.BASE_FILE_PATH + GameConstants.PLAYER_DATA_FILE_EXT);
			pw.println(userName);
			pw.println(profileCommentBox);
			pw.println(newInGameName);
			this.inGameName = newInGameName; /* Set this class's comment box immediately after we change the file */
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			/* We should do nothing and prompt a reset */
			System.out.println("Could not find player data file. Resetting player data.");
			resetPlayerData();
		}
	}
	
	void resetPlayerData() {
		PrintWriter pw;
		try {
			pw = new PrintWriter(GameConstants.BASE_FILE_PATH + GameConstants.PLAYER_DATA_FILE_EXT);
			pw.close();
		} catch (FileNotFoundException e) {
			/* If we can't find the file, we just create a new one */
			File f = new File(GameConstants.BASE_FILE_PATH + GameConstants.PLAYER_DATA_FILE_EXT);
			try {
				f.createNewFile();
			} catch (IOException e1) {
				System.out.println("Could not create new player data file.");
			}
		}
	}
	
	void loadPlayerData() {
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(GameConstants.BASE_FILE_PATH + GameConstants.PLAYER_DATA_FILE_EXT);
			br = new BufferedReader(fr);
			
			userName = br.readLine();
			profileCommentBox = br.readLine();
			inGameName = br.readLine();
			
			br.close();
		} catch (FileNotFoundException e) {
			/* Data does not exist. Create a new one. */
			resetPlayerData();
		} catch (IOException e) {
			/* Can't read the data. Inform the user and prompt to retry */
			try {
				br.close();
			} catch (IOException e1) {
				System.out.println("Could not close data file reader.");
			}
		}
		
	}
}
