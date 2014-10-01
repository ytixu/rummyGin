package ca.mcgill.cs.comp303.rummy.model;

import java.util.Iterator;

/**
 * A player. 
 * @author Yi Tian
 *
 */
public class Player implements ICardSet 
{
	private Hand aHand = new Hand();
	
	@Override
	public Iterator<Card> iterator() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(Card pCard) 
	{
		if (aHand.contains(pCard))
		{
			return true;
		}
		return false;
	}

	@Override
	public int size() 
	{
		return aHand.size();
	}

	@Override
	public boolean isGroup() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRun() 
	{
		// TODO Auto-generated method stub
		return false;
	}

}
