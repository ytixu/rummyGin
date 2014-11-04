package ca.mcgill.cs.comp303.rummy.model.gamePlayers;

import java.util.AbstractMap.SimpleEntry;
import java.util.Random;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.CardSet;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameModelPlayer;

/**
 * The more intelligent player. 
 * Decision made based on which option optimizes the score. 
 * Knock whenever score is below a threshold. 
 * @author Yi Tian
 *
 */
public class RobotPlayer extends AbstractPlayer 
{
	private static Random rnd = new Random();
	private static final short SCORE_THD = 5;

	private RobotHand aTestHand = new RobotHand();  
	
	/**
	 * Constructor.
	 */
	public RobotPlayer() 
	{
		super("Psyduck");
	}
	
	private SimpleEntry<Integer, Card> simulate(Card pCard, int pScore)
	{
		aTestHand.copy(aHand);
		Card takeOut = null;
		Card putIn = pCard;
		int minScore = pScore;
		for (Card c : aHand.getHand()) 
		{
			aTestHand.remove(c);
			aTestHand.add(putIn);
			aTestHand.autoMatch();
			if (aTestHand.score() < minScore)
			{
				takeOut = c;
				minScore = aTestHand.score();
			}
			putIn = c;
		}
		return new SimpleEntry<Integer, Card>(minScore, takeOut);
	}

	@Override
	public boolean play(GameModelPlayer pModel) 
	{	
		// only pick discard card if we can make a better score
		aHand.autoMatch();
		Card newCard = pModel.getDiscard();
		SimpleEntry<Integer, Card> result = simulate(newCard, aHand.score());
		if (aHand.score() <= result.getKey())
		{
			newCard = pModel.draw();
			result = simulate(newCard, Integer.MAX_VALUE);
		}
		aHand.remove(result.getValue());
		aHand.add(newCard);
		pModel.setDiscard(result.getValue());
		// check score
		if (result.getKey() < SCORE_THD)
		{
			return true;
		}
		return false;
	}
}
