package ca.mcgill.cs.comp303.rummy.model;

public interface GameObserver {
	
	void nextMove();
	void update(GameViewer pGame);
}