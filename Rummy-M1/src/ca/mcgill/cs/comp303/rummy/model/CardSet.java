package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A immutable set of cards with factory.
 * @author Yi Tian
 * @inv size >= 3
 */
	
public class CardSet implements ICardSet 
{
	private enum SetType 
	{ GRP, RUN, IDIOT }
	
	private SetType aGroupOrRunID;
	
	// Assume that the cards are sorted and has at least 3 elements
		
	private ArrayList<Card> aSet;
	
	/**
	 * Creates a new, card set.
	 * @param pCards a set of cards
	 */
	public CardSet(ArrayList<Card> pCards)
	{
		//assume that the cards are distinct
		aSet = pCards;
		aGroupOrRunID = SetType.IDIOT;
	}
	
	@Override
	public Iterator<Card> iterator() 
	{
		return aSet.iterator();
	}

	@Override
	public boolean contains(Card pCard) 
	{
		for (Card c : this)
		{
			if (c.equals(pCard))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int size() 
	{
		return aSet.size();
	}

	@Override
	public boolean isGroup() 
	{
		if (aGroupOrRunID.equals(SetType.GRP))
		{
			return true;
		}
		// linear scan
		int rank = -1;
		for (Card c : this)
		{
			if (rank == -1)
			{
				rank = c.getRank().ordinal();
			} 
			else if (rank != c.getRank().ordinal())
			{
				return false;
			}
		}
		aGroupOrRunID = SetType.GRP;
		return true;
	}

	@Override
	public boolean isRun() 
	{
		if (aGroupOrRunID.equals(SetType.RUN))
		{
			return true;
		}
		// linear scan, since we assume sorted order 
		for (int i = 0; i<size()-1; i++)
		{
			if (aSet.get(i).hashCode() + 1 != aSet.get(i+1).hashCode())
			{
				return false;
			}
		}
		aGroupOrRunID = SetType.RUN;
		return true;
	}
	
	public String toString()
	{
		return aSet.toString();
	}
}

