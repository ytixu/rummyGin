package ca.mcgill.cs.comp303.rummy.model.gameModel;

import ca.mcgill.cs.comp303.rummy.model.Card;

/**
 * This iterface shows the methods that the player can call.
 * @author ytixu
 *
 */
public interface GameModelPlayer extends GameModelLogger 
{
	/**
	 * Draw a card form the deck;
	 * @return
	 */
	Card draw();
	
	/**
	 * Add discard card.
	 */
	void discard(Card pCard);
}
