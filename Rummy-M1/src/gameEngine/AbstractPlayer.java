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
	
	public abstract void pickCard();

	public abstract void discard();

	public abstract void knock();
	
	public abstract Set<ICardSet> addDeadwook(Set<ICardSet> pSets);

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
	
	public String toString(){
		return aName;
	}
	
	@Override
	public int getHandScore(){
		return aHand.score();
	}
	
	public boolean doneLayout(){
		return aHasLayout;
	}
}
