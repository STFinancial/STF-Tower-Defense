package utilities;

public class TrigHelper {
	public static float angleBetween(float x1, float y1, float x2, float y2){
		float dX = x1 - x2;
		float dY = y1 - y2;
		return (float) Math.atan2(dY, dX);
	}
	
	public static double pythagDistance(double x, double y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
}
