package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.cs.comp303.rummy.testutils.AllCards;;

public class TestHand
{
	@Test
    public void test46()
    {
        Hand lHand = new Hand();
        lHand.add( AllCards.CAS );
        lHand.add( AllCards.C2S );
        lHand.add( AllCards.C3S );
        lHand.add( AllCards.C4S );
        lHand.add( AllCards.C4D );
        lHand.add( AllCards.C4C );
        lHand.add( AllCards.C2H );
        lHand.add( AllCards.C3H );
        lHand.add( AllCards.C4H );
        lHand.add( AllCards.C2D );
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
        lHand.add( AllCards.C9D );
        lHand.add( AllCards.CTD );
        lHand.add( AllCards.CJD );
        lHand.add( AllCards.CQD );
        lHand.add( AllCards.CQS );
        lHand.add( AllCards.CQC );
        lHand.add( AllCards.CJH );
        lHand.add( AllCards.CQH );
        lHand.add( AllCards.CKH );
        lHand.add( AllCards.CJC );
        lHand.autoMatch();
        Set<Card> lUnmatched = lHand.getUnmatchedCards();
        assertEquals( 1, lUnmatched.size());
        Set<ICardSet> lMatched = lHand.getMatchedSets();
        assertEquals( 3, lMatched.size());
        assertEquals( 10, lHand.score() );
    }
}
