package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;


/**
 * Models a hand of 10 cards. The hand is not sorted. Not threadsafe.
 * The hand is a set: adding the same card twice will not add duplicates
 * of the card.
 * @inv size() > 0
 * @inv size() <= HAND_SIZE
 */
public class Hand
{	
	public static final int HANDSIZE = 10;
	protected HashMap<Card, Boolean> aHand;
	
	/*
	 * A set of CardSet
	 * It tells which CardSets gives the optimal score
	 * But at each time a new card is added (and another card is removed), this must be rebuilt
	 * Might think of a better way to keep previous solution
	 */
	protected HashMap<CardSet, Boolean> aMatchedSet;
	
	protected int aScore;
		
	/**
	 * Creates a new, empty hand.
	 */
	public Hand()
	{
		aMatchedSet = new HashMap<CardSet, Boolean>();
		aHand = new HashMap<Card, Boolean>();
		aScore = 0;
	}
	
	/**
	 * Get a list of the card in the hand (used only for debug).
	 * @return a set of cards
	 */
	public Set<Card> getHand()
	{
		return aHand.keySet();
	}
	
	/*
	 * Prepare to call autoMatch again.
	 */
	protected void reset()
	{
		aMatchedSet.clear();
		for (Card c : aHand.keySet())
		{
			aHand.put(c, false);
		}
	}
	
	/**
	 * Check if automatch has already been called
	 * @return True if automatched has been called for a hand 
	 */
	public boolean isMatched()
	{
		return !aMatchedSet.isEmpty();
	}
	
	/**
	 * Adds pCard to the list of unmatched cards.
	 * If the card is already in the hand, it is not added.
	 * @param pCard The card to add.
	 * @throws HandException if the hand is complete.
	 * @throws HandException if the card is already in the hand.
	 * @pre pCard != null
	 */
	public void add( Card pCard )
	{
		assert pCard != null;
		if (contains(pCard))
		{
			throw new HandException("Card already in hand");
		}
		if (isComplete())
		{
			throw new HandException("Hand is complete.");
		}
		aHand.put(pCard, false);
		
		if (isMatched())
		{
			reset();
		}
	}
	
	/**
	 * Remove pCard from the hand and break any matched set
	 * that the card is part of. Does nothing if
	 * pCard is not in the hand.
	 * @param pCard The card to remove.
	 * @pre pCard != null
	 */
	public void remove( Card pCard )
	{
		assert pCard != null;
		Boolean removed = aHand.remove(pCard);
		
		if (removed != null && isMatched())
		{
			reset();
		}
	}
	
	/**
	 * @return True if the hand is complete.
	 */
	public boolean isComplete()
	{
		if (aHand.size() < HANDSIZE)
		{
			return false; 
		}
		return true; 
	}
	
	/**
	 * Removes all the cards from the hand.
	 */
	public void clear()
	{
		aHand.clear();
		reset();
	}
	
	
	/**
	 * @return A copy of the set of matched sets
	 */
	public Set<ICardSet> getMatchedSets() // changed input type Set<ICardSet>
	{
		if (size() < HANDSIZE || !isMatched())
		{
			autoMatch();
		}
		HashSet<ICardSet> matchedSets = new HashSet<ICardSet>();
		for (Entry<CardSet, Boolean> cs : aMatchedSet.entrySet())
		{
			if ((boolean) cs.getValue())
			{
				matchedSets.add(cs.getKey());
			}
		}
		
		return matchedSets;
	}
	
	/**
	 * @return A copy of the set of unmatched cards.
	 * @pre isMatched() must be true
	 */
	public Set<Card> getUnmatchedCards()
	{
		Set<Card> cardSet = new HashSet<Card>();
		for (Entry<Card, Boolean> cs : aHand.entrySet())
		{
			if (!(boolean) cs.getValue())
			{
				cardSet.add(cs.getKey());
			}
		}
		return cardSet; 
	}
	
	/**
	 * @return The number of cards in the hand.
	 */
	public int size()
	{
		return aHand.size();
	}
	
	/**
	 * Determines if pCard is already in the hand, either as an
	 * unmatched card or as part of a set.
	 * @param pCard The card to check.
	 * @return true if the card is already in the hand.
	 * @pre pCard != null
	 */
	public boolean contains( Card pCard )
	{
		for (Card c : aHand.keySet())
		{
			if (c.equals(pCard))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return The total point value of the unmatched cards in this hand.
	 */
	protected void calculateScore()
	{
		aScore = 0;
		for (Entry<Card, Boolean> cs : aHand.entrySet())
		{
			if (!(boolean) cs.getValue())
			{
				aScore += (cs.getKey().getRank().compareTo(Rank.TEN) < 0) ? 
						cs.getKey().getRank().ordinal() + 1 : Rank.JACK.ordinal();
			}
		}
	}
	
	public int score()
	{
		return aScore;
	}
	
	/**
	 * Creates a group of cards of the same rank.
	 * @param pCards The cards to groups
	 * @pre pCards != null
	 * @throws HandException If the cards in pCard are not all unmatched
	 * cards of the hand or if the group is not a valid group.
	 */
	protected void createGroup( Set<Card> pCards )
	{
		assert pCards != null;
		ArrayList<Card> sortedHand = new ArrayList<Card>(pCards);
		Collections.sort(sortedHand, new Comparator<Card>(){
            public int compare(Card pC1, Card pC2)
            {
                return pC2.getRank().ordinal() -  pC1.getRank().ordinal();
            }
        });
		for (int i = 0; i<sortedHand.size() - 3; i++)
		{
			for (int j = i+3; j<=sortedHand.size(); j++)
			{
				CardSet tryMatch = new CardSet(new ArrayList<Card>(sortedHand.subList(i, j)));
				if (tryMatch.isGroup())
				{
					aMatchedSet.put(tryMatch, false);
					if (tryMatch.size() == 4)
					{
						for (int k = 1; k < 3; k++)
						{
							ArrayList<Card> middleMatch = new ArrayList<Card>(sortedHand.subList(i, j));
							middleMatch.remove(k);
							aMatchedSet.put(new CardSet(middleMatch), false);
						}
					}
				}
				else
				{
					break;
				}
			}
		}
	}
	
	/**
	 * Creates a run of cards of the same suit.
	 * @param pCards The cards to group in a run
	 * @pre pCards != null
	 * @throws HandException If the cards in pCard are not all unmatched
	 * cards of the hand or if the group is not a valid group.
	 */
	protected void createRun( Set<Card> pCards )
	{
		assert pCards != null; 
		ArrayList<Card> sortedHand = new ArrayList<Card>(pCards);
		Collections.sort(sortedHand);
		for (int i = 0; i<sortedHand.size() - 3; i++)
		{
			for (int j = i+3; j<=sortedHand.size(); j++)
			{
				CardSet tryMatch = new CardSet(new ArrayList<Card>(sortedHand.subList(i, j)));
				if (tryMatch.isRun())
				{
					aMatchedSet.put(tryMatch, false);
				}
				else
				{
					break;
				}
			}
		}
	}
	
	/*
	 * Helper method for automatch
	 */
	protected HashMap<Integer, HashSet<CardSet>> autoMatchRecurse(ArrayList<CardSet> pSets, 
																ArrayList<Card> pCards)
	{
		if (pSets.isEmpty())
		{
			return new HashMap<Integer, HashSet<CardSet>>();
		}
		
		HashMap<Integer, HashSet<CardSet>> result  = 
				autoMatchRecurse(new ArrayList<CardSet>(pSets.subList(1, pSets.size())), 
								 new ArrayList<Card>(pCards));
		
		// if first element can be put in
		boolean isFree = true;
		for (Card c : pSets.get(0))
		{
			if (pCards.contains(c))
			{
				isFree = false;
				break;
			}
		}
		if (isFree)
		{
			int point = 0;
			ArrayList<Card> inCards = new ArrayList<Card>(pCards);
			for (Card c : pSets.get(0))
			{
				inCards.add(c);
				point += c.getRank().ordinal();
			}
			HashMap<Integer, HashSet<CardSet>> tempResult = 
					autoMatchRecurse(new ArrayList<CardSet>(pSets.subList(1, pSets.size())), inCards);
			if (tempResult.isEmpty())
			{
				HashSet<CardSet> resSet = new HashSet<CardSet>();
				resSet.add(pSets.get(0));
				result.put(point, resSet);
			}
			else
			{
				for (Integer i : tempResult.keySet())
				{
					HashSet<CardSet> resSet = new HashSet<CardSet>(tempResult.get(i));
					resSet.add(pSets.get(0));
					result.put(point + i, resSet);
				}
			}
		}
		return result; 
	}
	
	/**
	 * Calculates the matching of cards into groups and runs that
	 * results in the lowest amount of points for unmatched cards.
	 */
	public void autoMatch()
	{
		if (isMatched()) // matched already done.
		{
			return; 
		}
		
		createGroup(aHand.keySet());
		createRun(aHand.keySet());
		
		HashMap<Integer, HashSet<CardSet>> matches = 
				autoMatchRecurse(new ArrayList<CardSet>(aMatchedSet.keySet()), new ArrayList<Card>());
		
		if (matches.size() != 0)
		{		
		ArrayList<Integer> sortedPoints = new ArrayList<Integer>(matches.keySet());
			Collections.sort(sortedPoints);
					
			// Minimize deadwook = maximize points in the matches
			for (CardSet s : matches.get(sortedPoints.get(sortedPoints.size() - 1)))
			{
				for (Card c : s)
				{
					aHand.put(c, true);
				}
				aMatchedSet.put(s, true);
			}
		}
		calculateScore();
	}
	
	/**
	 * Check if the score is enough to knock.
	 * @return
	 * @pre isMatched()
	 */
	public boolean canKnock()
	{
		assert isMatched();
		
		if (score() <= HANDSIZE)
		{
			return true;
		}
		return false;
	}
}

