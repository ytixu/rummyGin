package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;


public class TestHand
{
	private Card[] aCards;
	private Hand aHand;
	private int aScore;
	private HashSet<Card> aUnmatched;
	private Card aSingleCard;
	
	@Before
	public void setup() 
	{
		aCards = new Card[]{	
							new Card(Rank.KING, Suit.SPADES),
							new Card(Rank.QUEEN, Suit.SPADES),
							new Card(Rank.NINE, Suit.SPADES),
							new Card(Rank.QUEEN, Suit.DIAMONDS),
							new Card(Rank.QUEEN, Suit.CLUBS),
							new Card(Rank.TEN, Suit.SPADES),
							new Card(Rank.QUEEN, Suit.HEARTS),
							new Card(Rank.JACK, Suit.SPADES),
							new Card(Rank.ACE, Suit.DIAMONDS),
							new Card(Rank.EIGHT, Suit.DIAMONDS)};
		// optimal combination is [k,q,j,10,9], [q,q,q]
		aScore = 9;
		aHand = new Hand();
		for (int i = 0; i < aCards.length; i++)
		{
			aHand.add(aCards[i]);
		}
		aUnmatched = new HashSet<Card>();
		aUnmatched.add(aCards[9]);
		aUnmatched.add(aCards[8]);
		aSingleCard = new Card(Rank.FOUR, Suit.HEARTS);
	}
	
	/**
	 * 
	 */
	@Test
	public void testAdd()
	{
		for (int i = 0; i < aCards.length; i++)
		{
			assertTrue(aHand.contains(aCards[i])); 
		}
		assertEquals(aHand.size(), aCards.length);
	}
	
	@Test
	public void testAutoMatch()
	{
		assertFalse(aHand.isMatched());
		long time = System.currentTimeMillis();
		aHand.autoMatch();
		Set<CardSet> matchedSet = aHand.getMatchedSets();
		time = System.currentTimeMillis() - time;
		assertTrue(aHand.isMatched());
		aHand.autoMatch();
		assertTrue(100 > time);
		for (CardSet s : matchedSet)
		{
			for (Card c : s)
			{
				assertFalse(aUnmatched.contains(c));
			}
		}
		System.out.println(matchedSet.toString());
		System.out.println(time + " miliseconds" +  aHand.score());
		assertEquals(aScore, aHand.score());
	}
	
	@Test
	public void testRemove()
	{
		aHand.autoMatch();
		aHand.remove(aCards[0]);
		assertFalse(aHand.isComplete());
		assertFalse(aHand.isMatched());
		aHand.add(aSingleCard);
		assertTrue(aHand.isComplete());
		assertFalse(aHand.isMatched());
		aHand.autoMatch();
		assertTrue(aHand.isMatched());
		aHand.remove(aCards[0]);
		assertTrue(aHand.isComplete());
		assertTrue(aHand.isMatched());
	}
	
	@Test 
	public void testUnmatched()
	{
		aHand.autoMatch();
		Set<Card> unmatched = aHand.getUnmatchedCards();
		for (Card c : unmatched)
		{
			assertTrue(aUnmatched.contains(c));
		}
		assertEquals(aUnmatched.size(), unmatched.size());
	}
	
	@Test(expected=HandException.class)
	public void testAddComplete()
	{
		assertTrue(aHand.isComplete());
		aHand.add(aSingleCard);
	}
	
	@Test(expected=HandException.class)
	public void testAddAlreadyIn()
	{
		aHand.remove(aCards[0]);
		assertTrue(aHand.contains(aCards[1]));
		aHand.add(aCards[1]);
	
	}
}
