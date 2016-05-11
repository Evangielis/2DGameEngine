import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;

public class Texture implements Iterable {
	public static Texture wood = new Texture("wood.png");
	public static Texture brick = new Texture("redbrick.png");
	public static Texture bluestone = new Texture("bluestone.png");
	public static Texture stone = new Texture("stone.png");
	public static Texture ship = new Texture("spaceship.png");
	
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
