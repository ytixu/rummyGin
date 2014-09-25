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
							new Card(Rank.JACK, Suit.SPADES)};
	}
	
	@Test
	public void testAdd(){
		Hand hand = new Hand();
		for (int i=0; i < aCards.length; i++) hand.add(aCards[i]);
		Set<Card> inHand = hand.getHand();
		System.out.println(inHand.toString());
		for (int i=0; i < aCards.length; i++) assert(hand.contains(aCards[i])); 
		assertEquals(hand.size(), aCards.length);
	}
	
	@Test
	public void testAutoMatch(){
		Hand hand = new Hand();
		for (int i=0; i < aCards.length; i++) hand.add(aCards[i]);
		hand.autoMatch();
		Set<CardSet> matchedSet = hand.getMatchedSets();
		System.out.println(matchedSet.toString());
	}
}
