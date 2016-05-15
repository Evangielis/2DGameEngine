package BAG;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollisionMap {
	
	Map<BAGObject, Rectangle> allColliders;
	Map<BAGObject, List<BAGObject>> allCollisions;
	public List<BAGObject> getCollisions(BAGObject gobj)
	{
		return allCollisions.get(gobj);
	}
	
	public CollisionMap()
	{
		allColliders = new HashMap<BAGObject, Rectangle>();
		
		//Holds lists of gameobjects that hit the keyed object since last update
		allCollisions = new HashMap<BAGObject, List<BAGObject>>();
	}
	
	/**
	 * Used to determine whether an objects intended location will result in one or more collisions.
	 * @param x the intended new x location
	 * @param y the intended new y location
	 * @param go the object doing the checking
	 * @return a list of BAGObjects which were collided with 
	 */
	public List<BAGObject> checkCollisions(int x, int y, BAGObject go)
	{
		List<BAGObject> clist = new ArrayList<BAGObject>();
		Rectangle c = new Rectangle(x, y, go.getWidth(), go.getHeight());
		//System.out.println("Checking collisions for " + go.getName() + 
		//		" using rectangle " + c.getX() + ":" + c.getY() + ":" + c.getWidth() + ":" + c.getHeight());
		for (BAGObject gobj : allColliders.keySet())
		{
			if ((go != gobj) && allColliders.get(gobj).intersects(c))
			{
				clist.add(gobj);
				allCollisions.get(gobj).add(go);
			}
		}
		
		return clist;
	}
	/**
	 * Meant for checking for collidable objects intersection non-collidables.  Doesn't register the collision in the
	 * object's list.
	 * @return
	 */
	public List<BAGObject> checkIntersections(int x, int y, BAGObject go)
	{
		List<BAGObject> clist = new ArrayList<BAGObject>();
		Rectangle c = new Rectangle(x, y, go.getWidth(), go.getHeight());
		for (BAGObject gobj : allColliders.keySet())
		{
			if ((go != gobj) && allColliders.get(gobj).intersects(c))
			{
				clist.add(gobj);
			}
		}
		
		return clist;
	}
	
	public void update()
	{
		clearCollisions();
		updateColliders();
	}
	
	public void updateColliders()
	{
		for (BAGObject gobj : allColliders.keySet())
		{
			if (!gobj.isImmobile())
			{
				Rectangle r = allColliders.get(gobj);
				r.setBounds(gobj.xloc, gobj.yloc, gobj.getWidth(), gobj.getHeight());
			}
		}
	}
	
	public void clearCollisions()
	{
		for (BAGObject gobj : allCollisions.keySet())
		{
			allCollisions.get(gobj).clear();
		}
	}
	
	public void addCollider(BAGObject gobj)
	{
		if (!gobj.isCollidable()) return;
		
		//System.out.println("Adding " + gobj.getName() + " to collision map!");
		allCollisions.put(gobj, new ArrayList<BAGObject>());
		//System.out.println(gobj.xloc + ":" + gobj.yloc + ":" + gobj.getWidth() + ":" + gobj.getHeight());
		allColliders.put(gobj, new Rectangle(gobj.xloc, gobj.yloc, gobj.getWidth(), gobj.getHeight()));			
	}
	public void removeCollider(BAGObject gobj)
	{
		allColliders.remove(gobj);
		allCollisions.remove(gobj);
	}
}