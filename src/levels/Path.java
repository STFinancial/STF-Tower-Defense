package levels;

import java.util.LinkedList;

import maps.DirectionType;
import maps.Vertex;

/**
 * List of vertices that a creep will follow on a given level, with helper functions as needed
 */
public class Path {
	/**
	 * Contains a sequence of Vertexes that constitutes the path that each creep will walk during the round.
	 */
	private LinkedList<Vertex> path;
	/**
	 * Contains a sequence of DirectionType that constitutes the direction to take at each step in the path. For example, the direction at index 1 is the direction to take from Vertex 0 to Vertex 1.
	 */
	private LinkedList<DirectionType> directions;
	private int size;
	
	public Path(LinkedList<Vertex> path, LinkedList<DirectionType> directions) {
		this.path = path;
		this.directions = directions;
		this.size = path.size();
		//TODO: Write an exception for if this size is less than 0?
	}
	
	public int getLength() { return size; }
	
	public Vertex getVertex(int pathIndex) {
		if (pathIndex >= size) {
			if (size == 0) {
				//Do we need to check for this?
				return null;
			} else {
				return path.getLast();
			}
		} else {
			return path.get(pathIndex);
		}
	}

	public DirectionType getDirection(int from, int to) {
		if (from > to) {
			if (to < 0) {
				return DirectionType.NONE;
			} else {
				return directions.get(to).getOpposite();
			}
		} else if (from < to) {
			if (to >= size) {
				return DirectionType.NONE;
			} else {
				return directions.get(to);
			}
		} else {
			return DirectionType.NONE;
		}
	}

	public Vertex getFinish() {
		return path.getLast();
	}
	
	public Vertex getFirst() {
		return path.getFirst();
	}
}
