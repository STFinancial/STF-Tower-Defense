package towers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import creeps.DamageType;
import game.Game;
import game.GameEventType;
import levels.LevelManager;
import levels.Tile;
import utilities.Circle;
import utilities.GameConstants;

public final class TowerManager {
	private static final TowerManager INSTANCE = new TowerManager();
	private Game game;
	
	private ArrayList<Tower> towers;
	private HashMap<Tile, Tower> towerPositions;
	private HashMap<Tower, ArrayList<Aura>> creates;
	private HashMap<Aura, ArrayList<Tower>> affects;
	
	private LevelManager levelManager = LevelManager.getInstance();
	
	private int currentTowerID;
	private int earthEarth;
	
	private TowerManager() { 
		towers = new ArrayList<Tower>();
		creates = new HashMap<Tower, ArrayList<Aura>>();
		affects = new HashMap<Aura, ArrayList<Tower>>();
		towerPositions = new HashMap<Tile, Tower>();
		currentTowerID = 0;
		earthEarth = 0;
	}
	
	public static TowerManager getInstance() {
		return INSTANCE;
	}
	
	public void initialize(Game game) {
		this.game = game;
		towers = new ArrayList<Tower>();
		creates = new HashMap<Tower, ArrayList<Aura>>();
		affects = new HashMap<Aura, ArrayList<Tower>>();
		towerPositions = new HashMap<Tile, Tower>();
		currentTowerID = 0;
		earthEarth = 0;
	}
	
	public boolean hasEarthEarth() { return earthEarth > 0; }
	
	public Tower getTower(Tile tile) {
		return towerPositions.get(tile);
	}
	
	public Tower getTower(int x, int y) {
		return towerPositions.get(levelManager.getTile(y, x));
	}
	
	public Tower constructTower(Tile tile, TowerType type) {
		Tower t = TowerFactory.generateTower(tile, type, currentTowerID++);
		towers.add(t);
		towerPositions.put(tile, t);
		levelManager.addTower(t, t.getTopLeftTile(), t.getWidth(), t.getHeight());
		return t;
	}
	
	private void constructTower(Tower t) {
		towers.add(t);
		levelManager.addTower(t, t.getTopLeftTile(), t.getWidth(), t.getHeight());
	}
	
	public void sellTower(Tower t) {
		//Need to unsiphon the tower and then get the gold value
		destroyTower(t);
	}
	
	private Tower destroyTower(Tower t) {
		unsiphonTower(t, false); // Remove any siphons.
		for (Tower siph: t.siphoningTo) { // Unsiphon from all Towers which this is siphoning to
			// We need to call the level version so that there is a game event for their changes and we can refund if needed
			unsiphonTower(siph, GameConstants.DEFAULT_UNSIPHON_REFUND_OPTION);
		}
		removeTower(t);
		levelManager.addGold(t.getTotalGoldValue());
		return t;
	}
	
	private void removeTower(Tower t) {
		levelManager.removeTower(t.getTopLeftTile(), t.getWidth(), t.getHeight());
		towers.remove(t);
	}
	
	public Tower siphonTower(Tower source, Tower destination) {
		if (destination.siphoningFrom != null) {
			return destination;
		}
		TowerType newType = TowerType.getUpgrade(source.type.getDowngradeType(), destination.type);
		if (newType == TowerType.EARTH_EARTH) {
			earthEarth++;
		}
		Tower newDest = TowerFactory.generateTower(destination.getTopLeftTile(), newType, destination.getTowerID());
		newDest.siphoningFrom = source; //siphon from the source
		newDest.siphoningTo = destination.siphoningTo; //maintain what we're siphoning to
		newDest.setUpgradeTracks(destination.getUpgradeTracks());//set upgrade tracks
		source.siphoningTo.add(newDest); //the source is now siphoning to our new destination
		for (Tower t: towers) {
			if (t.siphoningFrom != null && t.siphoningFrom.equals(destination)) { 
				t.siphoningFrom = newDest; //update all towers that were siphoning from the destination to siphon from the new destination
			}
		}
		removeTower(destination); // Remove the old destination tower.
		constructTower(newDest); //"build" the new one
		towerPositions.put(newDest.getTopLeftTile(), newDest);
		newDest.updateTowerChain(); //update the tower chain
		//TODO: Need to charge some gold here
		game.newEvent(GameEventType.TOWER_DESTROYED, destination);
		game.newEvent(GameEventType.TOWER_CREATED, newDest);
		return newDest;
	}
	
	public Tower unsiphonTower(Tower destination, boolean refund) {
		if (destination.siphoningFrom == null) {
			return destination;
		}
		if (refund) {
			levelManager.addGold(destination.getTrackGoldValue());
		}
		if (destination.type == TowerType.EARTH_EARTH) {
			earthEarth--;
		}
		TowerType newType = destination.type.getDowngradeType(); //get the type we downgrade to
		Tower newDest = TowerFactory.generateTower(destination.getTopLeftTile(), newType, destination.getTowerID()); //generate tower of that type
		
		// If we're refunding, then we need to set the current upgrade track to false
		boolean[][][] upgradeTracks = destination.getUpgradeTracks();
		if (refund && !destination.getType().isBaseType()) {
			DamageType trackType = DamageType.getDamageTypeFromTower(destination.siphoningFrom.getType().getDowngradeType());
			for (int path = 0; path < GameConstants.NUM_UPGRADE_PATHS; path++) {
				for (int uNum = 0; uNum < GameConstants.UPGRADE_PATH_LENGTH; uNum++) {
					upgradeTracks[trackType.ordinal()][path][uNum] = false;
				}
			}
		}
		newDest.setUpgradeTracks(destination.getUpgradeTracks());
		
		Tower siph = destination.siphoningFrom;
		siph.siphoningTo.remove(destination); //what we were siphoningfrom is no longer siphoning to us
		newDest.siphoningTo = destination.siphoningTo; //still siphoning to the same stuff
		for (Tower t: towers) {
			if (t.siphoningFrom != null && t.siphoningFrom.equals(destination)) {
				t.siphoningFrom = newDest; //update all towers that were siphoning from dest to siphon from newDest
			}
		}
		removeTower(destination); //remove old dest
		constructTower(newDest); //construct new dest
		towerPositions.put(newDest.getTopLeftTile(), newDest);
		newDest.updateTowerChain();
		siph.updateTowerChain();
		//TODO: Need to refund some gold
		game.newEvent(GameEventType.TOWER_DESTROYED, destination);
		game.newEvent(GameEventType.TOWER_CREATED, newDest);
		return newDest;
	}
	
	public void updateTowerChain(Tower t) {
		t.updateTowerChain();
	}
	
	Tower getRoot(Tower t) {
		Tower current = t;
		while (current.siphoningFrom != null) {
			current = current.siphoningFrom;
		}
		return current;
	}
	
	private ArrayList<Tower> getTowersInRange(Circle area) {
		Circle c = new Circle(0,0,0);
		ArrayList<Tower> inRange = new ArrayList<Tower>();
		for (Tower t: towers) {
			c.x = t.centerX;
			c.y = t.centerY;
			if (area.intersects(c)) {
				inRange.add(t);
			}
		}
		return inRange;
	}
	
	void createAuraChain(Tower root) {
		Queue<Tower> openList = new LinkedList<Tower>();
		openList.add(root);
		Tower current;
		while (!openList.isEmpty()) {
			current = openList.poll();
			openList.addAll(current.siphoningTo);
			current.createAuras();
		}
		openList.add(root);
		while (!openList.isEmpty()) {
			current = openList.poll();
			openList.addAll(current.siphoningTo);
			ArrayList<Aura> createdAuras = creates.get(current);
			if (createdAuras != null) {
				for (Aura aura: createdAuras) {
					for (Tower t: affects.get(aura)) {
						aura.effectApplication.operation(t);
					}
				}
			}
		}
	}
 	
	Aura createAura(Tower creator, Circle area, TowerEffect effectApplication, TowerEffect effectRemoval) {
		Aura a = new Aura(area, effectApplication, effectRemoval); //Create the aura
		ArrayList<Tower> affectedTowers = getTowersInRange(area); //Find all the towers in range
		affects.put(a, affectedTowers); //Set that it affects all the towers in range
		ArrayList<Aura> creatorCreates = creates.get(creator); //get the list of auras this tower is creating
		if (creatorCreates == null) { //if it's null, make a new list
			creatorCreates = new ArrayList<Aura>();
			creatorCreates.add(a);
			creates.put(creator, creatorCreates);
		} else {
			creatorCreates.add(a);
		}
		return a;
	}
	
	void clearAuras(Tower auraGenerator) {
		ArrayList<Aura> generatedAuras = creates.get(auraGenerator);
		if (generatedAuras != null) {
			for (Aura a: creates.get(auraGenerator)) {
				for (Tower affectedTower: affects.get(a)) {
					a.effectRemoval.operation(affectedTower);
				}
			}
			generatedAuras.clear();
		}
	}
	
	/**
	 * As a rule and to avoid circular dependencies, Auras are generated at the end of the siphoning life cycle and apply immediately when they are generated. This also helps avoid telescoping (though that may be desirable).
	 * @author Timothy
	 *
	 */
	class Aura {
		Circle area;
		TowerEffect effectApplication;
		TowerEffect effectRemoval;
		
		private Aura(Circle area, TowerEffect effectApplication, TowerEffect effectRemoval) {
			this.area = area;
			this.effectApplication = effectApplication;
			this.effectRemoval = effectRemoval;
		}
	}
	
	interface TowerEffect {
		void operation(Tower tower);
	}

	public void updateTowers() {
		for (Tower t: towers) {
			t.update();
		}
	}
}
