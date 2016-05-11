import java.awt.event.KeyEvent;

import javax.vecmath.Vector2d;


public class PlayerShip extends GameObject {
	
	InputMap inMap;
	Texture tex;
	Vector2d location;
	public static final double SPEED = 2.0;
	
	public PlayerShip(Game game, Painter ptr, InputMap iMap, Vector2d loc)
	{
		super(game);
		
		inMap = iMap;
		this.location = loc;
		tex = new Texture("spaceship.png");
	}
	
	@Override
	public void update()
	{
		Vector2d dv = new Vector2d(0,0);
		
		if (inMap.isKeyActive(KeyEvent.VK_W))
		{
			dv.add(new Vector2d(0,-1));
			//System.out.println("Ship up!");
		}
		if (inMap.isKeyActive(KeyEvent.VK_A))
		{
			dv.add(new Vector2d(-1,0));
			//System.out.println("Ship left!");
		}
		if (inMap.isKeyActive(KeyEvent.VK_D))
		{
			dv.add(new Vector2d(1,0));
			//System.out.println("Ship right!");
		}
		if (inMap.isKeyActive(KeyEvent.VK_S))
		{
			dv.add(new Vector2d(0,1));
			//System.out.println("Ship down!");
		}
		
		if (dv.lengthSquared() > 0)
		{
			dv.normalize();
			dv.scale(SPEED);
			this.location.add(dv);
		}
	}
	
	public void paint(Painter ptr)
	{
		//System.out.println(location.toString());
		ptr.enqueue(Texture.ship, location, 0.1F);
	}
}
