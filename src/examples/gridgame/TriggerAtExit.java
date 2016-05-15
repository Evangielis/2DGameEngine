package examples.gridgame;

import java.awt.Color;

import BAG.BAGTrigger;
import BAG.BAGame;
import BAG.BAGAgent;
import BAG.BAGDomain;
import BAG.BAGLocation;
import BAG.Painter;
import BAG.Texture;

public class TriggerAtExit extends BAGTrigger {
	
	BAGDomain domain;
	BAGAgent agt;
	BAGLocation exit;
	
	public TriggerAtExit(BAGame game, BAGDomain domain, BAGAgent agt, BAGLocation exit) {
		super(game); 
		
		this.domain = domain;
		this.agt = agt;
		this.exit = exit;
	}
	
	@Override
	public void evaluate()
	{
		satisfied = domain.isAtPropTrue(agt.getName(), exit.getName());
	}
	
	@Override
	public void resolve()
	{
		//System.out.println("Trigger satisfied!");
		//this.dispose();
		this.game.getInputMap().disableKeys();
		Texture box = Texture.getColoredRectangle(300, 200, Color.blue);
		int[] center = this.game.getPainter().getCenterPoint();
		game.enableMouse();
		VictoryUIBox vui = new VictoryUIBox("victorybox", game, domain, box, center[0], center[1]);
	}
}
