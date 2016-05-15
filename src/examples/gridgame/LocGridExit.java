package examples.gridgame;

import BAG.BAGMap;
import BAG.BAGame;
import BAG.BAGDomain;
import BAG.BAGLocation;
import BAG.Texture;

public class LocGridExit extends BAGLocation {
	
	public static final Texture TARDIS = new Texture("tardis.png"); 

	public LocGridExit(String name, BAGame game, BAGDomain domain,
			BAGMap gmap, int xpos, int ypos) {
		super(name, game, domain, gmap, TARDIS, xpos, ypos);
	}
}