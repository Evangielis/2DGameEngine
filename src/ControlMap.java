import java.util.ArrayList;
import java.util.List;

public abstract class ControlMap {
	
	public static List<ControlMap> AllMaps = new ArrayList<ControlMap>();
	public static void UpdateAllMaps()
	{
		for (ControlMap map : AllMaps)
		{
			map.update();
		}
	}
	
	public ControlMap()
	{
		AllMaps.add(this);
	}
	
	public void dispose()
	{
		AllMaps.remove(this);
	}
	
	public void update()
	{
	}
}