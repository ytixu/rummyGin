package ca.mcgill.cs.comp303.rummy.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Models an iterable card set representing a run.
 * This card set is immutable.
 */

public class Run implements ICardSet 
{	
	private Set<Card> aRun = new HashSet<Card>();

	/**
	 * Creates a group from a set of cards.
	 * @param pCards the set of cards to create.
	 */
	public Run(Set<Card> pCards)
	{
		aRun = pCards;
	}
	
	@Override
	public Iterator<Card> iterator()
	{
		return aRun.iterator();
	}

	@Override
	public boolean contains(Card pCard)
	{
		return aRun.contains(pCard);
	}

	@Override
	public int size()
	{
		return aRun.size();
	}

	@Override
	public boolean isGroup()
	{
		return false;
	}

	@Override
	public boolean isRun()
	{
		return true;
	}
	
	/**
	 * @return Whether the group or the run is valid or not.
	 */
	public boolean isValid()
	{
		if(size() < 3 )
		{
			return false;
		}
		
		// Make an array of ordinals
		int[] cardRanks = new int[size()];
		int i = 0;
		for(Card card : aRun)
		{
			cardRanks[i] = card.getRank().ordinal();
			i++;
		}
		// Sort the ranks
		Arrays.sort(cardRanks);
		
		// Check whether is a run
		for(int j = 0; j < cardRanks.length - 1; j++)
		{
			if(cardRanks[j + 1] != cardRanks[j] + 1)
			{
				return false;
			}
		}
		return true;
	}
}