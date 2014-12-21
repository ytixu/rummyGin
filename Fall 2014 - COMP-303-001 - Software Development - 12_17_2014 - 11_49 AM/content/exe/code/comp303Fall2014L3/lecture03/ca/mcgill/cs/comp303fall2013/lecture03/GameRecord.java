package ca.mcgill.cs.comp303fall2013.lecture03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Statistics about some game.
 */
public class GameRecord
{
	private int aGamesPlayed;
	private final Date  aLastGame;
	private ArrayList<Card> aBestHand = new ArrayList<>();
	
	/**
	 * Cards must be added one by one in case that's how they 
	 * get generated.
	 * @param pGamesPlayed
	 * @param pLastGame
	 */
	public GameRecord(int pGamesPlayed, Date pLastGame)
	{
		setGamesPlayed(pGamesPlayed);
		aLastGame = pLastGame;
	}
	
	public GameRecord(int pGamesPlayed, Date pLastGame, ArrayList<Card> pBestHand)
	{
		this(pGamesPlayed, pLastGame);
		aBestHand = pBestHand;
	}
	
	public void addToBestHand(Card card) 
	{
			if(aBestHand.size() < 5) // Let's say that what we want
		{
			aBestHand.add(card);
		}
	}
	
	public List<Card> getBestHand() { return Collections.unmodifiableList(aBestHand); }
	public Date getLastGameDate() { return (Date)aLastGame.clone(); }
	
	public static void main(String[] args)
	{
		ArrayList<Card> foo = new ArrayList<Card>();
		GameRecord r = new GameRecord(0, new Date(), foo);
//		foo.add(new Card(..GameRecord.))
		
		GameRecord record = new GameRecord(0, new Date());
		Deck deck = new Deck();
		record.getBestHand().add(deck.draw());
		record.addToBestHand(deck.draw());
		record.setGamesPlayed(297);
		record.getLastGameDate().setYear(2010);
	}

	public int getGamesPlayed()
	{
		return aGamesPlayed;
	}

	public void setGamesPlayed(int aGamesPlayed)
	{
		this.aGamesPlayed = aGamesPlayed;
	}
}
