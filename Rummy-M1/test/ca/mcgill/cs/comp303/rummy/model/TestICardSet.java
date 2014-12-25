package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;


public class TestICardSet
{
	@Test
	public void testGroupSet(){
		Card c = new Card(Rank.ACE, Suit.CLUBS);
		ICardSet s = new GroupSet(new HashSet<Card>(Arrays.asList(c)));
		assertTrue(s.isGroup());
		assertTrue(s.contains(c));
		assertTrue(s.size() == 1);
	}
	
	@Test 
	public void testRunSet(){
		Card c = new Card(Rank.ACE, Suit.CLUBS);
		ICardSet s = new RunSet(new HashSet<Card>(Arrays.asList(c)));
		assertFalse(s.isGroup());
		assertTrue(s.contains(c));
		assertTrue(s.size() == 1);
	}
}
