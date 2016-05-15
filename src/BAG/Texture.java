package BAG;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class Texture implements Iterable {
	
	public static Texture getEmptyTexture(int w, int h)
	{
		return new Texture(new int[w * h], w, h, "");
	}
	public static Texture getColoredRectangle(int w, int h, Color col)
	{
		int[] pels = new int[w * h];
		for (int i=0; i<pels.length; i++)
		{
			pels[i] = col.getRGB(); 
		}
		return new Texture(pels, w, h, "");
	}
	
	int[] raster;
	public String loc;
	int width, height;
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int[] getRaster() { return raster; }
	
	public int getPixel(int y, int x)
	{
		return raster[y*width + x];
	}
	
	public Texture(String location) {
		loc = location;
		raster = loadBitmap();
		//System.out.println(pixels.length);
	}
	protected Texture(int[] raster, int w, int h, String location)
	{
		this.raster = raster;
		this.width = w;
		this.height = h;
		this.loc = location;
	}
	
	/**
	 * Takes a raster and returns a 90 degree counterclockwise rotation.
	 * @param raster
	 * @return rotated Texture
	 */
	public Texture getCounterRotation()
	{
		int[] rotation = new int[raster.length];
		
		for (int i=0; i<height; i++)
		{
			for (int j=0; j<width; j++)
			{
				rotation[((width - j - 1) * height) + i] = raster[(i * width) + j];
			}
		}
		
		return new Texture(rotation, height, width, loc);
	}
	
	/**
	 * Derives a tiled version of the texture as a new texture.
	 * @param width of the new texture
	 * @param height of the new texture
	 * @return a new Texture
	 */
	public Texture getTiled(int width, int height)
	{
		int[] newRaster = new int[width * height];
		int oldy = 0, oldx = 0;
		for (int i=0; i<height; i++)
		{
			for (int j=0; j<width; j++)
			{
				newRaster[(i * width) + j] = this.getPixel(oldy, oldx);
				oldx = (oldx < this.getWidth() - 1) ? oldx + 1 : 0;
			}
			oldy = (oldy < this.getHeight() - 1) ? oldy + 1 : 0;
		}
		
		return new Texture(newRaster, width, height, "");
	}
	

	
	private int[] loadBitmap() {
		try {
			//URL u = getClass().getResource("/images/wood.png");
			BufferedImage image = ImageIO.read(new File("images/" + loc));
			width = image.getWidth();
			//System.out.println(width);
			height = image.getHeight();
			//System.out.println(height);
			int[] pels = new int[width * height];
			image.getRGB(0, 0, width, height, pels, 0, width);
			return pels;
		} catch (IOException e) {
			e.printStackTrace();
			return new int[0];
		}
	}
	
	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return new Iterator()
		{
			int x = 0, y = 0, lineOffset = 0;
			
			@Override
			public boolean hasNext() {
				return lineOffset + x < raster.length - 1;
			}

			@Override
			public Object next() {
				// TODO Auto-generated method stub
				int index = lineOffset + x;
				int pel = raster[index];
				
				if (x < width)
				{
					x++;
				}
				else
				{
					x=0;
					y++;
					lineOffset = y * width;
				}
				
				return pel;
			}
			
		};
	}
}
