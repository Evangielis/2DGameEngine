package BAG;

import java.util.ArrayList;
import java.util.List;

/**
 * A gridmap is a specific type of game map composed of a grid of tiles of equal size.
 * It is ideal for the construction of gridworld style games.  Note that grid follows raster
 * ordering (e.g. top to bottom, left to right).
 * @author Lee Painton
 */
public class GridMap extends BAGMap {

	protected int xorigin, yorigin;
	protected int tileSize;
	protected int[][] grid;
	public int getWidth() { return grid[0].length; }
	public int getHeight() { return grid.length; }
	Texture[] textures;
	//Counter for the name of the object
	
	/**
	 * Constructor for GridMap.  Automatically adds the map to the game.
	 * @param game the Game object this is intended for.
	 * @param grid is a 2d array representation of the grid.  Int values should correspond to n-1 indicies in the texture array.
	 * @param textures is an array of textures which correspond to wall values in the grid.  
	 * @param tileSize the size of each grid tile in pixels
	 * @param xorigin the top-left corner x-coordinate relative to the game window
	 * @param yorigin the top-left corner y-coordinate relative to the game window 
	 */
	public GridMap(BAGame game, int[][] grid, Texture[] textures, int tileSize, int xorigin, int yorigin) {
		super(game);
		
		this.xorigin = xorigin;
		this.yorigin = yorigin;
		
		this.grid = grid;
		this.textures = textures;
		this.tileSize = tileSize;
	}
	
	/**
	 * Method generates the map based on the provided array of textures.
	 */
	public void generateMap()
	{
		for (int i=0; i<getHeight(); i++)
		{
			for (int j=0; j<getWidth(); j++)
			{
				int wallVal = grid[i][j];
				if (wallVal > 0)
				{
					Texture t = textures[grid[i][j]-1];
					int xoffset = (t.getWidth() - tileSize)/2 + xorigin;
					int yoffset = (t.getHeight() - tileSize)/2 + yorigin;
					
					int x = (j * tileSize) + xoffset;
					int y = (i * tileSize) + yoffset;
					String wname = "wall" + i + "_" + j;
					
					//System.out.println(wname + " added at " + x + ":"  + y + " with size " + t.getWidth() + ":" + t.getHeight());					
					allCollidable.add(new GameWall(wname, game, null, t, x, y));	
				}
			}
		}
	}
	
	/**
	 * Translates grid coordinates into window coordinates for painter.
	 * @return array {x,y} with coordinates relative to window
	 */
	public int[] getPosFromGrid(int x, int y)
	{
		int xloc = (x * tileSize) + xorigin;
		int yloc = (y * tileSize) + yorigin;
		return new int[] {xloc, yloc};
	}
}
