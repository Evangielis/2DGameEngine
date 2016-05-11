import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.awt.Color;
import java.awt.Rectangle;

import javax.vecmath.Vector2d;

public class Painter {
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
	
    int width, height;
    Rectangle canvasArea;
    PriorityQueue jobQueue;
    Game myGame;
    
    public Painter(Game myGame) {
        width = myGame.frameWidth;
        height = myGame.frameHeight;
        canvasArea = new Rectangle(0, 0, width, height);
        
        Comparator<PaintJob> comp = new Comparator<PaintJob>() {
			@Override
			public int compare(PaintJob o1, PaintJob o2) {
				float lay1 = o1.getLayer();
				float lay2 = o2.getLayer();
				return (lay1 < lay2) ? -1 : (lay2 > lay1) ? 1 : 0;
			}
		};
        jobQueue = new PriorityQueue<PaintJob>(100, comp);
    }
    
    public void enqueue(Texture tex, Vector2d loc, float layer)
    {
    	jobQueue.offer(new PaintJob(tex, loc, layer));
    }
    
    public int[] paintBackground(int[] canvas, Color col)
    {
    	canvas = paintCeiling(canvas, col);
        canvas = paintFloor(canvas, col);
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
    
    public int[] paintTexture(int[] canvas, PaintJob job)
    {
    	int xoffset = (int)job.getLoc().x;
    	int yoffset = (int)job.getLoc().y;
    	
    	//Walking the raster line
    	int lineOffset;
    	for (int i=0; i<job.tex.getHeight(); i++)
    	{
    		lineOffset = (yoffset+i)*width;
    		for (int j=0; j<job.tex.getWidth(); j++)
    		{
    			try
    			{
    				int pel = job.tex.getPixel(i, j);
    				if (pel != 0)
    					canvas[lineOffset + j + xoffset] = pel; 
    			}
    			catch (ArrayIndexOutOfBoundsException e)
    			{
    				e.printStackTrace();
    			}
    		}
    	}	
    	return canvas;
    }
    
    public int[] paint(Camera camera, int[] canvas) {

    	canvas = paintBackground(canvas, Color.gray);
    	
    	while (jobQueue.size() > 0)
    	{
    		PaintJob j = (PaintJob)jobQueue.poll();
    		canvas = paintTexture(canvas, j);
    	}
    	
        return canvas;
    }
}
