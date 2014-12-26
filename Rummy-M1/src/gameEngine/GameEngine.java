package gameEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;
import ca.mcgill.cs.comp303.rummy.model.Deck;

/**
 * Basic game engine.
 */
public class GameEngine implements IGameEngineGetter, IGameEngineSetter {
	
	private enum POINTS{
		UNDERCUT (10),
		GOGIN (20),
		ENDGAME (100),
		LINEBONUS (10),
		ALLWIN (200);
		
		private int value;
		POINTS(int p){
			value = p;
		}
	}

	private Player[] aPlayers; 
	private ArrayList<GameObserver> aObservers;
	private Deck aDeck;
	private Card aDiscard;
	private String lastMove;
	private int turn;
	private Set<ICardSet> aMatchedSet = null;
	private int winner;
	
	public GameEngine(Player... pPlayers){
		aPlayers = new Player[pPlayers.length];
		for (int i=0; i<pPlayers.length; i++){
			aPlayers[i] = pPlayers[i];
		}
		aObservers = new ArrayList<GameObserver>();
		aDeck = new Deck();
		distributeCards();
		// set turn
		int minCard = Integer.MAX_VALUE;
		for (int i=0; i<aPlayers.length; i++){
			for (Card c: aPlayers[i]){
				if (c.hashCode() < minCard){
					turn = i;
					minCard = c.hashCode();
				}
			}
		}
		lastMove = "New game.\n" + aPlayers.toString() + "\n" 
				+ aPlayers[turn].toString() + " begins.";
	}
	
	public void reset(){
		aDeck.shuffle();
		lastMove = "New game.\n" + aPlayers.toString();
		aMatchedSet = null;
	}
	
	@Override
	public Card drawFromDeck() {
		lastMove = aPlayers[turn].toString() + " draws from deck.";
		return aDeck.draw();
		
	}

	@Override
	public Card takeDiscard() {
		lastMove = aPlayers[turn].toString() + " takes the discarded card.";
		return aDiscard;
	}

	@Override
	public void discard(Card pCard) {
		aDiscard = pCard;
		lastMove = aPlayers[turn].toString() + " discards " + pCard.toString() + ".";
	}

	@Override
	public void knock() {
		lastMove = aPlayers[turn] + " knocks.\n" + aPlayers[turn].getMatchedSets().toString() + 
					"\nUnmatched cards:" + aPlayers[turn].getDeadwook();
		aPlayers[turn+1].addDeadwook(aPlayers[turn].getMatchedSets());
	}

	@Override
	public void layout(Set<ICardSet> aSets) {
		turn++;
		lastMove = aPlayers[turn] + "'s matched set:\n" + aPlayers[turn].getMatchedSets().toString()
					+ "'s rest:" + aPlayers[turn].getDeadwook() + "\nFinal matched set\n"
					+ aSets.toString();
		aMatchedSet = aSets;
	}

	@Override
	public Card peekDiscard() {
		return aDiscard;
	}

	@Override
	public String getPlayed() {
		return lastMove;
	}

	@Override
	public void notifyGameObserver() {
		for (GameObserver o : aObservers){
			o.update(this);
		}
	}

	@Override
	public Iterator<Player> getPlayers() {
		return Arrays.asList(aPlayers).iterator();
	}

	public void distributeCards(){
		for (int i=0; i<Hand.HANDSIZE; i++){
			for (Player p: aPlayers){
				p.addCard(aDeck.draw());
			}
		}
	}
}
