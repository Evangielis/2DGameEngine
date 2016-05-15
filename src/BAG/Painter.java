package BAG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.vecmath.Vector2d;

/**
 * Handles all graphical functions for the game engine.
 * Note that text is always drawn over other graphics.
 * @author Lee Painton
 */
public class Painter {
	
	public static float LAYERBACKGROUND = 500.0f;
	public static float LAYERWALLS = 400.0f;
	public static float LAYERLOCATIONS = 300.0f;
	public static float LAYERAGENTS = 200.0f;
	public static float LAYERUI = 100.0f;
	
	private class PaintJob
	{
		Texture tex;
		int[] getRaster() { return tex.getRaster(); }
		float layer;
		float getLayer() { return layer; }
		Vector2d location;
		Vector2d getLoc() { return location; }
		
		public PaintJob(Texture tex, Vector2d loc, float layer)
		{
			this.tex = tex;
			this.location = loc;
			this.layer = layer;
		}
	}
	
	private class TypeJob
	{
		String text;
		public String getText() { return text; }
		int x, y, size;
		public int getX() { return x; }
		public int getY() { return y; }
		public int getSize() { return size; }
		Color color;		
		public Color getColor() { return color; }
		boolean centered;
		public boolean isCentered() { return centered; }
		
		public TypeJob(String text, Color col, int xpos, int ypos, int size, boolean centered)
		{
			this.text = text;
			this.color = col;
			this.x = xpos;
			this.y = ypos;
			this.size = size;
			this.centered = centered;
		}
	}
	
    int width, height;
    Rectangle canvasArea;
    PriorityQueue<PaintJob> jobQueue;
    PriorityQueue<TypeJob> typeQueue;
    BAGame myGame;
    Color floorColor, ceilingColor;
    public void setFloorColor(Color col) { floorColor = col; }
    public void setCeilingColor(Color col) { ceilingColor = col; }
    public void setBackgroundcolor(Color col)
    {
    	setFloorColor(col);
    	setCeilingColor(col);
    }
	Texture backgroundTex;
	public void setBackgroundTexture(Texture t)
	{
		this.backgroundTex = t.getTiled(myGame.frameWidth, myGame.frameHeight);
	}
	public void removeBackgroundTexture() { backgroundTex = null; }
    
    public Painter(BAGame myGame) {
    	this.myGame = myGame;
        width = myGame.frameWidth;
        height = myGame.frameHeight;
        canvasArea = new Rectangle(0, 0, width, height);
        backgroundTex = null;
        
        floorColor = Color.gray;
        ceilingColor = Color.gray;
        
        Comparator<PaintJob> comp = new Comparator<PaintJob>() {
			@Override
			public int compare(PaintJob o1, PaintJob o2) {
				float lay1 = o1.getLayer();
				float lay2 = o2.getLayer();
				return (lay1 < lay2) ? -1 : (lay2 > lay1) ? 1 : 0;
			}
		};
        jobQueue = new PriorityQueue<PaintJob>(100, comp);
        
        Comparator<TypeJob> tcomp = new Comparator<TypeJob>() {
			@Override
			public int compare(TypeJob o1, TypeJob o2) {
				int s1 = o1.getSize();
				int s2 = o2.getSize();
				return (s1 < s2) ? -1 : (s2 > s1) ? 1 : 0;
			}
		};
        typeQueue = new PriorityQueue<TypeJob>(100, tcomp);
        
    }
    
    /**
     * Enqueues a texture for painting at the location described a 2d vector.
     * @param layer paint jobs with lower layer numbers will be drawn first
     */
    public void enqueue(Texture tex, Vector2d loc, float layer)
    {
    	jobQueue.offer(new PaintJob(tex, loc, layer));
    }
    /**
     * Enqueues a texture for painting.
     * @param layer paint jobs with lower layer numbers will be drawn first
     */
    public void enqueue(Texture tex, int x, int y, float layer)
    {
    	jobQueue.offer(new PaintJob(tex, new Vector2d(x,y), layer));
    }
    /**
     * Enqueues a string for type-setting.
     * @param size strings with smaller font sizes will be drawn first
     */
    public void enqueue(String s, Color col, int x, int y, int size)
    {
    	typeQueue.offer(new TypeJob(s, col, x, y, size, false));
    }
    public void enqueue(String s, Color col, int x, int y, int size, boolean centered)
    {
    	typeQueue.offer(new TypeJob(s, col, x, y, size, centered));
    }
    
    public int[] paintBackground(int[] canvas)
    {
    	if (backgroundTex != null)
    		return paintTexture(canvas, backgroundTex, 0, 0, LAYERBACKGROUND);
    	
    	canvas = paintCeiling(canvas, floorColor);
    	canvas = paintFloor(canvas, ceilingColor);
        return canvas;
    }
    
    public int[] paintCeiling(int[] canvas, Color col)
    {
    	for(int n=0; n<canvas.length/2; n++) {
            if(canvas[n] != col.getRGB()) canvas[n] = col.getRGB();
        }
    	return canvas;
    }
    
    public int[] paintFloor(int[] canvas, Color col)
    {
    	for(int i=canvas.length/2; i<canvas.length; i++) {
            if(canvas[i] != col.getRGB()) canvas[i] = col.getRGB();
        }
    	return canvas;
    }
    
    public int[] paintTexture(int[] canvas, Texture tex, int x, int y, float layer)
    {
    	int xoffset = x;
    	int yoffset = y;
    	
    	//Walking the raster line
    	int lineOffset;
    	for (int i=0; i<tex.getHeight(); i++)
    	{
    		lineOffset = (yoffset+i)*width;
    		for (int j=0; j<tex.getWidth(); j++)
    		{
    			try
    			{
    				int pel = tex.getPixel(i, j);
    				if (pel != 0)
    					canvas[lineOffset + j + xoffset] = pel; 
    			}
    			catch (ArrayIndexOutOfBoundsException e)
    			{
    				//e.printStackTrace();
    			}
    		}
    	}	
    	return canvas;
    }
    public int[] paintTexture(int[] canvas, PaintJob job)
    {
    	return paintTexture(canvas, job.tex, (int)job.getLoc().x, (int)job.getLoc().y, job.layer);
    }
    
    public int[] paint(int[] canvas) {

    	canvas = paintBackground(canvas);    	
    	
    	while (jobQueue.size() > 0)
    	{
    		PaintJob j = jobQueue.poll();
    		canvas = paintTexture(canvas, j);
    	}
    	
        return canvas;
    }
    
    public void setType(Graphics g)
    {
    	while (typeQueue.size() > 0)
    	{
    		TypeJob tj = typeQueue.poll();    		
    		if (g.getColor() != tj.getColor())
    		{
    			g.setColor(tj.getColor());
    		}
    		if (g.getFont().getSize() != tj.getSize())
    		{
    			Font old = g.getFont();
				g.setFont(old.deriveFont(old.getStyle(), tj.getSize()));
    		}
    		if (tj.isCentered())
    		{
    			int width = g.getFontMetrics().stringWidth(tj.getText());
    			int xoffset = width/2;
    			g.drawString(tj.getText(), tj.getX() - xoffset, tj.getY());
    		} else {	
    			g.drawString(tj.getText(), tj.getX(), tj.getY());
    		}
    	}
    }
    
    public int[] getCenterPoint()
    {
    	return new int[] 
    			{(int) this.canvasArea.getCenterX(), 
    			(int) this.canvasArea.getCenterY()};
    }
}
