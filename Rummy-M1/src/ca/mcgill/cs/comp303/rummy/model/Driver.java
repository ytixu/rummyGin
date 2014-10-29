package ca.mcgill.cs.comp303.rummy.model;

public class Driver {
	public static void main(String[] args) {
		GameEngine game = new GameEngine();
		Player p1 = new HumanPlayer(game);
		Player p2 = new HumanPlayer(game);
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.startGame();
	}
}