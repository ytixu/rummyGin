package ca.mcgill.cs.comp303.rummy.model.gameModel;

import ca.mcgill.cs.comp303.rummy.model.Card;

/**
 * This interface enables loggers to look up the state of the game. 
 * Any set method should not be called by them.
 * @author ytixu
 *
 */
public interface GameModelLogger
{
	
	/**
	 * Get the top of the discard pile.
	 * @return
	 */
	Card getDiscard();
	
	/**
	 * Check who is playing now.
	 * @return
	 */
	Object getTrun();
	
	/**
	 * Check if a hand has terminated. 
	 * @return
	 */
	boolean isEnd();
	
	/**
	 * Get the scores for the players when the game ends. 
	 * @return
	 * @pre isEnd()
	 */
	Object getScore();
	
	/**
	 * See what has been played. 
	 * @return
	 */
	Object getPlayed();
}
