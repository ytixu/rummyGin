package ca.mcgill.cs.comp303.rummy.model.gameModel;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Deck;
import ca.mcgill.cs.comp303.rummy.model.gamePlayers.Player;
import ca.mcgill.cs.comp303.rummy.model.gamePlayers.RandomPlayer;
import ca.mcgill.cs.comp303.rummy.model.gamePlayers.RobotPlayer;


/**
 * 
 * @author Yi Tian
 *
 */
public class GameEngine implements GameModelPlayer
{
	// states
	private Player[] aPlayers;
	private Deck aDeck;
	private Card aDiscard;
	private int aTurn; 
	
	private GameObserver[] aLoggers;
	
	/**
	 * Constructor.
	 */
	public GameEngine()
	{
		aPlayers = new Player[2];
		aDeck = new Deck();
		aTurn = -1;
		aDiscard = null;
		// TODO: change this
		aLoggers = null;
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
		aDiscard = aDeck.draw();
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
	
	public void endGameWithKnock()
	{
		// TODO
	}
	
	public boolean moreThanTwoCardsLeft()
	{
		if (aDeck.size() > 2)
		{
			return false;
		}
		return true;
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

	@Override
	public Card getDiscard() 
	{
		return aDiscard;
	}

	@Override
	public Object getTrun() 
	{
		// TODO Auto-generated method stub
		return aPlayers[aTurn].getName();
	}

	@Override
	public Object getScore() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPlayed() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card draw() 
	{
		return aDeck.draw();
	}

	@Override
	public void discard(Card pCard) 
	{
		aDiscard = pCard;
	}
	
	/**
	 * Simulation of a game. 
	 * @param args
	 */
	public static void main(String[] deadliestBattleOfCOMP303ClassEver)
	{
		GameEngine ge = new GameEngine();
		ge.setPlayers(new RandomPlayer(), new RobotPlayer());
		ge.newGame();
		while(ge.moreThanTwoCardsLeft())
		{
			if (ge.getNextPlayer().play(ge))
			{
				
			}
		}
	}
}
