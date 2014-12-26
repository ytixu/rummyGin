package gameEngine;

import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;

public interface IGameEngineSetter {
	
	/**
	 * Draw a card from the deck.
	 * @return the drawn card
	 */
	Card drawFromDeck();
	
	/**
	 * Check the most recent discarded card.
	 * @return the top card of the discard pile
	 */
	Card peekDiscard();
	
	/**
	 * Take from the discard pile.
	 * @return the top card of the discard pile.
	 */
	Card takeDiscard();
	
	/**
	 * Discard a card and put onto discard pile.
	 * @param pCard
	 */
	void discard(Card pCard);
	
	/**
	 * Player knocking.
	 * @param pMatchedSet
	 */
	void knock();
	
	/**
	 * After opponent knocks, layout matched set and 
	 * add deadwook to opponent's matched set.
	 */
	void layout(Set<ICardSet> aSets);
}
