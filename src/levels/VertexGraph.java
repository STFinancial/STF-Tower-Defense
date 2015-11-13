package levels;

import java.util.ArrayList;

/*
 * This class is needed so that we can keep track of starting and ending vertices
 */
public class VertexGraph {
	public Vertex[] graph;
	public int effWidth, effHeight;
	
	/**
	 * The vertices where creep can spawn
	 */
	public ArrayList<Vertex> startingVertices;
	/**
	 * The vertices where creep can leave
	 */
	public ArrayList<Vertex> endingVertices;
}
