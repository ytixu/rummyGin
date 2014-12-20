package ca.mcgill.cs.comp303fall2014.lecture02b;

import java.util.Collections;
import java.util.Stack;

import ca.mcgill.cs.comp303fall2014.lecture02b.Card.Rank;
import ca.mcgill.cs.comp303fall2014.lecture02b.Card.Suit;

/**
 * Models a deck of 52 cards (no joker).
 */
public class Deck 
{
	private Stack<Card> aCards = new Stack<Card>();
	
	public Deck()
	{
		reset();
	}

	public void reset()
	{
		aCards.clear();
		for(Suit suit : Suit.values())
		{
			for(Rank rank : Rank.values())
			{
				if (rank.equals(Rank.JOCKER)){
					continue;
				}
				aCards.add(new Card(rank,suit));
			}
		}
		aCards.add(new Card(Rank.JOCKER));
		aCards.add(new Card(Rank.JOCKER));
	}
	
	public void shuffle()
	{
		Collections.shuffle(aCards);
	}
	
	public Card draw()
	{
		return aCards.pop();
	}
	
	public int size()
	{
		return aCards.size();
	}
}
