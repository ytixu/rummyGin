package ca.mcgill.cs.comp303fall2013.lecture03.fixed;

import java.util.ArrayList;
import java.util.Date;

import ca.mcgill.cs.comp303fall2013.lecture03.Card;

/**
 * Statistics about the play on a given day.
 */
public class GameRecord implements Cloneable
{
	private int aGamesPlayed;
	private Date aDay;
	private ArrayList<Card> aCards = new ArrayList<Card>();
	
	/**
	 * Cards must be added one by one in case that's how they 
	 * get generated.
	 * @param pGamesPlayed
	 * @param pDay
	 */
	public GameRecord(int pGamesPlayed, Date pDay, Card[] pCards)
	{
		aGamesPlayed = pGamesPlayed;
		aDay = pDay;
		for(Card card : pCards)
		{
			aCards.add(card);
		}
	}
	
	public GameRecord clone()
	{
		try
		{
			GameRecord gr = (GameRecord) super.clone();
			gr.aDay = (Date) aDay.clone();
			gr.aCards = (ArrayList<Card>)aCards.clone();
			return gr;
		}
		catch (CloneNotSupportedException e)
		{
return null;
}
	}
	
	public ArrayList<Card> getCards()
	{
		return aCards;
	}
	
	public Date getDay()
	{
		return aDay;
	}
	
	public String toString()
	{
		String lReturn = String.format("%td %tb %tY %4d ", aDay, aDay, aDay, getGamesPlayed());
		for(Card card : aCards)
		{
			lReturn += card + " ";
		}
		return lReturn;
	}

	public int getGamesPlayed()
	{
		return aGamesPlayed;
	}

	
//	public static void main(String[] args)
//	{
//		GameRecord record = new GameRecord(10, new Date(), GameEngine.fiveCards());
//		System.out.println(record);
//		ArrayList<Card> cards = record.getCards();
//		cards.add(new Card(ca.mcgill.cs.comp303fall2013.lecture03.Card.Rank.ACE, ca.mcgill.cs.comp303fall2013.lecture03.Card.Suit.CLUBS));
//		System.out.println(record);
//	}
}
