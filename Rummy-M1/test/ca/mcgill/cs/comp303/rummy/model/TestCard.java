package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;


public class TestCard
{
	@Test
	public void testRankSuit(){
		Card c = new Card(Rank.ACE, Suit.CLUBS);
		assertEquals(c.getRank(), Rank.ACE);
		assertEquals(c.getSuit(), Suit.CLUBS);
	}
	
	@Test
	public void testCompareEqual(){
		Card a = new Card(Rank.EIGHT, Suit.HEARTS);
		Card b = new Card(Rank.EIGHT, Suit.HEARTS);
		Card c = b;
		Card d = new Card(Rank.FOUR, Suit.HEARTS);
		Card e = new Card(Rank.EIGHT, Suit.CLUBS);
		Card f = new Card(Rank.NINE, Suit.DIAMONDS);
		assertEquals(a.compareTo(b), 0);
		assertEquals(b.compareTo(c), 0);
		assertEquals(a.compareTo(d), 4);
		assertEquals(a.compareTo(e), 26);
		assertEquals(a.compareTo(f), 12);
		assertTrue(a.equals(b));
		assertTrue(a.equals(c));
		assertTrue(b.equals(c));
		assertFalse(b.equals(f));
	}
	
	@Test
	public void testString(){
		Card c = new Card(Rank.KING, Suit.SPADES);
		assertEquals(c.toString(), "KING of SPADES");
	}
}
