package examples.shipgame;
import BAG.BAGame;
import BAG.BAGDomain;
import BAG.Texture;

public class ShipGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BAGame game = new BAGame(800, 600, BAGame.RENDERMODENORMAL);
		game.setName("Spaceship Example");
		BAGDomain gdom = new BAGDomain(game);
		new PlayerShip("agent0", game, gdom, 100, 100, BAGDomain.DIRECTIONNORTH);
		game.getPainter().setBackgroundTexture(new Texture("stars.png"));
		
		game.startGame();
	}
}