package BAG;

import java.awt.Rectangle;

public class BAGUIElement extends BAGObject {

	protected boolean centered;
	protected Rectangle area;
	
	public BAGUIElement(String name, BAGame game, BAGDomain domain,
			Texture tex, int xpos, int ypos, boolean centered) {
		super(name, game, domain, tex, xpos, ypos, false, true);
		
		this.centered = centered;
		
		//Calculate area
		int xoff=0, yoff=0;
		if (centered)
		{
			xoff = this.getWidth()/2;
			yoff = this.getHeight()/2;
		}
		this.area = new Rectangle(xpos - xoff, ypos - yoff, tex.getWidth(), tex.getHeight()); 
	}
	
	@Override
	public void paint(Painter ptr)
	{
		if (tex == null)
			return;
		
		int xoffset = 0;
		int yoffset = 0;
		
		if (centered)
		{
			xoffset = this.getWidth()/2;
			yoffset = this.getHeight()/2;
		}
		
		ptr.enqueue(tex, this.getX() - xoffset, this.getY() - yoffset, Painter.LAYERUI);
	}
}
