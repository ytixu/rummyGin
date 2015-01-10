package gameEngine;

import org.junit.Test;
import static org.junit.Assert.*;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;
import ca.mcgill.cs.comp303.rummy.model.Hand;

public class testOptPlayer {
	
	GameEngineTest ge = new GameEngineTest();
	
	@Test
	public void testAutoMatch(){
		Hand h = new Hand();
		h.add(new Card(Rank.ACE, Suit.CLUBS));
		h.add(new Card(Rank.ACE, Suit.SPADES));
		h.add(new Card(Rank.ACE, Suit.DIAMONDS));
		h.add(new Card(Rank.NINE, Suit.CLUBS));
		h.add(new Card(Rank.TEN, Suit.CLUBS));
		h.add(new Card(Rank.JACK, Suit.CLUBS));
		h.add(new Card(Rank.QUEEN, Suit.CLUBS));
		h.add(new Card(Rank.KING, Suit.CLUBS));
		h.add(new Card(Rank.NINE, Suit.SPADES));
		h.add(new Card(Rank.TWO, Suit.CLUBS));
		h.autoMatch();
		assertEquals(h.score(), 11);
		h.remove(new Card(Rank.TWO, Suit.CLUBS));
		h.add(new Card(Rank.NINE, Suit.DIAMONDS));
		h.autoMatch();
		assertEquals(h.score(), 0);
	}
	
	@Test
	public void testPlayer(){
		Player p = new OptPlayer(ge);
		p.addCard(new Card(Rank.ACE, Suit.CLUBS));
		p.addCard(new Card(Rank.ACE, Suit.SPADES));
		p.addCard(new Card(Rank.ACE, Suit.DIAMONDS));
		p.addCard(new Card(Rank.NINE, Suit.CLUBS));
		p.addCard(new Card(Rank.TEN, Suit.CLUBS));
		p.addCard(new Card(Rank.JACK, Suit.CLUBS));
		p.addCard(new Card(Rank.QUEEN, Suit.CLUBS));
		p.addCard(new Card(Rank.KING, Suit.CLUBS));
		p.addCard(new Card(Rank.NINE, Suit.SPADES));
		p.addCard(new Card(Rank.TWO, Suit.CLUBS));
		
		System.out.println(p.handToString() + " score: " + p.getHandScore());
		
		ge.setCards(new Card(Rank.NINE, Suit.DIAMONDS), new Card(Rank.FOUR, Suit.HEARTS));
				
		p.pickCard();
		p.discard();
		System.out.println(p.handToString() + " score: " + p.getHandScore());
		p.knock();
	}
}
