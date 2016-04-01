import javax.vecmath.Vector2d;

public abstract class Vector2dHelper {
	
	public static Vector2d rotate(Vector2d vec, double angle)
	{
		double newx = (vec.x * Math.cos(angle)) - (vec.y * Math.sin(angle));
	    double newy = (vec.x * Math.sin(angle)) + (vec.y * Math.cos(angle));
	    
	    return new Vector2d(newx, newy);
	}
	
	public static Vector2d getLeftOrthagonal(Vector2d vec)
	{
		return rotate(vec, 0.5 * Math.PI);
	}
	
	public static Vector2d getRightOrthagonal(Vector2d vec)
	{
		return rotate(vec, -0.5 * Math.PI);
	}
	
	public static Vector2d translate(Vector2d vec, Vector2d trans)
	{
		return new Vector2d();
	}
}
