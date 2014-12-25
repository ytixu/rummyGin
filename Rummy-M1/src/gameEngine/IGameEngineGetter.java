package gameEngine;

import java.util.HashMap;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;

public interface IGameEngineGetter {
	
	/**
	 * Get the top of the discard pile. 
	 * @return the top card of the discard pile
	 */
	Card peekDiscard();
	
	/**
	 * Get the cards in each player's hand.
	 * @return map of player and hand
	 */
	HashMap<Player, Set<Card>> getPlayerHand();
	
	/**
	 * Get the lated move.
	 * @return a string that describe what happened
	 */
	String getPlayed();
	
	/**
	 * Call all game observer for updates.
	 */
	void notifyGameObserver();
}
