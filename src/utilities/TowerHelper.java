package utilities;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import towers.Tower;

public class TowerHelper {
	DSF towerForests;
	
	public TowerHelper() {
		towerForests = new DSF();
	}
	
	//create a graph class and generate a graph using the towerchain
	private
	
	public LinkedList<Tower> getTopologicalSort(Tower t) {
		LinkedList<Tower> towerChain = exploreTowerChain(t);
		//loop through and find a tower from which no other tower is siphoning (no incoming arrows)
		LinkedList<Tower> sortedChain = new LinkedList<Tower>();
		Queue<Tower> 
		for (Tower )
	}
	
	private LinkedList<Tower> exploreTowerChain(Tower t) {
		//This just does a BFS of the graph
		Queue<Tower> toExplore = new LinkedList<Tower>();
		LinkedList<Tower> explored = new LinkedList<Tower>();
		toExplore.add(t);
		
		Tower current;
		while (!toExplore.isEmpty()) {
			current = toExplore.poll();
			explored.add(current);
			if (!explored.contains(current.siphoningFrom) && !toExplore.contains(current.siphoningFrom)) {
				toExplore.add(current.siphoningFrom);
			}
			for (Tower child: current.siphoningTo) {
				if (!explored.contains(child) && !toExplore.contains(child)) {
					toExplore.add(child);
				}
			}
		}
		
		return explored;
	}
}
