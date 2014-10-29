package ca.mcgill.cs.comp303.rummy.model;

import java.util.Iterator;
import java.util.Set;

public abstract class Player {

	protected Hand aHand;
	private int aScore;
	protected GameViewer aGame;
	
	public Player(GameViewer pGame) {
		aHand = new Hand();
		aScore = 0;
		aGame = pGame;
	}
	
	public Hand getHand() {
		return aHand;
	}
	
	public Card getLowestCard() {
		Set<Card> hand = aHand.getUnmatchedCards();
		Iterator<Card> it = hand.iterator();
		Card i = it.next();
		Card lowest = i;
		while(it.hasNext())
		{
			i = it.next();
			if(lowest.compareTo(i) < 0)
			{
				lowest = i;
			}
		}
		if(lowest.compareTo(i) < 0)
		{
			lowest = i;
		}
		return lowest;
	}
	/*
	 * Decides what card to discard (not actually discarding)
	 */
	abstract Card discard();
	
	/*
	 * Decides where to draw 
	 */
	abstract GameViewer.PILE draw();
	
	/*
	 * Decides to knock
	 */
	abstract boolean knock();
	
	public int getScore() {
		return aScore;
	}
}