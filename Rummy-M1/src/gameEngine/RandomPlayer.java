package gameEngine;

import java.util.Random;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;

public class RandomPlayer extends AbstractPlayer {
	
	private Random rnd;

	public RandomPlayer(IGameEngineSetter gameSetter) {
		super("Psyduck", gameSetter);
		rnd = new Random();
	}

	@Override
	public void pickCard() {
		if (rnd.nextBoolean()){
			newCard = gameEngine.drawFromDeck();
		}else{
			newCard = gameEngine.peekDiscard();
		}
	}

	@Override
	public void discard() {
		assert (newCard != null);
		int index = (int) Math.floor(rnd.nextDouble()*aHand.HANDSIZE);
		Card toDiscard;
		do{
			toDiscard = aHand.iterator().next();
			index--;
		}while (index >= 0);
		aHand.remove(toDiscard);
		aHand.add(newCard);
		gameEngine.discard(toDiscard);
		newCard = null;
	}

	@Override
	public void knock() {
		if (aHand.canKnock() && rnd.nextBoolean()){
			gameEngine.knock();
		}
	}

	@Override
	public boolean isRobot() {
		return true;
	}
}
