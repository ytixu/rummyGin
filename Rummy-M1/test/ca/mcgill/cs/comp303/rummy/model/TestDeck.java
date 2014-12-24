package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDeck
{
	@Test
	public void testSize(){
		Deck d = new Deck();
		assertEquals(d.size(), 52);
	}
	
	@Test 
	public void testTwoDeck(){
		Deck a = new Deck();
		Deck b = new Deck();
		while(a.size()>0){
			assertTrue(b.size()>0);
			if (!a.draw().equals(b.draw())){
				return;
			}
		}
		assertTrue(false);
	}
}
