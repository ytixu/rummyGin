package ca.mcgill.cs.comp303.rummy.model.gameModel;

import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.ICardSet;

/**
 * 
 * @author ytixu
 *
 */
public class EndGameObserver implements GameObserver 
{

	@Override
	public void logPlayed(GameModelLogger pEngine) 
	{
		return;
	}

	@Override
	public void logEndGame(GameModelLogger pEngine) 
	{
		for (ICardSet s : (Set<ICardSet>) pEngine.getLayout())
		{
			System.out.println(s);
		}
		
		System.out.println("Score:\n" + pEngine.getScore().toString());
	}

}
