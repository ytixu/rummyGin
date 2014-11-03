package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.Iterator;

public class CardSetPack implements ICardSet 
{
	private ICardSet aKnockerSet;
	private ArrayList<Card> aAppendTop;
	private ArrayList<Card> aAppendBottom;
	
	/**
	 * This Iterator combines a bunch of iterators.
	 *
	 */
	public class IteratorOfIterators implements Iterator<Card> 
	{
	    private final Iterator<Card>[] aIterators;
	    private int aCurrent;
	    
	    /**
	     * 
	     * @param iterators
	     */
	    public IteratorOfIterators(Iterator<Card> ... pIterators) 
	    {
	    	aIterators = pIterators;
	    	aCurrent = 0;
	    }

	    /**
	     * @return
	     */
	    public boolean hasNext() 
	    {
	    	while ( aCurrent < aIterators.length && !aIterators[aCurrent].hasNext() )
	    	{
	    		aCurrent++;
	    	}

	    	return aCurrent < aIterators.length;
		}

	    /**
	     * @return
	     */
	    public Card next() 
	    { 
	    	while ( aCurrent < aIterators.length && !aIterators[aCurrent].hasNext() )
	    	{
	    		aCurrent++;
	    	}

	    	return aIterators[aCurrent].next();
	    }
	}

	/**
	 * 
	 * @param pCardSet
	 */
	public CardSetPack(ICardSet pCardSet)
	{
		aKnockerSet = pCardSet;
		aAppendTop = new ArrayList<Card>();
		aAppendBottom = new ArrayList<Card>();
	}
	
	@Override
	public Iterator<Card> iterator() 
	{
		return new IteratorOfIterators(aAppendTop.iterator(), 
									   aKnockerSet.iterator(), 
									   aAppendBottom.iterator());
	}

	@Override
	public boolean contains(Card pCard) 
	{
		if (aKnockerSet.contains(pCard) || 
			aAppendTop.contains(pCard) ||
			aAppendBottom.contains(pCard))
		{
			return true;
		}
		return false;
	}

	@Override
	public int size() 
	{
		return aKnockerSet.size() + aAppendTop.size() + aAppendBottom.size();
	}

	@Override
	public boolean isGroup() 
	{
		return aKnockerSet.isGroup();
	}

	@Override
	public boolean isRun() 
	{
		return aKnockerSet.isRun();
	}
	
	/**
	 * Add a card to the beginning.
	 * @param pCard
	 */
	public void appendTop(Card pCard)
	{
		aAppendTop.add(pCard);
	}
	
	/**
	 * Add a card to the end.
	 * @param pCard
	 */
	public void appendBottom(Card pCard)
	{
		aAppendBottom.add(pCard);
	}

	public String toString()
	{
		return aAppendTop.toString() + aKnockerSet.toString() + aAppendBottom.toString();
		
	}
}


