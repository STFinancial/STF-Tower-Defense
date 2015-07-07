package utilities;

import towers.Tower;

class DSF {
	Node[] nodes;
	private class Node {
		Tower p;
		Tower t;
		int rank;
	}
	
	DSF() {
		nodes = new Node[10000];
	}

	void makeSet(Tower t) {
		Node newSet = new Node();
		newSet.p = t;
		newSet.rank = 0;
		newSet.t = t;
		nodes[t.towerID] = newSet;
	}
	
	void union(Tower t1, Tower t2) {
		link(findSet(t1), findSet(t2));
	}
	
	void link(Tower t1, Tower t2) {
		Node n1 = nodes[t1.towerID];
		Node n2 = nodes[t2.towerID];
		if (n1.rank > n2.rank) {
			n2.p = n1.t;
		} else {
			n1.p = n2.t;
			if (n1.rank == n2.rank) {
				n2.rank++;
			}
		}
	}
	
	Tower findSet(Tower t) {
		Node n = nodes[t.towerID];
		if (!t.equals(n.p)) {
			//if it isn't its own parent
			n.p = findSet(n.p);
		}
		return n.p;
	}
}
