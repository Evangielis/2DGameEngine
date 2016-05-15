package BAG;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class BAGMap implements IPaintable, IUpdatable {
	
	BAGame game;
	List<BAGLocation> allLocations;
	List<BAGObject> allCollidable;
	List<BAGObject> allDecorative;
	
	public BAGMap(BAGame game)
	{
		this.game = game;
		allLocations = new ArrayList<BAGLocation>();
		allCollidable = new ArrayList<BAGObject>();
		allDecorative = new ArrayList<BAGObject>();
	}
	
	public BAGLocation getLocByName(String lname)
	{
		for (BAGLocation gloc : allLocations)
		{
			if (gloc.getName() == lname)
				return gloc;
		}
		return null;
	}
	
	public void setBackgroundColor(Color col)
	{
		game.getPainter().setBackgroundcolor(col);
	}
	
	protected void addCollidable(BAGObject gobj)
	{
		allCollidable.add(gobj);
		//Need to add collidables to collision map
	}
	protected void addDecorative(BAGObject gobj)
	{
		allDecorative.add(gobj);
	}
	protected void addLocation(BAGLocation gloc)
	{
		allLocations.add((BAGLocation)gloc);
	}
	
	public void add(BAGObject gobj)
	{
		if (gobj instanceof BAGLocation)
		{
			addLocation((BAGLocation)gobj);
		} else {
			if (gobj.isCollidable())
				addCollidable(gobj);
			else
				addDecorative(gobj);
		}
	}
	
	/**
	 * Generates the map for this game context.
	 */
	public void generateMap()
	{
		
	}
	
	public void update()
	{
		
	}
	
	public void paint(Painter ptr)
	{

	}
	
	public void dispose()
	{
		for (BAGLocation gl : allLocations)
		{
			game.remove(gl);
		}
		for (BAGObject gobj : allCollidable)
		{
			game.remove(gobj);
		}
		for (BAGObject gobj : allDecorative)
		{
			game.remove(gobj);
		}
	}
	
	public int[] renderMap(int[] canvas)
	{
		return canvas;
	}
}
