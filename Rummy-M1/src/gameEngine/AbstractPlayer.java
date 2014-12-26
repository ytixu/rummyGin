package gameEngine;

import java.util.Iterator;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;

public abstract class AbstractPlayer implements Player{
	protected IGameEngineSetter gameEngine;
	protected Hand aHand;
	protected Card newCard;
	
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
		newCard = null;
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
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see gameEngine.Player#knock()
	 * Need to call gameEngine.knock() and handle bad knocking (score greater than 10)
	 */
	public abstract void knock();
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see gameEngine.Player#addDeadwook(java.util.Set)
	 * Need to call gameEngine.layout(sets);
	 */
	public abstract void addDeadwook(Set<ICardSet> pSets);

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
