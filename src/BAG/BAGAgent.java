package BAG;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector2d;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TransitionProbability;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.FullActionModel;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.common.SimpleAction;

public class BAGAgent extends BAGObject {
	
	protected int myFacing;
	public int getWidth() { return (tex == null) ? 0 : tex.getWidth(); }
	public int getHeight() { return (tex == null) ? 0 : tex.getHeight(); }
	
	protected Map<String, Integer> moveSpeeds;
	public void setSpeed(String action, int speed) { moveSpeeds.put(action, speed); }
	public int getSpeed(String action) { return moveSpeeds.get(action); }
	
	public BAGAgent(String name, BAGame game, BAGDomain domain, Texture tex, int xpos, int ypos, int facing) {
		super(name, game, domain, tex, xpos, ypos, true, false);
		
		ObjectInstance myInstance = new MutableObjectInstance(domain.getDomain().getObjectClass(BAGDomain.CLASSAGENT), name);
		myInstance.setValue(BAGDomain.ATTX, xpos);
		myInstance.setValue(BAGDomain.ATTY, ypos);
		myInstance.setValue(BAGDomain.ATTFACE, facing);
		domain.addObject(myInstance);
		
		myFacing = facing;
		
		moveSpeeds = new HashMap<String, Integer>();
	}
	
	@Override
	public void update()
	{
		super.update();
		//System.out.println(name);
		ObjectInstance oi = myDomain.getObject(name); 
		xloc = oi.getIntValForAttribute(BAGDomain.ATTX);
		yloc = oi.getIntValForAttribute(BAGDomain.ATTY);
		myFacing = oi.getIntValForAttribute(BAGDomain.ATTFACE);
		
		//System.out.println(myFacing);
	}
	
	@Override
	public void paint(Painter ptr)
	{
		if (this.tex != null)
			ptr.enqueue(tex, xloc, yloc, Painter.LAYERAGENTS);
	}
	
	public void turnLeft()
	{
		myDomain.performAction(BAGDomain.ACTIONTURNLEFT);
	}
	public void turnRight()
	{
		myDomain.performAction(BAGDomain.ACTIONTURNRIGHT);
	}
	public void moveForward()
	{
		myDomain.performAction(BAGDomain.ACTIONFORWARD);
	}
	public void moveBackward()
	{
		myDomain.performAction(BAGDomain.ACTIONBACKWARD);
	}
	public void moveNorth()
	{
		myDomain.performAction(BAGDomain.ACTIONNORTH);
	}
	public void moveEast()
	{
		myDomain.performAction(BAGDomain.ACTIONEAST);
	}
	public void moveSouth()
	{
		myDomain.performAction(BAGDomain.ACTIONSOUTH);
	}
	public void moveWest()
	{
		myDomain.performAction(BAGDomain.ACTIONWEST);
	}
	
	public class Turn extends SimpleAction implements FullActionModel {
		
		String actName;
		BAGAgent gAgent;
		
		public Turn(String actionName, BAGAgent gagt, BAGDomain domain) {
			super(actionName, domain.getDomain());
			this.actName = actionName;
			this.gAgent = gagt;
		}
		
		@Override
		public List<TransitionProbability> getTransitions(State s,
				GroundedAction groundedAction) {
			
			return null;
		}

		@Override
		protected State performActionHelper(State s,
				GroundedAction groundedAction) {
			//get agent and current direction
			ObjectInstance agent = s.getObject(gAgent.getName());
			int currDir = agent.getIntValForAttribute(BAGDomain.ATTFACE);
			int newDir = turnResult(currDir);
			
			agent.setValue(BAGDomain.ATTFACE, newDir);
			
			return s;
		}
		
		protected int turnResult(int currDir)
		{
			int deltaDir = 0;
			
			switch (this.actName) 
			{
				case BAGDomain.ACTIONTURNLEFT:
					deltaDir = -1;
					break;
				case BAGDomain.ACTIONTURNRIGHT:
					deltaDir = 1;
					break;
			}
			
			int newDir = currDir + deltaDir;
			if (newDir < 0)
				newDir = 3;
			if (newDir > 3)
				newDir = 0;
				
			return newDir;
		}
	}
	
	public class Move extends SimpleAction implements FullActionModel {
		
		BAGDomain domain;
		BAGAgent gAgent;
		
		public Move(String actionName, BAGAgent gagt, BAGDomain domain, int speed){
			super(actionName, domain.getDomain());
			this.domain = domain;
			this.gAgent = gagt;
			this.gAgent.setSpeed(actionName, speed);
		}

		@Override
		protected State performActionHelper(State s, GroundedAction groundedAction)
		{
			//get agent and current position
			ObjectInstance agent = s.getObject(gAgent.getName());
			int curX = agent.getIntValForAttribute(BAGDomain.ATTX);
			int curY = agent.getIntValForAttribute(BAGDomain.ATTY);
			int curDir = -1;
			
			//Determine which direction
			switch (name)
			{
				case BAGDomain.ACTIONFORWARD:
					curDir = agent.getIntValForAttribute(BAGDomain.ATTFACE);
					break;
				case BAGDomain.ACTIONBACKWARD:
					curDir = agent.getIntValForAttribute(BAGDomain.ATTFACE);
					curDir += 2;
					curDir = (curDir > 3) ? 0 : (curDir < 0) ? 3 : curDir;
					break;
				case BAGDomain.ACTIONNORTH:
					curDir = BAGDomain.DIRECTIONNORTH;
					break;
				case BAGDomain.ACTIONEAST:
					curDir = BAGDomain.DIRECTIONEAST;
					break;
				case BAGDomain.ACTIONSOUTH:
					curDir = BAGDomain.DIRECTIONSOUTH;
					break;
				case BAGDomain.ACTIONWEST:
					curDir = BAGDomain.DIRECTIONWEST;
					break;
			}

			//get resulting position
			int[] newpos = moveResult(curX, curY, curDir);
			
			//set the new position
			agent.setValue(BAGDomain.ATTX, newpos[0]);
			agent.setValue(BAGDomain.ATTY, newpos[1]);

			//return the state we just modified
			return s;
		}

		@Override
		public List<TransitionProbability> getTransitions(State s, GroundedAction groundedAction) {
			//get agent and current position
			ObjectInstance agent = s.getObject(name);
			int curX = agent.getIntValForAttribute(BAGDomain.ATTX);
			int curY = agent.getIntValForAttribute(BAGDomain.ATTY);

			List<TransitionProbability> tps = new ArrayList<TransitionProbability>(4);
			
			//create transition probability object and add to our list of outcomes
			tps.add(new TransitionProbability(s, 1.0));

			return null;
		}
		
		protected int[] moveResult(int curX, int curY, int direction) {
			
			//first get change in x and y from direction using 0: north; 1: east; 2:south; 3: west
			int dx = 0;
			int dy = 0;
			int speed = gAgent.getSpeed(this.name);
			
			switch (direction)
			{
				case BAGDomain.DIRECTIONNORTH:
					dy = -1 * speed;
					break;
				case BAGDomain.DIRECTIONEAST:
					dx = speed;
					break;
				case BAGDomain.DIRECTIONSOUTH:
					dy = speed;
					break;
				case BAGDomain.DIRECTIONWEST:
					dx = -1 * speed;
					break;
			}
			
			int nx = curX + dx;
			int ny = curY + dy;
			
			//make sure new position is valid
			if (!domain.isValidPosition(nx, ny))
			{
				nx = curX;
				ny = curY;
			} else {
				//Check collision map using intended new position
				List<BAGObject> cols = gAgent.myGame.getCollisionMap().checkCollisions(nx, ny, gAgent);
				//System.out.println("Checking collisions!");
				if (!cols.isEmpty())
				{
					nx = curX;
					ny = curY;
				}
			}
			
			return new int[]{nx,ny};			
		}
	}
}
