package ca.mcgill.cs.comp303.rummy.model;

import java.util.Stack;

/**
 * 
 * @author Yi Tian
 *
 */
public class GameEngine
{
	// states
	private Player[] aPlayers;
	private Deck aDeck;
	private Stack<Card> aDiscard;
	private int aTurn; 
	
	private GameObserver[] aLoggers;
	
	/**
	 * Constructor.
	 */
	public GameEngine()
	{
		aPlayers = new Player[2];
		aDeck = new Deck();
		aDiscard = new Stack<Card>();
		aTurn = -1;
	}
	
	/**
	 * Add a player to the game.
	 * @param pPlayer1
	 * @param pPlayer2
	 */
	public void setPlayers(Player pPlayer1, Player pPlayer2)
	{
		aPlayers[0] = pPlayer1;
		aPlayers[1] = pPlayer2;
	}
	
	/**
	 * Create a new game or reset the game.
	 * Distributes cards to players until both hands are complete.
	 * Start the discard pile.
	 */
	public void newGame()
	{
		aDeck.shuffle();
		for (Player p : aPlayers)
		{
			p.clearHand();
		}
		int drawTurn = aTurn;
		if (aTurn == -1)
		{
			drawTurn = 0;
		}
		while(!aPlayers[drawTurn].isComplete())
		{	
			aPlayers[drawTurn].draw(aDeck.draw());
			aPlayers[1-drawTurn].draw(aDeck.draw());
		}
		aDiscard.add(aDeck.draw());
		if (aTurn == -1) 
		{
			setTurn();
		}
	}
	
	/*
	 * Only called on the first hand.
	 */
	private void setTurn()
	{
		Card lowest = null;
		aTurn = 1;
		for (Card c: aPlayers[0].getHand())
		{
			if (lowest == null || lowest.compareTo(c) == 1)
			{
				lowest = c;
			}
		}
		for (Card c: aPlayers[1].getHand())
		{
			if (lowest.compareTo(c) == 1)
			{
				aTurn = 0;
				return;
			}
		}
	}
	
	/**
	 * Get the player for the next turn. 
	 * @return the player
	 */
	public Player getNextPlayer()
	{
		aTurn = 1-aTurn;
		return aPlayers[aTurn];
	}
	
	/**
	 * Save a game.
	 */
	public void save()
	{
		// TODO
	}
	
	/**
	 * Load a game.
	 */
	public void load()
	{
		// TODO
	}
	
	/*
	 * Notify loggers.
	 */
	private void log()
	{
		// TODO
	}
}
