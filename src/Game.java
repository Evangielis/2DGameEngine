import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

public class Game extends JFrame implements Runnable{	
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	public int[] pixels;
	public Camera camera;
	public Painter screen;
	public InputMap inputs;
	
	public int resWidth = 800;
	public int resHeight = 600;
	
	public Game() {
		thread = new Thread(this);
		
		image = new BufferedImage(resWidth, resHeight, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		setSize(resWidth, resHeight);
		setResizable(false);
		setTitle("2D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		
		
		camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
		screen = new Painter(resWidth, resHeight);
		inputs = new InputMap();
		
		inputs.enableKeys(1);
		inputs.enableMouse();
		addKeyListener(inputs);
		addMouseListener(inputs);
		addMouseMotionListener(inputs);
		inputs.addKeyCode(KeyEvent.VK_W);
		inputs.addKeyCode(KeyEvent.VK_A);
		inputs.addKeyCode(KeyEvent.VK_D);
		inputs.addKeyCode(KeyEvent.VK_S);
		
		start();
	}
	
	private synchronized void start() {
		// TODO Auto-generated method stub
		running = true;
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
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
		while(running) {
			long now = System.nanoTime();
			delta = delta + ((now-lastTime) / ns);
			lastTime = now;
			while (delta >= 1)//Make sure update is only happening 60 times a second
			{
				//handles all of the logic restricted time

				camera.update(null);
				screen.enqueue(Texture.ship, new Vector2d(300,300), 0.1F);
				/*for (int i = 0; i<1000; i++)
				{
					try
					{
						screen.enqueue(Texture.stone, new Vector2d((Math.random()*(resWidth)),(Math.random()*(resHeight))), (float)Math.random());
					}
					catch (ArrayIndexOutOfBoundsException e)
					{
						e.printStackTrace();
					}
				}*/
				screen.paint(camera, pixels);
				
				
				inputs.update();					
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
		
		Game game = new Game();
	}
}
