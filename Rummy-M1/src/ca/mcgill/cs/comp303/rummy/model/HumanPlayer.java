package ca.mcgill.cs.comp303.rummy.model;

import ca.mcgill.cs.comp303.rummy.model.GameViewer.PILE;

public class HumanPlayer extends Player {

	public HumanPlayer(GameViewer pGame) {
		super(pGame);
	}

	@Override
	Card discard() {
		// TODO Auto-generated method stub
		Card toRemove = aHand.getUnmatchedCards().iterator().next();
		aHand.remove(toRemove);
		return toRemove;
//		return null;
	}

	@Override
	PILE draw() {
		// TODO Auto-generated method stub
		return PILE.DECK;
	}

	@Override
	boolean knock() {
		// TODO Auto-generated method stub
		return false;
	}
	
}