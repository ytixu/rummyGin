package ca.mcgill.cs.comp303.rummy.model;

import java.util.Set;

/**
 * Abstract class, extend by humanPlayer and AIPlayer.
 * @author Yi Tian
 *
 */
abstract class Player 
{
	private Hand aHand;
	
	/**
	 * Constructor.
	 */
	public Player()
	{
		aHand = new Hand();
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
	 * Kocking. 
	 */
	abstract void knock();
	
	/**
	 * Player plays its turn, including drawing, discarding, knocking.
	 */
	abstract void play();
	
}
