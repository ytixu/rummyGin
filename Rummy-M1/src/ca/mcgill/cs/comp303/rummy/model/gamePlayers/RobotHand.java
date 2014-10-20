package ca.mcgill.cs.comp303.rummy.model.gamePlayers;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Hand;

/**
 * Allow copying and cloning.
 * @author ytixu
 *
 */
public class RobotHand extends Hand {
	
	/**
	 * Cloning.
	 */
	public Hand clone()
	{
		Hand newHand = new Hand();
		for (Card c : aHand.keySet())
		{
			newHand.add(c);
		}
		return newHand;
	}
	
	/**
	 * Copy the elements in this hand. 
	 * @param pHand
	 */
	public void copy(Hand pHand)
	{
		for (Card c : aHand.keySet())
		{
			if (!pHand.contains(c))
			{
				remove(c);
			}
		}
		for (Card c : pHand.getHand())
		{
			if (!contains(c))
			{
				add(c);
			}
		}
	}
}
