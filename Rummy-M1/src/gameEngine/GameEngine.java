package gameEngine;

import java.util.ArrayList;
import java.util.Arrays;
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
		
		private int bonus;
		POINTS(int p){
			bonus = p;
		}
	}

	private Player[] aPlayers; 
	private ArrayList<GameObserver> aObservers;
	private Deck aDeck;
	private Card aDiscard;
	private String lastMove;
	private int turn;
	private Set<ICardSet> aMatchedSet = null;
	private int knocker;
	private ArrayList<Integer> winners;
	
	public GameEngine(Player... pPlayers){
		aObservers = new ArrayList<GameObserver>();
		aDeck = new Deck();
		knocker = -1;
	} 
	
	public void setPlayer(Player... pPlayers){
		aPlayers = new Player[pPlayers.length];
		for (int i=0; i<pPlayers.length; i++){
			aPlayers[i] = pPlayers[i];
		}
		start();
	}
	
	public void setPlayer(Set<Player> pPlayers){
		aPlayers = pPlayers.toArray(new Player[pPlayers.size()]);
		start();
	}
	
	private void start(){
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
		winners = new ArrayList<Integer>();
		lastMove = "New game.\n" + Arrays.toString(aPlayers) + "\n" 
				+ aPlayers[turn].toString() + " begins.";
		notifyGameObserver();
	}
	
	public void addObserver(GameObserver o){
		aObservers.add(o);
	}
	
	public void reset(){
		aDeck.shuffle();
		lastMove = "New game.\n" + aPlayers.toString();
		aMatchedSet = null;
		winners.clear();
		knocker = -1;
		notifyGameObserver();
	}
	
	public int nextTurn(){
		return (turn+1) % aPlayers.length;
	}
	
	@Override
	public Card drawFromDeck() {
		Card drawn = aDeck.draw();
		lastMove = aPlayers[turn].toString() + " draws from deck " + drawn.toString() + ".";
		notifyGameObserver();
		return drawn;
	}

	@Override
	public Card takeDiscard() {
		lastMove = aPlayers[turn].toString() + " takes the discarded card " 
					+ aDiscard.toString() + ".";
		notifyGameObserver();
		return aDiscard;
	}

	@Override
	public void discard(Card pCard) {
		aDiscard = pCard;
		lastMove = aPlayers[turn].toString() + " discards " + pCard.toString() + ".";
		notifyGameObserver();
	}

	@Override
	public void knock() {
		knocker = turn;
		lastMove = aPlayers[turn] + " knocks.\n" + aPlayers[turn].getMatchedSets().toString() + 
					"\nUnmatched cards:" + aPlayers[turn].getDeadwook();
		notifyGameObserver();
		turn = nextTurn();
		aPlayers[turn].addDeadwook(aPlayers[knocker].getMatchedSets());
	}

	@Override
	public void layout(Set<ICardSet> aSets) {
		String match = null;
		if (aPlayers[turn].getMatchedSets() != null){
			match = aPlayers[turn].getMatchedSets().toString();
		}
		lastMove = "New Layout\n" + aSets.toString() + "\n" + 
				aPlayers[turn].toString() + "'s matched set:\n" + match
				+ "\nand rest:" + aPlayers[turn].getDeadwook();
		notifyGameObserver();
		turn = nextTurn();
		if (aPlayers[turn].doneLayout()){
			lastMove += "\nFinal matched set\n" + aSets.toString();
			aMatchedSet = aSets;
			setPoints();
		}else{
			aPlayers[nextTurn()].addDeadwook(aSets);
		}
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
	public Player[] getPlayers() {
		return aPlayers;
	}
	
	public void distributeCards(){
		for (int i=0; i<Hand.HANDSIZE; i++){
			for (Player p: aPlayers){
				p.addCard(aDeck.draw());
			}
		}
		aDiscard = aDeck.draw();
	}
	
	public void playNextRound(){
		while (aPlayers[turn].isRobot()){
			if ( aDeck.size() < 2|| knocker != -1){
				endGame();
				return;
			}
			aPlayers[turn].pickCard();
			aPlayers[turn].discard();
			aPlayers[turn].knock();
			turn = nextTurn();
		}
	}
	
	private void setPoints(){
		int maxScore = Integer.MIN_VALUE;
		int knockerScore = aPlayers[knocker].getHandScore();
		for (Player p: aPlayers){
			System.out.println(p.toString() + " " + p.getHandScore());
			if (maxScore < p.getHandScore()){
				maxScore = p.getHandScore();
			}
		}
		for (int i = 0; i<aPlayers.length; i++){
			int temp;
			// check if it's knocker 
			if (i == knocker){
				temp = knockerScore;
				// check if go gin
				if (temp == 0){
					aPlayers[i].updateScore(POINTS.GOGIN.bonus);
				}
			}else{
				temp = aPlayers[i].getHandScore();
				// check undercut 
				if (temp < knockerScore){
					aPlayers[i].updateScore(POINTS.UNDERCUT.bonus);
				}
			}
			aPlayers[i].updateScore(maxScore - temp);
			// check more than 100 points
			if (aPlayers[i].getScore() > POINTS.ENDGAME.bonus){
				aPlayers[i].updateScore(POINTS.ENDGAME.bonus);
				winners.add(i);
				aPlayers[i].wonAGame();
			}
		}
	}
	
	private void endGame(){
		if (winners.size() == 1){
			boolean allWin = true;
			for (int i=0; i<aPlayers.length; i++){
				if (aPlayers[i].getScore() > 0 && !winners.contains(i)){
					allWin = false;
					break;
				}
			}
			if (allWin){
				aPlayers[winners.get(0)].updateScore(POINTS.ALLWIN.bonus 
						+ POINTS.LINEBONUS.bonus*aPlayers[winners.get(0)].getGameWon());
			}
		}
		lastMove = "END GAME\n";
		if (aDeck.size() < 2){
			lastMove += "No more cards in deck.\n";
			for (Player p: aPlayers){
				lastMove += p.toString() + " : " + p.handToString() + "\n";
			}
		}
		for (Player p: aPlayers){
			p.clear();
			lastMove += p.toString() + ": " + p.getTotalScore() + "\n";
		}  
		notifyGameObserver();
	}
	
	public void saveGame(){
		// TODO
	}
	
	public void loadGame(){
		// TODO
	}
}
