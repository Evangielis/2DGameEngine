package examples.shipgame;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Vector2d;

import BAG.*;

/**
 * This example class illustrates an agent which moves based on its orientation.
 * @author Lee Painton
 *
 */
public class PlayerShip extends BAGAgent {
	
	InputMap inMap;
	Map<Integer, Boolean> keyTriggers;
	
	public static Texture[] textures;
	
	public static Texture getTexture(int facing)
	{
		if (textures == null)
		{
			textures = new Texture[4];
			textures[BAGDomain.DIRECTIONEAST] = new Texture("spaceship.png");
			textures[BAGDomain.DIRECTIONNORTH] = textures[BAGDomain.DIRECTIONEAST].getCounterRotation();
			textures[BAGDomain.DIRECTIONWEST] = textures[BAGDomain.DIRECTIONNORTH].getCounterRotation();
			textures[BAGDomain.DIRECTIONSOUTH] = textures[BAGDomain.DIRECTIONWEST].getCounterRotation();
		}
		return textures[facing];
	}
	
	public PlayerShip(String name, BAGame game, BAGDomain domain, int xpos, int ypos, int facing)
	{
		super(name, game, domain, getTexture(facing), xpos, ypos, facing);
		
		inMap = game.getInputMap();
		inMap.enableKeys();
		inMap.addKeyCode(KeyEvent.VK_W);
		inMap.addKeyCode(KeyEvent.VK_A);
		inMap.addKeyCode(KeyEvent.VK_D);
		inMap.addKeyCode(KeyEvent.VK_S);
		
		keyTriggers = new HashMap<Integer, Boolean>();
		keyTriggers.put(KeyEvent.VK_W, false);
		keyTriggers.put(KeyEvent.VK_A, false);
		keyTriggers.put(KeyEvent.VK_D, false);
		keyTriggers.put(KeyEvent.VK_S, false);
		
		//Declare BURLAP actions
		new Turn(BAGDomain.ACTIONTURNLEFT, this, domain);
		new Turn(BAGDomain.ACTIONTURNRIGHT, this, domain);
		new Move(BAGDomain.ACTIONFORWARD, this, domain, 20);
		new Move(BAGDomain.ACTIONBACKWARD, this, domain, 10); 
	}
	
	@Override
	public void update()
	{		
		if (inMap.isKeyActive(KeyEvent.VK_W))
		{
			this.keyTriggers.put(KeyEvent.VK_W, true);
		} else {
			if (this.keyTriggers.get(KeyEvent.VK_W))
			{
				this.moveForward();
				this.keyTriggers.put(KeyEvent.VK_W, false);
			}
		}
		if (inMap.isKeyActive(KeyEvent.VK_A))
		{
			this.keyTriggers.put(KeyEvent.VK_A, true);
		} else {
			if (this.keyTriggers.get(KeyEvent.VK_A))
			{
				this.turnLeft();
				this.keyTriggers.put(KeyEvent.VK_A, false);
			}
		}
		if (inMap.isKeyActive(KeyEvent.VK_D))
		{
			this.keyTriggers.put(KeyEvent.VK_D, true);
		} else {
			if (this.keyTriggers.get(KeyEvent.VK_D))
			{
				this.turnRight();
				this.keyTriggers.put(KeyEvent.VK_D, false);
			}
		}
		if (inMap.isKeyActive(KeyEvent.VK_S))
		{
			this.keyTriggers.put(KeyEvent.VK_S, true);
		} else {
			if (this.keyTriggers.get(KeyEvent.VK_S))
			{
				this.moveBackward();
				this.keyTriggers.put(KeyEvent.VK_S, false);
			}
		}
		
		tex = textures[myFacing];
		
		super.update();
	}
}
