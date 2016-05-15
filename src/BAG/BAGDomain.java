package BAG;
import java.util.ArrayList;
import java.util.List;

import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.*;
import burlap.oomdp.core.Attribute.AttributeType;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.Action;
import burlap.oomdp.singleagent.FullActionModel;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.SADomain;
import burlap.oomdp.singleagent.common.SimpleAction;

public class BAGDomain implements DomainGenerator {
		
	public static final String ATTX = "x";
	public static final String ATTY = "y";
	public static final String ATTFACE = "facing";

	public static final String CLASSAGENT = "agent";
	public static final String CLASSLOCATION = "location";
	
	public static final int DIRECTIONNORTH = 0;
	public static final int DIRECTIONEAST = 1;
	public static final int DIRECTIONSOUTH = 2;
	public static final int DIRECTIONWEST = 3;
	
	public static final String ACTIONFORWARD = "moveforward";
	public static final String ACTIONBACKWARD = "movebackward";
	public static final String ACTIONTURNLEFT = "turnleft";
	public static final String ACTIONTURNRIGHT = "turnright";
	public static final String ACTIONNORTH = "movenorth";
	public static final String ACTIONEAST = "moveeast";
	public static final String ACTIONSOUTH = "movesouth";
	public static final String ACTIONWEST = "movewest";
	
	public static final String PFAT = "at";
	
	Domain myDomain;
	BAGame game;
	public Domain getDomain() { return myDomain; }
	public BAGame getGame() { return game; }
	State currentState;
	List<GroundedProp> atgplist;
	List<PropositionalFunction> atpflist; 
	
	public BAGDomain(BAGame game)
	{
		this.game = game;
		myDomain = generateDomain();
		currentState = new MutableState();
		
		Attribute xatt = new Attribute(myDomain, ATTX, AttributeType.INT);
		xatt.setLims(0, game.frameWidth);
		
		Attribute yatt = new Attribute(myDomain, ATTY, AttributeType.INT);
		yatt.setLims(0, game.frameHeight);
		
		//0: north; 1: east; 2:south; 3: west
		Attribute diratt = new Attribute(myDomain, ATTFACE, AttributeType.INT);
		
		ObjectClass agentClass = new ObjectClass(myDomain, CLASSAGENT);
		agentClass.addAttribute(xatt);
		agentClass.addAttribute(yatt);
		agentClass.addAttribute(diratt);
		
		ObjectClass locationClass = new ObjectClass(myDomain, CLASSLOCATION);
		locationClass.addAttribute(xatt);
		locationClass.addAttribute(yatt);
		
		atpflist = new ArrayList<PropositionalFunction>();
		atpflist.add(new AtLocation(this));
		atgplist = new ArrayList<GroundedProp>();
	}

	@Override
	public Domain generateDomain() {
		
		SADomain domain = new SADomain();
		
		return domain;
	}
	
	public void addObject(ObjectInstance obj)
	{
		currentState.addObject(obj);
	}
	
	public void performAction(String actionName)
	{
		Action act = myDomain.getAction(actionName);
		if (act != null)
		{
			State ns = act.performAction(currentState, null);
			currentState = ns;
			updatePropFunctions();
		}
	}
	
	public boolean isValidPosition(int x, int y) {
		
		return ((x >= 0) && 
				(y >= 0) && 
				(x < game.frameWidth) && 
				(y < game.frameHeight));
	}
	
	public ObjectInstance getObject(String name)
	{
		return this.currentState.getObject(name);
	}
	public void updatePropFunctions()
	{
		this.atgplist = PropositionalFunction.getAllGroundedPropsFromPFList(atpflist, currentState);
	}
	
	public boolean isAtPropTrue(String agent, String loc)
	{
		for (GroundedProp gp : atgplist)
		{
			if (gp.params[0] == agent &&
					gp.params[1] == loc)
				return gp.isTrue(currentState);
		}
		return false;
	}
	
	protected class AtLocation extends PropositionalFunction{

		BAGDomain gdomain;
		
		public AtLocation(BAGDomain domain){
			super(PFAT, domain.getDomain(), new String []{CLASSAGENT, CLASSLOCATION});
			this.gdomain = domain;
		}
		
		@Override
		public boolean isTrue(State s, String[] params) {
			ObjectInstance agent = s.getObject(params[0]);
			ObjectInstance location = s.getObject(params[1]);
			
			int lx = location.getIntValForAttribute(ATTX);
			int ly = location.getIntValForAttribute(ATTY);
			
			//System.out.println(gdomain.getGame().getGameMap());
			BAGLocation gloc = gdomain.getGame().getGameMap().getLocByName(location.getName());
			List<BAGObject> clist = gdomain.getGame().getCollisionMap().checkIntersections(lx, ly, gloc);
			
			for (BAGObject go : clist)
			{
				if (go.getName() == agent.getName())
				{
					return true;
				}
			}
			return false;	
		}
	}
}
