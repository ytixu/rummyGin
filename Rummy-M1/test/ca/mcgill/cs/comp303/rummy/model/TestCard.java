package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;
import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;

import org.junit.Before;
import org.junit.Test;

public class TestCard
{
	private Card aCard;
	
	@Before
	public void setup() 
	{
		aCard = new Card(Rank.ACE, Suit.SPADES);
	}

	@Test
	public void testConstructor()
	{
		assertEquals(Rank.ACE, aCard.getRank());
		assertEquals(Suit.SPADES, aCard.getSuit());
	}
	
	@Test
	public void testHashCode()
	{
		assertEquals(39, aCard.hashCode());
	}
}
