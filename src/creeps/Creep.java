package creeps;

import java.util.HashSet;

import levels.Path;
import maps.DirectionType;
import maps.Vertex;

public class Creep {
	//we can decide how we want these stats to be
	public int health;
	public int armor;
	public int earthResist, fireResist, windResist, waterResist;
	public int slowResist;
	
	public ElementType elementType;
	HashSet<CreepType> creepTypes = new HashSet<CreepType>();
	
	public Vertex currentVertex;
	public Vertex nextVertex;
	public DirectionType direction;
	public float xOff, yOff;
	public float speed; //In Tiles per Tick (Imagining 30 - 50 being a normal speed)
	public Path path;
	public int pathIndex;
	
	public void setLocation(int index) {
		currentVertex = path.getVertex(0);
		xOff = 0;
		yOff = 0;
	}
	
	public void setDestination(int index){
		nextVertex = path.getVertex(index);
		direction = path.getDirection(index);
	}
	
	public void updateMovement(){
		xOff = direction.x * speed;
		yOff = direction.y * speed;
		if(xOff > 1 || yOff > 1){
			//Back step, figure out how much speed was spent for movement
			float speedRemaining = 0f;
			
			currentVertex = nextVertex;
			setDestination(pathIndex);

			xOff = direction.x * speedRemaining;
			yOff = direction.y * speedRemaining;
		}
	}

	public void setPath(Path path) {
		this.path = path;
		setLocation(0);
		setDestination(1);
	}
	
	
}
