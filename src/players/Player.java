package players;
/*
 * Class that contains all player information and is used to save/load player data
 */
public class Player {
	MacroTalentTree talentTree;
	PlayerProgress playerProgress;
	String displayName;
	String profileCommentBox;
	
	public Player() {
		playerProgress = new PlayerProgress();
	}
}
