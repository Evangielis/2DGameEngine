import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.vecmath.Vector2d;

public class InputMap implements KeyListener, MouseListener, MouseMotionListener {
	
	Boolean mouseEnabled, keyEnabled;
	ArrayList<KeyEvent> keysMonitored;
	
	public InputMap()
	{
		keysMonitored = new ArrayList<KeyEvent>();
	}
	
	public void enableMouse() { mouseEnabled = true; }
	public void disableMouse() { mouseEnabled = false; }
	public void enableKeys() { keyEnabled = true; }
	public void disableKeys() { keyEnabled = false; }
	
	public void update()
	{
		
	}

	@Override
	public void keyPressed(KeyEvent key) {
		// TODO Auto-generated method stub
		/*if((key.getKeyCode() == KeyEvent.VK_Q))
			left = true;
		if((key.getKeyCode() == KeyEvent.VK_E))
			right = true;
		if((key.getKeyCode() == KeyEvent.VK_A))
			sleft = true;
		if((key.getKeyCode() == KeyEvent.VK_D))
			sright = true;
		if((key.getKeyCode() == KeyEvent.VK_W))
			forward = true;
		if((key.getKeyCode() == KeyEvent.VK_S))
			back = true;*/
	}

	@Override
	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub
		/*if((key.getKeyCode() == KeyEvent.VK_Q))
			left = false;
		if((key.getKeyCode() == KeyEvent.VK_E))
			right = false;
		if((key.getKeyCode() == KeyEvent.VK_A))
			sleft = false;
		if((key.getKeyCode() == KeyEvent.VK_D))
			sright = false;
		if((key.getKeyCode() == KeyEvent.VK_W))
			forward = false;
		if((key.getKeyCode() == KeyEvent.VK_S))
			back = false;*/
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
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
