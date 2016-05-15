package BAG;

import java.util.ArrayList;
import java.util.List;

public abstract class BAGObject implements IUpdatable, IPaintable {
	
	BAGame myGame;
	public BAGame getGame() { return myGame; }
	BAGDomain myDomain;
	public BAGDomain getDomain() { return myDomain; } 
	protected Texture tex;
	public int getWidth() { return (tex != null) ? tex.width : 0; }
	public int getHeight() { return (tex != null) ? tex.height : 0; }
	int xloc, yloc;
	public int getX() { return xloc; }
	public int getY() { return yloc; }
	
	boolean collidable;
	public boolean isCollidable() { return collidable; }
	boolean immobile;
	public boolean isImmobile() { return immobile; }
	
	protected String name;
	public String getName() { return name; }
	
	public BAGObject(String name, BAGame game, BAGDomain domain, Texture tex, int xpos, int ypos, boolean collidable, boolean immobile)
	{
		this.tex = tex;
		this.name = name;
		
		myGame = game;
		myGame.add(this);
		
		myDomain = domain;
		
		xloc = xpos;
		yloc = ypos;
		
		this.collidable = collidable;
		this.immobile = immobile; 
		if (collidable)
		{
			game.getCollisionMap().addCollider(this);
		}
	}
	
	public void update()
	{
		
	}
	
	public void paint(Painter ptr)
	{
		if (tex != null)
			ptr.enqueue(tex, xloc, yloc, 0);
	}
	
	public void dispose()
	{
		myGame.remove(this);
	}
}
