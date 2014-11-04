package ca.mcgill.cs.comp303.rummy.model.gameCM;

import java.util.HashMap;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.ICardSet;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameEngine;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameModelLogger;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameObserver;

/**
 * 
 * @author ytixu
 *
 */
public class CMObserver implements GameObserver 
{

	@Override
	public void logPlayed(GameModelLogger pEngine) 
	{
		System.out.println(pEngine.getTrun() + " played.");
		Object knock = pEngine.getKnocked();
		Object layout = pEngine.getLayout();
		if (layout != null)
		{
			System.out.println("Layout.");
			for (ICardSet s : (Set<ICardSet>) layout)
			{
				System.out.println(s.toString());
			}
		}
		else if (knock != null)
		{
			System.out.println("Player knocked.");
			for (ICardSet s : (Set<ICardSet>) knock)
			{
				System.out.println(s.toString());
			}
		}
		else
		{
			System.out.println("The top of the dicard pile is " + pEngine.getDiscard().toString());
		}
	}
	
	public void logEndGame(GameModelLogger pEngine)
	{
		System.out.println("Score:\n" + pEngine.getScore().toString());
		System.out.println("Game Ended");
	}
}
