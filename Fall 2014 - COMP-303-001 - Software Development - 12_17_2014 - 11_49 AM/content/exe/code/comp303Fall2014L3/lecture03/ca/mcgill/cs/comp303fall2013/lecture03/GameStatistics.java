package ca.mcgill.cs.comp303fall2013.lecture03;

import java.util.ArrayList;

public class GameStatistics
{
	private ArrayList<GameRecord> aRecords = new ArrayList<GameRecord>();
	
	public void add(GameRecord pRecord)
	{
		aRecords.add(pRecord);
	}
	
	public ArrayList<GameRecord> getRecords()
	{
		return aRecords;
	}
	
	public void reset(GameRecord pRecord)
	{
		pRecord.setGamesPlayed(0);
	}
}
