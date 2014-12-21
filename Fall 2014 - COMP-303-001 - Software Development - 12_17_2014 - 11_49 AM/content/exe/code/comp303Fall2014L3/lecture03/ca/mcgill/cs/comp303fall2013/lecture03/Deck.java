package ca.mcgill.cs.comp303fall2013.lecture03;

import java.util.Collections;
import java.util.Stack;

import ca.mcgill.cs.comp303fall2013.lecture03.Card.Rank;
import ca.mcgill.cs.comp303fall2013.lecture03.Card.Suit;


/**
 * Models a deck of 52 cards (no joker).
 */
public class Deck 
{
	private Stack<Card> aCards = new Stack<Card>();
	
	public Deck()
	{
		reset();
		shuffle();
	}

	public void reset()
	{
		aCards.clear();
		for(Suit suit : Suit.values())
		{
			for(Rank rank : Rank.values())
			{
				aCards.add(new Card(rank,suit));
			}
		}
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
