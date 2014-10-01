package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A immutable set of cards with factory.
 * @author Yi Tian
 *
 */
	
public class CardSet implements ICardSet 
{
	
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
		// if (size() < 3) return false;
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
		return true;
	}

	@Override
	public boolean isRun() 
	{
		// if (size() < 3) return false;
		// linear scan, since we assume sorted order 
		for (int i = 0; i<size()-1; i++)
		{
			if (aSet.get(i).hashCode() + 1 != aSet.get(i+1).hashCode())
			{
				return false;
			}
		}
		return true;
	}
	
	public String toString()
	{
		return aSet.toString();
	}
}

