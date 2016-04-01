import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageTest {


	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] pixels = load();
		
		if (pixels != null)
		{
			for (int i : pixels)
				System.out.println(i);
		}
	}
	
	private static int[] load() {
		try {
			int[] pixels = new int[64 * 64];
			File f = new File("images/wood.png");
			BufferedImage image = ImageIO.read(f);
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
			
			return pixels;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
