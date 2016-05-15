package BAG;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.vecmath.Vector2d;

/**
 * Class is an event-based mapping between inputs and game window.
 * @author Lee Painton
 *
 */
public class InputMap implements KeyListener, MouseListener, MouseMotionListener {
	
	public static List<InputMap> AllMaps = new ArrayList<InputMap>();
	public static void UpdateAllMaps()
	{
		for (InputMap map : AllMaps)
		{
			map.update();
		}
	}
	
	Boolean mouseEnabled, keyEnabled;
	Set<Integer> keyCodes;
	Map<Integer, Boolean> activeCodes;
	Vector2d mousePos, lastClick;
	Boolean mousePresent, mouseDown, mouseDrag;
	
	public InputMap(BAGame game)
	{
		game.add(this);
		keyCodes = new HashSet<Integer>();
		activeCodes = new HashMap<Integer, Boolean>();
		
		game.addKeyListener(this);
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
		
		mouseEnabled = false;
		keyEnabled = false;
	}
	
	public void dispose()
	{
		AllMaps.remove(this);
	}
	
	public void enableMouse() { mouseEnabled = true; }
	public void disableMouse() { mouseEnabled = false; }
	public void enableKeys() 
	{ 
		keyEnabled = true;
	}
	public void disableKeys() { keyEnabled = false; }
	
	public void update()
	{
		/*if (keyEnabled)
		{
			for (int code : activeCodes.keySet())
			{
				int val = activeCodes.get(code); 
				if (val > 0)
				{
					//System.out.println("Code timing out: " + code + "-" + val);
					activeCodes.put(code, val - 1);
				}
			}
		}*/
	}
	
	public void addKeyCode(int code) 
	{ 
		keyCodes.add(code);
		activeCodes.put(code, false);
	}
	public void removeKeyCode(int code) 
	{ 
		keyCodes.remove(code); 
		activeCodes.remove(code);
	}
	public Boolean isKeyActive(int code) 
	{ 
		return keyEnabled && activeCodes.get(code);
	}
	
	public Vector2d getMouseLoc() { return mousePos; }
	public Vector2d getClickLoc() { return lastClick; }
	public Boolean isMouseDragging() { return mouseDrag; }
	public Boolean isMouseDown() { return mouseDown; }
	public Boolean isMousePresent() { return mousePresent; }
	

	@Override
	public void keyPressed(KeyEvent key) {
		// TODO Auto-generated method stub
		if (keyEnabled)
		{
			int code = key.getKeyCode();
			if (keyCodes.contains(code))
			{
				//System.out.println("Key code: " + code);
				activeCodes.put(code, true);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub
		if (keyEnabled)
		{
			int code = key.getKeyCode();
			if (keyCodes.contains(code))
			{
				//System.out.println("Key code: " + code);
				activeCodes.put(code, false);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {
		// TODO Auto-generated method stub
	}
	
	public void update(int[][] map) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (mouseEnabled)
		{
			mouseDrag = true;
			//System.out.println("Mouse dragged!");
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (mouseEnabled) 
		{
			mousePos = new Vector2d(arg0.getX(), arg0.getY());
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (mouseEnabled)
		{
			//System.out.println("Mouse clicked " + arg0.getX() + " " + arg0.getY());
			lastClick = new Vector2d(arg0.getX(), arg0.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (mouseEnabled)
		{
			//System.out.println("Mouse entered");
			mousePresent = true;
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (mouseEnabled)
		{
			//System.out.println("Mouse exited");
			mousePresent = false;
		}
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (mouseEnabled)
		{
			//System.out.println("Mouse down!");
			mouseDown = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (mouseEnabled)
		{
			//System.out.println("Mouse released!");
			mouseDown = false;
			mouseDrag = false;
		}
	}	
}
