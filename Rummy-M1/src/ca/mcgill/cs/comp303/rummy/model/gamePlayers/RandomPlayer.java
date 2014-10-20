package ca.mcgill.cs.comp303.rummy.model.gamePlayers;

import java.util.Random;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameModelPlayer;

/**
 * A random player, what else do you need to know?
 * @author ytixu
 *
 */
public class RandomPlayer extends Player 
{
	private static Random rnd = new Random();
	
	/**
	 * Constructor for a random player. 
	 */
	public RandomPlayer() 
	{
		super("Wobbuffet");
	}

	@Override
	public boolean play(GameModelPlayer pModel) 
	{
		// Discard a card first. 
		Object[] theHand = aHand.getHand().toArray();
		Card discard = (Card) theHand[(int)Math.floor(rnd.nextFloat()*aHand.size())];
		aHand.remove(discard);
		// either draw or pickup discard
		if (rnd.nextBoolean()) // draw()
		{
			aHand.add(pModel.draw());
		}
		else
		{ // pickup discard
			aHand.add(pModel.getDiscard());
		}
		pModel.discard(discard);
		// decide whether or not to knock. 
		if (rnd.nextBoolean())
		{
			aHand.autoMatch();
			if (aHand.canKnock())
			{
				return true;
			}
		}
		return false;
	}
}
