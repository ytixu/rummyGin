package ca.mcgill.cs.comp303.rummy.model.gameModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Deck;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;
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
	
	private Set<ICardSet> aKnocked;
	private Set<ICardSet> aKnockedLayout;
	
	private final int UNDERCUTPTS = 10;
	private final int GOGINPTS = 20;
	private final int ENDGAME = 100;
	private final int ENDGAMENOSCORE = 200;
	
	private ArrayList<GameObserver> aLoggers;
	
	/**
	 * Constructor.
	 */
	public GameEngine()
	{
		aPlayers = new Player[2];
		aDeck = new Deck();
		aTurn = -1;
		aDiscard = null;
		aLoggers = new ArrayList<GameObserver>();
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
	
	public void addObservers(GameObserver pObs)
	{
		aLoggers.add(pObs);
	}
	
	/**
	 * Create a new game or reset the game.
	 * Distributes cards to players until both hands are complete.
	 * Start the discard pile.
	 */
	public void newGame()
	{
		aDeck.shuffle();
		aKnocked = null;
		aKnockedLayout = null;
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
	private Player getNextPlayer()
	{
		aTurn = 1-aTurn;
		return aPlayers[aTurn];
	}
	
	private boolean isGoGin(Set<ICardSet> aSet)
	{
		int size = 0;
		for (ICardSet s : aSet)
		{
			size += s.size();
		}
		return size == Hand.HANDSIZE;
	}

	/**
	 * If a player knocked.
	 * Distribute scores.
	 */
	private void endGameWithKnock()
	{
		aKnocked = aPlayers[aTurn].getKnock();
		int standardScore = aPlayers[aTurn].getKnockingScore();
		boolean goGin = isGoGin(aKnocked);
		logPlayed();
		
		getNextPlayer().getKnock();
		aKnockedLayout = aPlayers[aTurn].layout(aKnocked);
		standardScore -= aPlayers[aTurn].getLayoutScore();
		logPlayed();
		if (standardScore > 0) // no undercut
		{
			if (goGin)
			{
				getNextPlayer().updateScore(GOGINPTS + standardScore);
			}
			else
			{
				getNextPlayer().updateScore(standardScore);
			}
		}
		else // undercut
		{
			aPlayers[aTurn].updateScore(UNDERCUTPTS - standardScore);
		}
		endGame();
		
	}
	
	/**
	 * Give bonus scores.
	 */
	private void endGame()
	{
		int score1 = aPlayers[aTurn].getScore();
		int score2 = getNextPlayer().getScore();
		if (Math.max(score1, score2) <= ENDGAME)
		{
			if (score1 > score2)
			{
				if (score2 > 0)
				{
					getNextPlayer().updateScore(ENDGAME);
				}
				else
				{
					getNextPlayer().updateScore(ENDGAMENOSCORE);
				}
			}
			else if (score2 > score2)
			{
				if (score1 > 0)
				{
					aPlayers[aTurn].updateScore(ENDGAME);
				}
				else
				{
					aPlayers[aTurn].updateScore(ENDGAMENOSCORE);
				}
			}
		}
		logEndGame();
	}
	
	private boolean moreThanTwoCardsLeft()
	{
		if (aDeck.size() > 2)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * @pre aPlayers.length != 0
	 */
	public void start()
	{
		boolean canPlay = true;
		while(canPlay)
		{
			if (getNextPlayer().play(this))
			{
				canPlay = false;
				endGameWithKnock();
			}
			if (!moreThanTwoCardsLeft())
			{
				canPlay = false;
				endGame();
			}
			logPlayed();
		}
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
	private void logPlayed()
	{
		for (GameObserver o : aLoggers)
		{
			o.logPlayed(this);
		}
	}
	
	private void logEndGame()
	{
		
		for (GameObserver o : aLoggers)
		{
			o.logEndGame(this);
		}
	}

	@Override
	public Card getDiscard() 
	{
		return aDiscard;
	}

	@Override
	public Object getTrun() 
	{
		return aPlayers[aTurn].getName();
	}

	@Override
	public Object getScore() 
	{
		HashMap<String, Integer> score = new HashMap<String, Integer>();
		score.put(aPlayers[0].getName(), aPlayers[0].getScore());
		score.put(aPlayers[1].getName(), aPlayers[1].getScore());
		return score;
	}

	@Override
	public Object getKnocked() 
	{
		return aKnocked; 
	}
	
	public Object getLayout()
	{
		return aKnockedLayout;
	}

	@Override
	public Card draw() 
	{
		return aDeck.draw();
	}

	@Override
	public void setDiscard(Card pCard) 
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
		ge.start();
	}
}
