package ca.mcgill.cs.comp303.rummy.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;

/**
 * Models an iterable card set representing a group.
 * This card set is immutable.
 */

public class Group implements ICardSet 
{	
	private Set<Card> aGroup = new HashSet<Card>();

	/**
	 * Creates a group from a set of cards.
	 * @param pCards the set of cards to create.
	 */
	public Group(Set<Card> pCards)
	{
		aGroup = pCards;
	}
	
	@Override
	public Iterator<Card> iterator()
	{
		return aGroup.iterator();
	}

	@Override
	public boolean contains(Card pCard)
	{
		return aGroup.contains(pCard);
	}

	@Override
	public int size()
	{
		return aGroup.size();
	}

	@Override
	public boolean isGroup()
	{
		return true;
	}

	@Override
	public boolean isRun()
	{
		return false;
	}
	
	/**
	 * @return Whether the group or the run is valid or not.
	 */
	public boolean isValid()
	{
		if(!(size() == 3 || size() == 4))
		{
			return false;
		}
		
		Iterator<Card> groupIt = iterator();
		Card card = groupIt.next();
		Rank groupRank = card.getRank();
		
		while(groupIt.hasNext())
		{
			if(!(card.getRank().equals(groupRank)))
			{
				return false;
			}
			card = groupIt.next();
		}
		return true;
	}
}