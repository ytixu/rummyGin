package ca.mcgill.cs.comp303.rummy.model;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;


public class TestAutoMatch
{
	private Card[] aCards;
	private Hand aHand;
	private int aScore;
	private HashSet<Card> aUnmatched;
	
	@Before 
	public void setup(){
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
		aUnmatched = new HashSet<Card>();
		aUnmatched.add(aCards[9]);
		aUnmatched.add(aCards[8]);
	}
	
	@Test
	public void testAutoMatch(){
		long time = System.currentTimeMillis();
		aHand.autoMatch();
		Set<ICardSet> matchedSet = aHand.getMatchedSets();
		time = System.currentTimeMillis() - time;
		assertTrue(100 > time);
		for (ICardSet s : matchedSet){
			for (Card c : s) assertFalse(aUnmatched.contains(c));
		}
		System.out.println(matchedSet.toString());
		System.out.println(time + " miliseconds");
		assertEquals(aScore, aHand.score());
	}
	
	@Test 
	public void testClear(){
		aHand.autoMatch();
		aHand.clear();
		assertNull(aHand.getMatchedSets());
	}
	
//	@Test
//	public void testReset(){
//		Card c = new Card(Rank.EIGHT, Suit.DIAMONDS);
//		aHand.autoMatch();
//		aHand.remove(c);
//		assertNull(aHand.getMatchedSets());
//		aHand.autoMatch();
//		assertNull(aHand.getMatchedSets());
//		aHand.add(c);
//		assertNull(aHand.getMatchedSets());
//	}
	
	/*
	* From mycourses
	*/
	@Test(timeout=100)
	public void test46()
	{
		Hand lHand = new Hand();
		lHand.add( new Card(Rank.ACE, Suit.SPADES) );
		lHand.add( new Card(Rank.TWO, Suit.SPADES) );
		lHand.add( new Card(Rank.THREE, Suit.SPADES) );
		lHand.add( new Card(Rank.FOUR, Suit.SPADES) );
		lHand.add( new Card(Rank.FOUR, Suit.DIAMONDS) );
		lHand.add( new Card(Rank.FOUR, Suit.CLUBS) );
		lHand.add( new Card(Rank.TWO, Suit.HEARTS) );
		lHand.add( new Card(Rank.THREE, Suit.HEARTS) );
		lHand.add( new Card(Rank.FOUR, Suit.HEARTS) );
		lHand.add( new Card(Rank.TWO, Suit.DIAMONDS) );
		lHand.autoMatch();
		Set<Card> lUnmatched = lHand.getUnmatchedCards();
		assertEquals( 1, lUnmatched.size());
		Set<ICardSet> lMatched = lHand.getMatchedSets();
		assertEquals( 3, lMatched.size());
		assertEquals( 2, lHand.score() );
	}
	@Test(timeout=100)
	public void test47()
	{
		Hand lHand = new Hand();
		lHand.add( new Card(Rank.NINE, Suit.DIAMONDS) );
		lHand.add( new Card(Rank.TEN, Suit.DIAMONDS) );
		lHand.add( new Card(Rank.JACK, Suit.DIAMONDS) );
		lHand.add( new Card(Rank.QUEEN, Suit.DIAMONDS) );
		lHand.add( new Card(Rank.QUEEN, Suit.SPADES) );
		lHand.add( new Card(Rank.QUEEN, Suit.CLUBS) );
		lHand.add( new Card(Rank.JACK, Suit.HEARTS) );
		lHand.add( new Card(Rank.QUEEN, Suit.HEARTS) );
		lHand.add( new Card(Rank.KING, Suit.HEARTS) );
		lHand.add( new Card(Rank.JACK, Suit.CLUBS) );
		lHand.autoMatch();
		Set<Card> lUnmatched = lHand.getUnmatchedCards();
		assertEquals( 1, lUnmatched.size());
		Set<ICardSet> lMatched = lHand.getMatchedSets();
		assertEquals( 3, lMatched.size());
		assertEquals( 10, lHand.score() );
	}
}
