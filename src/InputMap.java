import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.vecmath.Vector2d;

public class InputMap implements KeyListener, MouseListener, MouseMotionListener {
	
	Boolean mouseEnabled, keyEnabled;
	Set<Integer> keyCodes;
	Map<Integer, Integer> activeCodes;
	int keyLatency;
	
	public InputMap()
	{
		keyCodes = new HashSet<Integer>();
		activeCodes = new HashMap<Integer, Integer>();
	}
	
	public void enableMouse() { mouseEnabled = true; }
	public void disableMouse() { mouseEnabled = false; }
	public void enableKeys(int keyLatency) 
	{ 
		keyEnabled = true;
		this.keyLatency = keyLatency;
	}
	public void disableKeys() { keyEnabled = false; }
	
	public void update()
	{
		if (keyEnabled)
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
		}
	}
	
	public void addKeyCode(int code) 
	{ 
		keyCodes.add(code);
		activeCodes.put(code, 0);
	}
	public void removeKeyCode(int code) 
	{ 
		keyCodes.remove(code); 
		activeCodes.remove(code);
	}
	public Boolean isKeyActive(int code) 
	{ 
		return activeCodes.get(code) > 0;
	}

	@Override
	public void keyPressed(KeyEvent key) {
		// TODO Auto-generated method stub
		if (keyEnabled)
		{
			int code = key.getKeyCode();
			if (keyCodes.contains(code))
			{
				System.out.println("Key code: " + code);
				activeCodes.put(code, keyLatency);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub
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
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Mouse clicked " + arg0.getX() + " " + arg0.getY());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Mouse entered");
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Mouse exited");
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
}
