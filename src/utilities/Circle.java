package utilities;

public class Circle {
	private float radius;
	private float x;
	private float y;

	public Circle(float x, float y, float radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public float getRadius() { 
		return radius;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

	public boolean intersects(Circle c) {
		return (((x - c.x) * (x - c.x) + (y - c.y) * (y - c.y)) <= ((radius + c.radius) * (radius + c.radius)));
	}
	
	@Override
	public Circle clone() {
		return new Circle(x, y, radius);
	}
}
