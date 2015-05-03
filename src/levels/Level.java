package levels;

import maps.Map;
import creeps.ElementType;
import maps.Tile;
import players.Player;

/*
 * Executes main game logic loop
 */
public class Level {

	private final Map map;
	private final Player player;

	public Level(Player player, Map map){
		this.player = player;
		this.map = map;
	}
	
	public void gameTick(){
		//@TODO
	}
	
	public void buildTower(ElementType type, Tile location){
		//@TODO
	}
}
