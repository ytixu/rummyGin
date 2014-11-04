package ca.mcgill.cs.comp303.rummy.model.gameCM;

import java.util.Scanner;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameEngine;
import ca.mcgill.cs.comp303.rummy.model.gamePlayers.RandomPlayer;

/**
 * Testing on the command line.
 * @author ytixu
 *
 */
public class CMGame 
{
	/**
	 * 
	 * @param pTheDeadliestBattleOfRummyGinWahahahaha
	 */
	public static void main(String[] pTheDeadliestBattleOfRummyGinWahahahaha)
	{
		GameEngine theMatrix = new GameEngine();
		theMatrix.addObservers(new CMObserver());
		
		// get player name
		Scanner input = new Scanner(System.in);
		System.out.println("Dear worrior, you are about to enter in a battle "
				+ "with the most dangerous random player AI ever created by the "
				+ "prettiest programmer of PandaLand. As to assert that your have "
				+ "the necessary skills for this challenge, we will ask you to answer "
				+ "the following command.");
		input.nextLine();
		System.out.println("Enter your name.");
		String someRandomPersonsName = input.nextLine();
		System.out.println("Ah! " + someRandomPersonsName + " is a glorious name! From the sound "
				+ "of it, I can tell that you are ready for this challenge!");
		input.nextLine();
		System.out.println("*And little does " + someRandomPersonsName + " know, this shall be "
				+ "the last game that " + someRandomPersonsName + " will ever play...*");
		input.nextLine();
		theMatrix.setPlayers(new RandomPlayer(), new CMHumanPlayer(someRandomPersonsName));
		theMatrix.newGame();
		theMatrix.start();
	}
}
