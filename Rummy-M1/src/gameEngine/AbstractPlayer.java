package gameEngine;

import java.util.Iterator;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;

public abstract class AbstractPlayer implements Player{
	private final int MAXSCORE = 10;
	
	private IGameEngineSetter gameEngine;
	private Hand aHand;
	private String aName;
	private int aScore;
	private int accumulatedScore;
	private int gameWon;
	private boolean aHasLayout;
	
	public AbstractPlayer(String name, IGameEngineSetter gameSetter){
		gameEngine = gameSetter;
		aHand = new Hand();
		aName = name;
		aScore = 0;
		accumulatedScore = 0;
		gameWon = 0;
		aHasLayout = false;
	}
	
	@Override
	public void clear(){
		aHand.clear();
		accumulatedScore += aScore;
		aScore = 0;
		aHasLayout = false;
	}

	@Override
	public void addCard(Card pCard){
		aHand.add(pCard);
	}
	
	@Override
	public abstract void pickCard();

	@Override
	public abstract void discard();

	/**
	 * Strategy to knock.
	 */
	protected abstract void strategyKnock();
	
	/**
	 * If cannot knock, need to handle it.
	 */
	protected abstract void handleBadKnock();
	
	@Override
	public void knock(){
		strategyKnock();
		if (getHandScore() > MAXSCORE) handleBadKnock();
		gameEngine.knock();
	}
	
	/**
	 * Strategy to add deadwook to matched sets.
	 * @param pSets the current matched set
	 * @return the updated matched sets
	 */
	protected abstract Set<ICardSet> strategyAddDeadwook(Set<ICardSet> pSets);
	
	@Override
	public void addDeadwook(Set<ICardSet> pSets){
		 Set<ICardSet> sets = strategyAddDeadwook(pSets);
		 gameEngine.layout(sets);
	}

	@Override
	public Set<ICardSet> getMatchedSets() {
		return aHand.getMatchedSets();
	}

	@Override
	public Set<Card> getDeadwook() {
		return aHand.getUnmatchedCards();
	}

	@Override
	public Iterator<Card> iterator() {
		return aHand.iterator();
	}
	
	@Override 
	public void updateScore(int point){
		aScore += point;
	}
	
	@Override
	public int getScore(){
		return aScore;
	}
	
	@Override
	public int getTotalScore(){
		return accumulatedScore;
	}
	
	@Override
	public int getGameWon(){
		return gameWon;
	}
	
	@Override
	public void wonAGame(){
		gameWon++;
	}
	
	/**
	 * Return name.
	 */
	public String toString(){
		return aName;
	}
	
	@Override
	public int getHandScore(){
		return aHand.score();
	}
	
	@Override
	public boolean doneLayout(){
		return aHasLayout;
	}
}
