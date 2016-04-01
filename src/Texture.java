import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Texture {
	public static Texture wood = new Texture("wood.png");
	public static Texture brick = new Texture("redbrick.png");
	public static Texture bluestone = new Texture("bluestone.png");
	public static Texture stone = new Texture("stone.png");
	public static Texture ship = new Texture("spaceship.png");
	
	int[] pixels;
	public String loc;
	int width, height;
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int[] getPixels() { return pixels; }
	public int getPixel(int y, int x)
	{
		return pixels[y*width + x];
	}
	
	public Texture(String location) {
		loc = location;
		pixels = loadBitmap();
		//System.out.println(pixels.length);
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
}
