package utilities;

public class TrigHelper {
	public static float angleBetween(float x1, float y1, float x2, float y2){
		float dX = x1 - x2;
		float dY = y1 - y2;
		return (float) Math.atan2(dY, dX);
	}
}
