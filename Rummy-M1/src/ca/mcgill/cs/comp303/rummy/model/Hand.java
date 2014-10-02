package ca.mcgill.cs.comp303.rummy.model;

import java.util.HashSet;
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
	private static final int FACE_POINT = 10;
	private static final int HAND_SIZE = 10;
	private Set<Card> aHand;
	private Set<ICardSet> aMatchedSets;
	private Set<Card> aUnmatchedCards;
	
	/**
	 * Creates a new, empty hand.
	 */
	public Hand()
	{
		aHand = new HashSet<Card>();
		aMatchedSets = new HashSet<ICardSet>();
		aUnmatchedCards = new HashSet<Card>();
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
		if(isComplete()) 
		{
			throw new HandException("Hand is complete.");
		}
		if(!aHand.add(pCard))
		{
			throw new HandException("Card is already in hand.");
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
		aHand.remove(pCard);
	}
	
	/**
	 * @return True if the hand is complete.
	 */
	public boolean isComplete()
	{
		if(aHand.size() == HAND_SIZE) 
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Removes all the cards from the hand.
	 */
	public void clear()
	{
		aHand.clear();
	}
	
	/**
	 * @return A copy of the set of matched sets
	 */
	public Set<ICardSet> getMatchedSets()
	{
		Set<ICardSet> copy = new HashSet<ICardSet>();
		copy.addAll(aMatchedSets);
		return copy;
	}
	
	/**
	 * @return A copy of the set of unmatched cards.
	 */
	public Set<Card> getUnmatchedCards()
	{
		Set<Card> copy = new HashSet<Card>();
		copy.addAll(aUnmatchedCards);
		return copy;
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
		assert pCard != null;
		return aHand.contains(pCard);
	}
	
	/**
	 * @return The total point value of the unmatched cards in this hand.
	 */
	public int score()
	{
		int score = 0;
		for(Card card : aUnmatchedCards)
		{
			if(card.getRank().ordinal() >= Rank.TEN.ordinal())
			{
				score += FACE_POINT;
			}
			else
			{
				score += card.getRank().ordinal() + 1;
			}
		}
		return score;
	}
	
	/**
	 * Creates a group of cards of the same rank.
	 * @param pCards The cards to groups
	 * @pre pCards != null
	 * @throws HandException If the cards in pCard are not all unmatched
	 * cards of the hand or if the group is not a valid group.
	 */
	public void createGroup( Set<Card> pCards )
	{
		assert pCards != null;
		if(!aUnmatchedCards.containsAll(pCards))
		{
			throw new HandException("Cards are not all from unmatched cards.");
		}
		
		Group newGroup = new Group(pCards);
		if(newGroup.isValid())
		{
			aMatchedSets.add(newGroup);
		}
		else 
		{
			throw new HandException("Not valid group.");
		}
	}
	
	/**
	 * Creates a run of cards of the same suit.
	 * @param pCards The cards to group in a run
	 * @pre pCards != null
	 * @throws HandException If the cards in pCard are not all unmatched
	 * cards of the hand or if the group is not a valid group.
	 */
	public void createRun( Set<Card> pCards )
	{
		assert pCards != null;
		if(!aUnmatchedCards.containsAll(pCards))
		{
			throw new HandException("Cards are not all from unmatched cards.");
		}
		
		Run newRun = new Run(pCards);
		if(newRun.isValid())
		{
			aMatchedSets.add(newRun);
		}
		else 
		{
			throw new HandException("Not valid run.");
		}
	}
	
	/**
	 * Calculates the matching of cards into groups and runs that
	 * results in the lowest amount of points for unmatched cards.
	 */
	public void autoMatch()
	{
		// TODO
	}
}
