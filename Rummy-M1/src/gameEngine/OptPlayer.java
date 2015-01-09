package gameEngine;

import ca.mcgill.cs.comp303.rummy.model.Card;

public class OptPlayer extends AbstractPlayer {

	public OptPlayer(IGameEngineSetter gameSetter) {
		super("Wobbuffet", gameSetter);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * return the card to discard
	 */
	private Card maximize(Card toAdd, int minScore){
		Card optDiscard = null;
		Card preRemoved = null;
		for (Card c: aHand){
			aHand.remove(c);
			if (preRemoved == null){
				aHand.add(toAdd);
			}else{
				aHand.add(preRemoved);
			}
			preRemoved = c;
			aHand.autoMatch();
			int score = getHandScore();
			if (minScore > score){
				minScore = score;
				optDiscard = c;
			}
		}
		if (optDiscard != null){
			aHand.remove(optDiscard);
			aHand.add(preRemoved);
			return optDiscard;
		}
		aHand.remove(toAdd);
		aHand.add(preRemoved);
		return null;
	}
	
	@Override
	public void pickCard() {
		aHand.autoMatch();
		Card toDiscard = maximize(gameEngine.peekDiscard(), getHandScore());
		if (toDiscard != null){
			gameEngine.takeDiscard();
			newCard = toDiscard;
			return;
		}
		toDiscard = maximize(gameEngine.drawFromDeck(),Integer.MAX_VALUE);
		newCard = toDiscard;
	}

	@Override
	public void discard() {
		gameEngine.discard(newCard);
	}

	@Override
	public void knock() {
		if (getHandScore() < 5){
			gameEngine.knock();
		}
	}

	@Override
	public boolean isRobot() {
		return true;
	}

}
