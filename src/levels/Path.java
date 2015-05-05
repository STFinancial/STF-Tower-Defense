package levels;

import java.util.LinkedList;

import maps.DirectionType;
import maps.Vertex;

/*
 * List of vertices that a creep will follow on a given level, with helper functions as needed
 */
public class Path {

	LinkedList<Vertex> path;
	LinkedList<DirectionType> directions; //Direction to vertex
	int size;
	
	public Vertex getVertex(int pathIndex) {
		if(pathIndex >= size){
			if(size == 0){
				//Do we need to check for this?
				return null;
			}else{
				return path.getLast();
			}
		}else{
			return path.get(pathIndex);
		}
	}

	public DirectionType getDirection(int pathIndex) {
		if(pathIndex >= size){
			return DirectionType.NONE;
		}else{
			return directions.get(pathIndex);
		}
	}

}
