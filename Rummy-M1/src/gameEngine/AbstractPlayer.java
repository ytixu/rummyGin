package gameEngine;

import java.util.Iterator;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;

public abstract class AbstractPlayer implements Player{
	
	private IGameEngineSetter gameEngine;
	private Hand aHand;
	private String aName;
	private int aScore;
	private boolean aHasLayout;
	
	public AbstractPlayer(String name, IGameEngineSetter gameSetter){
		gameEngine = gameSetter;
		aHand = new Hand();
		aName = name;
		aScore = 0;
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
	public abstract void knock();
	
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
