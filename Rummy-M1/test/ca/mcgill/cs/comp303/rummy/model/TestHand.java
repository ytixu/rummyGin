package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.cs.comp303.rummy.testutils.AllCards;;

public class TestHand
{
	private Hand aHand;
	@Before
	public void setup() {
		aHand = new Hand();
	}
	
	@Test(expected = HandException.class)
	public void testAdd()
	{
		aHand.add(AllCards.C2C);
		assertEquals(1, aHand.size());
		assertEquals(1, aHand.getUnmatchedCards().size());
		aHand.add(AllCards.C2C);
		assertEquals(1, aHand.size());
	}
}
