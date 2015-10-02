package utilities;

public class Circle {

	public float radius, x, y;

	public Circle(float x, float y, float radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public boolean intersects(Circle c) {
		return (((x - c.x) * (x - c.x) + (y - c.y) * (y - c.y)) <= ((radius + c.radius) * (radius + c.radius)));
	}
	
	@Override
	public Circle clone() {
		return new Circle(x, y, radius);
	}
}
