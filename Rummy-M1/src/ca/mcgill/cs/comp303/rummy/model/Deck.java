package ca.mcgill.cs.comp303.rummy.model;

import java.util.Collections;
import java.util.ArrayList;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;

/**
 * Models a deck of 52 cards (no joker).
 */
public class Deck 
{
	private static int SIZE = 52;
	
	private ArrayList<Card> aCards; 
	private int aTopCard;
	
	/**
	 * Creates a new deck of 52 cards, shuffled.
	 */
	public Deck()
	{
		aCards = new ArrayList<Card>();
		for( Suit lSuit : Suit.values() )
		{
            for( Rank lRank : Rank.values() )
            {
                aCards.add( new Card( lRank, lSuit ));
            }
		}
		shuffle();
	}
	
	private void reset()
	{
		aTopCard = SIZE;
	}

	/**
	 * Shuffle the deck of cards by retrieving all 52 cards and randomizing
	 * their order.
	 */
	public void shuffle()
	{
		reset();
		Collections.shuffle( aCards );
	}
	
	/**
	 * Draws a card from the deck and removes the card from the deck.
	 * @return The card drawn.
	 * @pre initial.size() > 0
	 * @post final.size() == initial.size() - 1
	 */
	public Card draw()
	{
		assert size() > 0;
		aTopCard --;
		return aCards.get(aTopCard);
	}
	
	/**
	 * Returns the size of the deck.
	 * @return The number of cards in the deck.
	 */
	public int size()
	{
		return aTopCard;
	}
}
