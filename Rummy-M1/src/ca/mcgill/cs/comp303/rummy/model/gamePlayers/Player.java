package ca.mcgill.cs.comp303.rummy.model.gamePlayers;

import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.CardSet;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameModelPlayer;

/**
 * 
 * @author ytixu
 *
 */
public interface Player 
{
	/**
	 * Player plays its turn, including drawing, discarding, knocking.
	 * If knocking, then return true.
	 * @param pModel
	 * @return
	 */
	boolean play(GameModelPlayer pModel);
	
	/**
	 * 
	 */
	void clearHand();
	
	/**
	 * 
	 * @return
	 */
	boolean isComplete();
	
	/**
	 * 
	 * @param pCard
	 */
	void draw(Card pCard);
	
	/**
	 * 
	 * @return
	 */
	Set<Card> getHand();
	
	/**
	 * 
	 * @return
	 */
	String getName();
	
	/**
	 * 
	 * @return
	 */
	Set<ICardSet> getKnock();
	
	/**
	 * The global score.
	 * @return
	 */
	int getScore();
	
	/**
	 * The hand score for the player who didn't knock.
	 * @return
	 */
	int getLayoutScore();
	
	/**
	 * The hand score of the player who knocked. 
	 * @return
	 */
	int getKnockingScore();
	
	/**
	 * Update the global score.
	 * @param pUpdate
	 * @return
	 */
	void updateScore(int pUpdate);
	
	/**
	 * 
	 * @return
	 */
	Set<Card> getDeadWook();
	
	/**
	 * Layout all the cards after the opponent knocks.
	 * Match to runs and groups and extend deadwook to opponent's matches. 
	 * @param knocking
	 * @return
	 */
	Set<ICardSet> layout(Set<ICardSet> knocking);
	
	/**
	 * 
	 * @param pTurnNumber
	 */
	void reset(int pTurnNumber);
}
