package ca.mcgill.cs.comp303.rummy.model;

import ca.mcgill.cs.comp303.rummy.model.GameViewer.PILE;

public class HumanPlayer extends Player {

	public HumanPlayer(GameViewer pGame) {
		super(pGame);
	}

	@Override
	Card discard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	PILE draw() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	boolean knock() {
		// TODO Auto-generated method stub
		return false;
	}
	
}