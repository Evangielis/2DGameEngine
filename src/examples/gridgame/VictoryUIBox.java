package examples.gridgame;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;

import javax.vecmath.Vector2d;

import BAG.BAGUIElement;
import BAG.BAGame;
import BAG.BAGDomain;
import BAG.Painter;
import BAG.Texture;

public class VictoryUIBox extends BAGUIElement {

	public VictoryUIBox(String name, BAGame game, BAGDomain domain,
			Texture tex, int xpos, int ypos) {
		super(name, game, domain, tex, xpos, ypos, true);
	}
	
	@Override
	public void update()
	{
		Vector2d click = this.getGame().getInputMap().getClickLoc();
		if (click != null)
		{
			if (this.area.contains(click.x, click.y))
			{
				//System.out.println("Exit!");
				this.getGame().stopGame();
				this.getGame().dispatchEvent(new WindowEvent(this.getGame(), WindowEvent.WINDOW_CLOSING));
			}
		}
	}
	
	@Override
	public void paint(Painter ptr)
	{
		super.paint(ptr);
		ptr.enqueue("Victory!", Color.yellow, this.getX(), this.getY()+10, 50, true);
	}
}
