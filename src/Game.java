import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

/**
 * Class represents a discrete instance of a game.
 * @author Lee Painton
 *
 */
public class Game extends JFrame implements Runnable{	
	private static final long serialVersionUID = 1L;
	
	private Thread gameThread;
	private boolean isRunning;
	
	private BufferedImage image;
	public int[] pixels;
	
	Camera camera;
	Painter screen;
	InputMap inputs;
	public InputMap getInputMap() { return inputs; }
	GameDomain domain;
	
	int frameWidth, frameHeight;
	public int getFrameWidth() { return frameWidth; }
	public int getFrameHeight() { return frameHeight; }
	
	//GameObject management
	List<IUpdatable> allUpdatableObjects = new ArrayList<IUpdatable>();
	List<IPaintable> allPaintableObjects = new ArrayList<IPaintable>();
	void updateAllObjects()
	{
		for (IUpdatable obj : allUpdatableObjects)
			obj.update();
	}
	void paintAllObjects(Painter ptr)
	{
		for (IPaintable obj : allPaintableObjects)
			obj.paint(ptr);
	}
	public void add(Object obj)
	{
		if (obj instanceof IUpdatable)
		{
			allUpdatableObjects.add((GameObject)obj);
		}
		if (obj instanceof IPaintable)
		{
			allPaintableObjects.add((GameObject)obj);
		}
	}
	public void remove(Object obj)
	{
		if (obj instanceof IUpdatable)
		{
			allUpdatableObjects.remove(obj);
		}
		if (obj instanceof IPaintable)
		{
			allPaintableObjects.remove(obj);
		}
	}
	
	public PlayerShip ship;
	
	/**
	 * Constructor for Game object.
	 * @param width Resolution width
	 * @param height Resolution height
	 */
	public Game(int width, int height, int dWidth, int dHeight) {
		
		frameWidth = width;
		frameHeight = height;
		
		//Instantiate game thread
		gameThread = new Thread(this);
		
		//Prepare image components for rendering
		image = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		createFrame(frameWidth, frameHeight);
		
		camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
		screen = new Painter(this);
		inputs = new InputMap(this);
		domain = new GameDomain(dWidth, dHeight);
		
		inputs.enableKeys();
		inputs.enableMouse();

		inputs.addKeyCode(KeyEvent.VK_W);
		inputs.addKeyCode(KeyEvent.VK_A);
		inputs.addKeyCode(KeyEvent.VK_D);
		inputs.addKeyCode(KeyEvent.VK_S);
		
		ship = new PlayerShip(this, screen, inputs, new Vector2d(300, 300));
		
		startGame();
	}
	
	private void createFrame(int width, int height)
	{
		setSize(width, height);
		setResizable(false);
		setTitle("2D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private synchronized void startGame() {
		// TODO Auto-generated method stub
		isRunning = true;
		gameThread.start();
	}
	
	public synchronized void stopGame() {
		isRunning = false;
		try {
			gameThread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;//60 times per second
		double delta = 0;
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta = delta + ((now-lastTime) / ns);
			lastTime = now;
			while (delta >= 1)//Make sure update is only happening 60 times a second
			{
				//handles all of the logic restricted time

				camera.update(null);
				InputMap.UpdateAllMaps();
				updateAllObjects();
				paintAllObjects(screen);
				
				screen.paint(camera, pixels);
								
				delta--;
			}
			render();//displays to the screen unrestricted time
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		bs.show();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Game game = new Game(800, 600, 10, 10);
	}
}
