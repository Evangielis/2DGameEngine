import java.util.ArrayList;
import java.util.List;

public abstract class GameObject implements IUpdatable, IPaintable {
	
	Game myGame;
	GameDomain myDomain;
	
	boolean paintable;
	boolean collidable;
	boolean gstatic;
	
	String name;
	public String getName() { return name; }
	
	public GameObject(String name, Game game, GameDomain domain)
	{
		myGame = game;
		myGame.add(this);
		
		myDomain = domain;
		domain.add(this);
	}
	
	public void update()
	{
		
	}
	
	public void paint()
	{
		
	}
	
	public void dispose()
	{
		myGame.remove(this);
	}
}
