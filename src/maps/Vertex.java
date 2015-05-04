package maps;

import java.util.ArrayList;

/*
 * Helper class that contains information about area between 4 tiles, used primarily in pathing
 */
public class Vertex implements Comparable<Vertex> {
	public boolean airTraversable = false;
	public boolean groundTraversable = false;
	public boolean isStart = false;
	public boolean isFinish = false;
	
	public int index;
	public int x;
	public int y;
	
	public double gScore;
	public double hScore;
	
	public Tile TL, TR, BL, BR;
	public ArrayList<Vertex> neighbors;
	
	public Vertex () {
		neighbors = new ArrayList<Vertex>();
	}
	
	public Vertex (Tile TL, Tile TR, Tile BL, Tile BR) {
		this.TL = TL;
		this.TR = TR;
		this.BL = BL;
		this.BR = BR;
		neighbors = new ArrayList<Vertex>();
	}

	@Override
	public int compareTo(Vertex v) {
		if (hScore > v.hScore) {
			return 1;
		} else if (hScore < v.hScore) {
			return -1;
		} else {
			return 0;
		}
	}
	
	
	public boolean equals(Vertex v) {
		return v.x == x && v.y == y;
	}
	
}
