package examples.gridgame;
import java.awt.Color;

import BAG.BAGame;
import BAG.BAGAgent;
import BAG.BAGDomain;
import BAG.BAGLocation;
import BAG.GridMap;
import BAG.Texture;

public class GridGame {
	
	public static final int TILESIZE = 64;
	public static final String BRICKTEXTURE = "redbrick.png";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BAGame game = new BAGame(640, 660, BAGame.RENDERMODENORMAL);
		game.setName("Grid World Game");
		BAGDomain gdom = new BAGDomain(game);
		
		//Create map
		int [][] map = new int[][]{
				{0,0,0,0,0,1,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,1,0,0,0,0,0},
				{0,0,0,0,0,1,0,0,0,0,0},
				{0,0,0,0,0,1,0,0,0,0,0},
				{1,0,1,1,1,1,1,1,0,1,1},
				{0,0,0,0,1,0,0,0,0,0,0},
				{0,0,0,0,1,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,1,0,0,0,0,0,0},
				{0,0,0,0,1,0,0,0,0,0,0},
		};
		Texture[] texs = new Texture[] {
				new Texture(GridGame.BRICKTEXTURE)			
		};
		GridMap gmap = new GridMap(game, map, texs, 64, 0, 20);
		gmap.setBackgroundColor(Color.WHITE);
		game.add(gmap);
		
		int[] playerPos = gmap.getPosFromGrid(8, 1);
		PlayerGridMan ga = new PlayerGridMan("gman", game, gdom, playerPos[0], playerPos[1], 0);
		int[] exitPos = gmap.getPosFromGrid(1, 8);
		LocGridExit gl = new LocGridExit("gexit", game, gdom, gmap, exitPos[0], exitPos[1]);
		
		new TriggerAtExit(game, gdom, ga, gl); 
		
		game.startGame();
	}
}