package gameEngine;

import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;

public interface Player {
	
	/**
	 * Set the game engine.
	 * @param gameSetter
	 */
	void setGameEngine(IGameEngineSetter gameSetter);
	
	/**
	 * Take a card.
	 */
	void pickCard();
	
	/**
	 * Discard a card and put onto the discard pile.
	 */
	void discard();
	
	/**
	 * Kocking. 
	 */
	void knock();
	
	/**
	 * Get matched sets. 
	 * @return matched sets
	 */
	Set<ICardSet> getMatchedSets();
	
	/**
	 * Get deadwook.
	 * @return set of deadwook
	 */
	Set<Card> getDeadwook();
	
	/**
	 * Get player's name.
	 * @return player's name
	 */
	String getName();
	
	/**
	 * Get the cards in hand.
	 * @return the set of cards in hand
	 */
	Set<Card> getHand();
}
