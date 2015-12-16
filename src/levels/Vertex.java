package levels;

import java.util.ArrayList;

/*
 * Helper class that contains information about area between 4 tiles, used primarily in pathing
 */
public final class Vertex implements Comparable<Vertex> {
	boolean airTraversable = false;
	boolean groundTraversable = false;
	boolean isStart = false;
	boolean isFinish = false;
	
	int index;
	int x;
	int y;
	
	double gScore;
	double hScore;
	
	Tile TL, TR, BL, BR;
	ArrayList<Vertex> neighbors;
	
	Vertex () {
		neighbors = new ArrayList<Vertex>();
	}
	
	Vertex (Tile TL, Tile TR, Tile BL, Tile BR) {
		this.TL = TL;
		this.TR = TR;
		this.BL = BL;
		this.BR = BR;
		neighbors = new ArrayList<Vertex>();
	}

	public int getX() { return x; }
	public int getY() { return y; }
	
	public boolean isFinish() {
		return isFinish;
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
	
	boolean contains(Tile tile) {
		return TL.equals(tile) || TR.equals(tile) || BL.equals(tile) || BR.equals(tile);
	}
	
	@Override
	public String toString() {
		if (isFinish) {
			return new String("X: " + x + "\t Y: " + y + "\t IS finish");
		} else {
			return new String("X: " + x + "\t Y: " + y + "\t Not finish");
		}
		
	}
}
