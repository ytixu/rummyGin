package ca.mcgill.cs.comp303.rummy.model.gamePlayers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;
import ca.mcgill.cs.comp303.rummy.model.CardSetPack;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;

/**
 * Abstract class, extend by humanPlayer and AIPlayer.
 * @author Yi Tian
 *
 */
public abstract class AbstractPlayer implements Player
{
	protected Hand aHand;
	protected String aName;
	protected int aTurnNumber = 0;
	protected int aLayoutScore = 0;
	protected int aScore = 0;
	
	/**
	 * Constructor.
	 */
	public AbstractPlayer(String pName)
	{
		aHand = new Hand();
		aName = pName;
	}
	
	public void reset(int pTurnNumber)
	{
		aHand.clear();
		aLayoutScore = 0;
		aTurnNumber = pTurnNumber;
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
	@Override
	public String getName()
	{
		return aName;
	}
	
	/**
	 * Check if hand has 10 cards.
	 * @return
	 */
	@Override
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
	
	@Override
	public int getScore()
	{
		return aScore;
	}
	
	@Override
	public int getLayoutScore()
	{
		return getScore() + aLayoutScore;
	}
	
	@Override
	public int getKnockingScore()
	{
		return aHand.score();
	}
	
	@Override
	public void updateScore(int pUpdate)
	{
		aScore += pUpdate;
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
	@Override
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
	
	@Override
	public Set<Card> getDeadWook()
	{
		return aHand.getUnmatchedCards();
	}
	
	
	@Override
	public Set<ICardSet> layout(Set<ICardSet> pCardSets)
	{
		Set<Card> deadwook = getDeadWook();
		HashSet<ICardSet> result = new HashSet<ICardSet>();
		
		for (ICardSet s : pCardSets)
		{
			CardSetPack newSet = new CardSetPack(s);
			if (s.isGroup() && s.size() == 3)
			{
				Rank rank = s.iterator().next().getRank();
				for (Card c : deadwook)
				{
					if (c.getRank().equals(rank))
					{
						newSet.appendBottom(c);
						aLayoutScore -= Math.min(c.getRank().ordinal() + 1, 10);
						break;
					}
				}
			}
			else if (s.isRun())
			{
				Suit suit = s.iterator().next().getSuit();
				ArrayList<Card> potentialCandidates = new ArrayList<Card>();
				for (Card c : deadwook)
				{
					if (c.getSuit().equals(suit))
					{
						potentialCandidates.add(c);
					}
				}
				if (potentialCandidates.isEmpty())
				{
					continue;
				}
				Collections.sort(potentialCandidates);
				int bRank = s.iterator().next().getRank().ordinal() - 1;
				int eRank = bRank + s.size() + 1;
				for (Card c : potentialCandidates)
				{
					int tempRank = c.getRank().ordinal();
					// check beginning
					if (tempRank == bRank)
					{
						newSet.appendTop(c);
						aLayoutScore -= Math.min(c.getRank().ordinal() + 1, 10);
						bRank --;
					}
					// check end
					else if (tempRank == eRank)
					{
						newSet.appendBottom(c);
						aLayoutScore -= Math.min(c.getRank().ordinal() + 1, 10);
						eRank ++;
					}
				}
			}
			if (newSet.size() == s.size())
			{
				result.add(s);
			}
			else
			{
				result.add(newSet);
			}
		}
		return result;
	}
	
	@Override
	public Set<ICardSet> getKnock() 
	{
		return aHand.getMatchedSets();
	}
}
