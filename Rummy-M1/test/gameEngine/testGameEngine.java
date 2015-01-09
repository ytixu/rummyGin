package gameEngine;

import org.junit.Test;

public class testGameEngine {

	private GameEngine ge = new GameEngine();
	private CommandLineObs clo = new CommandLineObs();
	
	@Test 
	public void testGameEngine(){
		ge.addObserver(clo);
		ge.setPlayer(new RandomPlayer(ge), new OptPlayer(ge));
		ge.playNextRound();
	}
}
