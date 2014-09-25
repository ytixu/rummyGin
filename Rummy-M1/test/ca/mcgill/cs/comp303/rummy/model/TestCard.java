package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;
import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;
import ca.mcgill.cs.comp303.rummy.testutils.*;

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
	
	@Test
	public void testCompareAndEqual()
	{
		Card smallCard = new Card(Rank.ACE, Suit.CLUBS);
		assertEquals(0, aCard.compareTo(aCard));
		assertEquals(1, aCard.compareTo(smallCard));
		assertEquals(true, aCard.equals(aCard));
		assertEquals(false, aCard.equals(smallCard));
	}
	
	@Test
	public void testCompareTo()
	{
		assertEquals(1, aCard.compareTo(AllCards.CAC));
		assertEquals(0, aCard.compareTo(AllCards.CAS));
		assertEquals(-1, aCard.compareTo(AllCards.CKS));
	}
	
	@Test
	public void testEquals()
	{
		assertEquals(true, aCard.equals(AllCards.CAS));
		assertEquals(false, aCard.equals(null));
		// Random object used to compared
		assertEquals(false, aCard.equals(AllCards.CAS.getClass()));
	}
}
