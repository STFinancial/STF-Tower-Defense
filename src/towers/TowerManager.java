package towers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import levels.Level;
import utilities.Circle;

//TODO: Should this just change into TowerManager?
public final class TowerManager {
	private static final TowerManager INSTANCE = new TowerManager();
	private Level level;
	
	private ArrayList<Tower> towers;
	private ArrayList<Aura> auras;
	private HashMap<Tower, ArrayList<Aura>> pairs;
	
	private TowerManager() { 
		towers = new ArrayList<Tower>();
		auras = new ArrayList<Aura>();
		pairs = new HashMap<Tower, ArrayList<Aura>>();
	}
	
	public static TowerManager getInstance() {
		return INSTANCE;
	}
	
	public void setLevel(Level level) {
		this.level = level;
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
		for (Tower t: level.getTowers()) {
			c.x = t.centerX;
			c.y = t.centerY;
			if (area.intersects(c)) {
				inRange.add(t);
			}
		}
		return inRange;
	}
	
	Aura createAura(Circle area, TowerEffect effect, int priority) {
		Aura a = new Aura(area, effect, priority);
		auras.add(a);
		HashSet<Tower> roots = new HashSet<Tower>();
		for (Tower t: getTowersInRange(area)) {
			t.addAura(a);
			ArrayList<Aura> towerAuras = pairs.get(t);
			if (towerAuras == null) {
				towerAuras = new ArrayList<Aura>();
				towerAuras.add(a);
				pairs.put(t, towerAuras);
			} else {
				towerAuras.add(a);
			}
			roots.add(getRoot(t));
		}
		for (Tower t: roots) {
			Tower.adjustSiphonChain(t);
		}
		return a;
	}
	
	void adjustAuras(Tower t, int priority) { 
		for (Aura a: pairs.get(t)) {
			if (a.priority == priority) {
				a.effect.operation(t);
			}
		}
	}
	
	class Aura {
		boolean applied;
		Circle area;
		TowerEffect effect;
		int priority;
		
		private Aura(Circle area, TowerEffect effect, int priority) {
			this.area = area;
			this.effect = effect;
			this.priority = priority;
			this.applied = false;
		}
	}
	
	interface TowerEffect {
		void operation(Tower tower);
	}
}
