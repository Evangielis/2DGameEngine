package BAG;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.PropositionalFunction;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;

public class BAGLocation extends BAGObject {

	BAGMap gmap;
	
	public BAGLocation(String name, BAGame game, BAGDomain domain, BAGMap gmap, Texture tex, int xpos, int ypos) {
		super(name, game, domain, tex, xpos, ypos, false, true);
		
		ObjectInstance myInstance = new MutableObjectInstance(domain.getDomain().getObjectClass(BAGDomain.CLASSLOCATION), name);
		myInstance.setValue(BAGDomain.ATTX, xpos);
		myInstance.setValue(BAGDomain.ATTY, ypos);
		domain.addObject(myInstance);
		
		this.gmap = gmap;
		gmap.add(this);
	}
	
	@Override
	public void update()
	{
		super.update();
	}
	
	@Override
	public void paint(Painter ptr)
	{
		if (tex != null)
			ptr.enqueue(tex, xloc, yloc, Painter.LAYERLOCATIONS);
	}
}
