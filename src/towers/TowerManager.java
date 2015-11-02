package towers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import levels.Level;
import levels.Updatable;
import maps.Map;
import maps.Tile;
import utilities.Circle;

public final class TowerManager implements Updatable {
	private static final TowerManager INSTANCE = new TowerManager();
	private Level level;
	
	private ArrayList<Tower> towers;
	private ArrayList<Aura> auras;
	private HashMap<Tower, ArrayList<Aura>> creates;
	private HashMap<Aura, ArrayList<Tower>> affects;
	private Map map;
	
	private int currentTowerID;
	private int earthEarth;
	
	private TowerManager() { 
		towers = new ArrayList<Tower>();
		auras = new ArrayList<Aura>();
		creates = new HashMap<Tower, ArrayList<Aura>>();
		affects = new HashMap<Aura, ArrayList<Tower>>();
		map = null; //TODO: how do we deal with the null pointer exception that this will create
		currentTowerID = 0;
		earthEarth = 0;
	}
	
	public static TowerManager getInstance() {
		return INSTANCE;
	}
	
	public void setLevel(Level level) {
		this.level = level;
		//Need to clear out all the old junk
		towers = new ArrayList<Tower>();
		auras = new ArrayList<Aura>();
		creates = new HashMap<Tower, ArrayList<Aura>>();
		affects = new HashMap<Aura, ArrayList<Tower>>();
		map = level.getMap();
		currentTowerID = 0;
		earthEarth = 0;
	}
	
	public boolean hasEarthEarth() { return earthEarth > 0; }
	
	public Tower constructTower(Tile tile, TowerType type) {
		Tower t = TowerFactory.generateTower(level, tile, type, currentTowerID++);
		towers.add(t);
		for (int i = 0; i < t.width; i++) {
			for (int j = 0; j < t.height; j++) {
				map.getTile(t.y + j, t.x + i).addTower(t);
			}
		}
		return t;
	}
	
	private void constructTower(Tower t) {
		towers.add(t);
		for (int i = 0; i < t.width; i++) {
			for (int j = 0; j < t.height; j++) {
				map.getTile(t.y + j, t.x + i).addTower(t);
			}
		}
	}
	
	public Tower destroyTower(Tower t) {
		
	}
	
	private void removeTower(Tower t) {
		for (int i = 0; i < t.width; i++) {
			for (int j = 0; j < t.height; j++) {
				map.getTile(t.y + j, t.x + i).removeTower();
			}
		}
		towers.remove(t);
	}
	
	public Tower siphonTower(Tower source, Tower destination) {
		TowerType newType = TowerType.getUpgrade(source.type.getDowngradeType(), destination.type);
		if (newType == TowerType.EARTH_EARTH) {
			earthEarth++;
		}
		Tower newDest = TowerFactory.generateTower(level, destination.topLeft, newType, destination.towerID);
		newDest.upgradeTracks = destination.upgradeTracks; //set upgrade tracks
		newDest.siphoningFrom = source; //siphon from the source
		newDest.siphoningTo = destination.siphoningTo; //maintain what we're siphoning to
		source.siphoningTo.add(newDest); //the source is now siphoning to our new destination
		for (Tower t: towers) {
			if (t.siphoningFrom.towerID == destination.towerID) {
				t.siphoningFrom = newDest; //update all towers that were siphoning from the destination to siphon from the new destination
			}
		}
		removeTower(destination); //destroy the old destinatino tower
		constructTower(newDest); //"build" the new one
		newDest.updateTowerChain(); //update the tower chain
		return newDest;
	}
	
	public Tower unsiphonTower(Tower destination) {
		if (destination.type == TowerType.EARTH_EARTH) {
			earthEarth--;
		}
		TowerType newType = destination.type.getDowngradeType(); //get the type we downgrade to
		Tower newDest = TowerFactory.generateTower(level, destination.topLeft, newType, destination.towerID); //generate tower of that type
		newDest.upgradeTracks = destination.upgradeTracks; //set the upgrade tracks
		Tower siph = destination.siphoningFrom;
		siph.siphoningTo.remove(destination); //what we were siphoningfrom is no longer siphoning to us
		newDest.siphoningTo = destination.siphoningTo; //still siphoning to the same stuff
		for (Tower t: towers) {
			if (t.siphoningFrom.towerID == destination.towerID) {
				t.siphoningFrom = newDest; //update all towers that were siphoning from dest to siphon from newDest
			}
		}
		removeTower(destination); //destroy old dest
		constructTower(newDest); //construct new dest
		newDest.updateTowerChain();
		siph.updateTowerChain();
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

	@Override
	public int update() {
		for (Tower t: towers) {
			t.update();
		}
		return 0;
	}
}
