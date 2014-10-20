package ca.mcgill.cs.comp303.rummy.model.gamePlayers;

import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.HandException;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameModelPlayer;

/**
 * Abstract class, extend by humanPlayer and AIPlayer.
 * @author Yi Tian
 *
 */
public abstract class Player 
{
	protected Hand aHand;
	protected String aName;
	protected int aTurnNumber = 0;
	
	/**
	 * Constructor.
	 */
	public Player(String pName)
	{
		aHand = new Hand();
		aName = pName;
	}
	
	/**
	 * Set the turn number. 
	 * @param pTurnNumber
	 */
	public void setTurnNumber(int pTurnNumber)
	{
		aTurnNumber = pTurnNumber;
	}
	
	public int getTurnNumber()
	{
		return aTurnNumber;
	}
	
	/**
	 * Get the name of the player.
	 * @return
	 */
	public String getName()
	{
		return aName;
	}
	
	/**
	 * Check if hand has 10 cards.
	 * @return
	 */
	public boolean isComplete()
	{
		return aHand.isComplete();
	}
	
	/**
	 * Reset the hand.
	 */
	public void clearHand()
	{
		aHand.clear();
	}
	
	/**
	 * Get the score for this hand.
	 */
	public int getScore()
	{
		return aHand.score();
	}
	
	public Set<Card> getHand()
	{
		return aHand.getHand();
	}
	
	/**
	 * Draw from the deck.
	 * @throws HandException if hand is complete 
	 * @param pCard
	 */
	public void draw(Card pCard)
	{
		aHand.add(pCard);
	}
	
	/**
	 * Discard a card.
	 * @throws HandException if discard card was just picked up from the discard pile. 
	 * @param pCard
	 */
	public void discard(Card pCard)
	{
		aHand.remove(pCard);
		//TODO: Need to add pCard to discard stack
	}
	
	/**
	 * Layout all the cards after the opponent knocks.
	 * Match to runs and groups and extend deadwook to opponent's matches. 
	 */
	public void layout()
	{
		// TODO
	}
	
	/**
	 * Player plays its turn, including drawing, discarding, knocking.
	 * If knocking, then return true.
	 * @param pModel
	 */
	abstract boolean play(GameModelPlayer pModel);
	
}
