package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
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
	private static final int HAND_SIZE = 11;
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
		if(aHand.contains(pCard))
		{
			throw new HandException("Card is already in hand.");
		}
		aHand.add(pCard);
		aUnmatchedCards.add(pCard);
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
		if(aHand.contains(pCard))
		{
			aHand.remove(pCard);
			aUnmatchedCards.remove(pCard);
			ICardSet toBeRemoved = null;
			for(ICardSet cs : aMatchedSets)
			{
				if(cs.contains(pCard))
				{
					toBeRemoved = cs;
				}
			}
			aMatchedSets.remove(toBeRemoved);
		}
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
		aUnmatchedCards.clear();
		aMatchedSets.clear();
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
			aUnmatchedCards.removeAll(pCards);
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
			aUnmatchedCards.removeAll(pCards);
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
		Node<Hand> tree = matchSets(copy());
		Hand lowestScoringHand = getLowestHand(tree);
		aHand = lowestScoringHand.aHand;
		aUnmatchedCards = lowestScoringHand.aUnmatchedCards;
		aMatchedSets = lowestScoringHand.aMatchedSets;
	}

	private Hand getLowestHand( Node<Hand> pTree ) 
	{
		if(pTree.getChildren().size() == 0)
		{
			return pTree.get();
		}
		

		Hand minHand = null;
		
		// Find lowest scoring child
		for(Node<Hand> child : pTree.getChildren())
		{
			Hand lowestGrandChild = getLowestHand(child);
			if(child.get().score() < lowestGrandChild.score())
			{
				minHand = child.get();
			}
			else
			{
				minHand = lowestGrandChild;
			}
		}
		
		// Compare with parent
		if(pTree.get().score() < minHand.score())
		{
			return pTree.get();
		}
		else
		{
			return minHand;
		}
	}
	
	private Node<Hand> matchSets( Hand pHand )
	{
		if(pHand.aUnmatchedCards.size() < 3)
		{
			return null;
		}
		
		Node<Hand> node = new Node<Hand>(pHand);
		for(Set<Card> group : pHand.getPossibleGroups())
		{
			Hand childHand = pHand.copy();
			childHand.createGroup(group);
			node.add(matchSets(childHand));
		}
		
		for(Set<Card> runs : pHand.getAllRuns())
		{
			node.get().createRun(runs);
		}
		return node;
	}
	
	private ArrayList<Set<Card>>getPossibleGroups()
	{
		ArrayList<Set<Card>> returnList = new ArrayList<Set<Card>>();
		int[] numberOfAppearance = new int[Card.NUMBER_OF_RANKS];
		for(Card c : aUnmatchedCards)
		{
			numberOfAppearance[c.getRank().ordinal()]++;
		}
		
		for(int i = 0; i < numberOfAppearance.length; i++)
		{
			if(numberOfAppearance[i] == 4)
			{
				Set<Card> fourOfAKind = new HashSet<Card>();
				Set<Card> threeOfAKind = new HashSet<Card>();
				for(Card c : aUnmatchedCards)
				{
					if(c.getRank().ordinal() == i)
					{
						fourOfAKind.add(c);
						if(threeOfAKind.size() < 3)
						{
							threeOfAKind.add(c);
						}
					}
				}
				returnList.add(fourOfAKind);
				returnList.add(threeOfAKind);
			}
			else if(numberOfAppearance[i] == 3)
			{
				Set<Card> threeOfAKind = new HashSet<Card>();
				for(Card c : aUnmatchedCards)
				{
					if(c.getRank().ordinal() == i)
					{
						threeOfAKind.add(c);
					}
				}
				returnList.add(threeOfAKind);
			}
			else
			{
				continue;
			}
		}
		return returnList;
	}
	
	private ArrayList<Set<Card>>getAllRuns()
	{
		ArrayList<Set<Card>> returnList = new ArrayList<Set<Card>>();
		int[] numberOfAppearance = new int[Card.NUMBER_OF_RANKS];
		for(Card c : aUnmatchedCards)
		{
			numberOfAppearance[c.getRank().ordinal()]++;
		}
		
		Set<Card> workingCards = new HashSet<Card>(aUnmatchedCards);
		for(int i = 0; i < numberOfAppearance.length; i++)
		{
			if(i + 2 >= numberOfAppearance.length)
			{
				break;
			}
			if(numberOfAppearance[i] > 0 && numberOfAppearance[i + 1] > 0 && numberOfAppearance[i + 2] > 0)
			{
				Set<Card> runToAdd = new HashSet<Card>();
				for(int j = i; j < i + 3; j++)
				{
					Card found = null;
					for(Card c : workingCards)
					{
						if(c.getRank().ordinal() == j)
						{
							runToAdd.add(c);
							found = c;
							numberOfAppearance[j]--;
							break;
						}
					}
					workingCards.remove(found);
				}
				returnList.add(runToAdd);
			}
		}
		
		for(Card c : workingCards)
		{
			Set<Card> toRemove = null;
			for(Set<Card> run : returnList)
			{
				Set<Card> possibleRun = new HashSet<Card>(run);
				possibleRun.add(c);
				if(new Run(possibleRun).isValid())
				{
					toRemove = run;
					returnList.add(possibleRun);
					break;
				}
			}
			if(toRemove != null)
			{
				returnList.remove(toRemove);
			}
		}
		
		return returnList;
	}
	
	private Hand copy()
	{
		Hand returnHand = new Hand();
		returnHand.aHand = new HashSet<Card>(aHand);
		returnHand.aMatchedSets = new HashSet<ICardSet>(aMatchedSets);
		returnHand.aUnmatchedCards = new HashSet<Card>(aUnmatchedCards);
		return returnHand;
	}
}
