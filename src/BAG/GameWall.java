package BAG;

public class GameWall extends BAGObject {

	public GameWall(String name, BAGame game, BAGDomain domain, Texture tex, int xpos, int ypos) {
		super(name, game, domain, tex, xpos, ypos, true, true);
	}

	@Override
	public void paint(Painter ptr)
	{
		if (tex != null)
		{
			//System.out.println(name + " painting myself at " + xloc + ":" + yloc);
			ptr.enqueue(tex, xloc, yloc, Painter.LAYERWALLS);
		}
	}
}
