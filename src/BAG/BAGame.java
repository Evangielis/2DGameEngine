package BAG;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
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
public class BAGame extends JFrame implements Runnable{	
	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULTTITLE = "BAG Game";
	
	public static final String RENDERMODENORMAL = "normal";
	public static final String RENDERMODEMAP = "raycast";
	
	private Thread gameThread;
	private boolean isRunning;
	
	private BufferedImage image;
	public int[] pixels;
	
	String name;
	public String getName() { return name; }
	public void setName(String name) 
	{ 
		this.name = name;
		setTitle(name);
	} 
	Painter screen;
	public Painter getPainter() { return screen; }
	InputMap inputs;
	public InputMap getInputMap() { return inputs; }
	CollisionMap cmap;
	public CollisionMap getCollisionMap() { return cmap; }
	BAGMap gmap;
	public BAGMap getGameMap() { return gmap; }
	
	public String renderMode;
	
	int frameWidth, frameHeight;
	public int getFrameWidth() { return frameWidth; }
	public int getFrameHeight() { return frameHeight; }
	
	//Game component management
	List<InputMap> allInputMaps = new ArrayList<InputMap>();
	List<BAGTrigger> allTriggers = new ArrayList<BAGTrigger>();
	List<IUpdatable> allUpdatableObjects = new ArrayList<IUpdatable>();
	List<IPaintable> allPaintableObjects = new ArrayList<IPaintable>();	
	void evalAllTriggers()
	{
		for (BAGTrigger t : allTriggers)
			t.evaluate();
	}
	void resolveAllTriggers()
	{
		List<BAGTrigger> tlist = new ArrayList<BAGTrigger>();
		for (BAGTrigger t : allTriggers)
		{
			if (t.isSatisfied())
				tlist.add(t);
		}
		for (BAGTrigger t : tlist)
		{
			t.resolve();
		}
	}
	void updateAllInputMaps()
	{
		for (InputMap obj : allInputMaps)
			obj.update();
	}
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
		if (obj instanceof BAGMap)
		{
			//System.out.println("Adding map");
			gmap = (BAGMap)obj;
			gmap.generateMap();
			return;
		}
		if (obj instanceof InputMap)
		{
			allInputMaps.add((InputMap)obj);
			return;
		}
		if (obj instanceof BAGTrigger)
		{
			allTriggers.add((BAGTrigger)obj);
			return;
		}
		if (obj instanceof IUpdatable)
		{
			allUpdatableObjects.add((BAGObject)obj);
		}
		if (obj instanceof IPaintable)
		{
			allPaintableObjects.add((BAGObject)obj);
		}
	}
	public void remove(Object obj)
	{
		if (obj instanceof BAGMap)
		{
			gmap = null;
			return;
		}
		if (obj instanceof InputMap)
		{
			allInputMaps.remove(obj);
			return;
		}
		if (obj instanceof BAGTrigger)
		{
			allTriggers.remove(obj);
			return;
		}
		if (obj instanceof IUpdatable)
		{
			allUpdatableObjects.remove(obj);
		}
		if (obj instanceof IPaintable)
		{
			allPaintableObjects.remove(obj);
		}
	}
	
	/**
	 * Constructor for Game object.
	 * @param width Resolution width
	 * @param height Resolution height
	 */
	public BAGame(int resolutionWidth, int resolutionHeight, String renderMode) {
		name = DEFAULTTITLE;
		
		frameWidth = resolutionWidth;
		frameHeight = resolutionHeight;
		
		this.renderMode = renderMode;
		
		//Instantiate game thread
		gameThread = new Thread(this);
		
		//Prepare image components for rendering
		image = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		createFrame(frameWidth, frameHeight);
		
		//camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
		screen = new Painter(this);
		inputs = new InputMap(this);
		cmap = new CollisionMap();
	}
	
	public void enableMouse()
	{
		inputs.enableMouse();
	}
	public void enableKeys()
	{
		inputs.enableKeys();
	}
	
	private void createFrame(int width, int height)
	{
		setSize(width, height);
		setResizable(false);
		setTitle(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public synchronized void startGame() {
		if (!isRunning)
		{
			isRunning = true;
			gameThread.start();
		}
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
		long lastTime = System.nanoTime();
		double ns = 1000000000.0 / 60.0;//60 times per second
		double delta = 0;
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta = delta + ((now-lastTime) / ns);
			lastTime = now;
			//Updating 60 per second at most
			while (delta >= 1)
			{
				//Handles all of the logic restricted time

				//System.out.println("Update...");
				updateAllInputMaps();
				updateAllObjects();
				cmap.update();
				
				//Handle triggers
				evalAllTriggers();
				resolveAllTriggers();
				
				delta--;
			}
			
			//Prepare for rendering
			render();
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
			
		if (renderMode == RENDERMODENORMAL)
		{
			paintAllObjects(screen);
			pixels = screen.paint(pixels);
		} else if (renderMode == RENDERMODEMAP && gmap != null) {
			pixels = gmap.renderMap(pixels);
		}

		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		screen.setType(g);
		bs.show();
	}
}
