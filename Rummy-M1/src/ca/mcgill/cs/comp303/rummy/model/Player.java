package ca.mcgill.cs.comp303.rummy.model;

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
	 * Get the score for this hand.
	 */
	public int getScore()
	{
		return aHand.score();
	}
	
	/**
	 * Draw from the deck.
	 * @throws HandException if hand is complete 
	 * @param pDeck
	 */
	public void draw(Deck pDeck)
	{
		// TODO
	}
	
	/**
	 * Discard a card.
	 * @throws HandException if discard card was just picked up from the discard pile. 
	 * @param pCard
	 */
	public void discard(Card pCard)
	{
		// TODO
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
