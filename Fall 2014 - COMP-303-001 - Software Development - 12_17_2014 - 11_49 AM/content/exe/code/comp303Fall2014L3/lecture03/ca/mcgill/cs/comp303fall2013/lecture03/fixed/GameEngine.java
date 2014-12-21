package ca.mcgill.cs.comp303fall2013.lecture03.fixed;

import java.util.Calendar;
import java.util.Date;

import ca.mcgill.cs.comp303fall2013.lecture03.Card;
import ca.mcgill.cs.comp303fall2013.lecture03.Deck;


public class GameEngine
{
	public static void main(String[] args)
	{
		GameStatistics stats = generate();
		
		for( GameRecord record : stats.getRecords() )
		{
			System.out.println(record);
		}
		
		numberOfGamesPlayed(stats);
		GameRecord oldest = removeOldestRecord(stats);
		setToToday(oldest);
	}

	private static void setToToday(GameRecord oldest)
	{
		// change the date to today
		Date today = new Date();
		oldest.getDay().setDate(today.getDate());
		oldest.getDay().setMonth(today.getMonth());
		oldest.getDay().setYear(today.getYear());
	}

	private static GameRecord removeOldestRecord(GameStatistics stats)
	{
		// Remove the oldest record
		GameRecord oldest = stats.getRecords().get(0);
		for( GameRecord record : stats.getRecords() )
		{
			if( record.getDay().compareTo(oldest.getDay()) < 0)
			{
				oldest = record;
			}
		}
		stats.getRecords().remove(oldest);
		return oldest;
	}

	private static void numberOfGamesPlayed(GameStatistics stats)
	{
		// Obtain the total number of games played
		int total = 0;
		for( GameRecord record : stats.getRecords() )
		{
			total += record.getGamesPlayed();
		}
		System.out.println(total + " games played");
	}
	
	public static GameStatistics generate()
	{
		Deck deck = new Deck();
		GameStatistics lReturn = new GameStatistics();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 1, 20);
		GameRecord record = new GameRecord(10, calendar.getTime(), fiveCards());
		lReturn.add(record);
		
		deck.shuffle();
		calendar.set(2013, 0, 1);
		record = new GameRecord(5, calendar.getTime(), fiveCards());
		lReturn.add(record);
		
		deck.shuffle();
		calendar.set(2013, 5, 5);
		record = new GameRecord(25, calendar.getTime(), fiveCards());
		lReturn.add(record);
		
		deck.shuffle();
		calendar.set(2013, 4, 4);
		record = new GameRecord(6, calendar.getTime(), fiveCards());
		lReturn.add(record);
		
		return lReturn;
	}
	
	public static Card[] fiveCards()
	{
		Deck deck = new Deck();
		deck.shuffle();
		Card[] lReturn = new Card[5];
		for( int i = 0; i < lReturn.length; i++ )
		{
			lReturn[i] = deck.draw();
		}
		return lReturn;
	}
}
