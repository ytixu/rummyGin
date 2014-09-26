package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;

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
		for (int i=0; i < aCards.length; i++) aHand.add(aCards[i]);
		
	}
	
	@Test
	public void testAdd(){
		Set<Card> inHand = aHand.getHand();
//		System.out.println(inHand.toString());
		for (int i=0; i < aCards.length; i++)
		{
			assertTrue(aHand.contains(aCards[i])); 
		}
		assertEquals(aHand.size(), aCards.length);
	}
//	
//	@Test
//	public void testComplete(){
//		assertTrue(aHand.isComplete());
//	}
//	
//	@Test
//	public void testRemove(){
//		aHand.remove(aCards[0]);
//		assertTrue(!aHand.isComplete());
//	}
	
	@Test
	public void testAutoMatch(){
		aHand.autoMatch();
		Set<CardSet> matchedSet = aHand.getMatchedSets();
		System.out.println(matchedSet.toString());
	}
}
