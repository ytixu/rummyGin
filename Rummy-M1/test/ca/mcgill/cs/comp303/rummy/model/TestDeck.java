package ca.mcgill.cs.comp303.rummy.model;

import org.junit.Test;

import static org.junit.Assert.*;


public class TestDeck
{
	@Test
	public void testShuffle()
	{
		Deck decka = new Deck();
		Deck deckb = new Deck(); 
		int countSame = 0;
		while (decka.size() > 0)
		{
			if (decka.draw().equals(deckb.draw()))
			{
				countSame ++;
			}
		}
		assertFalse(countSame == 52);
	}
	
	@Test 
	public void testDraw()
	{
		Deck deck = new Deck();
		Card card = deck.draw();
		assertTrue(deck.size() == 51);
		System.out.println(card.toString());	
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testError()
	{
		Deck deck = new Deck();
		while(true)
		{
			deck.draw();
		}
	}
}
