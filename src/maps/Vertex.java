package maps;

import java.util.ArrayList;

/*
 * Helper class that contains information about area between 4 tiles, used primarily in pathing
 */
public class Vertex {
	public boolean airTraversable = false;
	public boolean groundTraversable = false;
	
	
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
	
}
