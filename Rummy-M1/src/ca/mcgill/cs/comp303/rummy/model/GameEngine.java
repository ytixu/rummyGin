package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.Stack;

public class GameEngine implements GameViewer 
{
	
	private ArrayList<GameObserver> aObservers;
	private Deck aDeck;
	private Stack<Card> aDiscardPile;
	private ArrayList<Player> aPlayers;
	private boolean[] aPlayerTurn;
	private boolean aGameEnded;
	
	public GameEngine() 
	{
		aObservers = new ArrayList<GameObserver>();
		aPlayers = new ArrayList<Player>();
		aPlayerTurn = new boolean[2];
	}
	
	public void startGame() 
	{
		aGameEnded = false;
		aDeck = new Deck();
		aDiscardPile = new Stack<Card>();
		firstDeal();
		while(!isGameOver()) 
		{
			play(aPlayers.get(2));
			play(aPlayers.get(1));
		}
	}
	
	public void firstDeal() 
	{
		// Initial dealing of cards
		for(int i = 0; i < 10; i++)
		{
			aPlayers.get(0).getHand().add(aDeck.draw());
			aPlayers.get(1).getHand().add(aDeck.draw());
		}
		
		// Find dealer
		if(getDealer(aPlayers.get(0), aPlayers.get(1)).equals(aPlayers.get(0)))
		{
			aPlayerTurn[0] = false;
			aPlayerTurn[1] = true;
		}
		else
		{
			aPlayerTurn[0] = true;
			aPlayerTurn[1] = false;
		}
		
		aDiscardPile.push(aDeck.draw());
		notifyObservers();
	}
	
	public void play(Player pPlayer) 
	{

		// Not a player from the game
		if(!aPlayers.contains(pPlayer))
		{
			return;
		}
		
		// Not player's turn to play
		int playerIndex = aPlayers.indexOf(pPlayer);
		if(!aPlayerTurn[playerIndex])
		{
			return;
		}
		
		// Draw card
		drawCard(pPlayer);
		
		// Discard card
		discardCard(pPlayer);
		
		// Knock
		if(pPlayer.knock()) 
		{
			endGame();
		}
		notifyObservers();
		
		aPlayerTurn[playerIndex] = false;
		aPlayerTurn[(playerIndex + 1) % 2] = true;
	}
	
	private void drawCard(Player pPlayer) 
	{
		PILE drawingPile = pPlayer.draw();
		if(drawingPile.equals(PILE.DECK))
		{
			Card toDraw = aDeck.draw();
			pPlayer.getHand().add(toDraw);
		}
		else
		{
			assert !aDiscardPile.isEmpty();
			Card toDraw = aDiscardPile.pop();
			pPlayer.getHand().add(toDraw);
		}
		notifyObservers();
	}
	
	private void discardCard(Player pPlayer) 
	{
		Card toDiscard = pPlayer.discard();
		aDiscardPile.push(toDiscard);
		notifyObservers();
	}
	
	public void endGame() 
	{
		aGameEnded = true;
	}
	
	public boolean isGameOver() 
	{
		return aGameEnded;
	}
	
	public Card topOfStack() 
	{
		return aDiscardPile.peek();
	}
	
	public Player getPlayer1() 
	{
		return aPlayers.get(0);
	}
	
	public Player getPlayer2() 
	{
		return aPlayers.get(1);
	}
	
	public void addPlayer(Player pP1)
	{
		aPlayers.add(pP1);
	}
	
	public void addObservers(GameObserver pObserver) 
	{
		aObservers.add(pObserver);
	}
	
	private void notifyObservers() 
	{
		
		for(GameObserver o : aObservers) 
		{
			o.update(this);
		}
	}
	
	private Player getDealer(Player pP1, Player pP2) 
	{
		if(pP1.getLowestCard().compareTo(pP2.getLowestCard()) < 0)
		{
			return pP1;
		}
		else
		{
			return pP2;
		}
	}
}