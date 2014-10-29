package ca.mcgill.cs.comp303.rummy.model;

public interface GameViewer {
	enum PILE
	{ DECK, DISCARD }
	
	boolean isGameOver();
	
	Card topOfStack();
	
}