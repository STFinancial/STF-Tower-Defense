package utilities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import levels.Path;
import maps.DirectionType;
import maps.Tile;
import maps.TileType;
import maps.Vertex;
import maps.Map;
import maps.VertexGraph;

/*
 * Tool to grab the path for a certain level, will need to be run anytime a tower is built
 */
public class PathFinder {
	
	
	public Path AStar(Vertex start, Vertex finish, VertexGraph vg) {
		Vertex[] graph = vg.graph;
		for (int i = 0; i < graph.length; i++) {
			graph[i].gScore = 10000;
		}
		
		LinkedList<Vertex> closedSet = new LinkedList<Vertex>(); //can switch to hashmap for constant time at some point
		PriorityQueue<Vertex> openSet = new PriorityQueue<Vertex>();
		openSet.add(start);
		HashMap<Vertex, Vertex> cameFrom = new HashMap<Vertex, Vertex>();
		
		start.gScore = 0;
		start.hScore = heuristicCost(start, finish, vg);
		
		Vertex current;
		double tentativeGScore;
		while (!openSet.isEmpty()) {
			current = openSet.poll();
			if (current.equals(finish)) {
				return reconstructPath(cameFrom, finish);
			}
			closedSet.add(current);
			for (Vertex neighbor : current.neighbors) {
				if (closedSet.contains(neighbor)) {
					continue;
				}
				tentativeGScore = current.gScore + 1;
				
				if (!openSet.contains(neighbor) || tentativeGScore < neighbor.gScore) {
					cameFrom.put(neighbor, current);
					neighbor.gScore = tentativeGScore;
					neighbor.hScore = tentativeGScore + heuristicCost(neighbor, finish, vg);
					if (!openSet.contains(neighbor)) {
						openSet.add(neighbor);
					}
				}
			}
		}
		
		return null;
	}
	
	private Path reconstructPath(HashMap<Vertex, Vertex> cameFrom, Vertex current) {
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		LinkedList<DirectionType> directions = new LinkedList<DirectionType>();
		path.addFirst(current);
		while (cameFrom.get(current) != null) {
			directions.addFirst(getDirectionBetween(cameFrom.get(current), current));
			current = cameFrom.get(current);
			path.addFirst(current);
		}
		directions.addFirst(DirectionType.NONE);
		Path totalPath = new Path();
		totalPath.directions = directions;
		totalPath.path = path;
		totalPath.size = path.size();
		return totalPath;
	}
	
	private double heuristicCost(Vertex v, Vertex goal, VertexGraph vg) {
		return Math.sqrt(Math.pow(v.x - goal.x, 2) + Math.pow(v.y - goal.y, 2));
	}
	
	private DirectionType getDirectionBetween(Vertex from, Vertex to) {
		int x = from.x;
		int y = from.y;
		if (to.x > x) { //if it's to the right
			if (to.y > y) {
				return DirectionType.DOWN_RIGHT;
			} else if (to.y < y) {
				return DirectionType.UP_RIGHT;
			} else {
				return DirectionType.RIGHT;
			}
		} else if (to.x < x) { //to the left
			if (to.y > y) {
				return DirectionType.DOWN_LEFT;
			} else if (to.y < y) {
				return DirectionType.UP_LEFT;
			} else {
				return DirectionType.LEFT;
			}
		} else {
			if (to.y > y) {
				return DirectionType.DOWN;
			} else if (to.y < y) {
				return DirectionType.UP;
			} else {
				return DirectionType.NONE;
			}
		}
	}
	
	public static VertexGraph mapToVertexGraph(VertexGraph vg, Map map) {
		int effHeight = map.height - 1;
		int effWidth = map.width - 1;
		vg.effHeight = effHeight;
		vg.effWidth = effWidth;
		
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
			vert.x = TL_x;
			vert.y = TL_y;
			TL = map.getTile(TL_y, TL_x);
			TR = map.getTile(TL_y, TL_x + 1);
			BL = map.getTile(TL_y + 1, TL_x);
			BR = map.getTile(TL_y + 1, TL_x + 1);
			vert.TL = TL;
			vert.TR = TR;
			vert.BL = BL;
			vert.BR = BR;
			vert.index = v;
			assignType(vg, vert, TL, TR, BL, BR);
			//this can be optimized by only passing the vertex and setting TR, TL, etc. directly with the getTile
			
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
			//there will be additional logic once teleporters are added
		}
		
		vg.graph = graph;
		return vg;
	}
	
	private static void assignType(VertexGraph vg, Vertex vert, Tile TL, Tile TR, Tile BL, Tile BR) {
		if (TL.type.groundTraversable && 
			TR.type.groundTraversable && 
			BL.type.groundTraversable && 
			BR.type.groundTraversable) {
			vert.groundTraversable = true;
		}
		
		if (TL.type.airTraversable &&
			TR.type.airTraversable &&
			BL.type.airTraversable &&
			BR.type.airTraversable) {
			vert.airTraversable = true;
		}
		
		if (TL.type == TileType.START &&
			TR.type == TileType.START &&
			BL.type == TileType.START &&
			BR.type == TileType.START) {
			vg.startingVertices.add(vert);
		}
		
		if (TL.type == TileType.FINISH &&
			TR.type == TileType.FINISH &&
			BL.type == TileType.FINISH &&
			BR.type == TileType.FINISH) {
			vg.endingVertices.add(vert);
		}
	}
}
