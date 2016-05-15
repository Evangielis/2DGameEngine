package examples.gridgame;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Vector2d;

import BAG.*;

/**
 * This example class illustrates an agent which moves in standard gridworld fashion.
 * @author Lee Painton
 *
 */
public class PlayerGridMan extends BAGAgent {
	
	public static final String GRIDMAN = "doctor.png";
	
	InputMap inMap;
	Map<Integer, Boolean> keyTriggers;
	
	public PlayerGridMan(String name, BAGame game, BAGDomain domain, int xpos, int ypos, int facing)
	{
		super(name, game, domain, new Texture(GRIDMAN), xpos, ypos, facing);
		
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
		new Move(BAGDomain.ACTIONNORTH, this, domain, GridGame.TILESIZE);
		new Move(BAGDomain.ACTIONSOUTH, this, domain, GridGame.TILESIZE);
		new Move(BAGDomain.ACTIONEAST, this, domain, GridGame.TILESIZE);
		new Move(BAGDomain.ACTIONWEST, this, domain, GridGame.TILESIZE);
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
				this.moveNorth();
				this.keyTriggers.put(KeyEvent.VK_W, false);
			}
		}
		if (inMap.isKeyActive(KeyEvent.VK_A))
		{
			this.keyTriggers.put(KeyEvent.VK_A, true);
		} else {
			if (this.keyTriggers.get(KeyEvent.VK_A))
			{
				this.moveWest();
				this.keyTriggers.put(KeyEvent.VK_A, false);
			}
		}
		if (inMap.isKeyActive(KeyEvent.VK_D))
		{
			this.keyTriggers.put(KeyEvent.VK_D, true);
		} else {
			if (this.keyTriggers.get(KeyEvent.VK_D))
			{
				this.moveEast();
				this.keyTriggers.put(KeyEvent.VK_D, false);
			}
		}
		if (inMap.isKeyActive(KeyEvent.VK_S))
		{
			this.keyTriggers.put(KeyEvent.VK_S, true);
		} else {
			if (this.keyTriggers.get(KeyEvent.VK_S))
			{
				this.moveSouth();
				this.keyTriggers.put(KeyEvent.VK_S, false);
			}
		}
		
		super.update();
	}
}
