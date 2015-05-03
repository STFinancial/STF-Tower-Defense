package maps;
/*
 * Helper class that contains information about area between 4 tiles, used primarily in pathing
 */
public class Vertex {
	int x, y;
	Tile upRight, upLeft, downRight, downLeft;
}
