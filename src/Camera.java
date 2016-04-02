import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.vecmath.Vector2d;

public class Camera {
	public double xPos, yPos, xDir, yDir, xPlane, yPlane;
	public boolean left, right, forward, back, sleft, sright;
	public final double MOVE_SPEED = .05;
	public final double ROTATION_SPEED = .045;
	
	Vector2d position;
	public double getXPos() { return position.x; }
	public double getYPos() { return position.y; }
	Vector2d facing;
	public double getXFacing() { return facing.x; }
	public double getYFacing() { return facing.y; }
	Vector2d plane;
	public double getXPlane() { return plane.x; }
	public double getYPlane() { return plane.y; }
	
	public Camera(double x, double y, double xd, double yd, double xp, double yp) 
	{	
		xPos = x;
		yPos = y;
		xDir = xd;
		yDir = yd;
		xPlane = xp;
		yPlane = yp;
		
		position = new Vector2d(x, y);
		facing = new Vector2d(xd, yd);
		plane = new Vector2d(xp, yp);
	}

	public void update(int[][] map) {

	}
	
	public Boolean CheckCollision(int[][] map, Vector2d pos)
	{
		return map[(int)pos.x][(int)pos.y] == 0;
	}
	
	public void TryMove(int[][] map, Vector2d newpos)
	{
		if (CheckCollision(map, newpos))
		{
			xPos = newpos.x;
			yPos = newpos.y;
			this.position = newpos;
		} else {
			Vector2d vx = new Vector2d(newpos.x, position.y);
			Vector2d vy = new Vector2d(position.x, newpos.y);
			if (CheckCollision(map, vx))
			{
				xPos = newpos.x;
				yPos = position.y;
				this.position = vx;
			} 
			else if (CheckCollision(map, vy))
			{
				xPos = position.x;
				yPos = newpos.y;
				this.position = vy;
			}
		}
	}
	
	public void MoveForward(int[][] map)
	{
		Vector2d n = new Vector2d(position.x, position.y);
		Vector2d d = new Vector2d(facing.x, facing.y);
		d.normalize();
		d.scale(MOVE_SPEED);
		n.add(d);		
		TryMove(map, n);
	}
	
	public void MoveBackward(int[][] map)
	{
		Vector2d n = new Vector2d(position.x, position.y);
		Vector2d d = new Vector2d(xDir, yDir);
		d.normalize();
		d.scale(-1 * MOVE_SPEED);
		n.add(d);		
		TryMove(map, n);
	}
	
	public void SlideRight(int[][] map)
	{
		Vector2d n = new Vector2d(position.x, position.y);
		Vector2d p = new Vector2d(xDir, yDir);
		Vector2d lv = Vector2dHelper.getRightOrthagonal(p);
		lv.normalize();
		lv.scale(MOVE_SPEED);
		n.add(lv);
		TryMove(map, n);
	}
	
	public void SlideLeft(int[][] map)
	{
		Vector2d n = new Vector2d(position.x, position.y);
		Vector2d p = new Vector2d(xDir, yDir);
		Vector2d lv = Vector2dHelper.getLeftOrthagonal(p);
		lv.normalize();
		lv.scale(MOVE_SPEED);
		n.add(lv);
		TryMove(map, n);
	}
	
	public void TurnLeft()
	{
		Vector2d o = new Vector2d(xDir, yDir);
		Vector2d n = Vector2dHelper.rotate(o, ROTATION_SPEED);
		facing = n;
		xDir = n.x;
		yDir = n.y;
		
		o = new Vector2d(xPlane, yPlane);
		n = Vector2dHelper.rotate(o, ROTATION_SPEED);
		plane = n;
		xPlane = n.x;
		yPlane = n.y;
	}
	
	public void TurnRight()
	{
		Vector2d o = new Vector2d(xDir, yDir);
		Vector2d n = Vector2dHelper.rotate(o, -1 * ROTATION_SPEED);
		facing = n;
		xDir = n.x;
		yDir = n.y;
		
		o = new Vector2d(xPlane, yPlane);
		n = Vector2dHelper.rotate(o, -1 * ROTATION_SPEED);
		plane = n;
		xPlane = n.x;
		yPlane = n.y;
	}
	
}
