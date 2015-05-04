package utilities;

import maps.Tile;
import maps.Vertex;
import maps.Map;

/*
 * Tool to grab the path for a certain level, will need to be run anytime a tower is built
 */
public class PathFinder {
	
	
	public static Vertex[] mapToVertexGraph(Map map) {
		int effHeight = map.height - 1;
		int effWidth = map.width - 1;
		
		Vertex[] graph = new Vertex[(effHeight) * (effWidth)];
		
		//this can be optimized by caching two of the tiles for the next iteration instead of regrabbing them
		Tile TL, TR, BL, BR; //topleft, topright, bottomleft, bottomright
		int TL_x, TL_y;
		Vertex vert;
		for (int v = 0; v < graph.length; v++) {
			vert = new Vertex();
			graph[v] = vert;
		}
		for (int v = 0; v < graph.length; v++) {
			vert = graph[v];
			TL_y = v / effWidth;
			TL_x = v % effWidth;
			TL = map.getTile(TL_y, TL_x);
			TR = map.getTile(TL_y, TL_x + 1);
			BL = map.getTile(TL_y + 1, TL_x);
			BR = map.getTile(TL_y + 1, TL_x + 1);
			vert.TL = TL;
			vert.TR = TR;
			vert.BL = BL;
			vert.BR = BR;
			assignType(vert, TL, TR, BL, BR);
			
			boolean left = true, right = true, top = true, bot = true;
			if (TL_x != 0) { //not against the left edge of the map
				vert.neighbors.add(graph[v - 1]);
				left = false;
			}
			if (TL_x + 1 != effWidth) { //not against the right edge of the map
				vert.neighbors.add(graph[v + 1]);
				right = false;
			}
			if (TL_y != 0) { //not against the top
				vert.neighbors.add(graph[v - effWidth]);
				top = false;
			}
			if (TL_y != effHeight) {
				vert.neighbors.add(graph[v + effWidth]);
				bot = false;
			}
			if (!right || !top) {
				vert.neighbors.add(graph[v - effWidth + 1]);
			}
			if (!right || !bot) {
				vert.neighbors.add(graph[v + effWidth + 1]);
			}
			if (!left || !top) {
				vert.neighbors.add(graph[v - effWidth - 1]);
			}
			if (!left || !bot) {
				vert.neighbors.add(graph[v + effWidth + 1]);
			}
			
			graph[v] = vert;
		}
		
		return graph;
	}
	
	private static void assignType(Vertex v, Tile TL, Tile TR, Tile BL, Tile BR) {
		if (TL.type.groundTraversable && 
			TR.type.groundTraversable && 
			BL.type.groundTraversable && 
			BR.type.groundTraversable) {
			v.groundTraversable = true;
		}
		
		if (TL.type.airTraversable &&
			TR.type.airTraversable &&
			BL.type.airTraversable &&
			BR.type.airTraversable) {
			v.airTraversable = true;
		}
	}
}
