package examples.raycast;

import java.awt.Color;

import examples.gridgame.GridGame;
import BAG.BAGame;
import BAG.Camera;
import BAG.RaycastMap;
import BAG.Texture;

public class MazeGame {

	public static void main(String[] args) {
		
		BAGame game = new BAGame(800,600,BAGame.RENDERMODEMAP);
		game.setName("A-Maze-ing Game");
		
		//Create map
		int [][] map = new int[][]{
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,1,0,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
				{1,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,1,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1},
				{1,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,1,0,1,0,1},
				{1,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,1,0,0,1,0,1,1,0,0,0,1,0,0,0,1,0,1},
				{1,1,0,1,1,1,1,1,1,0,1,1,1,0,1,0,1,1,0,1,0,1,1,1,0,0,1,0,1,0,0,0,1},
				{1,0,0,0,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1},
				{1,0,0,0,0,1,0,0,0,0,0,0,1,0,1,1,1,1,0,1,1,0,0,0,0,0,0,0,1,0,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,1,1,0,1,1,1,1,1,0,1,0,1,0,1},
				{1,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1,0,0,1,0,0,0,0,0,0,0,1,1,0,1,0,1},
				{1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		};
		Texture[] texs = new Texture[] {
				new Texture(GridGame.BRICKTEXTURE)			
		};
		Camera camera = new Camera(1.5, 1.5, 1, 0, 0, -.66);
		game.addKeyListener(camera);
		RaycastMap gmap = new RaycastMap(game, map, texs, camera, 64);
		gmap.setBackgroundColor(Color.WHITE);
		game.add(gmap);
		
		game.startGame();
	}

}
