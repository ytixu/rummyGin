package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestHand  
{
	private Hand h;
	private Deck d;
	
	@Before
	public void setVars(){
		h = new Hand();
		d = new Deck();
	}
	
	@Test
	public void testAddContains(){
		for (int i = 0; i<10; i++){
			Card c = d.draw();
			assertFalse(h.contains(c));
			h.add(c);
			assertTrue(h.contains(c));
		}
		assertTrue(h.isComplete());
		Card c = d.draw();
		assertFalse(h.contains(c));
	}
	
	@Test (expected=HandException.class)
	public void addCompleteHand(){
		for (int i = 0; i<11; i++){
			h.add(d.draw());
		}
	}
	
	@Test (expected=HandException.class)
	public void reAddCard(){
		Card c = d.draw();
		h.add(c);
		h.add(c);
	}
	
	@Test
	public void testRemove(){
		Card c = d.draw();
		h.add(c);
		assertTrue(h.contains(c));
		h.remove(c);
		assertFalse(h.contains(c));
	}
	
}
