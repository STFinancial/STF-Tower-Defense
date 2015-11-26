package levels;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {

	public int width = 40;
	public int height = 30;
	public double primsFactor = .15;
	public Random r;
	public int tries = 100;

	public double trimFactor = .1; // chance a TileType.VOID is trimmed
	public int trimSize = 25; // Number of blocks trimmed
	public double bulkFactor = .3; // Chance a TileType.VOID is extended
	public int bulkSize = 1; // Number of blocks added

	public Map generateMap() {
		r = new Random();
		Maze maze = new Maze(width, height, r, .15);
		trim(maze);
		bulk(maze);
		placeEdges(maze);
		placeGates(maze);
		Map map = mapFromMaze(maze);
		VertexGraph vg = new VertexGraph();
		vg = PathFinder.mapToVertexGraph(vg, map);
		//TODO: Need to do an air path check
		Path groundPath = PathFinder.AStar(vg.startingVertices.get(0), vg.endingVertices.get(0), vg, true);
		if (groundPath == null && tries > 0) {
			tries--;
			return generateMap();
		} else {
			tries = 100;
			return map;
		}
	}

	private Map mapFromMaze(Maze maze) {
		Map map = new Map(width, height);
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(maze.maze[y][x] == null){
					map.setTile(y, x, TileType.LAND);
				}else{
					map.setTile(y, x, maze.maze[y][x]);
				}
			}
		}
		return map;
	}

	private void trim(Maze m) {
		for (int x = 0; x < m.width; x++) {
			for (int y = 0; y < m.height; y++) {
				if (r.nextDouble() < trimFactor && m.maze[y][x] == TileType.VOID) {
					int oldX = x;
					int oldY = y;

					for (int i = 0; i < trimSize; i++) {
						m.maze[y][x] = TileType.LAND;
						Directions d = Directions.grabRandom(r);
						x += d.x;
						y += d.y;
						if (x < 0) {
							x = 0;
						}
						if (y < 0) {
							y = 0;
						}
						if (x >= m.width) {
							x = m.width - 1;
						}
						if (y >= m.height) {
							y = m.height - 1;
						}
					}

					x = oldX;
					y = oldY;
				}
			}
		}
	}

	private void bulk(Maze m) {
		for (int x = 0; x < m.width; x++) {
			for (int y = 0; y < m.height; y++) {
				if (r.nextDouble() < bulkFactor && m.maze[y][x] == TileType.VOID) {
					int oldX = x;
					int oldY = y;

					for (int i = 0; i < bulkSize; i++) {
						Directions d = Directions.grabRandom(r);
						x += d.x;
						y += d.y;
						if (x < 0) {
							x = 0;
						}
						if (y < 0) {
							y = 0;
						}
						if (x >= m.width) {
							x = m.width - 1;
						}
						if (y >= m.height) {
							y = m.height - 1;
						}

						m.maze[y][x] = TileType.VOID;
					}

					x = oldX;
					y = oldY;
				}
			}
		}
	}

	private void placeEdges(Maze m) {

		for (int x = 0; x < m.width; x++) {
			m.maze[0][x] = TileType.EDGE;
			m.maze[m.height - 1][x] = TileType.EDGE;
		}

		for (int y = 0; y < m.height; y++) {
			m.maze[y][0] = TileType.EDGE;
			m.maze[y][m.width - 1] = TileType.EDGE;
		}

	}

	private void placeGates(Maze m) {
		m.maze[1][1] = TileType.START;
		m.maze[1][2] = TileType.START;
		m.maze[2][1] = TileType.START;
		m.maze[2][2] = TileType.START;
		m.maze[m.height - 2][m.width - 2] = TileType.FINISH;
		m.maze[m.height - 2][m.width - 3] = TileType.FINISH;
		m.maze[m.height - 3][m.width - 2] = TileType.FINISH;
		m.maze[m.height - 3][m.width - 3] = TileType.FINISH;

	}
	
	public void setMazeMode(){
		setNoTrim();
		setNoBulk();
	}
	
	public void setNoTrim(){
		trimFactor = 0;
		trimSize = 0;
	}
	
	public void setNoBulk(){
		bulkFactor = 0;
		bulkSize = 0;
	}
	
	public void increaseTrim(){
		trimSize++;
		trimFactor *= 1.1;
	}
	
	public void increaseBulk(){
		bulkSize++;
		bulkFactor *= 1.1;
	}
	
	public void decreaseTrim(){
		trimSize--;
		if(trimSize < 0){
			trimSize = 0;
		}
		trimFactor *= .9;
	}
	
	public void decreaseBulk(){
		bulkSize--;
		if(bulkSize < 0){
			bulkSize = 0;
		}
		bulkFactor *= .9;
	}

	public class Maze {
		
		TileType[][] maze;
		Cell[][] cellMap;
		int width, height;
		private ArrayList<Cell> cells;

		int cellMapHeight, cellMapWidth;
		private Random r;
		private double primsFactor;

		public Maze(int width, int height, Random r){
			this(width, height, r, 0);
		}
		
		public Maze(int width, int height, Random r, double primsFactor) {
			cellMapHeight = (height - 1) / 3;
			cellMapWidth = (width - 1) / 3;
			cellMap = new Cell[cellMapHeight][cellMapWidth];
			maze = new TileType[height][width];
			cells = new ArrayList<Cell>();

			this.r = r;
			this.width = width;
			this.height = height;

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if ((i % 3 == 1 && j % 3 == 1)) {
						if (i < height - 2 && j < width - 2) {
							Cell c = new Cell(j / 3, i / 3);
							cellMap[i / 3][j / 3] = c;
						}
					}
				}
			}
			
			this.primsFactor = primsFactor;

			updateCells();
			paintMaze();
		}
		
		private void updateCells() {
			// pick random start
			Cell currentCell, newCell;
			newCell = cellMap[r.nextInt(cellMapHeight)][r.nextInt(cellMapWidth)];
			cells.add(newCell);
			newCell.visited = true;

			while (!cells.isEmpty()) {
				if(r.nextDouble() > primsFactor){
					currentCell = cells.get(cells.size() - 1);
				}else{
					currentCell = cells.get(r.nextInt(cells.size()));
				}
				newCell = grabNeighbor(currentCell);
				if (newCell == null) {
					cells.remove(currentCell);
				} else {
					newCell.visited = true;
					int x = newCell.x - currentCell.x;
					int y = newCell.y - currentCell.y;
					if (x == 0) {
						if (y > 0) {
							currentCell.down = true;
						} else {
							newCell.down = true;
						}
					} else {
						if (x > 0) {
							currentCell.right = true;
						} else {
							newCell.right = true;
						}
					}
					cells.add(newCell);
				}
			}

		}

		private Cell grabNeighbor(Cell cell) {
			ArrayList<Cell> neighbors = new ArrayList<Cell>(4);
			Cell c;

			for (Directions d : Directions.values()) {
				if (neighborExist(cell, d)) {
					c = grabNeighbor(cell, d);
					if (!c.visited) {
						neighbors.add(c);
					}
				}
			}
			if (neighbors.isEmpty()) {
				cell.done = true;
				return null;
			} else {
				return neighbors.get(r.nextInt(neighbors.size()));
			}

		}

		private Cell grabNeighbor(Cell cell, Directions d) {
			int x = cell.x + d.x;
			int y = cell.y + d.y;
			return cellMap[y][x];
		}

		private boolean neighborExist(Cell cell, Directions d) {
			int x = cell.x + d.x;
			int y = cell.y + d.y;
			return (x >= 0 && x < cellMapWidth && y >= 0 && y < cellMapHeight);
		}
		
		private void paintMaze() {
			for (int i = 0; i < cellMapHeight; i++) {
				for (int j = 0; j < cellMapWidth; j++) {
					Cell c = cellMap[i][j];
					maze[c.mapY + 2][c.mapX + 2] = TileType.VOID;
					if (!c.right) {
						maze[c.mapY + 1][c.mapX + 2] = TileType.VOID;
						maze[c.mapY + 0][c.mapX + 2] = TileType.VOID;
					} else {
						maze[c.mapY + 1][c.mapX + 2] = TileType.LAND;
						maze[c.mapY + 0][c.mapX + 2] = TileType.LAND;
					}
					if (!c.down) {
						maze[c.mapY + 2][c.mapX + 0] = TileType.VOID;
						maze[c.mapY + 2][c.mapX + 1] = TileType.VOID;
					} else {
						maze[c.mapY + 2][c.mapX + 0] = TileType.LAND;
						maze[c.mapY + 2][c.mapX + 1] = TileType.LAND;
					}
				}
			}
		}
		
		
		public String toString() {
			String toReturn = "";
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					toReturn += maze[i][j];
					toReturn += ",";
				}
				toReturn += "\n";
			}
			return toReturn;
		}
	}
	
	public class Cell {
		String [][] cell;
		
		public boolean left, right, up, down;
		int x, y; //Cell Coordinates
		int mapX, mapY;
		
		public boolean visited = false;
		public boolean done = false;
		
		public Cell(int x, int y){
			this.x = x;
			this.y = y;
			mapX = x * 3 + 1;
			mapY = y * 3 + 1;
		}
		
		public String toString(){
			return (x + " , " + y + " " + right + " " + up + " " + left+ " " + down);
		}
	}
	
	public enum Directions {
		LEFT(-1,0), RIGHT(1,0), UP(0,-1), DOWN(0,1);
		
		
		int x, y;
		
		Directions(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		static Directions grabRandom(Random r){
			return Directions.values()[r.nextInt(4)];
		}
	}
	
}



