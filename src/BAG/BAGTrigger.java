package BAG;

public class BAGTrigger {
	
	protected BAGame game;
	
	protected boolean satisfied;
	public boolean isSatisfied() { return satisfied; }
	
	public BAGTrigger(BAGame game)
	{
		this.game = game;
		game.add(this);
	}
	
	public void evaluate()
	{
		
	}
	
	public void resolve()
	{
		
	}
	
	public void dispose()
	{
		game.remove(this);
	}
}