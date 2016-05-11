import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.*;
import burlap.oomdp.core.Attribute.AttributeType;
import burlap.oomdp.singleagent.SADomain;

public class GameDomain implements DomainGenerator {
		
	public static final String ATTX = "x";
	public static final String ATTY = "y";
	public static final String ATTW = "w";
	public static final String ATTH = "h";

	public static final String CLASSAGENT = "agent";
	public static final String CLASSLOCATION = "location";
	
	int gridWidth = 0;
	int gridHeight = 0;
	int tileSize = 0;
	
	Domain myDomain;
	public Domain getDomain() { return myDomain; }
	
	public GameDomain(int width, int height, int tile)
	{
		gridWidth = width;
		gridHeight = height;
		tileSize = tile;
		myDomain = generateDomain();
		
		Attribute xatt = new Attribute(myDomain, ATTX, AttributeType.INT);
		xatt.setLims(0, gridWidth);
		
		Attribute yatt = new Attribute(myDomain, ATTY, AttributeType.INT);
		yatt.setLims(0, gridHeight);
		
		Attribute watt = new Attribute(myDomain, ATTW, AttributeType.INT);
		Attribute hatt = new Attribute(myDomain, ATTH, AttributeType.INT);
		
		ObjectClass agentClass = new ObjectClass(myDomain, CLASSAGENT);
		agentClass.addAttribute(xatt);
		agentClass.addAttribute(yatt);
		
		ObjectClass locationClass = new ObjectClass(myDomain, CLASSLOCATION);
		locationClass.addAttribute(xatt);
		locationClass.addAttribute(yatt);
	}

	@Override
	public Domain generateDomain() {
		
		SADomain domain = new SADomain();
		
		return domain;
	}	
}
